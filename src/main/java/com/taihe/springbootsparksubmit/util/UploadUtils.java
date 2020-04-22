package com.taihe.springbootsparksubmit.util;

import com.taihe.springbootsparksubmit.constant.RedisKeyConstants;
import com.taihe.springbootsparksubmit.entity.AnalysisDatabase;
import com.taihe.springbootsparksubmit.entity.AnalysisSchema;
import com.taihe.springbootsparksubmit.entity.AnalysisTable;
import com.taihe.springbootsparksubmit.result.Result;
import com.taihe.springbootsparksubmit.service.AnalysisDatabaseService;
import com.taihe.springbootsparksubmit.service.AnalysisSchemaService;
import com.taihe.springbootsparksubmit.service.AnalysisTableService;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.hadoop.fs.Path;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.*;
import java.util.Date;

/**
 * @Author : Grayson
 * @Email : sunhui@yunliketech.com
 * @Date ：2020/4/16
 */
@CommonsLog
@Configuration
public class UploadUtils {
    private static final String DATABASE_NAME = "upload_file";


    @Value("${fileUplodPath}")
    private String UPLOAD_FILE_PATH;
    @Value("${hdfsFilePath}")
    private String HDFS_FILE_PATH;

    @Autowired
    private AnalysisDatabaseService analysisDatabaseService;
    @Autowired
    private AnalysisTableService analysisTableService;
    @Autowired
    private AnalysisSchemaService analysisSchemaService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;


    public static UploadUtils uploadUtils;

    @PostConstruct
    public void init() {
        uploadUtils = this;
        uploadUtils.analysisDatabaseService = this.analysisDatabaseService;
        uploadUtils.analysisTableService = this.analysisTableService;
        uploadUtils.analysisSchemaService = this.analysisSchemaService;
    }

    public Result<T> uploadFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return Result.error("上传失败，文件体为空");
        }
        String fileName = file.getOriginalFilename();

        int size = (int) file.getSize();
        log.info("文件名为：" + fileName + ",文件大小为：" + size);
        File uploadFile = new File(UPLOAD_FILE_PATH + fileName);
        String fileType = uploadFile.getName().substring(uploadFile.getName().lastIndexOf("."));
        file.transferTo(uploadFile);
        //1.读取文件
        FileInputStream in = new FileInputStream(uploadFile);
        short lastCellNum = 0;
        //如果是CSV文件
        if (".csv".equals(fileType)) {
            BufferedReader reader = new BufferedReader(new FileReader(uploadFile));
            reader.readLine();
            String line = reader.readLine();
            String item[] = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分
            lastCellNum = (short) item.length;
        }
        //2.通过poi解析流HSSFWorkbook处理流得到的对象，就是封装的excel文件所有的数据
        else if (".xls".equals(fileType)) {
            HSSFWorkbook workBook = new HSSFWorkbook(in);
            //3.获取excel文件里面的工作表sheet
            HSSFSheet sheet = workBook.getSheetAt(0);
            lastCellNum = sheet.getRow(0).getLastCellNum();
        } else if (".xlsx".equals(fileType)) {
            XSSFWorkbook workBook = new XSSFWorkbook(in);
            //3.获取excel文件里面的工作表sheet
            XSSFSheet sheet = workBook.getSheetAt(0);
            lastCellNum = sheet.getRow(0).getLastCellNum();
        } else if (".txt".equals(fileType)) {
            log.info(getJavaEncode(uploadFile.getPath()));
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(uploadFile), getJavaEncode(uploadFile.getPath()));
            BufferedReader br = new BufferedReader(inputStreamReader);
            String content = br.readLine();
            br.close();
            inputStreamReader.close();
            if (StringUtils.isEmpty(content)) {
                return Result.error("文件内容为空");
            } else {
                lastCellNum = (short) content.split(",").length;
            }
        }
        long nowDate = new Date().getTime();
        //根据导入的文件先生成表名
        String tableName = fileType.contains(".txt") ? fileType.replace(".txt", "csv") + nowDate : fileType.replace(".", "") + nowDate;
        //发送到HDFS
        Path fileUploadPath = new Path(uploadFile.getPath());
        String path = HDFS_FILE_PATH + "/" + DATABASE_NAME + "/" + tableName;
        Path hdfsFilePath = new Path(path);
        new HdfsUtils().uploadFileToHdfs(fileUploadPath, hdfsFilePath);
        in.close();
        uploadFile.delete();
        //插入成功后获取id缓存到redis
        if (!hasKey(RedisKeyConstants.getDatabaseKey(DATABASE_NAME))) {
            AnalysisDatabase analysisDatabase = new AnalysisDatabase();
            //第一次插入先插入库表 获取到id
            analysisDatabase.setDatabaseName(DATABASE_NAME);
            this.analysisDatabaseService.insert(analysisDatabase);
            stringRedisTemplate.opsForValue().set(RedisKeyConstants.getDatabaseKey(DATABASE_NAME), analysisDatabase.getId().toString());
        }
        String databaseId = stringRedisTemplate.opsForValue().get(RedisKeyConstants.getDatabaseKey(DATABASE_NAME));
        //根据对应的字段长度 生成字段名称
        AnalysisTable analysisTable = new AnalysisTable();
        analysisTable.setTableName(tableName);
        assert databaseId != null;
        analysisTable.setDatabaseid(Integer.parseInt(databaseId));
        analysisTable.setTableDescribe(fileName);
        this.analysisTableService.insert(analysisTable);
        //表字段随机生成
        for (int i = 0; i < lastCellNum; i++) {
            AnalysisSchema analysisSchema = new AnalysisSchema();
            analysisSchema.setFieldName("field_" + i);
            analysisSchema.setFieldType("String");
            analysisSchema.setTableId(analysisTable.getId().toString());
            this.analysisSchemaService.insert(analysisSchema);
        }
        return Result.ok();
    }

    /**
     * 获取文件格式
     *
     * @param filePath
     * @return
     */
    public static String getJavaEncode(String filePath) {
        EncodingDetect.BytesEncodingDetect s = new EncodingDetect.BytesEncodingDetect();
        return EncodingDetect.BytesEncodingDetect.javaname[s.detectEncoding(new File(filePath))];
    }


    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public boolean hasKey(String key) {
        Assert.notNull(key, "non null key required");
        try {
            return StringUtils.isEmpty(key) ? false : stringRedisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

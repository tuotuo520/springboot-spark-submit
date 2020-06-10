package com.taihe.springbootsparksubmit.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.clearspring.analytics.util.Lists;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taihe.springbootsparksubmit.dao.ExcuteRecordDao;
import com.taihe.springbootsparksubmit.entity.ExcuteRecord;
import com.taihe.springbootsparksubmit.result.Result;
import com.taihe.springbootsparksubmit.service.ExcuteRecordService;
import com.taihe.springbootsparksubmit.starter.SparkStarter;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

/**
 * (ExcuteRecord)表服务实现类
 *
 * @author Grayson
 * @since 2020-04-17 17:44:07
 */
@CommonsLog
@Service("excuteRecordService")
public class ExcuteRecordServiceImpl implements ExcuteRecordService {
    private final static String COUNT = "count";
    private final static String SUM = "sum";
    private final static String MAX = "max";
    private final static String MIN = "min";
    private final static String AVG = "avg";

    private final static Set<String> schemaSet = new HashSet<>(Arrays.asList(COUNT, SUM, MAX, MIN, AVG));
    @Resource
    private ExcuteRecordDao excuteRecordDao;
    @Resource
    private SparkStarter sparkStarter;


    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ExcuteRecord queryById(Integer id) {
        return this.excuteRecordDao.queryById(id);
    }


    /**
     * 新增数据
     *
     * @param excuteRecord 实例对象
     * @return 实例对象
     */
    @Override
    public ExcuteRecord insert(ExcuteRecord excuteRecord) {
        this.excuteRecordDao.insert(excuteRecord);
        return excuteRecord;
    }

    /**
     * 修改数据
     *
     * @param excuteRecord 实例对象
     * @return 实例对象
     */
    @Override
    public ExcuteRecord update(ExcuteRecord excuteRecord) {
        this.excuteRecordDao.update(excuteRecord);
        return this.queryById(excuteRecord.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.excuteRecordDao.deleteById(id) > 0;
    }


    /**
     * 保存执行记录
     *
     * @param excuteRecord
     * @return
     */
    @Override
    public Result<ExcuteRecord> saveExecuteRecord(ExcuteRecord excuteRecord) {
        excuteRecord.setIsComplete(0);
        excuteRecord.setCreateTime(new Date());
        this.excuteRecordDao.insert(excuteRecord);
        return Result.ok(excuteRecord);
    }

    /**
     * 执行记录
     *
     * @param excuteRecord
     * @return
     */
    @Override
    public Result<ExcuteRecord> executeRecord(ExcuteRecord excuteRecord) {
        //先保存到执行记录表
        excuteRecord.setIsComplete(0);
        excuteRecord.setCreateTime(new Date());
        this.excuteRecordDao.insert(excuteRecord);
        try {
            sparkStarter.submitSqlTask(excuteRecord.getId().toString());
        } catch (IOException e) {
            Result.error("任务提交失败" + e);
        }
        return Result.ok(excuteRecord);
    }


    /**
     * 根据表id查询结果
     *
     * @param id
     * @return
     */
    @Override
    public Result<JSONObject> queryByTableId(ExcuteRecord id) {
        List<ExcuteRecord> list = this.excuteRecordDao.queryByBelongTo(id);
        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(list));
        JSONObject resData = jsonArray.getJSONObject(0);
        JSONObject conditionContent = JSONObject.parseObject(resData.getString("conditionContent"));
        JSONObject relationInfo =  JSONObject.parseObject(conditionContent.getString("relationInfo"));
        JSONArray  groupByArr = relationInfo.getJSONArray("groupBy");
        List<String> compareList = Lists.newArrayList();
        for (int i = 0; i < groupByArr.size() ; i++) {
            if(groupByArr.getString(i).contains("#")){
                compareList.add(groupByArr.getString(i).substring(0,groupByArr.getString(i).indexOf("#")).replace(".","_"));
            }else{
                compareList.add(groupByArr.getString(i).replace(".","_"));
            }

        }
        JSONObject object = new JSONObject();
        //先查询生成的表名里有哪些字段
        //先生产表名
        String tableName = "searchresult_" + id.getId();
        //根据表名查询字段
        List<String> schemaList = this.excuteRecordDao.querySchemaByTable(tableName);

        if (schemaList.size() > 0) {
            JSONArray resultArray = JSONArray.parseArray(JSON.toJSONString(this.excuteRecordDao.queryByTableId(id)));

            JSONArray ja = new JSONArray();
            if (resultArray.size() > 0) {
                for (int i = 0; i < resultArray.size(); i++) {
                    JSONObject jo = new JSONObject();
                    JSONObject job = resultArray.getJSONObject(i);
                    StringBuffer nameSb = new StringBuffer();
                    for (String schema : schemaList) {
                        if (compareList.contains(schema)) {
                            nameSb.append(job.getString(schema)).append(" ");
                            jo.put("name", nameSb.toString());
                        } else {
                            jo.put(schema, job.getString(schema));
                        }
                    }
                    nameSb.setLength(0);
                    ja.add(jo);
                }
            }
            object.put("list", ja);
        } else {
            object.put("list", this.excuteRecordDao.queryByTableId(id));
        }
        if (list.size() > 0) {
            object.put("record", list.get(0));
        }
        return Result.ok(object);
    }

    /**
     * 查询结果记录中有多少belongTo21l'p'l'g'y
     *
     * @return
     */
    @Override
    public Result<List<String>> queryAllBelongTo() {
        return Result.ok(this.excuteRecordDao.queryAllBelongTo());
    }

    /**
     * 根据belongTo查询记录
     *
     * @return
     */
    @Override
    public Result<PageInfo<ExcuteRecord>> queryByBelongTo(ExcuteRecord excuteRecord) {
        PageHelper.startPage(excuteRecord.getPage(), excuteRecord.getLimit());
        PageInfo<ExcuteRecord> returnPage = new PageInfo<>(this.excuteRecordDao.queryByBelongTo(excuteRecord));
        return Result.ok(returnPage);
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        String a = "\\x00\\x00\\x00\\x00\\x00\\x00\\x00\\x0A\n";
        System.out.println(URLDecoder.decode(a.replaceAll("\\\\x", "%"), "UTF-8"));
    }
}
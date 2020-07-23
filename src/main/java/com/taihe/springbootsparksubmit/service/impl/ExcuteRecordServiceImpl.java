package com.taihe.springbootsparksubmit.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.clearspring.analytics.util.Lists;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.taihe.springbootsparksubmit.constant.RedisKeyConstants;
import com.taihe.springbootsparksubmit.dao.*;
import com.taihe.springbootsparksubmit.entity.*;
import com.taihe.springbootsparksubmit.result.Result;
import com.taihe.springbootsparksubmit.service.ExcuteRecordService;
import com.taihe.springbootsparksubmit.starter.SparkStarter;
import com.taihe.springbootsparksubmit.util.RedisUtil;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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

    private final static Set<String> SCHEMA_SET = new HashSet<>(Arrays.asList(COUNT, SUM, MAX, MIN, AVG));
    @Resource
    private ExcuteRecordDao excuteRecordDao;
    @Resource
    private AnalysisDatabaseDao analysisDatabaseDao;
    @Resource
    private AnalysisTableDao analysisTableDao;
    @Resource
    private AnalysisSchemaDao analysisSchemaDao;
    @Resource
    private AnalysisEnumDao analysisEnumDao;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private SparkStarter sparkStarter;
    @Resource
    private RedisUtil redisUtil;


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
        Map<String, Integer> idMap = Maps.newHashMap();
        JSONObject jb = JSONObject.parseObject(excuteRecord.getConditionContent());
        JSONArray ja = jb.getJSONArray("sourceInfo");
        JSONArray schemaJa = jb.getJSONArray("resultInfo");
        JSONObject relationInfo = JSONObject.parseObject(jb.getString("relationInfo"));
        JSONArray groupByArr = relationInfo.getJSONArray("groupBy");
        for (int i = 0; i < ja.size(); i++) {
            idMap.put(ja.getJSONObject(i).getString("tableName"), ja.getJSONObject(i).getInteger("databaseId"));
        }
        //0代表非二次分析，1代表二次分析 二次分析的立即执行
        if (excuteRecord.getSecondaryTag() == 1 && excuteRecord.getExecute() != 1) {
            AnalysisDatabase analysisDatabase = new AnalysisDatabase();
            if (!redisUtil.hasKey(RedisKeyConstants.getAnalysisKey("secondary_analysis"))) {
                analysisDatabase.setDatabaseName("secondary_analysis");
                analysisDatabaseDao.insert(analysisDatabase);
                stringRedisTemplate.opsForValue().set(RedisKeyConstants.getAnalysisKey("secondary_analysis"), analysisDatabase.getId().toString());
            } else {
                analysisDatabase.setId(Integer.parseInt(Objects.requireNonNull(stringRedisTemplate.opsForValue().get(RedisKeyConstants.getAnalysisKey("secondary_analysis")))));
            }
            //存table
            AnalysisTable analysisTable = new AnalysisTable();
            analysisTable.setTableName(excuteRecord.getSecondaryTableName());
            analysisTable.setSecondaryTag(excuteRecord.getSecondaryTag());
            analysisTable.setDatabaseid(analysisDatabase.getId());
            AnalysisTable rAnalysisTable = analysisTableDao.queryTableInfoByTableName(analysisTable);
            if (Objects.isNull(rAnalysisTable)) {
                try {
                    analysisTableDao.insert(analysisTable);
                    analysisTable = analysisTableDao.queryTableInfoByTableName(analysisTable);
                } catch (Exception e) {
                    return Result.error("任务提交失败,原因:二次分析保存表失败," + e);
                }
            }else{
                analysisTable.setId(rAnalysisTable.getId());
            }
            if(CollectionUtils.isEmpty(schemaJa)){
                //r如果是空的则需要去除sourceinfo里的数据库name和databaseid拼接字段
                JSONObject fieldJb = JSONObject.parseObject(excuteRecord.getRestoreData());
                JSONObject fieldJo = fieldJb.getJSONObject("tableFieldObj");

                JSONArray tableNameJa = fieldJb.getJSONArray("tableNameList");
                Map<String,String> tableNameMap = Maps.newHashMap();
                for (int i = 0; i < tableNameJa.size(); i++) {
                    tableNameMap.put(tableNameJa.getJSONObject(i).getString("id"),tableNameJa.getJSONObject(i).getString("tableName"));
                }
                if(CollectionUtils.isEmpty(fieldJo)){
                    return Result.error("字段为空无法生成表!");
                }else{
                    Map<String,Object> itemMap = Maps.newHashMap();
                    itemMap.putAll(JSONObject.toJavaObject(fieldJo, Map.class));
                    Iterator entries = itemMap.entrySet().iterator();
                    while(entries.hasNext()){
                        Map.Entry entry = (Map.Entry) entries.next();
                        JSONArray itemJa = JSON.parseArray(entry.getValue().toString());
                        for (int i = 0; i < itemJa.size(); i++) {
                            AnalysisSchema analysisSchema = new AnalysisSchema();
                            analysisSchema.setFieldType(itemJa.getJSONObject(i).getString("fieldType"));
                            analysisSchema.setTableId(analysisTable.getId().toString());
                            String fieldName = tableNameMap.get(itemJa.getJSONObject(i).getString("tableId"))+"."+itemJa.getJSONObject(i).getString("fieldName");
                            analysisSchema.setFieldName(fieldName.replace(".","_"));
                            analysisSchema.setFileldDescribe(getEnum(fieldName,idMap));
                            analysisSchemaDao.insert(analysisSchema);
                        }
                    }
                }

            }else{
                for (int i = 0; i < schemaJa.size(); i++) {
                    AnalysisSchema analysisSchema = new AnalysisSchema();
                    analysisSchema.setFieldType(getSchemaType(schemaJa.getString(i), idMap));
                    analysisSchema.setTableId(analysisTable.getId().toString());
                    analysisSchema.setFieldName(returnSchema(schemaJa.getString(i)));
                    analysisSchema.setFileldDescribe(getEnum(schemaJa.getString(i), idMap));
                    analysisSchemaDao.insert(analysisSchema);
                }
                for (int i = 0; i < groupByArr.size(); i++) {
                    AnalysisSchema analysisSchema = new AnalysisSchema();
                    analysisSchema.setFieldType(getSchemaType(groupByArr.getString(i), idMap));
                    analysisSchema.setTableId(analysisTable.getId().toString());
                    analysisSchema.setFieldName(returnSchema(groupByArr.getString(i)));
                    analysisSchema.setFileldDescribe(getEnum(groupByArr.getString(i), idMap));
                    analysisSchemaDao.insert(analysisSchema);
                }
            }

            excuteRecord.setIsComplete(0);
            excuteRecord.setCreateTime(new Date());
            excuteRecord.setSecondaryTableId(analysisTable.getId());
            excuteRecord.setSecondaryDatabaseId(analysisTable.getDatabaseid());
            this.excuteRecordDao.insert(excuteRecord);
            if(excuteRecord.getExecute() == 0){
                try {
                    sparkStarter.submitSqlTask(excuteRecord.getId().toString());
                } catch (IOException e) {
                    Result.error("任务提交失败" + e);
                }
            }
            return Result.ok(excuteRecord);
            //非二次分析直接保存传id给侯勇
        } else if (excuteRecord.getSecondaryTag() == 1 && excuteRecord.getExecute() == 1) {
            excuteRecord.setIsComplete(0);
            excuteRecord.setCreateTime(new Date());
            Iterator entries = idMap.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry entry = (Map.Entry) entries.next();
                excuteRecord.setSecondaryTableName(entry.getKey().toString());
            }
            if (StringUtils.isEmpty(excuteRecord.getSecondaryTableName())) {
                return Result.error("ExecuteRecord fail");
            }
            //除了上述信息我还需要提供给侯勇几个状态
            //是否二次分析，执行状态 二次分析的执行，前端都传了，还需要查询前一次的执行条件，直接根据json中传入的表名查询前一次的结果，前一次肯定标识了是否为二次分析所以只用在二次分析的里面查询
            ExcuteRecord erVo = excuteRecordDao.queryFirstIdByNameAndTag(excuteRecord);
            excuteRecord.setFirstId(erVo.getId());
            excuteRecord.setBelongTo(erVo.getBelongTo());
            excuteRecord.setSecondaryTableName("");
            this.excuteRecordDao.insert(excuteRecord);
        }else{
            excuteRecord.setIsComplete(0);
            excuteRecord.setCreateTime(new Date());
            this.excuteRecordDao.insert(excuteRecord);
            //二次分析的执行
        }
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
        JSONObject relationInfo = JSONObject.parseObject(conditionContent.getString("relationInfo"));
        JSONArray groupByArr = relationInfo.getJSONArray("groupBy");
        List<String> compareList = Lists.newArrayList();
        for (int i = 0; i < groupByArr.size(); i++) {
            if (groupByArr.getString(i).contains("#")) {
                compareList.add(groupByArr.getString(i).substring(0, groupByArr.getString(i).indexOf("#")).replace(".", "_"));
            } else {
                compareList.add(groupByArr.getString(i).replace(".", "_"));
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
                    StringBuilder nameSb = new StringBuilder();
                    for (String schema : schemaList) {
                        if (compareList.contains(schema)) {
                            nameSb.append(job.getString(schema)).append(" ");
                            jo.put("name", nameSb.toString());
                        } else {
                            if(schemaList.size() > 2){
                                jo.put(schema, job.getString(schema));
                            }else{
                                jo.put("value", job.getString(schema));
                            }

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
    public Result<List<String>> queryAllBelongTo(ExcuteRecord excuteRecord) {
        return Result.ok(this.excuteRecordDao.queryAllBelongTo(excuteRecord));
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

    /**
     * 查询表明是否存在
     *
     * @param excuteRecord
     * @return
     */
    @Override
    public Result<Boolean> isTmpTableNameExist(ExcuteRecord excuteRecord) {
        AnalysisTable analysisTable = new AnalysisTable();
        analysisTable.setTableName(excuteRecord.getSecondaryTableName());
        return Result.ok(analysisTableDao.isTmpTableNameExist(analysisTable) > 0);
    }

    /**
     * 根据字段查出该字段数据类型
     *
     * @param schema
     * @return
     */
    private String getSchemaType(String schema, Map<String, Integer> idMap) {
        String[] arg;
        if (schema.contains("as")) {
            arg = schema.substring(schema.indexOf("(") + 1, schema.indexOf(")")).trim().split("\\.");
            //先根据数据库找到数据id
        } else {
            arg = schema.split("\\.");
        }
        AnalysisTable analysisTable = new AnalysisTable();
        analysisTable.setDatabaseid(idMap.get(!arg[0].contains("DISTINCT") ? arg[0].trim() : arg[0].replace("DISTINCT", "").trim()));
        analysisTable.setTableName(!arg[0].contains("DISTINCT") ? arg[0].trim() : arg[0].replace("DISTINCT", "").trim());
        //查询原表所以为一次查询表 //0代表非二次分析，1代表二次分析的首次分析，2代表二次分析的再次分析
        analysisTable.setSecondaryTag(0);
        analysisTable = analysisTableDao.queryTableInfoByTableName(analysisTable);
        AnalysisSchema analysisSchema = new AnalysisSchema();
        analysisSchema.setSecondaryTag(0);
        analysisSchema.setTableId(analysisTable.getId().toString());
        analysisSchema.setFieldName(arg[1].trim());
        analysisSchema = analysisSchemaDao.querySchemaInfoByTableName(analysisSchema);
        return analysisSchema.getFieldType();
    }

    /**
     * 返回新的表名
     *
     * @param schema
     * @return
     */
    private String returnSchema(String schema) {
        log.error(schema);
        if (schema.contains("as")) {
            return schema.substring(schema.indexOf("as") + 2).replace("`", "").trim();

            //先根据数据库找到数据id
        } else if(schema.contains("(") || schema.contains(")") ) {
            return schema.substring(schema.indexOf("(") + 1, schema.indexOf(")")).replace(".", "_").trim();
        }else{
            return schema.replace(".", "_").trim();
        }
    }

    /**
     * 根据字段查出该字段数据类型
     *
     * @param schema
     * @return
     */
    private String getEnum(String schema, Map<String, Integer> idMap) {
        String[] arg;
        if (schema.contains("as")) {
            arg = schema.substring(schema.indexOf("(") + 1, schema.indexOf(")")).trim().split("\\.");
            //先根据数据库找到数据id
        } else {
            arg = schema.split("\\.");
        }
        AnalysisTable analysisTable = new AnalysisTable();
        analysisTable.setSecondaryTag(0);
        analysisTable.setDatabaseid(idMap.get(!arg[0].contains("DISTINCT") ? arg[0].trim() : arg[0].replace("DISTINCT", "").trim()));
        analysisTable.setTableName(!arg[0].contains("DISTINCT") ? arg[0].trim() : arg[0].replace("DISTINCT", "").trim());
        analysisTable = analysisTableDao.queryTableInfoByTableName(analysisTable);
        AnalysisSchema analysisSchema = new AnalysisSchema();
        analysisSchema.setSecondaryTag(0);
        analysisSchema.setTableId(analysisTable.getId().toString());
        analysisSchema.setFieldName(arg[1].trim());
        analysisSchema = analysisSchemaDao.querySchemaInfoByTableName(analysisSchema);
        AnalysisEnum analysisEnum = new AnalysisEnum();
        analysisEnum.setFieldId(analysisSchema.getId());
        List<AnalysisEnum> enumList = analysisEnumDao.queryBySchemaId(analysisEnum);
        if (CollectionUtils.isEmpty(enumList)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("如填写条件请选择以下几个值:");
        enumList.forEach(analysisEnum1 -> {
            sb.append(analysisEnum1.getEnumName()).append(",");
        });
        return sb.toString();
    }



}
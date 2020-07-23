package com.taihe.springbootsparksubmit.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.taihe.springbootsparksubmit.entity.ExcuteRecord;
import com.taihe.springbootsparksubmit.result.Page;
import com.taihe.springbootsparksubmit.result.PageResult;
import com.taihe.springbootsparksubmit.result.Result;
import com.taihe.springbootsparksubmit.service.ExcuteRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * (ExcuteRecord)表控制层
 *
 * @author Grayson
 * @since 2020-04-17 17:44:07
 */
@CrossOrigin("*")
@RestController
@RequestMapping("executeRecord")
@Api(tags = "执行历史记录控制类")
public class ExcuteRecordController {
    /**
     * 服务对象
     */
    @Resource
    private ExcuteRecordService excuteRecordService;




    /**
     * 保存执行记录
     * @param excuteRecord
     * @return
     */
    @ApiOperation("保存执行记录为模板")
    @PostMapping("saveExecuteRecord")
    public Result<ExcuteRecord> saveExecuteRecord(@RequestBody ExcuteRecord excuteRecord){
        return this.excuteRecordService.saveExecuteRecord(excuteRecord);
    }

    /**
     * 执行记录
     * @param excuteRecord
     * @return
     */
    @ApiOperation("执行记录")
    @PostMapping("executeRecord")
    public Result<ExcuteRecord> executeRecord(@RequestBody ExcuteRecord excuteRecord) {
        return this.excuteRecordService.executeRecord(excuteRecord);
    }

    /**
     * 根据表名查询执行结果
     * @param id
     * @return
     */
    @ApiOperation("根据表名查询执行结果")
    @PostMapping("queryByTableId")
    public Result<JSONObject> queryByTableId(@RequestBody ExcuteRecord id){
        return this.excuteRecordService.queryByTableId(id);
    }


    /**
     * 根据表名查询执行结果
     * @param excuteRecord
     * @return
     */
    @ApiOperation("查询所包含的所有所属库")
    @PostMapping("queryAllBelongTo")
    public Result<List<String>> queryAllBelongTo(@RequestBody ExcuteRecord excuteRecord){
        return this.excuteRecordService.queryAllBelongTo(excuteRecord);
    }


    /**
     * 根据表名查询执行结果
     * @param id
     * @return
     */
    @ApiOperation("根据belongTo查询执行记录")
    @PostMapping("queryByBelongTo")
    public Result<PageInfo<ExcuteRecord>> queryByBelongTo(@RequestBody ExcuteRecord id){
        return this.excuteRecordService.queryByBelongTo(id);
    }


    /**
     * 根据表名查询执行结果
     * @param id
     * @return
     */
    @ApiOperation("判断表名在临时库是否存在")
    @PostMapping("isTmpTableNameExist")
    public Result<Boolean> isTmpTableNameExist(@RequestBody ExcuteRecord id){
        return this.excuteRecordService.isTmpTableNameExist(id);
    }
}
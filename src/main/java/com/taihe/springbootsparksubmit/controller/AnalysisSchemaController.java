package com.taihe.springbootsparksubmit.controller;

import com.taihe.springbootsparksubmit.entity.AnalysisSchema;
import com.taihe.springbootsparksubmit.result.Result;
import com.taihe.springbootsparksubmit.service.AnalysisSchemaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 字段与表的关系(AnalysisSchema)表控制层
 *
 * @author Grayson
 * @since 2020-04-17 17:44:04
 */
@RestController
@RequestMapping("analysisSchema")
@Api(tags = "表字段控制类")
public class AnalysisSchemaController {
    /**
     * 服务对象
     */
    @Resource
    private AnalysisSchemaService analysisSchemaService;



    /**
     * 通过表id查询该表所有的字段
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiOperation("通过表ID查询该表所有的字段")
    @PostMapping("querySchemasByTableId")
    public Result<List<AnalysisSchema>> querySchemasByTableId(@RequestParam String tableId) {
        return this.analysisSchemaService.querySchemasByTableId(tableId);
    }


    /**
     * 通过表id查询该表所有的字段
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiOperation("通过ID更新字段描述")
    @PostMapping("updateSchemasById")
    public Result<AnalysisSchema> updateSchemasById(@RequestParam AnalysisSchema id) {
        return this.analysisSchemaService.updateSchemasById(id);
    }
}
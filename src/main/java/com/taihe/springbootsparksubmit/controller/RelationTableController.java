package com.taihe.springbootsparksubmit.controller;


import com.taihe.springbootsparksubmit.entity.RelationTable;
import com.taihe.springbootsparksubmit.result.Result;
import com.taihe.springbootsparksubmit.service.RelationTableService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 表关联字段关系(RelationTable)表控制层
 *
 * @author Grayson
 * @since 2020-04-17 17:44:08
 */
@CrossOrigin("*")
@RestController
@RequestMapping("relationTable")
@Api(tags = "关系表控制层")
public class RelationTableController {
    /**
     * 服务对象
     */
    @Resource
    private RelationTableService relationTableService;

    /**
     * 查询有关联关系或者无关联关系表
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiOperation("查询有关联关系或者无关联关系表")
    @PostMapping("queryOtherTablesByTableId")
    public Result<List<RelationTable>> queryOtherTablesByTableId(@RequestBody RelationTable tableId) {
        return this.relationTableService.queryOtherTablesByTableId(tableId);
    }

}
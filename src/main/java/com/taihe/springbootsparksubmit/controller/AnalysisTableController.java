package com.taihe.springbootsparksubmit.controller;

import com.taihe.springbootsparksubmit.entity.AnalysisTable;
import com.taihe.springbootsparksubmit.result.Result;
import com.taihe.springbootsparksubmit.service.AnalysisTableService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;


/**
 * 所有表与库的对应关系(AnalysisTable)表控制层
 *
 * @author Grayson
 * @since 2020-04-17 17:44:05
 */
@CrossOrigin("*")
@RestController
@RequestMapping("analysisTable")
@Api(tags = "表控制类")
public class AnalysisTableController {
    /**
     * 服务对象
     */
    @Resource
    private AnalysisTableService analysisTableService;

//    /**
//     * 通过主键查询单条数据
//     *
//     * @param id 主键w
//     * @return 单条数据
//     */
//    @GetMapping("selectOne")
//    public AnalysisTable selectOne(Integer id) {
//        return this.analysisTableService.queryById(id);
//    }

    /**
     * 查询所有所属库
     * @return
     */
    @GetMapping("queryAllBelongTo")
    @ApiOperation("获取所有所属库")
    public Result<List<String>> queryAllBelongTo(){
        return this.analysisTableService.queryAllBelongTo();
    }

    /**
     * 根据所属库查询下面有多少表
     * @param belongTo
     * @return
     */
    @PostMapping("queryTablesByBelongTo")
    @ApiOperation("根据所属库查询下面有多少表")
    public Result<List<AnalysisTable>> queryTablesByBelongTo(@Valid  @RequestBody AnalysisTable belongTo){
        return this.analysisTableService.queryTablesByBelongTo(belongTo);
    }

    /**
     * 更新表描述
     * @param
     * @return
     */
    @PostMapping("updateTableDescById")
    @ApiOperation("根据id更新表描述")
    public Result<AnalysisTable> updateTableDescById(@Valid  @RequestBody() AnalysisTable analysisTable){
        return this.analysisTableService.updateTableDescById(analysisTable);
    }

}
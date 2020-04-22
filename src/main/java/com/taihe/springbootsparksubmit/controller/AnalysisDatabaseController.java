package com.taihe.springbootsparksubmit.controller;

import com.taihe.springbootsparksubmit.entity.AnalysisDatabase;
import com.taihe.springbootsparksubmit.service.AnalysisDatabaseService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 库名(AnalysisDatabase)表控制层
 *
 * @author Grayson
 * @since 2020-04-17 17:44:01
 */
@RestController
@RequestMapping("analysisDatabase")
public class AnalysisDatabaseController {
    /**
     * 服务对象
     */
    @Resource
    private AnalysisDatabaseService analysisDatabaseService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public AnalysisDatabase selectOne(Integer id) {
        return this.analysisDatabaseService.queryById(id);
    }

}
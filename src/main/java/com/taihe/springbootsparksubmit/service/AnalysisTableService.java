package com.taihe.springbootsparksubmit.service;

import com.taihe.springbootsparksubmit.entity.AnalysisTable;
import com.taihe.springbootsparksubmit.result.Result;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 所有表与库的对应关系(AnalysisTable)表服务接口
 *
 * @author Grayson
 * @since 2020-04-17 17:44:05
 */
public interface AnalysisTableService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    AnalysisTable queryById(AnalysisTable analysisTable);


    /**
     * 新增数据
     *
     * @param analysisTable 实例对象
     * @return 实例对象
     */
    AnalysisTable insert(AnalysisTable analysisTable);

    /**
     * 修改数据
     *
     * @param analysisTable 实例对象
     * @return 实例对象
     */
    AnalysisTable update(AnalysisTable analysisTable);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(AnalysisTable analysisTable);

    /**
     * 查询所有所属库
     * @return
     */
    Result<List<String>> queryAllBelongTo(AnalysisTable analysisTable);

    /**
     * 查询该库下所有表
     * @param belongTo
     * @return
     */
    Result<List<AnalysisTable>> queryTablesByBelongTo(AnalysisTable belongTo);


    /**
     * 修改数据
     *
     * @param analysisTable 实例对象
     * @return 实例对象
     */
    Result<AnalysisTable> updateTableDescById(AnalysisTable analysisTable);

}
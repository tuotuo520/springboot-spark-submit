package com.taihe.springbootsparksubmit.service;

import com.taihe.springbootsparksubmit.entity.AnalysisDatabase;
import com.taihe.springbootsparksubmit.result.Result;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 库名(AnalysisDatabase)表服务接口
 *
 * @author Grayson
 * @since 2020-04-17 17:44:00
 */
public interface AnalysisDatabaseService {

    /**
     * 通过ID查询单条数据
     *
     * @param analysisDatabase 主键
     * @return 实例对象
     */
    Result<AnalysisDatabase> queryById(AnalysisDatabase analysisDatabase);

    /**
     * 新增数据
     *
     * @param analysisDatabase 实例对象
     * @return 实例对象
     */
    AnalysisDatabase insert(AnalysisDatabase analysisDatabase);

    /**
     * 修改数据
     *
     * @param analysisDatabase 实例对象
     * @return 实例对象
     */
    Result<AnalysisDatabase> update(AnalysisDatabase analysisDatabase);

    /**
     * 通过主键删除数据
     *
     * @param analysisDatabase 主键
     * @return 是否成功
     */
    boolean deleteById(AnalysisDatabase analysisDatabase);

}
package com.taihe.springbootsparksubmit.service;

import com.taihe.springbootsparksubmit.entity.AnalysisDatabase;
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
     * @param id 主键
     * @return 实例对象
     */
    AnalysisDatabase queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<AnalysisDatabase> queryAllByLimit(int offset, int limit);

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
    AnalysisDatabase update(AnalysisDatabase analysisDatabase);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
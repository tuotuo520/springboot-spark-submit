package com.taihe.springbootsparksubmit.service.impl;

import com.taihe.springbootsparksubmit.entity.AnalysisDatabase;
import com.taihe.springbootsparksubmit.dao.AnalysisDatabaseDao;
import com.taihe.springbootsparksubmit.result.Result;
import com.taihe.springbootsparksubmit.service.AnalysisDatabaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 库名(AnalysisDatabase)表服务实现类
 *
 * @author Grayson
 * @since 2020-04-17 17:44:01
 */
@Service("analysisDatabaseService")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AnalysisDatabaseServiceImpl implements AnalysisDatabaseService {
    @Resource
    private AnalysisDatabaseDao analysisDatabaseDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Result<AnalysisDatabase> queryById(AnalysisDatabase analysisDatabase) {
        return Result.ok(this.analysisDatabaseDao.queryById(analysisDatabase));
    }


    /**
     * 新增数据
     *
     * @param analysisDatabase 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AnalysisDatabase insert(AnalysisDatabase analysisDatabase) {
        this.analysisDatabaseDao.insert(analysisDatabase);
        return analysisDatabase;
    }

    /**
     * 修改数据
     *
     * @param analysisDatabase 实例对象
     * @return 实例对象
     */
    @Override
    public Result<AnalysisDatabase> update(AnalysisDatabase analysisDatabase) {
        this.analysisDatabaseDao.update(analysisDatabase);
        return Result.ok(this.analysisDatabaseDao.queryById(analysisDatabase));
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(AnalysisDatabase analysisDatabase) {
        return this.analysisDatabaseDao.deleteById(analysisDatabase) > 0;
    }
}
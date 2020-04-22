package com.taihe.springbootsparksubmit.service.impl;


import com.taihe.springbootsparksubmit.dao.AnalysisTableDao;
import com.taihe.springbootsparksubmit.entity.AnalysisTable;
import com.taihe.springbootsparksubmit.result.Result;
import com.taihe.springbootsparksubmit.service.AnalysisTableService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 所有表与库的对应关系(AnalysisTable)表服务实现类
 *
 * @author Grayson
 * @since 2020-04-17 17:44:05
 */
@Service("analysisTableService")
public class AnalysisTableServiceImpl implements AnalysisTableService {
    @Resource
    private AnalysisTableDao analysisTableDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public AnalysisTable queryById(Integer id) {
        return this.analysisTableDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<AnalysisTable> queryAllByLimit(int offset, int limit) {
        return this.analysisTableDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param analysisTable 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AnalysisTable insert(AnalysisTable analysisTable) {
        this.analysisTableDao.insert(analysisTable);
        return analysisTable;
    }

    /**
     * 修改数据
     *
     * @param analysisTable 实例对象
     * @return 实例对象
     */
    @Override
    public AnalysisTable update(AnalysisTable analysisTable) {
        this.analysisTableDao.update(analysisTable);
        return this.queryById(analysisTable.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.analysisTableDao.deleteById(id) > 0;
    }

    /**
     * 查询所有所属库
     *
     * @return
     */
    @Override
    public Result<List<String>> queryAllBelongTo() {
        return Result.ok(this.analysisTableDao.queryAllBelongTo());
    }


    /**
     * 查询该库下所有表
     *
     * @param belongTo
     * @return
     */
    @Override
    public Result<List<AnalysisTable>> queryTablesByBelongTo(String belongTo) {
        AnalysisTable dto = new AnalysisTable();
        dto.setBelongTo(belongTo);
        return Result.ok(this.analysisTableDao.queryAll(dto));
    }

    /**
     * 修改数据
     *
     * @param tableId@return 实例对象
     */
    @Override
    public Result<AnalysisTable> updateTableDescById(Integer tableId,String tableDesc) {
        AnalysisTable dto = new AnalysisTable();



        dto.setId(tableId);
        dto.setTableDescribe(tableDesc);
        this.analysisTableDao.update(dto);
        return Result.ok(this.queryById(dto.getId()));
    }
}
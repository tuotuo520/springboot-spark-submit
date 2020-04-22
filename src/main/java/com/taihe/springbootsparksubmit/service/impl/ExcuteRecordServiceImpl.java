package com.taihe.springbootsparksubmit.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taihe.springbootsparksubmit.entity.ExcuteRecord;
import com.taihe.springbootsparksubmit.dao.ExcuteRecordDao;
import com.taihe.springbootsparksubmit.result.PageRequest;
import com.taihe.springbootsparksubmit.result.Result;
import com.taihe.springbootsparksubmit.service.ExcuteRecordService;
import com.taihe.springbootsparksubmit.starter.SparkStarter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * (ExcuteRecord)表服务实现类
 *
 * @author Grayson
 * @since 2020-04-17 17:44:07
 */
@Service("excuteRecordService")
public class ExcuteRecordServiceImpl implements ExcuteRecordService {
    @Resource
    private ExcuteRecordDao excuteRecordDao;
    @Resource
    private SparkStarter sparkStarter;


    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ExcuteRecord queryById(Integer id) {
        return this.excuteRecordDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<ExcuteRecord> queryAllByLimit(int offset, int limit) {
        return this.excuteRecordDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param excuteRecord 实例对象
     * @return 实例对象
     */
    @Override
    public ExcuteRecord insert(ExcuteRecord excuteRecord) {
        this.excuteRecordDao.insert(excuteRecord);
        return excuteRecord;
    }

    /**
     * 修改数据
     *
     * @param excuteRecord 实例对象
     * @return 实例对象
     */
    @Override
    public ExcuteRecord update(ExcuteRecord excuteRecord) {
        this.excuteRecordDao.update(excuteRecord);
        return this.queryById(excuteRecord.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.excuteRecordDao.deleteById(id) > 0;
    }

    /**
     * 查询所有的执行记录
     *
     * @param pageRequest
     * @return
     */
    @Override
    public Result<PageInfo<ExcuteRecord>> queryAlLRecord(ExcuteRecord excuteRecord) {
        PageHelper.startPage(excuteRecord.getPage(), excuteRecord.getLimit());
        List<ExcuteRecord> result = this.excuteRecordDao.queryAlLRecord(excuteRecord);
        PageInfo<ExcuteRecord> pageInfo = new PageInfo<>(result);
        return Result.ok(pageInfo);
    }

    /**
     * 保存执行记录
     *
     * @param excuteRecord
     * @return
     */
    @Override
    public Result<ExcuteRecord> saveExecuteRecord(ExcuteRecord excuteRecord) {

        excuteRecord.setIsComplete(0);
        excuteRecord.setCreateTime(new Date());
        this.excuteRecordDao.insert(excuteRecord);
        return Result.ok(excuteRecord);
    }

    /**
     * 执行记录
     *
     * @param excuteRecord
     * @return
     */
    @Override
    public Result<ExcuteRecord> executeRecord(ExcuteRecord excuteRecord) {
        //先保存到执行记录表
        excuteRecord.setIsComplete(0);
        excuteRecord.setCreateTime(new Date());
        this.excuteRecordDao.insert(excuteRecord);
        try {
            sparkStarter.submitSqlTask(excuteRecord.getId().toString());
        } catch (IOException e) {
            Result.error("任务提交失败" + e);
        }
        return Result.ok(excuteRecord);
    }


    /**
     * 根据表id查询结果
     *
     * @param id
     * @return
     */
    @Override
    public List<Object> queryByTableId(Integer id) {
        return this.excuteRecordDao.queryByTableId(id);
    }
}
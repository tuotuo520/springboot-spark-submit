package com.taihe.springbootsparksubmit.dao;

import com.taihe.springbootsparksubmit.entity.AnalysisEnum;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (AnalysisEnum)表数据库访问层
 *
 * @author makejava
 * @since 2020-06-28 17:42:45
 */
@Mapper
public interface AnalysisEnumDao {

    /**
     * 根据id获取枚举信息
     * @param analysisEnum
     * @return
     */
    List<AnalysisEnum> queryBySchemaId(AnalysisEnum analysisEnum);


}
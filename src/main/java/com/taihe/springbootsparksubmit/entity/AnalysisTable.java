package com.taihe.springbootsparksubmit.entity;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 所有表与库的对应关系(AnalysisTable)实体类
 *
 * @author Grayson
 * @since 2020-04-17 17:44:04
 */
@ApiModel
public class AnalysisTable implements Serializable {
    private static final long serialVersionUID = -81656645378489020L;

    private Integer id;
    @ApiModelProperty(value = "表名")
    private String tableName;
    @ApiModelProperty(value = "表描述")
    private String tableDescribe;
    @ApiModelProperty(value = "数据库id")
    private Integer databaseid;
    @ApiModelProperty(value = "备注 预留字段")
    private String remake;
    @ApiModelProperty(value = "属于哪个数据库")
    private String belongTo;
    @ApiModelProperty(value = "//0代表非二次分析，1代表二次分析的首次分析，2代表二次分析的再次分析")
    private Integer secondaryTag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableDescribe() {
        return tableDescribe;
    }

    public void setTableDescribe(String tableDescribe) {
        this.tableDescribe = tableDescribe;
    }

    public Integer getDatabaseid() {
        return databaseid;
    }

    public void setDatabaseid(Integer databaseid) {
        this.databaseid = databaseid;
    }

    public String getRemake() {
        return remake;
    }

    public void setRemake(String remake) {
        this.remake = remake;
    }

    public String getBelongTo() {
        return belongTo;
    }

    public void setBelongTo(String belongTo) {
        this.belongTo = belongTo;
    }


    public Integer getSecondaryTag() {
        return secondaryTag;
    }

    public void setSecondaryTag(Integer secondaryTag) {
        this.secondaryTag = secondaryTag;
    }


}
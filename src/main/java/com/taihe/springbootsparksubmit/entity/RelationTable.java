package com.taihe.springbootsparksubmit.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 表关联字段关系(RelationTable)实体类
 *
 * @author Grayson
 * @since 2020-04-17 17:44:08
 */
@ApiModel
public class RelationTable implements Serializable {
    private static final long serialVersionUID = 698318693892248712L;
    
    private Integer id;
    @ApiModelProperty(value = "原表id")
    private Integer sourceTableId;
    @ApiModelProperty(value = "目标表id")
    private Integer targetTableId;
    @ApiModelProperty(value = "原表关联字段")
    private String sourceRelatedField;
    @ApiModelProperty(value = "目标表表关联字段")
    private String targetRelatedField;
    @ApiModelProperty(value = "目标表表名")
    private String tableName;

    public String getSourceRelatedField() {
        return sourceRelatedField;
    }

    public void setSourceRelatedField(String sourceRelatedField) {
        this.sourceRelatedField = sourceRelatedField;
    }

    public String getTargetRelatedField() {
        return targetRelatedField;
    }

    public void setTargetRelatedField(String targetRelatedField) {
        this.targetRelatedField = targetRelatedField;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSourceTableId() {
        return sourceTableId;
    }

    public void setSourceTableId(Integer sourceTableId) {
        this.sourceTableId = sourceTableId;
    }

    public Integer getTargetTableId() {
        return targetTableId;
    }

    public void setTargetTableId(Integer targetTableId) {
        this.targetTableId = targetTableId;
    }

}
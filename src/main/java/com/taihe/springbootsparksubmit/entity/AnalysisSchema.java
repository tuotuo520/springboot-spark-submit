package com.taihe.springbootsparksubmit.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 字段与表的关系(AnalysisSchema)实体类
 *
 * @author Grayson
 * @since 2020-04-17 17:44:02
 */
@ApiModel
public class AnalysisSchema implements Serializable {
    private static final long serialVersionUID = 252841742658900774L;
    
    private Integer id;
    /**
    * 字段名
    */
    @ApiModelProperty(value = "字段名")
    private String fieldName;
    /**
    * 字段描述
    */
    @ApiModelProperty(value = "字段描述")
    private String fileldDescribe;
    /**
    * 字段类型
    */
    @ApiModelProperty(value = "字段类型")
    private String fieldType;
    /**
    * 表名
    */
    @ApiModelProperty(value = "表id")
    private String tableId;
    /**
    * 备注（预留字段）
    */
    @ApiModelProperty(value = "备注(预留字段)")
    private String remake;

    @ApiModelProperty(value = "//0代表非二次分析，1代表二次分析的首次分析，2代表二次分析的再次分析")
    private Integer secondaryTag;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFileldDescribe() {
        return fileldDescribe;
    }

    public void setFileldDescribe(String fileldDescribe) {
        this.fileldDescribe = fileldDescribe;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }



    public String getRemake() {
        return remake;
    }

    public void setRemake(String remake) {
        this.remake = remake;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public Integer getSecondaryTag() {
        return secondaryTag;
    }

    public void setSecondaryTag(Integer secondaryTag) {
        this.secondaryTag = secondaryTag;
    }
}
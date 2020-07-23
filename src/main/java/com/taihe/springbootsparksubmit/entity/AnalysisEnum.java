package com.taihe.springbootsparksubmit.entity;

import java.io.Serializable;

/**
 * (AnalysisEnum)实体类
 *
 * @author makejava
 * @since 2020-06-28 17:43:22
 */
public class AnalysisEnum implements Serializable {
    private static final long serialVersionUID = -12649523747912301L;
    /**
    * 主键id
    */
    private Long id;
    /**
    * 字段id
    */
    private Integer fieldId;
    /**
    * 字段名称
    */
    private String fieldName;
    /**
    * 表id
    */
    private Integer tableId;
    /**
    * 表名
    */
    private String tableName;
    /**
    * 库id
    */
    private Integer databaseId;
    /**
    * 库名
    */
    private String databaseName;
    /**
    * 枚举id
    */
    private String enumCode;
    /**
    * 枚举值
    */
    private String enumName;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getFieldId() {
        return fieldId;
    }

    public void setFieldId(Integer fieldId) {
        this.fieldId = fieldId;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Integer getTableId() {
        return tableId;
    }

    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Integer getDatabaseId() {
        return databaseId;
    }

    public void setDatabaseId(Integer databaseId) {
        this.databaseId = databaseId;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getEnumCode() {
        return enumCode;
    }

    public void setEnumCode(String enumCode) {
        this.enumCode = enumCode;
    }

    public String getEnumName() {
        return enumName;
    }

    public void setEnumName(String enumName) {
        this.enumName = enumName;
    }

}
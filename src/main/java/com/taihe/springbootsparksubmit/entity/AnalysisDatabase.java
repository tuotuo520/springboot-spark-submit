package com.taihe.springbootsparksubmit.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 库名(AnalysisDatabase)实体类
 *
 * @author Grayson
 * @since 2020-04-17 17:43:58
 */
@ApiModel
public class AnalysisDatabase implements Serializable {
    private static final long serialVersionUID = 980722712188260991L;
    
    private Integer id;
    @ApiModelProperty(value = "数据库名")
    private String databaseName;
    @ApiModelProperty(value = "备注")
    private String remake;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getRemake() {
        return remake;
    }

    public void setRemake(String remake) {
        this.remake = remake;
    }
}
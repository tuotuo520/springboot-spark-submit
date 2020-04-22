package com.taihe.springbootsparksubmit.entity;

import com.taihe.springbootsparksubmit.result.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.io.Serializable;

/**
 * (ExcuteRecord)实体类
 *
 * @author Grayson
 * @since 2020-04-17 17:44:06
 */
@ApiModel
public class ExcuteRecord extends PageRequest implements Serializable {
    private static final long serialVersionUID = -47750267500899120L;
    /**
    * ID自增
    */
    private Integer id;
    /**
    * 条件json
    */
    @ApiModelProperty(value = "条件json")
    private String conditionContent;

    /**
    * 创建时间
    */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    /**
    * 0 未完成 ,1 已完成
    */
    @ApiModelProperty(value = "是否完成 0 未完成 ,1 已完成")
    private Integer isComplete;
    /**
    * 落地结果表
    */
    @ApiModelProperty(value = "落地结果表")
    private String resultTable;
    /**
    * 0 柱状图 1 饼状图 2 折线图..........
    */
    @ApiModelProperty(value = "0 柱状图 1 饼状图 2 折线图..........")
    private Integer resultType;
    /**
    * 预留字段
    */
    @ApiModelProperty(value = "预留字段")
    private String remark;

    /**
     * 邮件地址
     */
    @ApiModelProperty(value = "邮件地址")
    private String mailAddress;

    /**
     * 执行状态
     */
    @ApiModelProperty(value = "执行状态")
    private String executeStatus;
    /**
     * 执行描述
     */
    @ApiModelProperty(value = "执行描述")
    private String executeDescribe;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
    /**
     * 任务名称
     */
    @ApiModelProperty(value = "任务名称")
    private String taskName;

    public String getExecuteStatus() {
        return executeStatus;
    }

    public void setExecuteStatus(String executeStatus) {
        this.executeStatus = executeStatus;
    }

    public String getExecuteDescribe() {
        return executeDescribe;
    }

    public void setExecuteDescribe(String executeDescribe) {
        this.executeDescribe = executeDescribe;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getConditionContent() {
        return conditionContent;
    }

    public void setConditionContent(String conditionContent) {
        this.conditionContent = conditionContent;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getIsComplete() {
        return isComplete;
    }

    public void setIsComplete(Integer isComplete) {
        this.isComplete = isComplete;
    }

    public String getResultTable() {
        return resultTable;
    }

    public void setResultTable(String resultTable) {
        this.resultTable = resultTable;
    }

    public Integer getResultType() {
        return resultType;
    }

    public void setResultType(Integer resultType) {
        this.resultType = resultType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
}
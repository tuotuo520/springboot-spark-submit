package com.taihe.springbootsparksubmit.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.thymeleaf.context.Context;

@Data
@ApiModel
public class Mail {
    /**
     * 邮件id
     */
    @ApiModelProperty(value = "邮件id")
    private String id;
    /**
     * 邮件发送人
     */
    @ApiModelProperty(value = "邮件发送人")
    private String sender;
    /**
     * 邮件接收人 （多个邮箱则用逗号","隔开）
     */
    @ApiModelProperty(value = "邮件接收人 （多个邮箱则用逗号\",\"隔开）")
    private String receiver;
    /**
     * 邮件主题
     */
    @ApiModelProperty(value = "邮件主题")
    private String subject;
    /**
     * 邮件内容
     */
    @ApiModelProperty(value = "邮件内容")
    private String text;
    /**
     * 附件/文件地址
     */
    @ApiModelProperty(value = "附件/文件地址")
    private String filePath;
    /**
     * 附件/文件名称
     */
    @ApiModelProperty(value = "附件/文件名称")
    private String fileName;
    /**
     * 是否有附件（默认没有）
     */
    @ApiModelProperty(value = "是否有附件（默认没有）")
    private Boolean isTemplate = false;
    /**
     * 模版名称
     */
    @ApiModelProperty(value = "模版名称")
    private String emailTemplateName;
    /**
     * 模版内容
     */
    @ApiModelProperty(value = "模版内容")
    private Context emailTemplateContext;

}

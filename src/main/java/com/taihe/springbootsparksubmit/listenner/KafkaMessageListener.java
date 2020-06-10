package com.taihe.springbootsparksubmit.listenner;

import com.alibaba.fastjson.JSONObject;
import com.taihe.springbootsparksubmit.entity.ExcuteRecord;
import com.taihe.springbootsparksubmit.entity.Mail;
import com.taihe.springbootsparksubmit.service.ExcuteRecordService;
import com.taihe.springbootsparksubmit.service.MailService;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author : create by Grayson
 */
@CommonsLog
@Component
public class KafkaMessageListener {

    @Resource
    private ExcuteRecordService excuteRecordService;

    @Resource
    private MailService mailService;


    /**
     * @param json
     * @throws MessagingException
     */
    @KafkaListener(topics = "#{'${topics}'.split(',')}")
    public void processUser(String json) throws MessagingException {
        log.info("接受到任务跑完参数" + json);
        //先解析
        JSONObject jsonObject = JSONObject.parseObject(json);
        String tableName = jsonObject.getString("tableName");
        String searchStatus = jsonObject.getString("searchstatus");
        String describe = jsonObject.getString("describe");
        ExcuteRecord excuteRecord = new ExcuteRecord();
        if (!StringUtils.isEmpty(searchStatus) && "true".equals(searchStatus)) {
            excuteRecord.setIsComplete(1);
        } else {
            excuteRecord.setIsComplete(0);
        }
        excuteRecord.setId(getNum(tableName));
        excuteRecord.setExecuteDescribe(describe);
        excuteRecord.setExecuteStatus(searchStatus);
        excuteRecord.setUpdateTime(new Date());
        excuteRecordService.update(excuteRecord);
        ExcuteRecord resultVo = excuteRecordService.queryById(excuteRecord.getId());
        //发送邮件............包含结果展示页
        log.info("======================================开始发送邮件");
        Mail mail = new Mail();
        mail.setReceiver(StringUtils.isEmpty(resultVo.getMailAddress()) ? "empty receive" : resultVo.getMailAddress());
        mail.setSubject("任务ID" + resultVo.getId() + ",名称:" + resultVo.getExecuteDescribe() + "执行结果邮件");
        //创建模版正文
        Context context = new Context();
        context.setVariable("id", resultVo.getId());
        context.setVariable("name", resultVo.getExecuteDescribe());
        context.setVariable("result", ("true".equals(searchStatus)) ? "成功" : "失败");
        context.setVariable("url", "http://192.168.1.193:3006/#/app/dashboard/history");
        mail.setEmailTemplateContext(context);
        // 模版名称(模版位置位于templates目录下)
        mail.setEmailTemplateName("emailTemplate");
        if (!"empty receive".equals(mail.getReceiver())) {
            log.info("======================================发送成功");
            mailService.sendTemplateMail(mail);
        }

    }


    /**
     * 实现功能描述:取出字符串中的数字
     *
     * @param [arg]
     * @return java.lang.Integer
     */
    private Integer getNum(String arg) {
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(arg);
        return Integer.parseInt(m.replaceAll("").trim());
    }
}

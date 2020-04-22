package com.taihe.springbootsparksubmit.service;


import com.taihe.springbootsparksubmit.entity.Mail;
import javax.mail.MessagingException;

public interface MailService {
    /**
     * 发送纯文本邮件
     *
     * @param mail
     */
    void sendSimpleMail(Mail mail);

    /**
     * 发送邮件并携带附件
     *
     * @param mail
     * @throws MessagingException
     */
    void sendAttachmentsMail(Mail mail) throws MessagingException;

    /**
     * 发送模版邮件
     *
     * @param mail
     * @throws MessagingException
     */
    void sendTemplateMail(Mail mail) throws MessagingException;
}

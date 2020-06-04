package com.example.demo.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Map;

/**
 * 邮件发送工具类
 *
 * @author zj
 * @date 2019/12/23
 */
@Component
public class EmailUtils {
    @Resource
    private JavaMailSender mailSender;

    @Resource
    private TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String from;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 发送Template邮件
     *
     * @param to
     * @param subject
     */
    public void sendTemplate(String to, String subject, String templateName, Map<String, Object> variables) {
        Context context = new Context();
        context.setVariables(variables);
        String text = templateEngine.process(templateName, context);
        send(to, subject, text, null, null, null, null, null);
    }

    /**
     * 发送html邮件
     *
     * @param to
     * @param subject
     * @param text
     */
    public void sendHtml(String to, String subject, String text) {
        send(to, subject, text, null, null, null, null, null);
    }

    /**
     * 发送带附件的邮件
     *
     * @param to
     * @param subject
     * @param text
     * @param attachmentsFiles
     */
    public void sendAttachments(String to, String subject, String text, File[] attachmentsFiles) {
        send(to, subject, text, null, null, attachmentsFiles, null, null);
    }

    /**
     * 发送静态资源的邮件
     *
     * @param to
     * @param subject
     * @param text
     * @param inlineResourceFiles
     * @param inlineResourceId
     */
    public void sendInlineResource(String to, String subject, String text, File[] inlineResourceFiles, String[] inlineResourceId) {
        send(to, subject, text, null, null, null, inlineResourceFiles, inlineResourceId);
    }

    /**
     * @param to                  接收者
     * @param subject             主题
     * @param text                内容
     * @param cc                  抄送
     * @param bcc                 密送
     * @param attachmentsFiles    附件
     * @param inlineResourceFiles 静态资源
     */
    @Async
    public void send(String to, String subject, String text, String[] cc, String[] bcc, File[] attachmentsFiles, File[] inlineResourceFiles, String[] inlineResourceId) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            if (cc != null) {
                helper.setCc(cc);
            }
            if (bcc != null) {
                helper.setBcc(bcc);
            }
            if (attachmentsFiles != null) {
                for (File file : attachmentsFiles) {
                    helper.addAttachment(file.getName(), file);
                }
            }
            if (inlineResourceFiles != null) {
                for (int i = 0; i < inlineResourceFiles.length; i++) {
                    helper.addInline(inlineResourceId[i], inlineResourceFiles[i]);
                }
            }
            helper.setText(text, true);
        } catch (MessagingException e) {
            logger.error(e.getMessage());
        }
        mailSender.send(message);
    }
}


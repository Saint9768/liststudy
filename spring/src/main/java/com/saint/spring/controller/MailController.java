package com.saint.spring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * @author Saint
 * @version 1.0
 * @createTime 2021-01-05 6:27
 */
@RestController
@RequestMapping("/mail")
public class MailController {

    private final Logger LOGGER = LoggerFactory.getLogger(MailController.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @RequestMapping("/send")
    public void sendMail() {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("zhouxin9768@sina.com");
        mailMessage.setTo("805953980@qq.com");
        mailMessage.setSubject("Spring Boot Mail simple demo 标题");
        mailMessage.setText("simple文本内容");
        try {
            javaMailSender.send(mailMessage);
            LOGGER.info("发送邮件成功");
        } catch (Exception e) {
            LOGGER.error("发送邮件异常！", e);
        }
    }

    /**
     * 发送带附件的邮件
     */
    @RequestMapping("/sendAttach")
    public void sendAttach() throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        // true表示可以带多个附件
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom("zhouxin9768@sina.com");
        helper.setTo("805953980@qq.com");
        helper.setSubject("Spring Boot Mail 多个附件 demo 标题");
        helper.setText("这是一封带附件的邮件");
        FileSystemResource file1 = new FileSystemResource(new File("d:\\ids.txt"));
        FileSystemResource file2 = new FileSystemResource(new File("d:\\癞皮狗.jpg"));
        helper.addAttachment("ids.txt", file1);
        helper.addAttachment("癞皮狗.jpg", file2);
        try {
            javaMailSender.send(mimeMessage);
            LOGGER.info("发送邮件成功");
        } catch (Exception e) {
            LOGGER.error("发送邮件异常！", e);
        }
    }

    /**
     * 发送带静态资源的邮件
     */
    @RequestMapping("/sendStatic")
    public void sendStatic() throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        // true表示可以带多个附件
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom("zhouxin9768@sina.com");
        helper.setTo("xzhou.saint@wind.com.cn");
        helper.setSubject("Spring Boot Mail 静态文件 demo 标题");
        // 发送H5内容
        helper.setText("<html>\n" +
                "<body>\n" +
                "<p id=\"mailUuid\" style=\"color:red\">ABC</p>\n" +
                "<p id=\"uuid\" style=\"\">ABCDDDD</p>\n" +
                "<p>CDE</p>\n" +
                "<img src=“cid:test1”> \n" +
                "</body>\n" +
                "</html>", true);
        FileSystemResource file = new FileSystemResource(new File("d:/癞皮狗.jpg"));
        // 这里的contentId要与cid:一样
        helper.addInline("test1", file);
        try {
            javaMailSender.send(mimeMessage);
            LOGGER.info("发送邮件成功");
        } catch (Exception e) {
            LOGGER.error("发送邮件异常！", e);
        }
    }
}

package org.eu.xinyucraft;

//邮箱管理类

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class EmailManager {
    public static void sendEmail(String message, String toEmailAddress){ //发送邮件
        Properties properties = new Properties(); //配置文件
        try {
            properties.load(new FileInputStream("config.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //创建会话
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(properties.getProperty("mail.smtp.user"), properties.getProperty("mail.smtp.pass"));
            }
        });

        // 构建邮件消息
        MimeMessage mimeMessage = new MimeMessage(session);
        try {
            mimeMessage.setFrom(new InternetAddress(properties.getProperty("mail.smtp.user"))); //设置发送人邮箱
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmailAddress)); //设置收件人邮箱
            mimeMessage.setSubject("ShitChat邮箱验证");
            mimeMessage.setText(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        //发送邮件
        try {
            Transport.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}

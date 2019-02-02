package com.guiji.notice.service.impl;

import com.guiji.notice.controller.NoticeSendController;
import com.guiji.notice.service.SendEmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;


@Service
public class SendEmailServiceImpl implements SendEmailService {

    private final Logger logger = LoggerFactory.getLogger(SendEmailServiceImpl.class);

    @Value("${email.sendAccount}")
    private String myEmailAccount;
    @Value("${email.sendPassword}")
    private String myEmailPsw;
    @Value("${email.smtpHost}")
    private String myEmailSMTPHost;

    public void sendEmail(String receiveMailAccount, String subect, String content) throws Exception {

        logger.info("---start sendEmail receiveMailAccount[{}],subect[{}],content[{}]",receiveMailAccount,subect,content);

        // 1. 创建参数配置, 用于连接邮件服务器的参数配置(一般写在一个Properties文件里来读取，此处为了方便)
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");//使用协议(javaMail规范要求)
        props.setProperty("mail.smtp.host", myEmailSMTPHost);//发件人的邮箱的SMTP服务器地址
        props.setProperty("mail.smtp.auth", "true");//需要请求认证

        //2. 根据配置创建会话对象，用于和邮件服务器交互
        Session session = Session.getInstance(props);
        session.setDebug(false);//设置为debug模式，可以查看详细的发送Log

        //3.创建一封邮件
        MimeMessage message = createMimeMessage(session, myEmailAccount, receiveMailAccount, subect, content);

        //4.根据Session获取邮件传输对象
        Transport transport = session.getTransport();

        // 5. 使用 邮箱账号 和 密码 连接邮件服务器, 这里认证的邮箱必须与 message 中的发件人邮箱一致, 否则报错
        transport.connect(myEmailAccount, myEmailPsw);

        // 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
        transport.sendMessage(message, message.getAllRecipients());

        // 7. 关闭连接
        transport.close();
    }

    /**
     * 创建一封只包含文本的简单邮件
     *
     * @param session     和服务器交互的会话
     * @param sendMail    发件人邮箱
     * @param receiveMail 收件人邮箱
     * @return
     * @throws Exception
     */
    public MimeMessage createMimeMessage(Session session, String sendMail, String receiveMail, String subect, String content) throws Exception {
        // 1. 创建一封邮件
        MimeMessage message = new MimeMessage(session);

        // 2. From: 发件人（昵称有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改昵称）
        // InternetAddress(String address, String personal, String charset) 根据地址和名称来构建InternetAddress
        message.setFrom(new InternetAddress(sendMail, subect, "UTF-8"));

        // 3. To: 收件人（可以增加多个收件人、抄送、密送）
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, null, "UTF-8"));
        // 4. Subject: 邮件主题（标题有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改标题）

        message.setSubject(subect, "UTF-8");

        // 5. Content: 邮件正文（可以使用html标签）（内容有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改发送内容）
        message.setContent(content, "text/html;charset=utf-8");

        // 6. 设置发件时间
        message.setSentDate(new Date());

        // 7. 保存设置
        message.saveChanges();

        return message;
    }
}

package utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

public class MailUtils {
    public static void main(String[] args) throws MessagingException {
        sendMessage("3189529483@qq.com", "测试","测试");
    }
    private static Properties properties = null;
    static  {
        try {
            properties = new Properties();
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("mail.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendMessage(String toEmail, String subject, String text) throws MessagingException {
        if (properties == null) {
            return;
        }
        final String fromEmail = properties.getProperty("fromEmail");
        final String password = properties.getProperty("password");
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });
        MimeBodyPart htmlPart = new MimeBodyPart();
        htmlPart.setContent(text, "text/html;charset=UTF-8");
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(htmlPart);

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(fromEmail));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
        // 设置邮件主题
        message.setSubject(subject);
        message.setSentDate(new Date());
        message.setContent(multipart);
        Transport.send(message);
    }
}

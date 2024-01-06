package com.group10.contestPlatform.utils;

import com.group10.contestPlatform.entities.EmailSettingBag;
import com.group10.contestPlatform.services.imlp.SettingService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.io.UnsupportedEncodingException;

public class CustomerForgetPasswordUtil {
    public static void sendEmailUserCheat(String email, String contentClient, SettingService settingService)
            throws UnsupportedEncodingException, MessagingException {


        EmailSettingBag emailSettings = settingService.getEmailSettings();



        JavaMailSenderImpl mailSender = CustomerRegisterUtil.prepareMailSender(emailSettings);



        String toAddress = email;
        String subject = "BACKEND ROOKIE HACKATHON";

        String content = contentClient;

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");

        helper.setFrom(emailSettings.getFromAddress(), emailSettings.getSenderName());
        helper.setTo(toAddress);
        helper.setSubject(subject);

        helper.setText(content, true);



        mailSender.send(message);
    }
    public static void sendEmail(String link, String email, SettingService settingService)
            throws UnsupportedEncodingException, MessagingException {


        EmailSettingBag emailSettings = settingService.getEmailSettings();



        JavaMailSenderImpl mailSender = CustomerRegisterUtil.prepareMailSender(emailSettings);



        String toAddress = email;
        String subject = "Here's the link to reset your password";

        String content = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "Click the link below to change your password:</p>"
                + "<p><a href=\"" + link + "\">Change my password</a></p>"
                + "<br>"
                + "<p>You have "+ link + "</p>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");

        helper.setFrom(emailSettings.getFromAddress(), emailSettings.getSenderName());
        helper.setTo(toAddress);
        helper.setSubject(subject);

        helper.setText(content, true);



        mailSender.send(message);
    }
}

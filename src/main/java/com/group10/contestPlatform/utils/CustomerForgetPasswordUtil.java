package com.group10.contestPlatform.utils;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.group10.contestPlatform.entities.EmailSettingBag;
import com.group10.contestPlatform.services.imlp.SettingService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.List;

public class CustomerForgetPasswordUtil {
    public static void sendEmailUserCheat(String email, String contentClient, List<String> imageArray, SettingService settingService)
            throws UnsupportedEncodingException, MessagingException {


        EmailSettingBag emailSettings = settingService.getEmailSettings();



        JavaMailSenderImpl mailSender = CustomerRegisterUtil.prepareMailSender(emailSettings);



        String toAddress = email;
        String subject = "BACKEND ROOKIE HACKATHON";

//        String content = contentClient;

        StringBuilder contentBuilder = new StringBuilder();
        contentBuilder.append("<p>Hello Wellcome to ATC</p>")
                .append(contentClient)
                .append("<p>There are many images you cheated:</p>");
        for (String imageName : imageArray) {
            String imageUrl = generatePresignedUrl(imageName); // Replace with your method to generate pre-signed URLs
            contentBuilder.append("<img src='").append(imageUrl).append("'/>");
        }

        String content = contentBuilder.toString();
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");

        helper.setFrom(emailSettings.getFromAddress(), emailSettings.getSenderName());
        helper.setTo(toAddress);
        helper.setSubject(subject);

        helper.setText(content, true);



        mailSender.send(message);
    }
    private static String generatePresignedUrl(String imageName) {
        // Replace "your-bucket-name" and "your-object-key" with your S3 bucket name and object key
        String bucketName = "your-bucket-name";
        String objectKey = "your-object-key/" + imageName;

        AmazonS3 s3Client = AmazonS3ClientBuilder.defaultClient();

        // Set the expiration time for the pre-signed URL (e.g., 1 hour)
        java.util.Date expiration = new java.util.Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 60; // 1 hour
        expiration.setTime(expTimeMillis);

        // Generate the pre-signed URL
        URL url = s3Client.generatePresignedUrl(bucketName, objectKey, expiration, HttpMethod.GET);

        return url.toString();
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

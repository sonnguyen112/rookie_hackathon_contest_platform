package com.group10.contestPlatform.entities;

import java.util.List;

public class EmailSettingBag extends SettingBag {

    public EmailSettingBag(List<Setting> listSettings) {
        super(listSettings);
    }

    public String getHost() {

        return super.getValue("MAIL_HOST");

    }

    public int getPort() {
        String portValue = super.getValue("MAIL_PORT");
        System.out.println("portValue" + portValue);
        if (portValue != null && !portValue.isEmpty()) {
            return Integer.parseInt(portValue);
        } else {

            throw new IllegalArgumentException("MAIL_PORT is null or empty");
        }
    }

    public String getUsername() {
        return super.getValue("MAIL_USERNAME");
    }

    public String getPassword() {

        return super.getValue("MAIL_PASSWORD");
    }

    public String getSmtpAuth() {
        return super.getValue("SMTP_AUTH");
    }

    public String getSmtpSecured() {
        return super.getValue("SMTP_SECURED");
    }

    public String getFromAddress() {
        return super.getValue("MAIL_FROM");
    }

    public String getSenderName() {
        return super.getValue("MAIL_SENDER_NAME");
    }

    public String getCustomerVerifySubject() {
        return super.getValue("CUSTOMER_VERIFY_SUBJECT");
    }

    public String getCustomerVerifyContent() {
        return super.getValue("CUSTOMER_VERIFY_CONTENT");
    }

    public String getOrderConfirmationSubject() {
        return super.getValue("ORDER_CONFIRMATION_SUBJECT");
    }

    public String getOrderConfirmationContent() {
        return super.getValue("ORDER_CONFIRMATION_CONTENT");
    }
}


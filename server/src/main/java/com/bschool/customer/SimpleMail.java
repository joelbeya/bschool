package com.bschool.customer;

import lombok.Data;

@Data
public class SimpleMail {

    public final String MAIL_FROM = "jurenstelema@gmail.com";

    private Integer customerId;

    private String emailSubject;

    private String emailContent;

}

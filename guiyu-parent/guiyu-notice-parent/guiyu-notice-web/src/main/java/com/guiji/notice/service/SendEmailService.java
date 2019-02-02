package com.guiji.notice.service;

public interface SendEmailService {
    void sendEmail(String email, String emailSubject, String content) throws Exception;
}

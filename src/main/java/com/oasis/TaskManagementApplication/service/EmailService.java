package com.oasis.TaskManagementApplication.service;

import com.oasis.TaskManagementApplication.dto.req.EmailRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String emailSender;

    @Async
    public void sendEmail(EmailRequest emailRequest){
        try {
            SimpleMailMessage mailMsg = new SimpleMailMessage();
            mailMsg.setFrom(emailSender);
            mailMsg.setTo(emailRequest.getRecipient());
            mailMsg.setText(emailRequest.getMessageBody());
            mailMsg.setSubject(emailRequest.getSubject());
            javaMailSender.send(mailMsg);
            log.info("Mail sent successfully");
        }catch (MailException exception){
            log.debug("Failure occurred while sending email");
        }
    }

}

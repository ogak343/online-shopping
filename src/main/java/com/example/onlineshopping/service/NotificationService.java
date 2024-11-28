package com.example.onlineshopping.service;

import com.example.onlineshopping.entity.Payment;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@Slf4j
public class NotificationService {
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    public NotificationService(JavaMailSender mailSender,
                               TemplateEngine templateEngine
    ) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    @Async(value = "asyncExecutor")
    public void sendOtp(Long otpCode, String email) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            Context context = new Context();
            context.setVariable("otpCode", otpCode);
            helper.setTo(email);
            helper.setSubject("Account Verification Page");
            String emailBody = templateEngine.process("AccountVerification.html", context);
            helper.setText(emailBody, true);
            mailSender.send(message);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Async(value = "asyncExecutor")
    public void sendPaymentInitInfo(Payment payment, String email) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            Context context = new Context();
            context.setVariable("price", "$" + payment.getAmount());
            context.setVariable("otpCode", payment.getConfirmId());
            helper.setTo(email);
            helper.setSubject("Payment Information");
            String emailBody = templateEngine.process("PaymentInitInfo.html", context);
            helper.setText(emailBody, true);
            mailSender.send(message);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Async(value = "asyncExecutor")
    public void sendPaymentPerformInfo(Payment payment, String email) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            Context context = new Context();
            context.setVariable("message", "Payment has been performed");
            context.setVariable("price", "$" + payment.getAmount());
            helper.setTo(email);
            helper.setSubject("Payment Information");
            String emailBody = templateEngine.process("PaymentPerformInfo.html", context);
            helper.setText(emailBody, true);
            mailSender.send(message);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}

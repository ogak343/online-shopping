package com.example.onlineshopping.service;

import com.example.onlineshopping.contants.ErrorCode;
import com.example.onlineshopping.exception.CustomerException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

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

    public Mono<Void> sendOtp(Mono<Long> otpCode, String email) {
        return otpCode.flatMap(code ->
                Mono.fromCallable(() -> {
                    MimeMessage message = mailSender.createMimeMessage();
                    try {
                        MimeMessageHelper helper = new MimeMessageHelper(message, true);
                        Context context = new Context();
                        context.setVariable("code", code);
                        helper.setTo(email);
                        helper.setSubject("Account Verification Page");
                        helper.setText(templateEngine.process("AccountVerification.html", context), true);
                        mailSender.send(message);
                    } catch (MessagingException ex) {
                        log.error("Error sending email", ex);
                        throw new CustomerException(ErrorCode.OTP_FAILED);
                    }
                    return null;
                }).subscribeOn(Schedulers.boundedElastic()).then()
        );
    }
}

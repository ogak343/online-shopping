package com.example.onlineshopping.service;

import com.example.onlineshopping.contants.ErrorCode;
import com.example.onlineshopping.exception.CustomException;
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

    public Mono<Void> sendOtp(Long otpCode, String email) {
        return Mono.fromCallable(() -> {
                    MimeMessage message = mailSender.createMimeMessage();
                    MimeMessageHelper helper = new MimeMessageHelper(message, true);
                    Context context = new Context();
                    context.setVariable("otpCode", otpCode);
                    helper.setTo(email);
                    helper.setSubject("Account Verification Page");
                    String emailBody = templateEngine.process("AccountVerification.html", context);
                    helper.setText(emailBody, true);
                    mailSender.send(message);
                    return message;
                })
                .subscribeOn(Schedulers.boundedElastic())
                .doOnSuccess(message -> log.info("Email sent successfully to {}", email))
                .doOnError(ex -> log.error("Error sending email to {}: {}", email, ex.getMessage()))
                .onErrorMap(ex -> new CustomException(ErrorCode.NOTIFICATION_FAILED))
                .then();
    }

}

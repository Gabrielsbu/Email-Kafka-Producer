package com.gabriel.email.domain.services.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabriel.email.domain.models.Mail;
import com.gabriel.email.domain.services.SendMailService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SendMailServiceImpl implements SendMailService {

    private final JavaMailSender emailSender;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "enviar-email", groupId = "Email")
    public void sendEmail(String mensagemKafka) throws Exception {
        this.enviarEmail(this.getModel(mensagemKafka));
        System.out.println(mensagemKafka);
    }

    public void enviarEmail(Mail email) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("botdellead@gmail.com");
        mailMessage.setText(email.getMensagem());
        mailMessage.setTo(email.getEmailDestino());
        mailMessage.setSubject("STATUS DE VENDA");

        this.emailSender.send(mailMessage);
    }

    public Mail getModel(String modelString) throws Exception {
        try {
            return this.objectMapper.readValue(modelString, Mail.class);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
package com.trendyol.notification;

import com.trendyol.basket.externalservice.notification.NotificationService;
import com.trendyol.basket.externalservice.notification.request.NotificationProductInfoRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.List;
import java.util.function.Consumer;

public class NotificationServiceImpl implements NotificationService {
    @Value("${sender.email:placeholder}")
    private String senderEmail;
    private JavaMailSender javaMailSender;

    public NotificationServiceImpl(JavaMailSender javaMailSender){
        this.javaMailSender = javaMailSender;
    }

    //TODO: Localization için bir şeyler düşün
    @Override
    public void sendEmailWhenStockIsCritical(List<String> customerEmails, NotificationProductInfoRequest notificationProductInfo) {
        var subject = "Kritik Stok Uyarısı";
        var message = String.format("Sepetinizdeki %s ürünün stok adedi %d olmuştur. Acele edin kaçırmayın!",
                notificationProductInfo.getTitle(),
                notificationProductInfo.getQuantity()
        );
        customerEmails.forEach(customerEmail -> {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(senderEmail);
            simpleMailMessage.setTo(customerEmail);
            simpleMailMessage.setSubject(subject);
            simpleMailMessage.setText(message);
            javaMailSender.send(simpleMailMessage);
        });
    }

    @Override
    public void sendEmailWhenPriceDrops(List<String> customerEmails, NotificationProductInfoRequest notificationProductInfo) {
        var subject = "Fiyat düştü kaçırmayın!";
        var message = "Sepetinizdeki "+ notificationProductInfo.getTitle()+" ürünün fiyatı düşmüştür. " +
                "Eski fiyat: "+notificationProductInfo.getOldPrice()+ " " +
                "Yeni fiyat: "+notificationProductInfo.getPrice();
        customerEmails.forEach(customerEmail -> {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(senderEmail);
            simpleMailMessage.setTo(customerEmail);
            simpleMailMessage.setSubject(subject);
            simpleMailMessage.setText(message);
            javaMailSender.send(simpleMailMessage);
        });
    }
}

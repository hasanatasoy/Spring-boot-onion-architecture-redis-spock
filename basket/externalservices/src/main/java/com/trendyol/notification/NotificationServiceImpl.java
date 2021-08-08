package com.trendyol.notification;

import com.trendyol.basket.externalservice.notification.NotificationService;
import com.trendyol.basket.externalservice.notification.model.request.NotificationProductInfoRequest;
import com.trendyol.basket.externalservice.notification.model.request.StockNotificationType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {
    //@Value(value = "${spring.mail.username}")
    private String senderEmail = "droppler.ty@gmail.com";
    private final JavaMailSender javaMailSender;

    public NotificationServiceImpl(JavaMailSender javaMailSender){
        this.javaMailSender = javaMailSender;
    }

    //TODO: Localization için bir şeyler düşün
    //TODO: Type'a göre factory kullan, type'ı enum yap
    @Override
    public void sendEmailWhenStockIsCriticalOrUnAvaiable(List<String> customerEmails, NotificationProductInfoRequest notificationProductInfo, StockNotificationType type) {
        String subject;
        if(type.equals(StockNotificationType.Critic)){
            subject = "Kritik Stok Uyarısı";
        }
        else if(type.equals(StockNotificationType.UnAvaiable)){
            subject = "Sepetinizdeki ürünün stokları tükendi";
        }
        else{
            subject = "Stok Uyarısı";
        }
        var message = "Sepetinizdeki "+notificationProductInfo.getTitle()+" isimli ürünün stok adedi "+notificationProductInfo.getQuantity()+" olmuştur.Bilginize!";
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
        var message = "Sepetinizdeki "+ notificationProductInfo.getTitle()+" isimli ürünün fiyatı düşmüştür. " +
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

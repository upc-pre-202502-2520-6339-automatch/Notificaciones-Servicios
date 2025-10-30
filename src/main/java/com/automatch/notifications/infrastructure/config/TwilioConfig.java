package com.automatch.notifications.infrastructure.config;

import com.twilio.Twilio;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TwilioConfig {

    @Value("${TWILIO_ACCOUNT_SID:dummy_sid}")
    private String accountSid;

    @Value("${TWILIO_AUTH_TOKEN:dummy_token}")
    private String authToken;

    @Value("${TWILIO_PHONE_NUMBER:+10000000000}")
    private String phoneNumber;

    @Value("${notifications.twilio.enabled:false}")
    private boolean twilioEnabled;

    @PostConstruct
    public void init() {
        try {
            Twilio.init(accountSid, authToken);
            if (twilioEnabled) {
                System.out.println("Twilio inicializado correctamente con credenciales reales.");
            } else {
                System.out.println("Twilio en modo SIMULADO (mock). No se enviarán mensajes reales.");
            }
        } catch (Exception e) {
            System.err.println("Error al inicializar Twilio: " + e.getMessage());
        }
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public boolean isTwilioEnabled() {
        return twilioEnabled;
    }
}
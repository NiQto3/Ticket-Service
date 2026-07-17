package com.example.manager.service;

import com.example.manager.dto.PaymentRequest;
import com.example.manager.dto.PaymentResponse;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.loolzaaa.youkassa.client.ApiClient;
import ru.loolzaaa.youkassa.client.ApiClientBuilder;
import ru.loolzaaa.youkassa.model.Payment;
import ru.loolzaaa.youkassa.pojo.Amount;
import ru.loolzaaa.youkassa.pojo.Confirmation;
import ru.loolzaaa.youkassa.pojo.Currency;
import ru.loolzaaa.youkassa.processors.PaymentProcessor;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class PaymentService {

    @Value("${yookassa.shopId}")
    private String shopId;

    @Value("${yookassa.secretKey}")
    private String secretKey;

    @Value("${yookassa.returnUrl}")
    private String returnUrl;

    private PaymentProcessor paymentProcessor;

    @PostConstruct
    public void init() {
        ApiClient client = ApiClientBuilder.newBuilder()
                .configureBasicAuth(shopId, secretKey)
                .build();

        this.paymentProcessor = new PaymentProcessor(client);
    }

    public PaymentResponse createPayment(PaymentRequest request, int userId, int eventTicketId) {
        String idempotenceKey = UUID.randomUUID().toString();

        Payment newPayment = Payment.builder()
                .amount(Amount.builder()
                        .value(String.valueOf(request.getPrice()))
                        .currency(Currency.BYN)
                        .build())
                .description(request.getDescription())
                .confirmation(Confirmation.builder()
                        .type(Confirmation.Type.REDIRECT)
                        .returnUrl(returnUrl)
                        .build())
                .metadata(Map.of(
                        "userId", String.valueOf(userId),
                        "eventId", String.valueOf(request.getEventId()),
                        "eventTicketId", String.valueOf(eventTicketId)
                ))
                .build();

        try {
            Payment payment = paymentProcessor.create(newPayment, idempotenceKey);

            PaymentResponse response = new PaymentResponse();
            response.setPaymentId(payment.getId());
            response.setStatus(payment.getStatus());

            if (payment.getConfirmation() != null && payment.getConfirmation().getType() == Confirmation.Type.REDIRECT) {
                response.setConfirmationURL(payment.getConfirmation().getReturnUrl());
            }

            return response;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create payment", e);
        }
    }

}

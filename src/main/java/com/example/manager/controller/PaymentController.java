package com.example.manager.controller;

import com.example.manager.dto.EventTicketDTO;
import com.example.manager.dto.PaymentRequest;
import com.example.manager.dto.PaymentResponse;
import com.example.manager.dto.TicketSaleDTO;
import com.example.manager.security.UserDetailsInfo;
import com.example.manager.service.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.time.OffsetDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "${cors.url}", maxAge = 3600, allowCredentials = "true")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class PaymentController {

    private final PaymentService paymentService;
    private final TicketSaleService ticketSaleService;
    private final EventTicketService eventTicketService;
    private final EmailService emailService;

    @PostMapping
    public ResponseEntity<PaymentResponse> createPayment(@Valid @RequestBody EventTicketDTO eventTicketDTO,
                                                         BindingResult bindingResult,@RequestBody(required = false) String description,
                                                         @AuthenticationPrincipal UserDetailsInfo userDetails) {
        ErrorHandler.checkForErrors(bindingResult, "Payment creation failed");

        if (description.isEmpty()) {
            description = "Default description";
        }

        PaymentRequest request = PaymentRequest.builder()
                .price(eventTicketDTO.getPrice())
                .eventId(eventTicketDTO.getEventId())
                .description(description)
                .build();

        PaymentResponse response = paymentService.createPayment(request, userDetails.getId(), eventTicketDTO.getId());
        ticketSaleService.updateReserved(eventTicketDTO, 1);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/callback")
    public ResponseEntity<Void> handlePaymentCallback(@RequestBody String requestBody) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(requestBody);
            JsonNode jsonNode = root.path("metadata");

            if (jsonNode.isMissingNode()) {
                String description = root.path("description").asText();
                throw new RuntimeException(description);
            }

            Map<String, String> metadata =
                    mapper.convertValue(jsonNode, new TypeReference<>() {
                    });

            EventTicketDTO eventTicketDTO =  eventTicketService.
                    getById(Integer.parseInt(metadata.get("eventTicketId")));

            ticketSaleService.addSold(eventTicketDTO);

            TicketSaleDTO ticketSaleDTO = TicketSaleDTO.builder().
                    id(metadata.get("id")).
                    eventTicket(eventTicketDTO.getId()).
                    saleDateTime(OffsetDateTime.parse(metadata.get("created_at"))).
                    customerId(Integer.valueOf(metadata.get("userId"))).
                    salePrice(eventTicketDTO.getPrice()).
                    build();
            ticketSaleService.createTicketSale(ticketSaleDTO);

            emailService.sendEmail(metadata.get("userId"), "Subject here",
                    "Congratulations, you have purchased a ticket!");

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}

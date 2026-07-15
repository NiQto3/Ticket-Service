package com.example.manager.controller;

import com.example.manager.dto.EventTicketDTO;
import com.example.manager.dto.TicketSaleDTO;
import com.example.manager.dto.UserDTO;
import com.example.manager.service.TicketSaleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.OffsetDateTime;

@RestController
@RequestMapping("/ticket_sale")
@CrossOrigin(origins = "${cors.url}", maxAge = 3600, allowCredentials = "true")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class TicketSaleController {

    private final TicketSaleService ticketSaleService;

    @PutMapping("/")
    public TicketSaleDTO createTicketSale (@Valid @RequestBody TicketSaleDTO ticketSaleDTO, BindingResult bindingResult) {
        ErrorHandler.checkForErrors(bindingResult, "Ticket sale creation failed");
        return ticketSaleService.createTicketSale(ticketSaleDTO);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    public TicketSaleDTO performPurchase (@PathVariable int id, @Valid @RequestBody EventTicketDTO eventTicketDTO,
                                          BindingResult bindingResult) {
        ErrorHandler.checkForErrors(bindingResult, "Ticket purchase failed");

        //reserve ticket
        ticketSaleService.UpdateReserved(eventTicketDTO, 1);

        //here will be purchase attempt
        //if succseeded
        ticketSaleService.AddSold(eventTicketDTO);
        TicketSaleDTO ticketSaleDTO = TicketSaleDTO.builder().
                eventTicket(eventTicketDTO.getId()).
                saleDateTime(OffsetDateTime.from(Instant.now())).
                customerId(id).
                salePrice(eventTicketDTO.getPrice()).
                build();
        ticketSaleService.createTicketSale(ticketSaleDTO);
        //also need to write message send

        //if not
        ticketSaleService.UpdateReserved(eventTicketDTO, -1);

        return ticketSaleDTO;
    }

}

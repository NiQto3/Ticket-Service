package com.example.manager.controller;

import com.example.manager.dto.TicketSaleDTO;
import com.example.manager.service.ErrorHandler;
import com.example.manager.service.TicketSaleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ticket_sale")
@CrossOrigin(origins = "${cors.url}", maxAge = 3600, allowCredentials = "true")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class TicketSaleController {

    private final TicketSaleService ticketSaleService;

    @PutMapping("/create")
    public TicketSaleDTO createTicketSale (@Valid @RequestBody TicketSaleDTO ticketSaleDTO, BindingResult bindingResult) {
        ErrorHandler.checkForErrors(bindingResult, "Ticket sale creation failed");
        return ticketSaleService.createTicketSale(ticketSaleDTO);
    }

}

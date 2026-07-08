package com.example.manager.service;

import com.example.manager.dto.TicketSaleDTO;
import com.example.manager.model.TicketSale;
import com.example.manager.repository.TicketSaleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class TicketSaleService {

    private final TicketSaleRepository ticketSaleRepository;
    private final EventTicketService eventTicketService;
    private final UserService userService;

    @Transactional
    public TicketSale createTicketSale (TicketSaleDTO ticketSaleDTO) {

        TicketSale ticketSale = TicketSale.builder()
                .salePrice(ticketSaleDTO.getSalePrice())
                .saleDateTime(ticketSaleDTO.getSaleDateTime())
                .customer(userService.findUserById(ticketSaleDTO.getCustomerId()))
                .eventTicket(eventTicketService.findEventTicketById(ticketSaleDTO.getEventTicket()))
                .build();
        ticketSaleRepository.save(ticketSale);

        return ticketSale;
    }

}

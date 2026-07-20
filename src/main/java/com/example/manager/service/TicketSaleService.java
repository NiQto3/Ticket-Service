package com.example.manager.service;

import com.example.manager.dto.EventTicketDTO;
import com.example.manager.dto.TicketSaleDTO;
import com.example.manager.mapper.TicketSaleMapper;
import com.example.manager.model.TicketSale;
import com.example.manager.repository.EventTicketRepository;
import com.example.manager.repository.TicketSaleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class TicketSaleService {

    private final TicketSaleRepository ticketSaleRepository;
    private final EventTicketService eventTicketService;
    private final UserService userService;
    private final TicketSaleMapper ticketSaleMapper;

    @Transactional
    public TicketSaleDTO createTicketSale (TicketSaleDTO ticketSaleDTO) {

        TicketSale ticketSale = TicketSale.builder()
                .salePrice(ticketSaleDTO.getSalePrice())
                .saleDateTime(ticketSaleDTO.getSaleDateTime())
                .customer(userService.findUserById(ticketSaleDTO.getCustomerId()))
                .eventTicket(eventTicketService.findEventTicketById(ticketSaleDTO.getEventTicket()))
                .build();
        return ticketSaleMapper.toDto(ticketSaleRepository.save(ticketSale));
    }

    @Transactional
    public EventTicketDTO updateReserved (EventTicketDTO eventTicketDTO, Integer number) {
        return eventTicketService.updateReserved(eventTicketDTO, number);
    }

    @Transactional
    public EventTicketDTO addSold (EventTicketDTO eventTicketDTO) {
        eventTicketDTO = eventTicketService.updateReserved(eventTicketDTO, -1);
        return eventTicketService.updateSold(eventTicketDTO, 1);
    }

}

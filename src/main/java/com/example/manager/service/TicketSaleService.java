package com.example.manager.service;

import com.example.manager.dto.EventTicketDTO;
import com.example.manager.dto.TicketSaleDTO;
import com.example.manager.mapper.TicketSaleMapper;
import com.example.manager.model.TicketSale;
import com.example.manager.repository.TicketSaleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class TicketSaleService {

    private final TicketSaleRepository ticketSaleRepository;
    private final EventTicketService eventTicketService;
    private final UserService userService;
    private final TicketSaleMapper ticketSaleMapper;

    @Transactional
    @PreAuthorize("hasRole('ADMIN') or hasRole('EVENT_MANAGER')")
    public TicketSaleDTO createTicketSale (TicketSaleDTO ticketSaleDTO) {

        TicketSale ticketSale = ticketSaleMapper.toEntity(ticketSaleDTO);
        ticketSale.setCustomer(userService.findUserById(ticketSaleDTO.getCustomerId()));
        ticketSale.setEventTicket(eventTicketService.getEventTicketById(ticketSaleDTO.getEventTicket()));

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

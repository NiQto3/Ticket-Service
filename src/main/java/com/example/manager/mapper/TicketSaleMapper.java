package com.example.manager.mapper;

import com.example.manager.dto.TicketSaleDTO;
import com.example.manager.model.TicketSale;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TicketSaleMapper {

    @Mapping(target = "eventId", source = "eventTicket.event.id")
    @Mapping(target = "customerId", source = "customer.id")
    TicketSaleDTO toDto(TicketSale sale);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "eventTicket", ignore = true)
    @Mapping(target = "customer", ignore = true)
    TicketSale toEntity(TicketSaleDTO dto);
}
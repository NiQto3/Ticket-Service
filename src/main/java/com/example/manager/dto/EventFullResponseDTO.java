package com.example.manager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventFullResponseDTO {
    private Integer id;
    private LocalDateTime datetime;
    private UserDTO author;
    private EventLocationDTO location;
    private EventTicketDataDTO ticketData;
}

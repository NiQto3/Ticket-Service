package com.example.manager.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketSoldDataDTO {
    //TODO give access to this only for admins and event creators
    private Integer soldId;

    @NotNull(message = "Event ID is required")
    private Integer eventId;

    private LocalDateTime soldDateTime;

    @NotNull(message = "Buyer ID is required")
    private Integer soldToUserId;

    @NotNull(message = "Sold price is required")
    @Min(value = 0, message = "Sold price must be non-negative")
    private BigDecimal soldPrice;
}

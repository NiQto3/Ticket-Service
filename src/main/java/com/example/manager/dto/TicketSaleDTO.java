package com.example.manager.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketSaleDTO {
    private Integer id;

    @NotNull(message = "Event ID is required")
    private Integer eventTicket;

    private OffsetDateTime saleDateTime;

    @NotNull(message = "Customer ID is required")
    private Integer customerId;

    @NotNull(message = "Sold price is required")
    @Min(value = 0, message = "Sold price must be non-negative")
    private BigDecimal salePrice;
}

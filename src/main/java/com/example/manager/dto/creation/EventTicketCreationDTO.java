package com.example.manager.dto.creation;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventTicketCreationDTO {

    @NotNull(message = "Event ID is required")
    private Integer eventId;

    @NotNull(message = "Price is required")
    @Min(value = 0, message = "Price must be non-negative")
    private BigDecimal price;

    @NotNull(message = "Number of tickets is required")
    @Min(value = 1, message = "At least one ticket must be available")
    private Integer totalQuantity;
}

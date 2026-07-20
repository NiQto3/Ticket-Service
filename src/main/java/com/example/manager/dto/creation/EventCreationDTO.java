package com.example.manager.dto.creation;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventCreationDTO {
    @NotNull(message = "Event date and time is required")
    @FutureOrPresent(message = "Event datetime must be present or future")
    private OffsetDateTime datetime;

    @NotNull(message = "Organizer ID is required")
    private Integer organizerId;

    @NotNull(message = "Location ID is required")
    private Integer locationId;
}

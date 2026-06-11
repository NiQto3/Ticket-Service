package com.example.manager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ticket_sold_data")
public class TicketSoldData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sold_id")
    private Integer soldId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @CreationTimestamp
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "sold_datetime", nullable = false)
    private LocalDateTime soldDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sold_to", nullable = false)
    private User soldTo;

    @Column(name = "sold_price", nullable = false, columnDefinition = "MONEY")
    private BigDecimal soldPrice;
}

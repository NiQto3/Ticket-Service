package com.example.manager.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "event_tickets")
@Check(constraints = "total_quantity >= 0")
@Check(constraints = "reserved_quantity >= 0")
@Check(constraints = "sold_quantity >= 0")
@Check(constraints = "reserved_quantity + sold_quantity <= total_quantity")
public class EventTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "total_quantity", nullable = false)
    @Min(0)
    private Integer totalQuantity;

    @Builder.Default
    @Column(name = "reserved_quantity", nullable = false)
    private Integer reservedQuantity = 0;

    @Builder.Default
    @Column(name = "sold_quantity", nullable = false)
    private Integer soldQuantity = 0;
}

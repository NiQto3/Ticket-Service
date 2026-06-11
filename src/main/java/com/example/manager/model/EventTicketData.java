package com.example.manager.model;

import jakarta.persistence.*;
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
@Table(name = "event_ticket_data")
@Check(constraints = "reserved_num + sold_num <= num")
public class EventTicketData {
    @Id
    @Column(name = "event_id")
    private Integer eventId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "event_id")
    private Event event;

    @Column(nullable = false, columnDefinition = "MONEY")
    private BigDecimal price;

    @Column(nullable = false)
    private Integer num;

    @Builder.Default
    @Column(nullable = false, name = "reserved_num")
    private Integer reservedNum = 0;

    @Builder.Default
    @Column(nullable = false, name = "sold_num")
    private Integer soldNum = 0;
}

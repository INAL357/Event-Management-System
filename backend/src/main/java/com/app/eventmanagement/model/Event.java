package com.app.eventmanagement.model;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 1500)
    private String description;

    @Column(nullable = false)
    private LocalDateTime eventDate;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer totalTickets;

    @Column(nullable = false)
    private Integer availableTickets;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = true)
    private User createdBy;

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}

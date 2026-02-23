package com.app.eventmanagement.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
@Entity
@Table(name = "bookings")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Booking belongs to a user
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Booking belongs to an event
    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @Column(nullable = false)
    private Long amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus paymentStatus;

    private String paymentId;

    private LocalDateTime bookingDate = LocalDateTime.now();

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
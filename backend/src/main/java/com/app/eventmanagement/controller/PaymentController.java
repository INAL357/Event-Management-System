package com.app.eventmanagement.controller;

import com.app.eventmanagement.services.PaymentServices;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentServices PaymentServices;

    @PostMapping("/{bookingId}")
    public String makePayment(@PathVariable Long bookingId) {

        return PaymentServices.processPayment(bookingId);
    }
}

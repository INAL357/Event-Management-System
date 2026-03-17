package com.app.eventmanagement.controller;

import com.app.eventmanagement.Repositories.BookingRepository;
import com.app.eventmanagement.model.Booking;
import com.app.eventmanagement.model.PaymentStatus;
import com.app.eventmanagement.services.QRCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final QRCodeService qrCodeService;
    private final BookingRepository bookingRepository;

    @GetMapping(value = "/{bookingId}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getTicket(@PathVariable Long bookingId) throws Exception {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (booking.getPaymentStatus() != PaymentStatus.SUCCESS) {
            throw new RuntimeException("Payment required before generating ticket");
        }

        String ticketData = "BookingId:" + booking.getId() +
                ",User:" + booking.getUser().getUsername() +
                ",Event:" + booking.getEvent().getName() +
                ",Qty:" + booking.getQuantity();

        return qrCodeService.generateQRCode(ticketData);
    }

    @PostMapping("/validate/{bookingId}")
    public String validateTicket(@PathVariable Long bookingId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (booking.isUsed()) {
            return "Ticket already used";
        }

        booking.setUsed(true);
        bookingRepository.save(booking);

        return "Ticket validated successfully";
    }
}
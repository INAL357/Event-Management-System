package com.app.eventmanagement.services;

import com.app.eventmanagement.Repositories.BookingRepository;
import com.app.eventmanagement.model.Booking;
import com.app.eventmanagement.model.PaymentStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PaymentServicesImpl implements PaymentServices {

    private final BookingRepository bookingRepository;


    @Override
    public String processPayment(Long bookingId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (booking.getPaymentStatus() == PaymentStatus.SUCCESS) {
            throw new RuntimeException("Payment already completed");
        }

        booking.setPaymentStatus(PaymentStatus.SUCCESS);

        bookingRepository.save(booking);

        return "Payment successful";
    }
}

package com.app.eventmanagement.services;

import com.app.eventmanagement.dto.BookingResponse;
import com.app.eventmanagement.model.Booking;

import java.util.List;

public interface BookingService {

//    Booking bookEvent(Long eventId,int quantity);

    List<BookingResponse>getUserBooking();

    Booking  cancelBooking(Long bookingId);

    BookingResponse bookEvents(Long eventId, int quantity);


}

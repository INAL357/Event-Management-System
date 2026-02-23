package com.app.eventmanagement.Repositories;

import com.app.eventmanagement.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking,Long> {

}

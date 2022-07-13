package com.infosys.timd.bioskopapi.Service;


import com.infosys.timd.bioskopapi.Model.*;
import java.util.List;
import java.util.Optional;

public interface BookingService {

    List<Booking> getAll();
    Optional<Booking> getBookingById(Long Id);
    Booking createBooking(Booking booking);
    void deleteSBookingById(Long Id);
    Booking updateBooking(Booking booking);
    Booking getReferenceById (Long Id);
    List<Booking> getBookingByFilmName(String name);

}

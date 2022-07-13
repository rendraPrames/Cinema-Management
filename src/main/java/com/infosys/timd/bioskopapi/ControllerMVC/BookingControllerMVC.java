package com.infosys.timd.bioskopapi.ControllerMVC;


import com.infosys.timd.bioskopapi.Controller.BookingController;
import com.infosys.timd.bioskopapi.Exception.ResourceNotFoundException;
import com.infosys.timd.bioskopapi.Model.Booking;
import com.infosys.timd.bioskopapi.Model.Schedule;
import com.infosys.timd.bioskopapi.Service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
public class BookingControllerMVC {
    @Autowired
    private BookingService bookingService;

    @GetMapping("/booking")
    public String showBooking(Model model){
        List<Booking> bookings = bookingService.getAll();
        Collections.reverse(bookings);
        model.addAttribute("bookings", bookings);

        return "booking";
    }

    @GetMapping("/booking/detail/{bookingId}")
    public String showBooking(@PathVariable("bookingId") long Id, Model model){
        Optional<Booking> bookingget = bookingService.getBookingById(Id);
        Booking bookings = bookingget.get();
        model.addAttribute("bookings", bookings);
        return "booking_detail";
    }

    @GetMapping("/booking/new")
    public String showNewBooking(Model model) {
        model.addAttribute("booking", new Booking());
        model.addAttribute("pageTitle", "Add New Booking");

        return "booking_form";
    }

    @PostMapping("/booking/save")
    public String saveBooking(Booking booking, RedirectAttributes ra){
        bookingService.createBooking(booking);
        ra.addFlashAttribute("message", "Booking saved successfully");

        return "redirect:/booking";
    }

    @GetMapping("/booking/edit/{bookingId}")
    public String showBookingEdit(@PathVariable("bookingId") long Id, Model model, RedirectAttributes ra) {
        try {
            Optional<Booking> booking = bookingService.getBookingById(Id);
            model.addAttribute("booking", booking);
            model.addAttribute("pageTitle", "Edit Booking ID: " + Id );
            return "booking_form";
        } catch (ResourceNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());

            return "redirect:/booking";
        }
    }

    @GetMapping("/booking/delete/{bookingId}")
    public String showBookingDelete(@PathVariable("bookingId") long Id, RedirectAttributes ra) {
        try {
            bookingService.deleteSBookingById(Id);
            ra.addFlashAttribute("message", "Successfully deleted schedule.");
        } catch (ResourceNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }

        return "redirect:/booking";
    }
}

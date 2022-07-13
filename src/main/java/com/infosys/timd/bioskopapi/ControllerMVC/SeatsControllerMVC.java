package com.infosys.timd.bioskopapi.ControllerMVC;

import com.infosys.timd.bioskopapi.Model.Seats;
import com.infosys.timd.bioskopapi.Service.SeatsService;
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
public class SeatsControllerMVC {
    @Autowired
    private SeatsService seatsService;

    @GetMapping("/seats")
    public String showSeats(Model model) {
        List<Seats> seats = seatsService.findAllseats();
        Collections.reverse(seats);
        model.addAttribute("seats", seats);

        return "seatsPage";
    }

    @GetMapping("/seats/new")
    public String showNewForm(Model model) {
        model.addAttribute("seats", new Seats());
        model.addAttribute("pageTitle", "ADD NEW SEAT");

        return "seats_forms";
    }

    @PostMapping("/seats/save")
    public String createseat(Seats seat, RedirectAttributes ra) {
        seatsService.createseat(seat);
        ra.addFlashAttribute("message", "Seat Added");

        return "redirect:/seatsPage";
    }

    @GetMapping("/seats/update/{seatId}")
    public String showUpdateForm(@PathVariable("seatId") Long seatId, Model model, RedirectAttributes ra) {
        try {
            Optional<Seats> seat = seatsService.findbyid(seatId);
            model.addAttribute("seats", seat);
            model.addAttribute("pageTitle", "Update Seats (ID: " + seatId + ")");
            ra.addFlashAttribute("message", "Data Modified");
            return "seats_forms";
        } catch (Exception e) {
            ra.addFlashAttribute("message", e.getMessage());

            return "redirect:/seatsPage";
        }
    }

    @GetMapping("/seats/delete/{seatId}")
    public String deleteSeat(@PathVariable("seatId") Long seatId, RedirectAttributes ra) {
        try {
            seatsService.deleteseat(seatId);
        } catch (Exception e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/seatsPage";
    }


    @GetMapping("seats/search")
    public String searchSeats(Model model,Integer isAvailable){

        List<Seats> seats = seatsService.getSeatAvailableNew(isAvailable);

        model.addAttribute("seats", seats);

        return "seats_searchs";
    }

//    pembatas

    @GetMapping("/seatsPage")
    public  String getAllSeats(Model model) {
        int totalSeats;
        List<Seats> seats = seatsService.findAllseats();
        totalSeats = seats.size();
        Collections.reverse(seats);
        model.addAttribute("seats", seats);
        model.addAttribute("totalSeats", totalSeats);

        return "seatsPage.html";
//        return findPaginatedSeats(1, model);
    }
//
//    @GetMapping("/oageSeat/{pageNo}")
//    public String findPaginatedSeats(PathVariable(value = "pageNoSeat") M)
}


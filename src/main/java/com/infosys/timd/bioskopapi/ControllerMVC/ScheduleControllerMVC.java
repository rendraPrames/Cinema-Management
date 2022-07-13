package com.infosys.timd.bioskopapi.ControllerMVC;

import com.infosys.timd.bioskopapi.Exception.ResourceNotFoundException;
import com.infosys.timd.bioskopapi.Model.Films;
import com.infosys.timd.bioskopapi.Model.Schedule;
import com.infosys.timd.bioskopapi.Service.FilmsService;
import com.infosys.timd.bioskopapi.Service.ScheduleService;
import com.infosys.timd.bioskopapi.Service.ScheduleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Controller
public class ScheduleControllerMVC {
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private FilmsService filmsService;
//    @Autowired
//    private ScheduleServiceImpl scheduleServiceImpl;

    @GetMapping("/schedulePage")
    public String showSchedule(Model model) {
//        List<Schedule> schedules = scheduleService.getAll();
//        Collections.reverse(schedules);
//        model.addAttribute("schedules", schedules);
        return findPaginatedSchedule(1, model);

//        return "schedule";
    }

    @GetMapping("/schedule/new")
    public String showNewSchedule(Model model) {
        List<Films> films = this.filmsService.findAllFilms();
        model.addAttribute("films", films);
        model.addAttribute("schedules", new Schedule());
        model.addAttribute("pageTitle", "Add New Schedules");

        return "schedule_form";
    }

    @PostMapping("/schedule/save")
    public String saveSchedule(Model model, RedirectAttributes ra, @ModelAttribute Schedule schedules){
        scheduleService.createSchedule(schedules);
        ra.addFlashAttribute("message", "Schedule saved successfully");

        return "redirect:/schedulePage";
    }

    @GetMapping("/schedulePage/edit/{scheduleId}")
    public String showEditForm(@PathVariable("scheduleId") Integer id, Model model, RedirectAttributes ra) {
        try {
            Optional<Schedule> schedules = scheduleService.getScheduleById(id);
            List<Films> films = this.filmsService.findAllFilms();
            model.addAttribute("films", films);
            model.addAttribute("schedules", schedules);
            model.addAttribute("pageTitle", "Edit Schedule (ID: " + id + ")");
            return "schedule_form";
        } catch (ResourceNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());

            return "redirect:/schedulePage";
        }
    }

    @GetMapping("/schedule/delete/{scheduleId}")
    public String showDeleteForm(@PathVariable("scheduleId") Integer id, RedirectAttributes ra) {
        try {
            scheduleService.deleteScheduleById(id);
            ra.addFlashAttribute("message", "Successfully deleted schedule.");
        } catch (ResourceNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/schedulePage";
    }

    @PostMapping("/search")
    public String search(Films film, Model model, String name) {
        if(name!=null) {
            List<Schedule> list = scheduleService.getScheduleByFilmNameLike(film.getName());
            model.addAttribute("list", list);
        }else {
            List<Schedule> list = scheduleService.getAll();
            model.addAttribute("list", list);}
        return "scheduleSearchPage";
    }

    @GetMapping("/page/schedule/{pageNo}")
    public String findPaginatedSchedule(@PathVariable (value = "pageNo") int pageNo, Model model){
        int pageSize = 10;

        Page<Schedule> page = scheduleService.findPaginatedSchedule(pageNo, pageSize);
        List<Schedule> listSchdule = page.getContent();
//        Collections.reverse(listSchdule);

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listSchdule", listSchdule);
        return "/schedulePage";
    }
}

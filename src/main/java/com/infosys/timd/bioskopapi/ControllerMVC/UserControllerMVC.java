package com.infosys.timd.bioskopapi.ControllerMVC;

import com.infosys.timd.bioskopapi.Exception.ResourceNotFoundException;
import com.infosys.timd.bioskopapi.Model.Films;
import com.infosys.timd.bioskopapi.Model.Schedule;
import com.infosys.timd.bioskopapi.Model.User;
import com.infosys.timd.bioskopapi.Repository.UserRepository;
import com.infosys.timd.bioskopapi.Service.UserServiceImplements;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class UserControllerMVC {

    private final UserServiceImplements userServiceImplements;
    private final UserRepository userRepository;

    @GetMapping("/usersPage")
    public String getAllUser(Model model) {
//        model.addAttribute("listUsers", userServiceImplements.getAll());
        return findPaginated(1, model);
    }
    @GetMapping("")
    public String getAllUserIndex(Model model) {
//        model.addAttribute("listUsers", userServiceImplements.getAll());
        return findPaginated(1, model);
    }

    @GetMapping("/newUserForm")
    public String newUserForm(Model model){
        User user = new User();
        model.addAttribute("user", user);
        return "newUser";
    }

    @PostMapping ("/saveUser")
    public String saveUser(@ModelAttribute("user") User user){
        userServiceImplements.createUser(user);
        return "redirect:/usersPage";
    }

    @GetMapping("/showFormForUpdate/{usersId}")
    public String showFormForUpdate(@PathVariable (value = "usersId") Long usersId, Model model ){
        User user = userServiceImplements.getUserId(usersId);
        model.addAttribute("user", user);
        return "/editUser";
    }

    @GetMapping("/delete/{usersId}")
    public String deleteUser(@PathVariable("usersId") Long users_Id, RedirectAttributes ra) {
        try {
            userServiceImplements.deleteUserById(users_Id);
            ra.addFlashAttribute("message", "Successfully deleted user!");
        } catch (ResourceNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/usersPage";
    }

    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable (value = "pageNo") int pageNo, Model model){
        int pageSize = 10;

        Page<User> page = userServiceImplements.findPaginated(pageNo, pageSize);
        List<User> listUsers = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listUsers", listUsers);
        return "/usersPage";
    }

    @GetMapping("/search")
    public String search(User user, Model model, String keyword) {
        if(keyword!=null) {
            List<User> list = userServiceImplements.getByKeyword(user.getUsername());
            model.addAttribute("list", list);
        }else {
            List<User> list = userServiceImplements.getAll();
            model.addAttribute("list", list);}
        return "/usersPage";
    }
}
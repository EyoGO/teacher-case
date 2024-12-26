package com.eyogo.http.controller;

import com.eyogo.http.dto.UserReadDto;
import com.eyogo.http.entity.Role;
import com.eyogo.http.service.UnitService;
import com.eyogo.http.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/units")
@SessionAttributes({"useCascade", "selectedUserId"})
@RequiredArgsConstructor
public class UnitController {

    private final UnitService unitService;
    private final UserService userService;

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("units", unitService.findAll());
        model.addAttribute("usersSelection", userService.findAllNames());
        return "unit";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("unit", unitService.findById(id).get()); // TODO what if null
        return "singleunit";
    }

    // TODO remove it and at least create separate requests
    @PostMapping
    public String post(Model model,
                       @SessionAttribute UserReadDto user,
                       @RequestParam(required = false) Integer userToWatch,
                       @RequestParam(required = false) Boolean useCascade) {
        if (user.getRole().equals(Role.ADMIN)) {
            if (userToWatch != null) {
                model.addAttribute("selectedUserId", userToWatch);
            }
        }

        if (useCascade != null) {
            model.addAttribute("useCascade", useCascade);
        }
        return "redirect:/units";
    }
}

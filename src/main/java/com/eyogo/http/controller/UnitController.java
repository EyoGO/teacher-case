package com.eyogo.http.controller;

import com.eyogo.http.service.UnitService;
import com.eyogo.http.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/units")
@SessionAttributes({"useCascade"})
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
        model.addAttribute("unit", unitService.findById(id));
        return "singleunit";
    }

    // TODO remove it
//    @PostMapping
//    public String save(@ModelAttribute("unit") Unit unit) {
//    }
}

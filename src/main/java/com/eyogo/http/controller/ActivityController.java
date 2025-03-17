package com.eyogo.http.controller;

import com.eyogo.http.dto.ActivityCreateEditDto;
import com.eyogo.http.dto.ActivityReadDto;
import com.eyogo.http.dto.UnitReadDto;
import com.eyogo.http.dto.UserReadDto;
import com.eyogo.http.service.ActivityService;
import com.eyogo.http.service.UnitService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Controller
@RequestMapping("/activities")
@SessionAttributes({"unit"})
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityService activityService;
    private final UnitService unitService;

    @GetMapping
    protected String activityPage(Model model,
                                  @SessionAttribute Integer selectedUserId,
                                  @SessionAttribute Boolean useCascade,
                                  @RequestParam(value = "unit-id", required = false) Integer unitId) {
        UnitReadDto unitDto = unitService.findById(unitId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Unit with id %d not found", unitId)));
        model.addAttribute("unit", unitDto);
        model.addAttribute("activities", activityService.findByUserAndUnitId(selectedUserId, unitId, useCascade));
        return "activity";
    }

    @PostAuthorize("returnObject.authorId == #user.id")
    @GetMapping("/{id}")
    @ResponseBody
    public ActivityReadDto doGet(@PathVariable Integer id,
                        @SessionAttribute UserReadDto user) {
        Optional<ActivityReadDto> activityOptional = activityService.findActivityById(id);
        return activityOptional
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public String doPost(String action,
                         @SessionAttribute Integer selectedUserId,
                         @SessionAttribute UserReadDto user,
                         @ModelAttribute ActivityCreateEditDto activityCreateEditDto,
                         @SessionAttribute UnitReadDto unit,

                         @RequestParam(required = false) Integer activityId,

                         @RequestParam(required = false) String activityName,
                         @RequestParam(required = false) String description) {
        switch (action) {
            case "add" -> {
                Integer authorId = user.getId();
                ActivityCreateEditDto activityToCreate = ActivityCreateEditDto.builder()
                        .userId(selectedUserId)
                        .unitId(unit.getId()) //TODO maybe add ability to change unit of the activity - it can be done here changing unit.getId() from session to something else
                        .activityName(activityCreateEditDto.getActivityName())
                        .description(activityCreateEditDto.getDescription())
                        .authorId(authorId)
                        .build();
                activityService.create(activityToCreate);
            }
            case "delete" -> activityService.delete(activityId);
            case "update" -> {
                ActivityCreateEditDto activityDto = ActivityCreateEditDto.builder()
                        .activityName(activityName)
                        .description(description)
                        .build();
                activityService.update(activityId, activityDto);
            }
        }
        return "redirect:/activities?unit-id=" + unit.getId();
    }

}

package com.eyogo.http.controller;

import com.eyogo.http.dto.ActivityCreateEditDto;
import com.eyogo.http.dto.ActivityReadDto;
import com.eyogo.http.dto.UnitReadDto;
import com.eyogo.http.dto.UserReadDto;
import com.eyogo.http.service.ActivityService;
import com.eyogo.http.service.UnitService;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/activities")
@SessionAttributes({"unit"})
@RequiredArgsConstructor
public class ActivityController {

    public static final Gson GSON = new Gson();
    private final ActivityService activityService;
    private final UnitService unitService;

    @GetMapping
    protected String activityPage(Model model,
                                  @SessionAttribute Integer selectedUserId,
                                  @SessionAttribute Boolean useCascade,
                                  @RequestParam(value = "unit-id", required = false) Integer unitId) {
        Optional<UnitReadDto> unitDtoOptional = unitService.findById(unitId);
        model.addAttribute("unit", unitDtoOptional.get());//TODO what if null

        //TODO add ability not to specify unit to get all User's activities
        //TODO Consider 2 alternatives: either IF here and when 0 - call another method or pass 0 as parameter and handle it in service
        model.addAttribute("activities", activityService.findByUserAndUnitId(selectedUserId, unitId, useCascade));
        return "activity";
    }

    @GetMapping("/{id}")
    @ResponseBody
    public String doGet(@PathVariable Integer id) {
        Optional<ActivityReadDto> activityOptional = activityService.findStrictedDataById(id);
        return activityOptional
                .map(GSON::toJson)// TODO return raw type, not String
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public String doPost(Model model,
                         String action,
                         @SessionAttribute Integer selectedUserId,
                         @SessionAttribute UserReadDto user,
                         @ModelAttribute ActivityCreateEditDto activityCreateEditDto,
                         @SessionAttribute UnitReadDto unit,

                         @RequestParam(required = false) Integer activityId,

                         @RequestParam(required = false) String activityName,
                         @RequestParam(required = false) String description) throws ServletException, IOException {
        if (action.equals("add")) {//TODO can add/delete through POST request to other users. need to restrict like in JSP
            Integer authorId = user.getId();
            ActivityCreateEditDto activityToCreate = ActivityCreateEditDto.builder()
                    .userId(selectedUserId)
                    .unitId(unit.getId()) //TODO maybe add ability to change unit of the activity - it can be done here changing unit.getId() from session to something else
                    .activityName(activityCreateEditDto.getActivityName())
                    .description(activityCreateEditDto.getDescription())
                    .authorId(authorId)
                    .build();
            activityService.create(activityToCreate);
            return "redirect:/activities?unit-id=" + unit.getId();
        } else if (action.equals("delete")) {
            activityService.delete(activityId);
            return "redirect:/activities?unit-id=" + unit.getId();
        } else if (action.equals("update")) {
            ActivityCreateEditDto activityDto = ActivityCreateEditDto.builder()//TODO should update users or only data fields?
//                    .userId(selectedUserId)
//                    .unitId(req.getParameter("unit-id"))
                    .activityName(activityName)
                    .description(description)
//                    .authorId(authorId)
                    .build();
            activityService.update(activityId, activityDto);
            return "redirect:/activities?unit-id=" + unit.getId();
        }
        return "redirect:/activities?unit-id=" + unit.getId();
    }

}

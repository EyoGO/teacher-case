package com.eyogo.http.controller;

import com.eyogo.http.dto.CreateActivityDto;
import com.eyogo.http.dto.GetActivityDto;
import com.eyogo.http.dto.GetUnitDto;
import com.eyogo.http.dto.GetUserDto;
import com.eyogo.http.exception.ValidationException;
import com.eyogo.http.service.ActivityService;
import com.eyogo.http.service.UnitService;
import com.eyogo.http.util.JspHelper;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
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
        GetUnitDto unitDto = unitService.findById(unitId);
        model.addAttribute("unit", unitDto);

        //TODO add ability not to specify unit to get all User's activities
        //TODO Consider 2 alternatives: either IF here and when 0 - call another method or pass 0 as parameter and handle it in service
        model.addAttribute("activities", activityService.findByUserAndUnitId(selectedUserId, unitId, useCascade));
        return "activity";
    }

    @GetMapping("/{id}")
    @ResponseBody
    public String doGet(@PathVariable Integer id) {
        GetActivityDto getActivityDto = activityService.findStrictedDataById(id);
        return GSON.toJson(getActivityDto); // TODO return raw type, not String
    }

    @PostMapping
    public String doPost(Model model,
                            String action,
                            @SessionAttribute Integer selectedUserId,
                            @SessionAttribute GetUserDto user,
                            @ModelAttribute CreateActivityDto createActivityDto,
                            @SessionAttribute GetUnitDto unit,

                            @RequestParam(required = false) Integer activityId,

                            @RequestParam(required = false) String activityName,
                            @RequestParam(required = false) String description) throws ServletException, IOException {
        if (action.equals("add")) {//TODO can add/delete through POST request to other users. need to restrict like in JSP
            Integer authorId = user.getId();
            CreateActivityDto activityToCreate = CreateActivityDto.builder()
                    .userId(selectedUserId)
                    .unitId(unit.getId()) //TODO maybe add ability to change unit of the activity - it can be done here changing unit.getId() from session to something else
                    .activityName(createActivityDto.getActivityName())
                    .description(createActivityDto.getDescription())
                    .authorId(authorId)
                    .build();
            try {
                activityService.create(activityToCreate);
                return "redirect:/activities?unit-id=" + unit.getId();
            } catch (ValidationException exception) {
                model.addAttribute("errors", exception.getErrors()); // TODO make no sense with redirect?
                return "redirect:/activities?unit-id=" + unit.getId();
//                doGet(req, resp); // TODO investigate analogue
            }
        } else if (action.equals("delete")) {
            activityService.delete(activityId);
            return "redirect:/activities?unit-id=" + unit.getId();
        } else if (action.equals("update")) {
            CreateActivityDto activityDto = CreateActivityDto.builder()//TODO should update users or only data fields?
//                    .userId(selectedUserId)
//                    .unitId(req.getParameter("unit-id"))
                    .activityName(activityName)
                    .description(description)
//                    .authorId(authorId)
                    .build();
            try {
                activityService.update(activityId, activityDto);
                return "redirect:/activities?unit-id=" + unit.getId();
            } catch (ValidationException exception) {
                model.addAttribute("errors", exception.getErrors()); // TODO make no sense with redirect?
                return "redirect:/activities?unit-id=" + unit.getId();
//                doGet(req, resp); // TODO investigate analogue
            }
        }
        return "redirect:/activities?unit-id=" + unit.getId();
    }

}

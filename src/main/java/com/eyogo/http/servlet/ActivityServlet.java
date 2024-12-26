package com.eyogo.http.servlet;

import com.eyogo.http.dto.ActivityReadDto;
import com.eyogo.http.dto.UnitReadDto;
import com.eyogo.http.service.ActivityService;
import com.eyogo.http.service.UnitService;
import com.eyogo.http.util.JspHelper;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@WebServlet("/activity")
public class ActivityServlet extends HttpServlet {

    @Autowired
    private ActivityService activityService;
    private final UnitService unitService = UnitService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if (StringUtils.isNotBlank(id)) {
            int i = Integer.parseInt(id);
            Optional<ActivityReadDto> activityOptional = activityService.findStrictedDataById(i);
            String json = new Gson().toJson(activityOptional.get());//TODO what if empty
            PrintWriter out = resp.getWriter();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            out.write(json);
            out.flush();
        } else {
            Integer selectedUserId = (Integer) req.getSession().getAttribute("selectedUserId");
            boolean useCascade = (boolean) req.getSession().getAttribute("useCascade");

            String unitIdString = req.getParameter("unit-id");
            int unitId = 0;
            if (StringUtils.isNotBlank(unitIdString)) {
                unitId = Integer.parseInt(unitIdString);
            }

            Optional<UnitReadDto> unitReadDtoOptional = unitService.findById(unitId);
            req.getSession().setAttribute("unit", unitReadDtoOptional.get()); // TODO what if null

            //TODO add ability not to specify unit to get all User's activities
            //TODO Consider 2 alternatives: either IF here and when 0 - call another method or pass 0 as parameter and handle it in service
            req.setAttribute("activities", activityService.findByUserAndUnitId(selectedUserId, unitId, useCascade));

            req.getRequestDispatcher(JspHelper.getPath("activity"))
                    .forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        if (req.getParameter("action").equals("add")) {//TODO can add/delete through POST request to other users. need to restrict like in JSP
//            Integer selectedUserId = (Integer) req.getSession().getAttribute("selectedUserId");
//            Integer authorId = ((GetUserDto) req.getSession().getAttribute("user")).getId();
//            CreateActivityDto activityDto = CreateActivityDto.builder()
//                    .userId(selectedUserId)
//                    .unitId(req.getParameter("unit-id"))
//                    .activityName(req.getParameter("name"))
//                    .description(req.getParameter("description"))
//                    .authorId(authorId)
//                    .build();
//            try {
//                activityService.create(activityDto);
//                resp.sendRedirect(req.getContextPath() + "/activity?unit-id=" + ((GetUnitDto) req.getSession().getAttribute("unit")).getId());
//            } catch (ValidationException exception) {
//                req.setAttribute("errors", exception.getErrors());
//                doGet(req, resp);
//            }
//        } else if (req.getParameter("action").equals("delete")) {
//            String activityIdParameter = req.getParameter("id");
//            Integer activityId = Integer.valueOf(activityIdParameter);
//            activityService.delete(activityId);
//            resp.sendRedirect(req.getContextPath() + "/activity?unit-id=" + ((GetUnitDto) req.getSession().getAttribute("unit")).getId());
//        } else if (req.getParameter("action").equals("update")) {
//            String activityIdParameter = req.getParameter("id");
//            Integer activityId = Integer.valueOf(activityIdParameter);
//            CreateActivityDto activityDto = CreateActivityDto.builder()//TODO should update users or only data fields?
////                    .userId(selectedUserId)
////                    .unitId(req.getParameter("unit-id"))
//                    .activityName(req.getParameter("name"))
//                    .description(req.getParameter("description"))
////                    .authorId(authorId)
//                    .build();
//            try {
//                activityService.update(activityId, activityDto);
//                resp.sendRedirect(req.getContextPath() + "/activity?unit-id=" + ((GetUnitDto) req.getSession().getAttribute("unit")).getId());
//            } catch (ValidationException exception) {
//                req.setAttribute("errors", exception.getErrors());
//                doGet(req, resp);
//            }
//        }
    }
}

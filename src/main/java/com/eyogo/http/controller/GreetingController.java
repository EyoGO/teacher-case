package com.eyogo.http.controller;

import com.eyogo.http.repository.UnitRepository;
import com.eyogo.http.dto.UnitReadDto;
import com.eyogo.http.dto.UserReadDto;
import com.eyogo.http.entity.Gender;
import com.eyogo.http.entity.Role;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

// AFTER THIS look at LoginController - starting Forward, Include, Redirect

@Controller
@RequestMapping("/api/v1")
@SessionAttributes({"user", "user1"}) // (NEW-session_attribute)
public class GreetingController {
    // Here will be explained how we used to access Parameters, Headers, Cookies previously (OLD) and now with Spring (NEW)
    // All these variables are injected thanks to HandlerAdapter's parameter resolvers (debug and see list of them in DispatcherServlet.doDispatch)

    // @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @GetMapping("/hello1/{id}")
    public ModelAndView hello1(ModelAndView modelAndView, HttpServletRequest request, UnitRepository unitRepository,
                              @RequestParam(/*value = "age",*/ required = false) Integer age,           //(NEW-parameter) automatically matches existing param name (or provided) to variable name and converts to specified type
                              @RequestHeader(/*value = "accept",*/ required = false) String accept,     //(NEW-header) automatically matches existing header name (or provided) to variable name and converts to specified type
                              @CookieValue(value = "JSESSIONID", required = false) String jsessionId,   //(NEW-cookie) automatically matches existing cookie name (or provided) to variable name and converts to specified type
                              @PathVariable(/*value = "id"*/) Integer id) {                             // PathVariableArgumentResolver handles path processing
//        String age = request.getParameter("age");         // (OLD-parameter) returns string value by key
//        String accept = request.getHeader("accept");      // (OLD-header) returns string value by key
//        Cookie[] cookies = request.getCookies();          // (OLD-cookie) returns array where we search for Cookie
        modelAndView.setViewName("test/hello1");
        return modelAndView;
    }

//    Dynamic data is added through attributes (Map<String, Object>).
    // There are 3 types of attributes: Servlet (single for app, DEPRECATED), Session (like current USER), Request (all dynamic data that is not session)
    // First type not required in Spring, Session attributes defined over class with @SessionAttributes and retrieved as parameters with @SessionAttribute (bye method)
    // Request attributes @RequestAttribute (but it is not required as it is default), SAME as session are set using ModelAndView.addObject(...) methods!!!


    // We also can use one of Return value handlers to return String name of JSP and inject not ModelAndView, but Model.

    // IMPORTANT: we can have attributes automatically set to MODEL: @ModelAttribute (it is present by default, so optional) parameter annotation.
    // If we have DTO UserReadDto(Integer id, String name) and try to inject it as parameter, then spring will automatically search for
    // id and name request parameters (or body parameters) and create UserReadDto object and put it to model with "userReadDto" (or specified as param in @ModelAttribute) key.
    // BESIDES that, we can create method marked with @ModelAttribute, and it will ALWAYS be called for each request to add result of that method to model. (roles method)
    @GetMapping("/hello")
    public String hello(Model model, HttpServletRequest request,
                        @ModelAttribute("myUnit") UnitReadDto user) {
        UserReadDto attributeValue = new UserReadDto(1, "Yurii", "Hentash", "gentashyura@gmail.com",
                LocalDate.of(2000, 6, 26), null, Role.ADMIN, Gender.MALE);
//        request.setAttribute("user", attributeValue);                   // (OLD-request_attribute)
//        request.getSession().setAttribute("user", attributeValue);      // (OLD-session_attribute)
//        modelAndView.addObject("user", attributeValue);               // (NEW-attribute) Adds request attribute by default, BUT if there is @SesssionAttributes on Class with such key, then it is session.
        model.addAttribute("user", attributeValue);         // (NEW-attribute) Adds request attribute by default, BUT if there is @SesssionAttributes on Class with such key, then it is session.
        return "test/hello";
    }

    //    @RequestMapping(value = "/bye", method = RequestMethod.GET)
    @GetMapping("/bye")
    public String bye(Model model/*@SessionAttribute("user") GetUserDto user*/) {
        return "test/bye";
    }

    // This method is called for EACH REQUEST to add result to model!!!
    @ModelAttribute("roles")
    public List<Role> roles() {
        return Arrays.asList(Role.values());
    }

    @GetMapping("/test")
    public String test(Model model, HttpServletRequest request,
                       @ModelAttribute("user11") UnitReadDto unitReadDto) {
        model.addAttribute("user1", unitReadDto);
        return "test/hello";
    }

    @GetMapping("/testbye")
    public String testbye(Model model, HttpServletRequest request,
                       @SessionAttribute("user1") UnitReadDto unitReadDto) {
        return "test/bye";
    }
}

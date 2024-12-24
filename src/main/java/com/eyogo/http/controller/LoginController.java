package com.eyogo.http.controller;

import com.eyogo.http.dto.GetUnitDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

// When we return String value (JSP), then in fact DispatcherServlet executes either forward or include by default.
// We can override that adding prefix forward: or include: before returning value. BUT it will disable ViewResolver and we are required to specify full JSP path like in POST comment.
// BUT redirect is much more flexible, because we specify not the JSP, but path (URL) to redirect and we can use it like in POST.
@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginPage() {

        return "user/login";
    }

    @PostMapping("/login")
    public String login(Model model, @ModelAttribute("myUnitAttribute") GetUnitDto getUnitDto) {

//        return "forward:/WEB-INF/jsp/user/login.jsp";
//        return "user/login";
//        return "redirect:https://google.com";
        return "redirect:/login";
    }

}

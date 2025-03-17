package com.eyogo.http.controller;

import com.eyogo.http.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// When we return String value (JSP), then in fact DispatcherServlet executes either forward or include by default.
// We can override that adding prefix forward: or include: before returning value. BUT it will disable ViewResolver and we are required to specify full JSP path like in POST comment.
// BUT redirect is much more flexible, because we specify not the JSP, but path (URL) to redirect and we can use it like in POST.
@Controller
//@SessionAttributes({"user", "useCascade", "selectedUserId"})
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    //Provided by Spring Security
    /*@PostMapping("/login")
//    public String login(Model model, @ModelAttribute("myUnitAttribute") GetUnitDto getUnitDto) {
    public String login(Model model,
                        @RequestParam String email,
                        @RequestParam String password,
                        @SessionAttribute(required = false) Integer selectedUserId) {
        Optional<UserReadDto> userOptional = userService.login(email, password);
        if (userOptional.isPresent()) {
            UserReadDto user = userOptional.get();
            model.addAttribute("user", user);
            model.addAttribute("useCascade", false);

            if (selectedUserId == null) {
                model.addAttribute("selectedUserId", user.getId());
            }
            if (user.getRole().equals(Role.ADMIN)) {
                return "redirect:/units";
            } else {
                return "redirect:/units";
            }
        }

        return "redirect:/login?error&email=" + email;
//        return "forward:/WEB-INF/jsp/user/login.jsp";
//        return "user/login";
//        return "redirect:https://google.com";
//        return "redirect:/login";
    }*/

}

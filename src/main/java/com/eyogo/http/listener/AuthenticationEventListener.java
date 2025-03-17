package com.eyogo.http.listener;


import com.eyogo.http.repository.UserRepository;
import com.eyogo.http.dto.UserReadDto;
import com.eyogo.http.mapper.UserReadMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@RequiredArgsConstructor
public class AuthenticationEventListener {

    private final UserRepository userService;
    private final UserReadMapper userReadMapper;

    @EventListener
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();

        String username = ((User) event.getAuthentication().getPrincipal()).getUsername();
        com.eyogo.http.entity.User byEmail = userService.findByEmail(username)
                .orElseThrow();
        UserReadDto userReadDto = userReadMapper.mapFrom(byEmail);

        session.setAttribute("user", userReadDto);
        session.setAttribute("useCascade", false);
        session.setAttribute("selectedUserId", userReadDto.getId());
    }
}

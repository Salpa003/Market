package org.salpa.market.http.controller;

import lombok.RequiredArgsConstructor;
import org.salpa.market.dto.UserReadDto;
import org.salpa.market.entity.user.Role;
import org.salpa.market.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @GetMapping("/updateNews")
    public String getUpdateNewsPage(@CookieValue(name = "user") String userId) {
        Long id = Long.parseLong(userId);
        UserReadDto user = userService.getUserById(id);
        if (user.getRole() == Role.ADMIN)
            return "updateNews";
        else
            return "redirect:/home";

    }
}

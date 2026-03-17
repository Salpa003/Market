package org.salpa.market.http.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.salpa.market.dto.UserReadDto;
import org.salpa.market.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController {
    private final UserService userService;

    @GetMapping
    public String getHomePage(@CookieValue(name = "user", required = false) String id, Model model) {
        if (id == null) {
           return "redirect:/users/login";
       } else {
            Long userId = Long.parseLong(id);
            UserReadDto user = userService.getUserById(userId);
            model.addAttribute("user", user);
        }
        return "home";
    }
}

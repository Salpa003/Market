package org.salpa.market.http.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    @GetMapping
    public String getHomePage(@CookieValue(name = "user", required = false) String value) {
        if (value == null) {
           return "redirect:/users/login";
       }

        return "home";
    }
}

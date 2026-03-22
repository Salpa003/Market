package org.salpa.market.http.controller;

import lombok.RequiredArgsConstructor;
import org.salpa.market.dto.news.NewsReadDto;
import org.salpa.market.dto.user.UserReadDto;
import org.salpa.market.service.NewsService;
import org.salpa.market.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController {
    private final UserService userService;
    private final NewsService newsService;


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

    @GetMapping("/news")
    public String getNewsPage(@CookieValue(name = "user", required = false) String id,Model model) {
        if (id == null) {
            return "redirect:/users/login";
        } else {
            Long userId = Long.parseLong(id);
            UserReadDto user = userService.getUserById(userId);
            model.addAttribute("user", user);
        }
        List<NewsReadDto> list = newsService.getAllNews();
        model.addAttribute("news", list);
        return "news";
    }
}

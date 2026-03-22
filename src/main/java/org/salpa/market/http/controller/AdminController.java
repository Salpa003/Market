package org.salpa.market.http.controller;

import lombok.RequiredArgsConstructor;
import org.salpa.market.dto.news.NewsReadDto;
import org.salpa.market.dto.news.NewsUpdateDto;
import org.salpa.market.dto.user.UserReadDto;
import org.salpa.market.entity.news.News;
import org.salpa.market.entity.user.Role;
import org.salpa.market.service.NewsService;
import org.salpa.market.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final NewsService newsService;
    @GetMapping("/updateNews")
    public String getUpdateNewsPage(@CookieValue(name = "user") String userId, Model model) {
        Long id = Long.parseLong(userId);
        UserReadDto user = userService.getUserById(id);
        if (user.getRole() == Role.ADMIN) {
            List<NewsReadDto> allNews = newsService.getAllNews();
            model.addAttribute("news",allNews);
            System.out.println(allNews);
            return "updateNews";
        }
        else
            return "redirect:/home";

    }

    @PostMapping("/{id}/updateNews")
    public String a(@PathVariable("id") Integer id, NewsUpdateDto updateDto) {
        newsService.updateNews(id,updateDto);
        return "redirect:/admin/updateNews";
    }

    @GetMapping("/{id}/delete")
    public String deleteNews(@PathVariable("id") Integer id) {
        newsService.deleteNews(id);
        return "redirect:/admin/updateNews";
    }

    @GetMapping("/addNews")
    public String addNews() {
        newsService.createNews();
        return "redirect:/admin/updateNews";
    }


}

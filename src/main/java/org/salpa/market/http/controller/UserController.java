package org.salpa.market.http.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.salpa.market.dto.UserCreateDto;
import org.salpa.market.dto.UserReadDto;
import org.salpa.market.dto.UserUpdateDto;
import org.salpa.market.entity.User;
import org.salpa.market.exception.UserLoginException;
import org.salpa.market.service.ImageService;
import org.salpa.market.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ImageService imageService;

    @GetMapping
    public String getAllUsers() {
        return "registration";
    }

    @PostMapping("/avatar")
    public void p(@RequestParam("image") MultipartFile file) {
        System.out.println(file.getOriginalFilename());
        try {
            imageService.upload(file.getOriginalFilename(), file.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/registration")
    public String getRegPage(Model model) {
        model.addAttribute("user", User.builder().build());
        return "registration";
    }

    @PostMapping("/registration")
    public String regUser(UserCreateDto createDto, Model model, HttpServletResponse response) {
        try {
            userService.validateLogin(createDto.getLogin());
        } catch (UserLoginException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("user", createDto);
            return "registration";
        }
        Long id = userService.saveUser(createDto);
        Cookie cookie = new Cookie("user",id+"");
        cookie.setMaxAge(3600);           // 1 час
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        return "redirect:/home";
    }

    @GetMapping("/{id}")
    public String getUserById(@PathVariable Long id, Model model) {
        UserReadDto user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping("/{id}")
    public String updateUser(@PathVariable("id") Long id ,UserUpdateDto updateDto, Model model) {
        userService.updateUser(id, updateDto);
        UserReadDto user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "profile";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        model.addAttribute("user", User.builder().build());
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam("login") String login,
                            @RequestParam("password") String password,
                            Model model,
                            HttpServletResponse response) {
        System.out.println();
        try {
           Long id = userService.loginUser(login,password);
            Cookie cookie = new Cookie("user",id+"");
            cookie.setMaxAge(3600);           // 1 час
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
            return "redirect:/home";
        } catch (UserLoginException e) {
           model.addAttribute("errorMessage", e.getMessage());
           User user = User.builder()
                   .login(login)
                   .password(password)
                   .build();
          model.addAttribute("user", user);
           return "login";
        }
    }


}

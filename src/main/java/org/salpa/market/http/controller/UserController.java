package org.salpa.market.http.controller;

import lombok.RequiredArgsConstructor;
import org.salpa.market.dto.UserCreateDto;
import org.salpa.market.entity.User;
import org.salpa.market.exception.UserLoginException;
import org.salpa.market.service.ImageService;
import org.salpa.market.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
            imageService.upload(file.getOriginalFilename(),file.getInputStream());
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
    public String regUser(UserCreateDto createDto, Model model) {
        try {
            userService.validateLogin(createDto.getLogin());
        } catch (UserLoginException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("user", createDto);
            return "registration";
        }
        Long id = userService.saveUser(createDto);
        return "redirect:/users/"+id;
    }


}

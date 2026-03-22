package org.salpa.market.http.rest;

import lombok.RequiredArgsConstructor;
import org.salpa.market.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserRestController {
    private final UserService userService;

    @GetMapping("{id}/avatar")
    public byte[] getAvatar(@PathVariable Long id) {
       return userService.getUserAvatar(id);
    }


}

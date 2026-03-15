package org.salpa.market.http.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserRestController {

    @GetMapping("{id}/avatar")
    public Byte[] getAvatar(@PathVariable Long id) {

        return null;
    }
}

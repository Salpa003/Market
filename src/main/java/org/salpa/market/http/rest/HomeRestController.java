package org.salpa.market.http.rest;

import lombok.RequiredArgsConstructor;
import org.salpa.market.service.NewsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/home")
@RequiredArgsConstructor
public class HomeRestController {
    private final NewsService newsService;
    @GetMapping("{id}/news")
    public byte[] getNewsImage(@PathVariable Integer id) {
        return newsService.getNewsImage(id);
    }
}

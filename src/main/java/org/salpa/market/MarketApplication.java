package org.salpa.market;

import org.salpa.market.service.ImageService;
import org.salpa.market.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

@SpringBootApplication
public class MarketApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(MarketApplication.class, args);
    }

}

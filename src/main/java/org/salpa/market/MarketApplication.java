package org.salpa.market;

import org.salpa.market.service.ImageService;
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
        ImageService bean = context.getBean(ImageService.class);
        Path path = Path.of("C:\\Users\\rusik\\Market\\avatars\\mops.jpg");
        File file = path.toFile();
        try {
            InputStream inputStream = new FileInputStream(file);
            bean.upload("salpa.jpg",inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

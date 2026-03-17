package org.salpa.market.service;

import liquibase.resource.OpenOptions;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
//import org.apache.commons.io.Charsets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.Optional;
import java.util.UUID;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

@Service
public class ImageService {

    @Value("${app.image.bucket}")
    private String bucket;

    @SneakyThrows
    public void upload(String imageName, InputStream content) {
        Path fullPath = Path.of(bucket, imageName);

        Path parent = fullPath.getParent();
        if (!Files.exists(parent)) {
            System.out.println("Create parent");
            Files.createDirectories(parent);
        }

        try (content; OutputStream os = Files.newOutputStream(fullPath, CREATE, TRUNCATE_EXISTING)) {
            Files.write(fullPath, content.readAllBytes(), CREATE);
        }

    }

    @SneakyThrows
    public void updateImage(String oldFile, String newFile, InputStream inputStream) {
        deleteFile(oldFile);
        Path newPath = Path.of(bucket, newFile);
        try (inputStream; OutputStream os = Files.newOutputStream(newPath, CREATE, TRUNCATE_EXISTING)) {
            Files.write(newPath, inputStream.readAllBytes(), CREATE);
        }
    }

    @SneakyThrows
    public Optional<byte[]> getImage(String imageName) {
        Path path = Path.of(bucket, imageName);
        return Files.exists(path) ? Optional.of(Files.readAllBytes(path)) : Optional.empty();
    }

    @SneakyThrows
    public void deleteFile(String fileName) {
        Path path = Path.of(bucket, fileName);
        Files.deleteIfExists(path);
    }

}

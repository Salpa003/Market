package org.salpa.market.service;

import lombok.SneakyThrows;
//import org.apache.commons.io.Charsets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.*;
import java.util.Optional;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

@Service
public class ImageService {

    @Value("${app.images.avatars}")
    private String avatarBucket;

    @Value("${app.images.collections}")
    private String collectionBucket;

    @Value("${app.images.skins}")
    private String skinBucket;

    @Value("${app.images.news}")
    private String newsBucket;

    @SneakyThrows
    public void uploadAvatar(String imageName, InputStream content) {
        Path fullPath = Path.of(avatarBucket, imageName);

        Path parent = fullPath.getParent();
        if (!Files.exists(parent)) {
            Files.createDirectories(parent);
        }

        try (content; OutputStream os = Files.newOutputStream(fullPath, CREATE, TRUNCATE_EXISTING)) {
            Files.write(fullPath, content.readAllBytes(), CREATE);
        }

    }

    @SneakyThrows
    public void updateAvatar(String oldFile, String newFile, InputStream inputStream) {
        deleteAvatar(oldFile);
        Path newPath = Path.of(avatarBucket, newFile);
        try (inputStream; OutputStream os = Files.newOutputStream(newPath, CREATE, TRUNCATE_EXISTING)) {
            Files.write(newPath, inputStream.readAllBytes(), CREATE);
        }
    }

    @SneakyThrows
    public Optional<byte[]> getAvatar(String imageName) {
        Path path = Path.of(avatarBucket, imageName);
        return Files.exists(path) ? Optional.of(Files.readAllBytes(path)) : Optional.empty();
    }

    @SneakyThrows
    public void deleteAvatar(String fileName) {
        Path path = Path.of(avatarBucket, fileName);
        Files.deleteIfExists(path);
    }


    @SneakyThrows
    public void uploadNews(String imageName, InputStream content) {
        Path fullPath = Path.of(newsBucket, imageName);

        Path parent = fullPath.getParent();
        if (!Files.exists(parent)) {
            Files.createDirectories(parent);
        }

        try (content; OutputStream os = Files.newOutputStream(fullPath, CREATE, TRUNCATE_EXISTING)) {
            Files.write(fullPath, content.readAllBytes(), CREATE);
        }
    }

    @SneakyThrows
    public void updateNews(String oldFile, String newFile, InputStream inputStream) {
        deleteNews(oldFile);
        Path newPath = Path.of(newsBucket, newFile);
        try (inputStream; OutputStream os = Files.newOutputStream(newPath, CREATE, TRUNCATE_EXISTING)) {
            Files.write(newPath, inputStream.readAllBytes(), CREATE);
        }
    }

    @SneakyThrows
    public void deleteNews(String fileName) {
        if (fileName == null || fileName.isEmpty())
            return;
        Path path = Path.of(newsBucket, fileName);
        Files.deleteIfExists(path);
    }
    @SneakyThrows
    public Optional<byte[]> getNews(String imageName) {
        Path path = Path.of(newsBucket, imageName);
        return Files.exists(path) ? Optional.of(Files.readAllBytes(path)) : Optional.empty();
    }

    public String getImageFormat(MultipartFile file) {
        if (file == null || file.getOriginalFilename() == null) {
            return null;
        }

        String fileName = file.getOriginalFilename();
        int lastDotIndex = fileName.lastIndexOf(".");

        if (lastDotIndex == -1 || lastDotIndex == fileName.length() - 1) {
            return null;
        }

        return fileName.substring(lastDotIndex).toLowerCase();
    }


}

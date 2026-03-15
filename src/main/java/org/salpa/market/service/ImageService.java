package org.salpa.market.service;

import liquibase.resource.OpenOptions;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.Charsets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
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

        try (content; OutputStream os = Files.newOutputStream(fullPath, CREATE, TRUNCATE_EXISTING)) {
            content.transferTo(os);  // ← эффективно для видео и больших изображений
        }
    }

    @SneakyThrows
    public Optional<byte[]> getImage(String imageName) {
        Path path = Path.of(bucket, imageName);
        return Files.exists(path) ? Optional.of(Files.readAllBytes(path)) : Optional.empty();
    }


}

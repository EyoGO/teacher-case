package com.eyogo.http.service;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

@Service
public class ImageService {

    private final String bucket;

    public ImageService(@Value("${app.image.bucket}") String bucket) {
        this.bucket = bucket;
    }

    @SneakyThrows
    public void upload(String image, InputStream content) {
        Path fullImagePath = Path.of(bucket, image);

        // In our case it is just an image, so we can read whole InputStream. In case it is video we must write bytes from IS to OS with small packages.
        try (content) {
            Files.createDirectories(fullImagePath.getParent());
            Files.write(fullImagePath, content.readAllBytes(), CREATE, TRUNCATE_EXISTING);
        }
    }

    @SneakyThrows
    public Optional<byte[]> find(String image) {
        Path imageFullPath = Path.of(bucket, image);
        return Files.exists(imageFullPath)
                ? Optional.of(Files.readAllBytes(imageFullPath))
                : Optional.empty();
    }
}

package com.khooch.carsalesportal.service.impl;

import com.khooch.carsalesportal.service.StorageService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class StorageServiceImpl implements StorageService {

    private final Path rootLocation = Paths.get("upload-dir");

    @Override
    public String store(MultipartFile file) throws IOException {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        String uniqueFilename = UUID.randomUUID().toString() + "-" + filename;
        Files.copy(file.getInputStream(), this.rootLocation.resolve(uniqueFilename));
        return uniqueFilename;
    }
}

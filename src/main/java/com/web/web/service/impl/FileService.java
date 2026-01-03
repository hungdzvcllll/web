package com.web.web.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {
    public String saveFile(MultipartFile file, String subDirectory) throws IOException, java.io.IOException {
        // Lấy tên file và làm sạch tên file
        if(file==null||file.isEmpty())
            return null;
        String fileName = StringUtils.cleanPath(UUID.randomUUID().toString()+file.getOriginalFilename());
        // Xác định thư mục upload
        String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/" + subDirectory;

        // Tạo thư mục nếu chưa tồn tại
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Xác định đường dẫn đầy đủ của file
        Path filePath = uploadPath.resolve(fileName);
        // Lưu file vào thư mục
        file.transferTo(filePath.toFile());
        // Trả về đường dẫn tương đối của file
        return "/" + subDirectory + fileName;
    }
    public Path getFullPathFromLink(String link) {
        String filePath = System.getProperty("user.dir") + "/src/main/resources/static" + link;
        return Paths.get(filePath);
    }
    public void validateFileExists(Path path) {
        if (!Files.exists(path)) {
            throw new RuntimeException("File not found");
        }
    }
}

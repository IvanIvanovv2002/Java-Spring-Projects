package com.example.springbootcloudinary.service;

import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileUploadImpl implements FileUpload {

    private final Cloudinary cloudinary;
    @Override
    public String uploadFile(MultipartFile multipartFile) throws IOException {

        String folderPath = "staticImages/";

        String publicId = folderPath + UUID.randomUUID();

        return cloudinary.uploader().upload(multipartFile.getBytes(), Map.of("public_id", publicId)).get("url").toString();
    }
}

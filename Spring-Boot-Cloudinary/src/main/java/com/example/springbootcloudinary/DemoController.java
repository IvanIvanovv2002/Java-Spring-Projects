package com.example.springbootcloudinary;

import com.example.springbootcloudinary.service.FileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class DemoController {

    private final FileUpload fileUpload;

    public DemoController(FileUpload fileUpload) {
        this.fileUpload = fileUpload;
    }

    @GetMapping("/")
    public String home(){
        return "home";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("image") MultipartFile multipartFile, Model model) throws IOException {
        String imageURL = fileUpload.uploadFile(multipartFile);
        model.addAttribute("imageURL",imageURL);
        return "gallery";
    }

}

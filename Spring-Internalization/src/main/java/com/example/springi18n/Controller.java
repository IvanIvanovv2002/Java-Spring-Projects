package com.example.springi18n;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@org.springframework.stereotype.Controller
public class Controller {

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("channel","java channel");
        return "index";
    }

    @GetMapping("/locale")
    public String changeLocale(@RequestParam String language) {
        return "index";
    }

}

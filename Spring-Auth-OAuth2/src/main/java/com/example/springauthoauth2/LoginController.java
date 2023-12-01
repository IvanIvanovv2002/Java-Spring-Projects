package com.example.springauthoauth2;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @GetMapping("/login")
    public ModelAndView loginPage() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("html/login");
        return mv;
    }

}

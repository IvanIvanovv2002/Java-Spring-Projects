package com.example.springauthoauth2;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@org.springframework.stereotype.Controller
public class Controller {

    @GetMapping("/")
    public String home(Authentication authentication) {
        return authentication.getAuthorities().toString();
    }


    @GetMapping("/secured")
    public String secured() {
        return "<h1>Secured !</h1>";
    }


}

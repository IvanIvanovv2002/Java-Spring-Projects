package com.example.springsecurityjwt;

import com.example.springsecurityjwt.Utils.JWT;
import com.example.springsecurityjwt.models.AuthenticationRequest;
import com.example.springsecurityjwt.models.AuthenticationResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
public class ResourceController {

    private final UserDetailsService  userDetailsService;

    private final  AuthenticationManager manager;


    public ResourceController(UserDetailsService userDetailsService, AuthenticationManager manager) {
        this.userDetailsService = userDetailsService;
        this.manager = manager;
    }

    @GetMapping("/hello")
    public String homePage() {
        return "<h1>Hello!</h1>";
    }

    @GetMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestParam String username , @RequestParam String password, HttpServletResponse response) throws Exception {
    AuthenticationRequest request = new AuthenticationRequest(username,password);
     try {
         manager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));
     } catch (BadCredentialsException e) {
         throw new Exception("Invalid username or password");
     }
       final UserDetails user = userDetailsService.loadUserByUsername(request.getUsername());
       final String jwt = JWT.generateToken(user);

       Cookie cookie = new Cookie("accessToken",jwt);
       cookie.setMaxAge(60*10*10);
       response.addCookie(cookie);
       return new ResponseEntity<>(new AuthenticationResponse(jwt), OK);
    }
}

package com.example.springi18n;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Locale;

@SpringBootApplication
public class SpringI18nApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringI18nApplication.class, args);
    }

}

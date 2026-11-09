package com.ciclo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String inicio() {
        // Retorna la vista index.html que estará en templates/index.html
        return "index";
    }
}
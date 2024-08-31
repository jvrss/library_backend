package br.com.jvrss.library.hello.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for handling hello requests.
 */
@RestController
public class HelloController {

    /**
     * Handles GET requests to /hello.
     *
     * @return a greeting message
     */
    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, World!";
    }
}
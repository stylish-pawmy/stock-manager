package org.example.gestionstock.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloWorldController {

    @GetMapping("hello")
    public String getHelloWorld()
    {
        return "helloworld";
    }
}

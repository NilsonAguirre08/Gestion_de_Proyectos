package com.company.spring_boot_crud_app;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
@Controller
public class HomeWebController {

    @GetMapping("/Home")
    public String home() {
        return "Home";
    }
}

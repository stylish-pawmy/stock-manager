package org.example.gestionstock.Controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/")
    public String getHome(Model page)
    {
         UserDetails userDetails = (UserDetails) SecurityContextHolder
                 .getContext().getAuthentication().getPrincipal();

        page.addAttribute("user", userDetails.getUsername());
        return "dashboard";
    }

    @GetMapping("/login")
    public String getLogin()
    {
        return "login";
    }

    @GetMapping("/error")
    public String getError(Model page)
    {
        return "error";
    }
}

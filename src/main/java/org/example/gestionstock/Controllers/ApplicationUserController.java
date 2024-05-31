package org.example.gestionstock.Controllers;

import org.example.gestionstock.Models.ApplicationUser;
import org.example.gestionstock.Models.Dtos.CreateApplicationUser;
import org.example.gestionstock.Models.Role;
import org.example.gestionstock.Repositories.RoleRepository;
import org.example.gestionstock.Repositories.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ApplicationUserController {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public ApplicationUserController(
            UserRepository userRepository,
            RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/utilisateurs")
    public String getUtilisateurs(Model model)
    {
        List<ApplicationUser> users = userRepository.findAll();
        model.addAttribute("users", users);

        return "utilisateur/utilisateurs";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/ajouter-utilisateur")
    public String getAjouterUtilisateur(Model model)
    {
        CreateApplicationUser user = new CreateApplicationUser();
        model.addAttribute("user", user);

        List<Role> roles = roleRepository.findAll();
        model.addAttribute("roles", roles);

        return "utilisateur/ajouter-utilisateur";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/modifier-utilisateur")
    public String getModifierUtilisateur(
            Model model,
            @RequestParam("user_id") int id
    )
    {
        ApplicationUser user = userRepository.findById(id).orElse(null);
        CreateApplicationUser userData = new CreateApplicationUser();

        userData.setId(user.getId());
        userData.setUsername(user.getUsername());
        userData.setPassword(user.getPassword());
        userData.setRoleId(user.getRole().getId());

        model.addAttribute("user", userData);



        List<Role> roles = roleRepository.findAll();
        model.addAttribute("roles", roles);

        return "utilisateur/ajouter-utilisateur";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/ajouter-utilisateur")
    public String postAjouterArticle(@ModelAttribute CreateApplicationUser userData, Model page)
    {
        ApplicationUser user = new ApplicationUser();

        user.setId(userData.getId());
        user.setUsername(userData.getUsername());
        user.setPassword(userData.getPassword());

        Role role = roleRepository.findById(userData.getRoleId()).orElse(null);
        user.setRole(role);

        userRepository.save(user);

        page.addAttribute("users", userRepository.findAll());
        return "utilisateur/utilisateurs";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/supprimer-utilisateur")
    public String deleteArticle(@RequestParam("user_id") int id, Model page)
    {
        userRepository.deleteById(id);
        page.addAttribute("users", userRepository.findAll());

        return "utilisateur/utilisateurs";
    }
}

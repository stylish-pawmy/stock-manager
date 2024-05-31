package org.example.gestionstock.Services;

import org.example.gestionstock.Models.ApplicationUser;
import org.example.gestionstock.Models.ApplicationUserDetails;
import org.example.gestionstock.Repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public ApplicationUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser user = userRepository.findByUsername(username)
                .orElse(null);

        if (user == null)
        {
            throw new UsernameNotFoundException("Utilisateur introuvable.");
        }

        return new ApplicationUserDetails(user);
    }
}

package org.example.secureqabackend.service.user;

import jakarta.annotation.PostConstruct;
import org.example.secureqabackend.dto.UserDTO;
import org.example.secureqabackend.entity.Role;
import org.example.secureqabackend.entity.User;
import org.example.secureqabackend.exception.ResourceNotFoundException;
import org.example.secureqabackend.repository.RoleRepository;
import org.example.secureqabackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        if (roleRepository.findByName("ROLE_ADMIN").isEmpty()) {
            Role adminRole = new Role();
            adminRole.setName("ROLE_ADMIN");
            roleRepository.save(adminRole);
        }

        if (roleRepository.findByName("ROLE_USER").isEmpty()) {
            Role userRole = new Role();
            userRole.setName("ROLE_USER");
            roleRepository.save(userRole);
        }

        if (userRepository.findByUsername("admin").isEmpty()) {
            Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                    .orElseThrow(() -> new RuntimeException("ROLE_ADMIN not found"));

            Set<Role> roles = new HashSet<>();
            roles.add(adminRole);

            User adminUser = new User();
            adminUser.setEmail("admin@gmail.com");
            adminUser.setUsername("admin");
            adminUser.setPassword(passwordEncoder.encode("Admin123"));
            adminUser.setRoles(roles);

            userRepository.save(adminUser);
        }
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new ResourceNotFoundException("User not found")
        );
        return user.userDTO();
    }
}

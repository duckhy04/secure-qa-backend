package org.example.secureqabackend.service.user;

import org.example.secureqabackend.dto.UserDTO;

public interface UserService {
    UserDTO getUserByUsername(String username);
}

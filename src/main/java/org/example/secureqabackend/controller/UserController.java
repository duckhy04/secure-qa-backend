package org.example.secureqabackend.controller;

import org.example.secureqabackend.dto.ApiResponse;
import org.example.secureqabackend.dto.UserDTO;
import org.example.secureqabackend.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{username}")
    public ResponseEntity<Object> getUsers(@PathVariable String username) {
        UserDTO userDTO = userService.getUserByUsername(username);
        ApiResponse<Object> response = new ApiResponse<>("Get user successfully", userDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}

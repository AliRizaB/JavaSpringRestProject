package com.example.dims_project_2.controller;

import com.example.dims_project_2.dtos.UserDTO;
import com.example.dims_project_2.model.User;
import com.example.dims_project_2.security.Role;
import com.example.dims_project_2.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/read")
    public String allUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "user/user_read";
    }

    @GetMapping("/info/{id}")
    public String getUser(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "user/user_info";
    }

    // Create user
    @GetMapping("/create")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        return "user/user_create";
    }

    @PostMapping("/create")
    public String userAdd(@ModelAttribute("user") User user, @RequestParam("img") MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (fileName.isEmpty() && user.getImageUrl().isEmpty()) {
            user.setImageUrl("nophoto.jpg");
        } else if (!fileName.isEmpty()) {
            // File upload
            user.setImageUrl(fileName);
            String uploadDir = System.getProperty("user.dir") + "/images/";
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                try {
                    Files.createDirectories(uploadPath);
                } catch (IOException ex) {
                    System.out.println("File saving error! " + ex);
                }

            }

            try {
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex) {
                System.out.println("File saving error! " + ex);
            }
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        try {
            userService.createUser(user);
            System.out.println("USER SAVED SUCCESSFULLY: " + user.getUsername());
        } catch (Exception e) {
            System.out.println("CRITICAL SAVE ERROR: " + e.getMessage());
            e.printStackTrace();
        }
        return "redirect:/login";
    }

    // Update user
    @GetMapping("/edit/{id}")
    public String showUpdateUser(@PathVariable Long id, Model model) {
        try {
            UserDTO user = userService.getUserById(id);
            model.addAttribute("user", user);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error_handling";
        }
        return "User/user_edit";
    }

    @PostMapping("/edit/{id}")
    public String updateUser(@ModelAttribute("user") User user, @RequestParam("img") MultipartFile file, @PathVariable Long id) {
        if (id == null || id <= 0)
            throw new IllegalArgumentException("id is invalid");

        String fileName = file.getOriginalFilename();
        if (fileName.isEmpty() && user.getImageUrl().isEmpty()) {
            user.setImageUrl("nophoto.jpg");
        } else if (!fileName.isEmpty()) {
            // File upload
            user.setImageUrl(fileName);


            String uploadDir = System.getProperty("user.dir") + "/images/";
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                try {
                    Files.createDirectories(uploadPath);
                } catch (IOException ex) {
                    System.out.println("File saving error! " + ex);
                }

            }
            try {
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(file.getInputStream(), uploadPath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex) {
                System.out.println("File saving error! " + ex);
            }
        }
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        user.setRole(Role.USER);
        userService.updateUser(id, user);
        return "redirect:/user/read";
    }

    // DELETE
    @GetMapping("/delete/{id}")
    public String showUserDelete(Model model, @PathVariable Long id) {
        try {
            UserDTO user = userService.getUserById(id);
            model.addAttribute("user", user);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error_handling";
        }
        return "User/user_delete";
    }

    @PostMapping("/delete/{id}")
    public String UserDelete(Model model, @PathVariable Long id) {
        if (id == null || id <= 0)
            throw new IllegalArgumentException("id is invalid");
        // Delete image if exists
        UserDTO userDTO = userService.getUserById(id);
        if (!userDTO.getImageUrl().isEmpty()) {
            String fileName = userDTO.getImageUrl();
            Path filePath = Paths.get(System.getProperty("user.dir") + "/images/" + fileName);

            try {
                boolean result = Files.deleteIfExists(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        userService.deleteUser(id);
        return "redirect:/user/read";
    }
}

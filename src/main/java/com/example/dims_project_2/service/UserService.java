package com.example.dims_project_2.service;

import com.example.dims_project_2.dtos.UserDTO;
import com.example.dims_project_2.model.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {
    public List<UserDTO> getAllUsers();
    public UserDTO getUserById(Long id);
    public UserDTO createUser(User User);
    public UserDTO updateUser(Long id, User User);
    public void deleteUser(Long id);

}

package com.example.dims_project_2.dtos;

import com.example.dims_project_2.security.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private long id;
    private String name;
    private String address;
    private String username;
    private String password;
    private Role role;
    private String imageUrl;
}

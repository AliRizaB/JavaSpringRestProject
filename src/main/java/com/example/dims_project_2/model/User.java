package com.example.dims_project_2.model;

import com.example.dims_project_2.dtos.UserDTO;
import com.example.dims_project_2.security.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 25)
    @NotBlank(message = "Please enter a name")
    private String name;

    @Column(length = 50)
    @NotBlank(message = "Please enter an address")
    private String address;

    @Column(length = 25)
    @NotBlank(message = "Please enter an username")
    private String username;

    @NotBlank(message = "Please enter a password")
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String imageUrl = "nophoto.jpg";

    public User(long id, String name, String address, String username, String password, Role role, String imageUrl) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.username = username;
        this.password = password;
        this.role = role;
        this.imageUrl = imageUrl;
    }

    public UserDTO viewAsUserDTO(){
        return new UserDTO(id, name, address, username, password, role,imageUrl);
    }
}

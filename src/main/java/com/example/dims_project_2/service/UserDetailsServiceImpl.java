package com.example.dims_project_2.service;

import com.example.dims_project_2.dtos.UserDTO;
import com.example.dims_project_2.exception.AlreadyExistsError;
import com.example.dims_project_2.exception.ErrorMessages;
import com.example.dims_project_2.exception.NotFoundError;
import com.example.dims_project_2.model.User;
import com.example.dims_project_2.repository.UserRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class UserDetailsServiceImpl implements UserDetailsService, UserService{
    private final UserRepository repo;
    private final PasswordEncoder encoder;
    public UserDetailsServiceImpl(UserRepository userRepository, PasswordEncoder encoder) {
        this.repo = userRepository;
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if ("admin".equals(username)) {
            return org.springframework.security.core.userdetails.User.builder()
                    .username("admin")
                    .password(encoder.encode("admin123")) // Use your injected encoder
                    .roles("ADMIN")
                    .build();
        }

        com.example.dims_project_2.model.User user = repo.findByUsername(username);

        if (user == null) {
            System.out.println("USER NOT FOUND IN DB!");
            throw new UsernameNotFoundException(username);
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return repo.findAll().stream().map(User::viewAsUserDTO).toList();
    }

    @Override
    public UserDTO getUserById(Long id) {
        return repo.findById(id).orElseThrow(() -> new NotFoundError(ErrorMessages.ERROR_USER_NOT_FOUND + "with id : " + id)).viewAsUserDTO();
    }

    @Override
    public UserDTO createUser(User user) {
        if(repo.findByUsername(user.getUsername()) != null) {
            throw new AlreadyExistsError(ErrorMessages.ERROR_USER_ALREADY_EXIST + " with username : " + user.getUsername());
        }
        User savedUser = repo.save(user);
        return savedUser.viewAsUserDTO();
    }

    @Override
    public UserDTO updateUser(Long id, User User) {
        repo.findById(id).orElseThrow(() -> new NotFoundError(ErrorMessages.ERROR_USER_NOT_FOUND + "with id : " + id));
        User.setId(id);
        return repo.save(User).viewAsUserDTO();
    }

    @Override
    public void deleteUser(Long id) {
        repo.findById(id).orElseThrow(() -> new NotFoundError(ErrorMessages.ERROR_USER_NOT_FOUND + "with id : " + id));
        repo.deleteById(id);
    }


}
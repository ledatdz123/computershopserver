package com.example.computershopserver.service;

import com.example.computershopserver.dto.UserDTO;
import com.example.computershopserver.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface UserService {
    public List<UserDTO> retrieveUsers();

    public Optional<UserDTO> getUser(Long userId) throws ResourceNotFoundException;

    public UserDTO saveUser(UserDTO userDTO);

    public Boolean deleteUser(Long userId) throws ResourceNotFoundException;

    UserDTO updateUser(Long userId, UserDTO userDTO) throws ResourceNotFoundException;
}

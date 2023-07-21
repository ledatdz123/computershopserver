package com.example.computershopserver.service.impl;

import com.example.computershopserver.dto.UserDTO;
import com.example.computershopserver.entity.Role;
import com.example.computershopserver.entity.User;
import com.example.computershopserver.exception.ResourceNotFoundException;
import com.example.computershopserver.repository.RoleRepository;
import com.example.computershopserver.repository.UserRepository;
import com.example.computershopserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    @Override
    public List<UserDTO> retrieveUsers() {
        List<User> users = userRepository.findAll();
        return new UserDTO().toListDto(users);
    }

    @Override
    public Optional<UserDTO> getUser(Long userId) throws ResourceNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found for this id: "+userId));
        return Optional.of(new UserDTO().convertToDto(user));
    }

    @Override
    public UserDTO saveUser(UserDTO userDTO) {
        return null;
    }

    @Override
    public Boolean deleteUser(Long userId) throws ResourceNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found for this id: " + userId));
        this.userRepository.delete(user);
        return true;
    }

    @Override
    public UserDTO updateUser(Long userId, UserDTO userDTO) throws ResourceNotFoundException {
        User userExist = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("user not found for this id: "+userId));

        userExist.setFirstName(userDTO.getFirstName());
        userExist.setLastName(userDTO.getLastName());
        userExist.setAddress(userDTO.getAddress());
        userExist.setPhoneNumber(userDTO.getPhoneNumber());
        Set<String> strRoles = userDTO.getRoles();
        Set<Role> roles = new HashSet<>();
        if (strRoles == null) {
            Role userRole = roleRepository.findByRoleName("ROLE_USER")
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                if ("ROLE_ADMIN".equals(role.toString())) {
                    Role adminRole = roleRepository.findByRoleName("ROLE_ADMIN")
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(adminRole);
                } else if ("manager".equals(role.toString())) {
                    Role modRole = roleRepository.findByRoleName("ROLE_MANAGER")
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(modRole);
                } else {
                    Role userRole = roleRepository.findByRoleName("ROLE_USER")
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(userRole);
                }
            });
        }
        userExist.setRoles(roles);
        userExist.setGender(userDTO.getGender());
        userExist.setBirthday(userDTO.getBirthday());
        userExist.setStatus(userDTO.getStatus());
        User user = new User();
        user = userRepository.save(userExist);
        return new UserDTO().convertToDto(user);

    }
}


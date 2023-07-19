package com.example.computershopserver.authcontroller;

import com.example.computershopserver.dto.LoginRequest;
import com.example.computershopserver.dto.SignupRequest;
import com.example.computershopserver.entity.Role;
import com.example.computershopserver.entity.User;
import com.example.computershopserver.repository.RoleRepository;
import com.example.computershopserver.repository.UserRepository;
import com.example.computershopserver.response.JwtTokenResponse;
import com.example.computershopserver.response.MessageResponse;
import com.example.computershopserver.security.jwt.AuthTokenFilter;
import com.example.computershopserver.security.jwt.JwtUtils;
import com.example.computershopserver.service.impl.MyUserDetails;
import com.example.computershopserver.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    private AuthTokenFilter jwtFilter;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());
            return ResponseEntity.ok(new JwtTokenResponse(jwt,
                    userDetails.getId(),
                    userDetails.getUsername(),
                    roles,
                    userDetails.getAddress(),
                    userDetails.getFirstName(),
                    userDetails.getLastName(),
                    userDetails.getPhoneNumber(),
                    userDetails.getGender(),
                    userDetails.getBirthday(),
                    userDetails.getStatus()));
        }catch (Exception e){
            return new ResponseEntity<>(new MessageResponse("User login no success!"), HttpStatus.UNAUTHORIZED);
        }
    }
    @PostMapping(value = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest) {
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }
        User u = new User(signupRequest.getEmail(),
                encoder.encode(signupRequest.getPassword()));

        Set<String> strRoles = signupRequest.getRoles();
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
        u.setRoles(roles);
        userRepository.save(u);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
    @PostMapping("/changePassword")
    ResponseEntity<String> changePassword(@RequestBody Map<String, String> requestMap){
        BCryptPasswordEncoder encoder1=new BCryptPasswordEncoder();
        try {
            User userObj=userRepository.findByEmail(jwtFilter.getCurrentUser()).get();
            if (!userObj.equals(null)){
                if (encoder1.matches(requestMap.get("oldPassword"), userObj.getPassword())){
                    userObj.setPassword(passwordEncoder.encode(requestMap.get("newPassword")));
                    userRepository.save(userObj);
                    return ResponseUtils.getResponseEntity("Password update success", HttpStatus.OK);
                }
                return ResponseUtils.getResponseEntity("Incorrect old password", HttpStatus.BAD_REQUEST);
            }
            return ResponseUtils.getResponseEntity("something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return ResponseUtils.getResponseEntity("something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @PostMapping("/changePasswordMail")
    ResponseEntity<String> changePasswordMail(@RequestBody Map<String, String> requestMap){
        BCryptPasswordEncoder encoder1=new BCryptPasswordEncoder();
        try {
            User userObj=userRepository.findByEmail(jwtFilter.getCurrentUser()).get();
            if (!userObj.equals(null)){
//                if (encoder1.matches(requestMap.get("oldPassword"), userObj.getPassword())){
//                    userObj.setPassword(passwordEncoder.encode(requestMap.get("newPassword")));
//                    userRepository.save(userObj);
//                    return ResponseUtils.getResponseEntity("Password update success", HttpStatus.OK);
//                }
                if (requestMap.get("oldPassword").equals(userObj.getPassword())){
                    userObj.setPassword(passwordEncoder.encode(requestMap.get("newPassword")));
                    userRepository.save(userObj);
                    return ResponseUtils.getResponseEntity("Password update success", HttpStatus.OK);
                }
                return ResponseUtils.getResponseEntity("Incorrect old password", HttpStatus.BAD_REQUEST);
            }
            return ResponseUtils.getResponseEntity("something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return ResponseUtils.getResponseEntity("something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @PostMapping("/forgotPassword")
    ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> requestMap){
        try {
            User user=userRepository.findByEmail(requestMap.get("email")).get();
//            if(!Objects.isNull(user) && !Strings.isNullOrEmpty(user.getEmail()))
//                emailUtils.forgotMail(user.getEmail(),
//                        "credentials by computer system", user.getPassword());
            return ResponseUtils.getResponseEntity("Check your mail", HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return ResponseUtils.getResponseEntity("something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

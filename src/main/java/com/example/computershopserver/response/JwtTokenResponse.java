package com.example.computershopserver.response;

import com.example.computershopserver.entity.entityenum.EGender;
import com.example.computershopserver.entity.entityenum.EStatusUser;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JwtTokenResponse {
    private  String token;
    private  String type="Bearer";
    private Long id;
    private String email;
    private List<String> roles = new ArrayList<>();
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;
    private EGender gender;
    private LocalDate birthday;
    private EStatusUser status;
    public JwtTokenResponse() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public EGender getGender() {
        return gender;
    }

    public void setGender(EGender gender) {
        this.gender = gender;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public EStatusUser getStatus() {
        return status;
    }

    public void setStatus(EStatusUser status) {
        this.status = status;
    }

    public JwtTokenResponse(String token, Long id, String email, List<String> roles,
                            String address, String firstName, String lastName, String phoneNumber,
                            EGender eGender, LocalDate birthday, EStatusUser eStatusUser) {
        this.token = token;
        this.id = id;
        this.email = email;
        this.roles = roles;
        this.address=address;
        this.firstName=firstName;
        this.lastName=lastName;
        this.phoneNumber=phoneNumber;
        this.gender=eGender;
        this.birthday=birthday;
        this.status=eStatusUser;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}


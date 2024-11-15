package com.SynClick.quiziniapp.Data.Models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

public class userEntityDto {
    private String id;
    private String name;
    private String lastName;
    private String email;
    private String password;
    private LocalDate birthDay;
    private String address;
    private Boolean isBanned;
    private LocalDateTime createdDate;
    private LocalDateTime  updatedDate;
    private Set<String> questionsId;
    private Set<Integer> topicsId;
    private Set<String> topicsName;

    public userEntityDto(String id, String name, String lastName, String email, String password, LocalDate birthDay, String address, Boolean isBanned, LocalDateTime createdDate, LocalDateTime updatedDate, Set<String> questionsId, Set<Integer> topicsId, Set<String> topicsName) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.birthDay = birthDay;
        this.address = address;
        this.isBanned = isBanned;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.questionsId = questionsId;
        this.topicsId = topicsId;
        this.topicsName = topicsName;
    }
    public userEntityDto() {

    }

    public userEntityDto(Set<Integer> topicsId) {
        this.topicsId = topicsId;
    }

    public userEntityDto(String email, String password) {
        this.email = email;
        this.password = password;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getBanned() {
        return isBanned;
    }

    public void setBanned(Boolean banned) {
        isBanned = banned;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Set<String> getQuestionsId() {
        return questionsId;
    }

    public void setQuestionsId(Set<String> questionsId) {
        this.questionsId = questionsId;
    }

    public Set<Integer> getTopicsId() {
        return topicsId;
    }

    public void setTopicsId(Set<Integer> topicsId) {
        this.topicsId = topicsId;
    }

    public Set<String> getTopicsName() {
        return topicsName;
    }

    public void setTopicsName(Set<String> topicsName) {
        this.topicsName = topicsName;
    }
}

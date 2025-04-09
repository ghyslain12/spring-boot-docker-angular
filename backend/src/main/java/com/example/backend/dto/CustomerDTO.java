package com.example.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomerDTO {
    private String surnom;

    @JsonProperty("user_id") 
    private Integer userId; 

    public String getSurnom() {
        return surnom;
    }
    public void setSurnom(String surnom) {
        this.surnom = surnom;
    }
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
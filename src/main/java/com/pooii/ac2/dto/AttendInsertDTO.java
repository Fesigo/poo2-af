package com.pooii.ac2.dto;

import javax.validation.constraints.NotBlank;

public class AttendInsertDTO {
    
    @NotBlank(message = "Username is required!")
    private String name;
    @NotBlank(message = "Email is required!")
    private String email;

    public AttendInsertDTO() {

    }

    public AttendInsertDTO(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

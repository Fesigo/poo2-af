package com.pooii.ac2.dto;

import javax.validation.constraints.NotBlank;

public class AttendUpdateDTO {

    @NotBlank(message = "Email do usuário é obrigatório!")
    private String email;

    public AttendUpdateDTO() {

    }
    
    public AttendUpdateDTO(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    
    
}

package com.pooii.ac2.entities;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "TB_ADMIN")
@PrimaryKeyJoinColumn(name = "USER_ID")
public class Admin extends BaseUser {
    
    private String phoneNumber;

    public Admin(){

    }
    
    public Admin(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Admin(Long id, String name, String email, String phoneNumber) {
        super(id, name, email);
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    

}

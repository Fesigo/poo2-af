package com.pooii.ac2.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TB_ADMIN")
@PrimaryKeyJoinColumn(name = "ADMIN_ID")
public class Admin extends BaseUser {
    
    private String phoneNumber;

    @JsonIgnore
    @OneToMany(mappedBy = "admin")
    private List<Event> events = new ArrayList<>();

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

    public List<Event> getEvents() {
        return events;
    }

    public void addEvents(Event event) {
        this.events.add(event);
    }

    

}

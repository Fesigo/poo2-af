package com.pooii.ac2.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TB_ATTEND")
@PrimaryKeyJoinColumn(name = "ATTENDEE_ID")
public class Attend extends BaseUser {
    
    private Double balance;

    @JsonIgnore
    @OneToMany
    @JoinColumn(name = "ATTEND_ATTENDEE_ID")
    private List<Ticket> tickets = new ArrayList<>();

    public Attend(){
        
    }

    public Attend(Double balance) {
        this.balance = balance;
    }

    public Attend(Long id, String name, String email, Double balance) {
        super(id, name, email);
        this.balance = balance;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void addTickets(Ticket ticket) {
        this.tickets.add(ticket);
    }

    
}

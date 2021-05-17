package com.pooii.ac2.entities;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "TB_ATTEND")
@PrimaryKeyJoinColumn(name = "USER_ID")
public class Attend extends BaseUser {
    
    private Double balance;

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
}

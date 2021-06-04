package com.pooii.ac2.dto;

import java.util.List;

import com.pooii.ac2.entities.Ticket;

public class TicketGetDTO {

    private Long amountFreeTickets;
    private Long amountPayedTickets;
    
    private Long soldFreeTickets;
    private Long soldPayedTickets;
    
    List<Ticket> tickets;

    public Long getAmountFreeTickets() {
        return amountFreeTickets;
    }

    public void setAmountFreeTickets(Long amountFreeTickets) {
        this.amountFreeTickets = amountFreeTickets;
    }

    public Long getAmountPayedTickets() {
        return amountPayedTickets;
    }

    public void setAmountPayedTickets(Long amountPayedTickets) {
        this.amountPayedTickets = amountPayedTickets;
    }

    public Long getSoldFreeTickets() {
        return soldFreeTickets;
    }

    public void setSoldFreeTickets(Long soldFreeTickets) {
        this.soldFreeTickets = soldFreeTickets;
    }

    public Long getSoldPayedTickets() {
        return soldPayedTickets;
    }

    public void setSoldPayedTickets(Long soldPayedTickets) {
        this.soldPayedTickets = soldPayedTickets;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    
    
}

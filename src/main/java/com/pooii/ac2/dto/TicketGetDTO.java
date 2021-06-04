package com.pooii.ac2.dto;

import java.util.List;

import com.pooii.ac2.entities.Ticket;

public class TicketGetDTO {

    private Long totalAmountFreeTickets;
    private Long totalAmountPayedTickets;
    
    private Long soldFreeTickets;
    private Long soldPayedTickets;
    
    List<Ticket> tickets;

    public Long getTotalAmountFreeTickets() {
        return totalAmountFreeTickets;
    }

    public void setTotalAmountFreeTickets(Long totalAmountFreeTickets) {
        this.totalAmountFreeTickets = totalAmountFreeTickets;
    }

    public Long getTotalAmountPayedTickets() {
        return totalAmountPayedTickets;
    }

    public void setTotalAmountPayedTickets(Long totalAmountPayedTickets) {
        this.totalAmountPayedTickets = totalAmountPayedTickets;
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

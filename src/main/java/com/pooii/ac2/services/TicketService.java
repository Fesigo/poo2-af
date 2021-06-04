package com.pooii.ac2.services;

import java.time.Instant;

import com.pooii.ac2.dto.TicketSellDTO;
import com.pooii.ac2.entities.Event;
import com.pooii.ac2.entities.Ticket;
import com.pooii.ac2.entities.TicketType;
import com.pooii.ac2.repositories.TicketRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TicketService {

    @Autowired
    public TicketRepository ticketRepository;

    @Autowired
    public AttendService attendService;

    public Ticket insert(TicketSellDTO ticket, Event e){
        Ticket t = new Ticket();

        t.setType(ticket.getType());

        if(t.getType().equals(TicketType.FREE)){
            t.setPrice(0.0);
        }
        else{
            t.setPrice(e.getPriceTicket());
        }

        t.setDate(Instant.now());
        
        t.setAttend(attendService.getAttendById(ticket.getAttend().getId()));
        t.setEvent(e);
        t = ticketRepository.save(t);

        return t;
    }

    public Page<Ticket> getTickets(PageRequest pageRequest) {
        Page<Ticket> tickets = ticketRepository.find(pageRequest);

        return tickets;
    }

    public void deleteTicketById(Long id) {
        try{
            ticketRepository.deleteById(id);
        }
        catch(EmptyResultDataAccessException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket not found");
        }        
    }
    
}

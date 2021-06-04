package com.pooii.ac2.repositories;

import com.pooii.ac2.entities.Ticket;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TicketRepository extends JpaRepository <Ticket, Long> {

    @Query("SELECT t FROM Ticket t")
    public Page<Ticket> find(Pageable pageRequest);
    
}

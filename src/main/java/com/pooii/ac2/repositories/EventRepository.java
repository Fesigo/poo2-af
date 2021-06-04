package com.pooii.ac2.repositories;

import java.time.LocalDate;

import com.pooii.ac2.entities.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository <Event, Long> {
    
    @Query("SELECT e FROM Event e " +
        "WHERE " +
        " (LOWER(e.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
        /*" (LOWER(e.place) LIKE LOWER(CONCAT('%', :place, '%'))) AND " +*/
        " (LOWER(e.description) LIKE LOWER(CONCAT('%', :description, '%'))) AND " +
        " e.startDate >= :startDate "
    )
    public Page<Event> find(Pageable pageRequest, String name, String description, LocalDate startDate);

    /*@Query("SELECT e.tickets FROM Event e WHERE e.id = :id")
    public Page<Ticket> findTickets(Pageable pageRequest, Long id);*/

}

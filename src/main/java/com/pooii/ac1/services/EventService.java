package com.pooii.ac1.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import com.pooii.ac1.dto.EventDTO;
import com.pooii.ac1.entities.Event;
import com.pooii.ac1.repositories.EventRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EventService {

    @Autowired
    private EventRepository repository;

    public Page<Event> getEvents(PageRequest pageRequest, String name, String place, String description){
        Page<Event> list = repository.find(pageRequest, name, place, description);

        return list;
    }

    public Event getEventById(Long id) {
        Optional<Event> op = repository.findById(id);

        Event event = op.orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found"));
        return event;
    }
    
    public Event insert(Event e){
        Event event = new Event();
        event = repository.save(e);

        return event;
    }

    public EventDTO update(Long id, EventDTO event) {
        try{
            Event entity = repository.getOne(id);
            entity.setPlace(event.getPlace());
            entity.setStartDate(event.getStartDate());
            entity.setEndDate(event.getEndDate());
            entity.setStartTime(event.getStartTime());
            entity.setEndTime(event.getEndTime());

            entity = repository.save(entity);
            return new EventDTO(entity);
        }
        catch(EntityNotFoundException excep){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found");
        }
    }

    public void delete(Long id) {
        try{
            repository.deleteById(id);
        }
        catch(EmptyResultDataAccessException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found");
        }
    }
}

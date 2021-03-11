package com.pooii.ac1.services;

import java.util.List;
import java.util.Optional;

import com.pooii.ac1.entities.Event;
import com.pooii.ac1.repositories.EventRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EventService {

    @Autowired
    private EventRepository repository;

    public List<Event> getEvents(){
        List<Event> list = repository.findAll();

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

    public void delete(Long id) {
        try{
            repository.deleteById(id);
        }
        catch(EmptyResultDataAccessException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found");
        }
    }

    
}

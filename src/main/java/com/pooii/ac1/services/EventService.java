package com.pooii.ac1.services;

import java.util.List;

import com.pooii.ac1.entities.Event;
import com.pooii.ac1.repositories.EventRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    @Autowired
    private EventRepository repository;

    public List<Event> getEvents(){
        List<Event> list = repository.findAll();

        return list;
    }
    
    public Event insert(Event e){
        Event event = new Event();
        event = repository.save(e);

        return event;
    }
}

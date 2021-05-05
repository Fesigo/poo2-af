package com.pooii.ac2.services;

import java.time.LocalDate;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import com.pooii.ac2.dto.EventDTO;
import com.pooii.ac2.entities.Event;
import com.pooii.ac2.repositories.EventRepository;

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

    public Page<Event> getEvents(PageRequest pageRequest, String name, String description, LocalDate startDate){
        Page<Event> list = repository.find(pageRequest, name, description, startDate);

        return list;
    }

    public Event getEventById(Long id) {
        Optional<Event> op = repository.findById(id);

        Event event = op.orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found"));
        return event;
    }
    
    public Event insert(Event e){
        Event event = new Event();

        if(e.getStartDate().isAfter(e.getEndDate())){
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "Data inválida! A data de início deve ser antes da data de fim!");
        }

        if(e.getStartDate().equals(e.getEndDate())){
            if(e.getStartTime().isAfter(e.getEndTime())){
                throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "Horário inválido! O horário de início deve ser antes do horário de fim!");
            }
        }

        event = repository.save(e);

        return event;
    }

    /*
    public EventDTO update(Long id, EventDTO event) {
        try{
            Event entity = repository.getOne(id);
            entity.addPlace(event.getPlaces());
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
    }*/

    public void delete(Long id) {
        try{
            repository.deleteById(id);
        }
        catch(EmptyResultDataAccessException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found");
        }
    }
}

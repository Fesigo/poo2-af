package com.pooii.ac2.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import com.pooii.ac2.dto.EventDTO;
import com.pooii.ac2.entities.Admin;
import com.pooii.ac2.entities.Event;
import com.pooii.ac2.entities.Place;
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

    @Autowired
    private PlaceService placeService;

    @Autowired
    private AdminService adminService;

    public Page<Event> getEvents(PageRequest pageRequest, String name, String description, LocalDate startDate){
        Page<Event> list = repository.find(pageRequest, name, description, startDate);

        return list;
    }

    public Event getEventById(Long id) {
        Optional<Event> op = repository.findById(id);

        Event event = op.orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found"));
        return event;
    }
    
    @Transactional
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

        //
        Admin a = e.getAdmin();
        a = adminService.getAdminById(Long.parseLong(e.getAdmin().getPhoneNumber()));
        event.setAdmin(a);

        event = repository.save(e);

        return event;
    }

    
    public EventDTO update(Long id, EventDTO event) {
        try{
            Event entity = repository.getOne(id);

            verificaDataEvento(entity);

            verificaDispoLugar(event, entity.getPlaces());

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

    @Transactional
    public Event eventPlace(Long idEvent, Long idPlace){
        Event event = getEventById(idEvent);
        Place place = placeService.getPlaceById(idPlace);

        // Verificando disponibilidade do lugar
        if(!place.getEvents().isEmpty()){
            for(Event e : place.getEvents()){

                if(event.getStartDate().isAfter(e.getStartDate()) && event.getStartDate().isBefore(e.getEndDate())){
                    throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "O lugar não está disponível nessa data!");
                }
                else if(event.getEndDate().isAfter(e.getStartDate()) && event.getEndDate().isBefore(e.getEndDate())){
                    throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "O lugar não está disponível nessa data!");
                }
                else if(event.getStartDate().isBefore(e.getStartDate()) && event.getEndDate().isAfter(e.getEndDate())){
                    throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "O lugar não está disponível nessa data!");
                }
                else if(event.getStartDate().isEqual(e.getStartDate()) && event.getEndDate().isEqual(e.getEndDate())){
                    throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "O lugar não está disponível nessa data!");
                }
                else if(event.getStartDate().isEqual(e.getEndDate())){
                    if(event.getStartTime().isBefore(e.getEndTime())){
                        throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "O lugar não está disponível nesse horário!");
                    }
                }
                else if(event.getEndDate().isEqual(e.getStartDate())){
                    if(event.getEndTime().isAfter(e.getStartTime())){
                        throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "O lugar não está disponível nesse horário!");
                    }
                }
            }
        }
        

        event.addPlace(place);

        return event;
    }

    public void verificaDispoLugar(EventDTO event, List<Place> places){

        if(!places.isEmpty()){
            for(Place place : places){
                for(Event e : place.getEvents()){
    
                    if(event.getStartDate().isAfter(e.getStartDate()) && event.getStartDate().isBefore(e.getEndDate())){
                        throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "O lugar não está disponível nessa data!");
                    }
                    else if(event.getEndDate().isAfter(e.getStartDate()) && event.getEndDate().isBefore(e.getEndDate())){
                        throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "O lugar não está disponível nessa data!");
                    }
                    else if(event.getStartDate().isBefore(e.getStartDate()) && event.getEndDate().isAfter(e.getEndDate())){
                        throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "O lugar não está disponível nessa data!");
                    }
                    else if(event.getStartDate().isEqual(e.getStartDate()) && event.getEndDate().isEqual(e.getEndDate())){
                        throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "O lugar não está disponível nessa data!");
                    }
                    else if(event.getStartDate().isEqual(e.getEndDate())){
                        if(event.getStartTime().isBefore(e.getEndTime())){
                            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "O lugar não está disponível nesse horário!");
                        }
                    }
                    else if(event.getEndDate().isEqual(e.getStartDate())){
                        if(event.getEndTime().isAfter(e.getStartTime())){
                            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "O lugar não está disponível nesse horário!");
                        }
                    }
                }
            }
        }
    }

    public void verificaDataEvento(Event event){

        if(event.getEndDate().isBefore(LocalDate.now())){
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "O evento já foi realizado!");
        }

    }


}
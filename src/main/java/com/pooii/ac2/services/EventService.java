package com.pooii.ac2.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import com.pooii.ac2.dto.EventUpdateDTO;
import com.pooii.ac2.dto.TicketGetDTO;
import com.pooii.ac2.dto.TicketSellDTO;
import com.pooii.ac2.entities.Attend;
import com.pooii.ac2.entities.Event;
import com.pooii.ac2.entities.Place;
import com.pooii.ac2.entities.Ticket;
import com.pooii.ac2.entities.TicketType;
import com.pooii.ac2.repositories.AttendRepository;
import com.pooii.ac2.repositories.EventRepository;
import com.pooii.ac2.repositories.TicketRepository;

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
    private EventRepository eventRepository;

    @Autowired
    private PlaceService placeService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private AttendService attendService;

    @Autowired
    private AttendRepository attendRepository;

    @Autowired
    private TicketService ticketService;

    @Autowired TicketRepository ticketRepository;

    public Page<Event> getEvents(PageRequest pageRequest, String name, String description, LocalDate startDate){
        Page<Event> list = eventRepository.find(pageRequest, name, description, startDate);

        return list;
    }

    public Event getEventById(Long id) {
        Optional<Event> op = eventRepository.findById(id);

        Event event = op.orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found"));
        return event;
    }
    
    @Transactional
    public Event insert(Event e){
        Event event = new Event();

        if(e.getStartDate().isAfter(e.getEndDate())){
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "Invalid date! The start date must be before the end date!");
        }

        if(e.getStartDate().equals(e.getEndDate())){
            if(e.getStartTime().isAfter(e.getEndTime())){
                throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "Invalid time! The start date must be before the end date!");
            }
        }

        if(e.getStartDate().isBefore(LocalDate.now())){
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "Invalid date! Events can't be created in the past!");
        }

        e.setAdmin(adminService.getAdminById(e.getAdmin().getId()));

        event = eventRepository.save(e);

        return event;
    }

    
    public EventUpdateDTO update(Long id, EventUpdateDTO event) {
        try{
            Event entity = eventRepository.getOne(id);

            verificaDataEvento(entity);

            verificaDispoLugar(event, entity.getPlaces());

            entity.setStartDate(event.getStartDate());
            entity.setEndDate(event.getEndDate());
            entity.setStartTime(event.getStartTime());
            entity.setEndTime(event.getEndTime());

            entity.setPriceTicket(event.getPriceTicket());

            entity = eventRepository.save(entity);
            return new EventUpdateDTO(entity);
        }
        catch(EntityNotFoundException excep){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found");
        }
    }

    public void delete(Long id) {
        try{

            Event e = getEventById(id);
            if(e.getTickets().size() > 0){
                throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "This event has tickets sold!");
            }

            eventRepository.deleteById(id);
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
                    throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "The place is not available on that date!");
                }
                else if(event.getEndDate().isAfter(e.getStartDate()) && event.getEndDate().isBefore(e.getEndDate())){
                    throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "The place is not available on that date!");
                }
                else if(event.getStartDate().isBefore(e.getStartDate()) && event.getEndDate().isAfter(e.getEndDate())){
                    throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "The place is not available on that date!");
                }
                else if(event.getStartDate().isEqual(e.getStartDate()) && event.getEndDate().isEqual(e.getEndDate())){
                    throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "The place is not available on that date!");
                }
                else if(event.getStartDate().isEqual(e.getEndDate())){
                    if(event.getStartTime().isBefore(e.getEndTime())){
                        throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "The place is not available on that time!");
                    }
                }
                else if(event.getEndDate().isEqual(e.getStartDate())){
                    if(event.getEndTime().isAfter(e.getStartTime())){
                        throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "The place is not available on that time!");
                    }
                }
            }
        }
        

        event.addPlace(place);

        return event;
    }

    public void verificaDispoLugar(EventUpdateDTO event, List<Place> places){

        if(!places.isEmpty()){
            for(Place place : places){
                for(Event e : place.getEvents()){
    
                    if(event.getStartDate().isAfter(e.getStartDate()) && event.getStartDate().isBefore(e.getEndDate())){
                        throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "The place is not available on that date!");
                    }
                    else if(event.getEndDate().isAfter(e.getStartDate()) && event.getEndDate().isBefore(e.getEndDate())){
                        throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "The place is not available on that date!");
                    }
                    else if(event.getStartDate().isBefore(e.getStartDate()) && event.getEndDate().isAfter(e.getEndDate())){
                        throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "The place is not available on that date!");
                    }
                    else if(event.getStartDate().isEqual(e.getStartDate()) && event.getEndDate().isEqual(e.getEndDate())){
                        throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "The place is not available on that date!");
                    }
                    else if(event.getStartDate().isEqual(e.getEndDate())){
                        if(event.getStartTime().isBefore(e.getEndTime())){
                            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "The place is not available on that time!");
                        }
                    }
                    else if(event.getEndDate().isEqual(e.getStartDate())){
                        if(event.getEndTime().isAfter(e.getStartTime())){
                            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "The place is not available on that time!");
                        }
                    }
                }
            }
        }
    }

    public void verificaDataEvento(Event event){

        if(event.getEndDate().isBefore(LocalDate.now())){
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "The event has already happened!");
        }

    }

    public void removeEventPlace(Long idEvent, Long idPlace) {

        Event event = getEventById(idEvent);
        Place place = placeService.getPlaceById(idPlace);

        verificaDataEvento(event);

        int aux = 0;
        for(Place p : event.getPlaces()){
            if(p == place){
                aux = 1;
            }
        }

        if(aux == 0){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This place is not associated to this event!");
        }

       
        event.removePlace(place);
        eventRepository.save(event);

    }

    public Event sellTicket(Long idEvent, TicketSellDTO ticket) {
        
        Event e = getEventById(idEvent);

        verificaDataEvento(e);

        Long contTickets = 0l;
        for(Ticket tick : e.getTickets()){
            if(tick.getType().equals(ticket.getType())){
                contTickets++;
            }
        }

        if(ticket.getType().equals(TicketType.FREE)){
            if(contTickets == e.getAmountFreeTickets()){
                throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "The free tickets are sold out!");
            }
        }
        else{
            if(contTickets == e.getAmountPayedTickets()){
                throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "The paid tickets are sold out!");
            }
        }

        Ticket t = ticketService.insert(ticket, e);
        Attend a = attendService.getAttendById(ticket.getAttend().getId());

        e.addTicket(t);
        e = eventRepository.save(e);

        if(a.getBalance() >= e.getPriceTicket()){
            a.setBalance(a.getBalance() - e.getPriceTicket());
        }
        a.addTickets(t);
        a = attendRepository.save(a);

        return e;

    }

    public void deleteTickets(Long idEvent, TicketSellDTO ticket){

        Attend a = attendService.getAttendById(ticket.getAttend().getId());

        if(a.getTickets().size() == 0){
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "This attendee has no tickets!");
        }

        Event e = getEventById(idEvent);

        if(e.getStartDate().isBefore(LocalDate.now())){
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "The event has already started!");
        }

        for(Ticket t : a.getTickets()){
            if(t.getType().equals(TicketType.PAYED)){
                
                a.setBalance(a.getBalance() + t.getPrice());

                ticketService.deleteTicketById(t.getId());

                return;
            }
        }

        for(Ticket t : a.getTickets()){

            ticketService.deleteTicketById(t.getId());

            return;
        }
        
    }

    public TicketGetDTO getTickets(Long idEvent) {
        
        Event e = getEventById(idEvent);

        TicketGetDTO dto = new TicketGetDTO();

        dto.setTotalAmountFreeTickets(e.getAmountFreeTickets());
        dto.setTotalAmountPayedTickets(e.getAmountPayedTickets());
        dto.setSoldFreeTickets(0l);
        dto.setSoldPayedTickets(0l);

        for(Ticket t : e.getTickets()){
            if(t.getType().equals(TicketType.FREE)){
                dto.setSoldFreeTickets(dto.getSoldFreeTickets() + 1);
            }
            else{
                dto.setSoldPayedTickets(dto.getSoldPayedTickets() + 1);
            }
        }

        dto.setTickets(e.getTickets());

        return dto;

    }

}
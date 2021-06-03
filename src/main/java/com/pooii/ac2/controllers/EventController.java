package com.pooii.ac2.controllers;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.validation.Valid;

import com.pooii.ac2.dto.EventDTO;
import com.pooii.ac2.entities.Event;
import com.pooii.ac2.services.EventService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService service;

    @GetMapping
    public ResponseEntity<Page<Event>> getEvents(

        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "linesPerPage", defaultValue = "6") Integer linesPerPage,
        @RequestParam(value = "direction", defaultValue = "ASC") String direction,
        @RequestParam(value = "orderBy", defaultValue = "id") String orderBy,
        @RequestParam(value = "name", defaultValue = "") String name,
        //@RequestParam(value = "place", defaultValue = "") String place,
        @RequestParam(value = "description", defaultValue = "") String description,
        @RequestParam(value = "startDate", defaultValue = "01/01/1900") String startDate

    ){

        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction),orderBy);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse(startDate, formatter);
        
        Page<Event> list = service.getEvents(pageRequest, name, description, date);
        return ResponseEntity.ok(list);
    }

    @GetMapping("{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id){
        Event event = service.getEventById(id);
        return ResponseEntity.ok(event);
    }

    @PostMapping
    public ResponseEntity<Event> insert(@RequestBody @Valid Event event){
        Event e = service.insert(event);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(e.getId()).toUri();
        return ResponseEntity.created(uri).body(e);
    }

    
    @PutMapping("{id}")
    public ResponseEntity<EventDTO> update(@PathVariable Long id, @RequestBody @Valid EventDTO eventDTO){
        EventDTO dto = service.update(id, eventDTO);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{idEvent}/places/{idPlace}")
    public ResponseEntity<Event> addPlace(@PathVariable Long idEvent, @PathVariable Long idPlace){
        Event event = service.eventPlace(idEvent, idPlace);
        return ResponseEntity.ok(event);
    }

    @DeleteMapping("/{idEvent}/places/{idPlace}")
    public ResponseEntity<Event> removePlace(@PathVariable Long idEvent, @PathVariable Long idPlace){
        service.removeEventPlace(idEvent, idPlace);
        return ResponseEntity.noContent().build();
    }
}
package com.pooii.ac2.controllers;

import java.net.URI;

import javax.validation.Valid;

import com.pooii.ac2.dto.AttendInsertDTO;
import com.pooii.ac2.dto.AttendUpdateDTO;
import com.pooii.ac2.entities.Attend;
import com.pooii.ac2.services.AttendService;

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
@RequestMapping("/attendees")
public class AttendController {
    
    @Autowired
    public AttendService attendService;

    @GetMapping
    public ResponseEntity<Page<Attend>> getAttendees(

        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "linesPerPage", defaultValue = "6") Integer linesPerPage,
        @RequestParam(value = "direction", defaultValue = "ASC") String direction,
        @RequestParam(value = "orderBy", defaultValue = "id") String orderBy

    ){

        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);

        Page<Attend> attendees = attendService.getAttendees(pageRequest);
        return ResponseEntity.ok(attendees);
    }

    @PostMapping
    public ResponseEntity<Attend> insertAttendee(@RequestBody @Valid AttendInsertDTO attend){

        Attend a = attendService.insert(attend);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(a.getId()).toUri();
        return ResponseEntity.created(uri).body(a);
        
    }

    @PutMapping("{id}")
    public ResponseEntity<Attend> updateAttendee(@PathVariable Long id, @RequestBody @Valid AttendUpdateDTO attendDTO){
        Attend a = attendService.update(id, attendDTO);
        return ResponseEntity.ok().body(a);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        attendService.delete(id);
        return ResponseEntity.noContent().build();
    }

}

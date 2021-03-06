package com.pooii.ac2.controllers;

import java.net.URI;

import javax.validation.Valid;

import com.pooii.ac2.entities.Place;
import com.pooii.ac2.services.PlaceService;

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
@RequestMapping("/places")
public class PlaceController {

    @Autowired
    public PlaceService placeService;

    @GetMapping
    public ResponseEntity<Page<Place>> getPlaces(
        
        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "linesPerPage", defaultValue = "6") Integer linesPerPage,
        @RequestParam(value = "direction", defaultValue = "ASC") String direction,
        @RequestParam(value = "orderBy", defaultValue = "id") String orderBy

    ){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);

        Page<Place> places = placeService.getPlaces(pageRequest);
        return ResponseEntity.ok(places);
    }

    @PostMapping
    public ResponseEntity<Place> insertPlace(@RequestBody @Valid Place place){

        Place p = placeService.insert(place);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(p.getId()).toUri();
        return ResponseEntity.created(uri).body(p);
        
    }

    @PutMapping("{id}")
    public ResponseEntity<Place> updatePlace(@PathVariable Long id, @RequestBody @Valid Place place){
        Place p = placeService.update(id, place);
        return ResponseEntity.ok().body(p);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        placeService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
}

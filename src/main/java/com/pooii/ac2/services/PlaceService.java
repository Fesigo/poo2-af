package com.pooii.ac2.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import com.pooii.ac2.entities.Place;
import com.pooii.ac2.repositories.PlaceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PlaceService {

    @Autowired
    public PlaceRepository placeRepository;
    
    public Page<Place> getPlaces(PageRequest pageRequest){
        Page<Place> places = placeRepository.find(pageRequest);
        return places;
    }

    public Place getPlaceById(Long id) {
        Optional<Place> op = placeRepository.findById(id);

        Place place = op.orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Place not found"));
        return place;
    }

    public Place insert(Place place) {
        Place p = new Place();

        p = placeRepository.save(place);
        return p;
    }

    public Place update(Long id, Place place) {
        try{
            Place p = placeRepository.getOne(id);
            p.setName(place.getName());
            p.setAddress(place.getAddress());
            p.setEvents(place.getEvents());

            p = placeRepository.save(p);
            return p;
        }
        catch(EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Place not found");
        }
    }

    public void delete(Long id) {
        try{

            Place p = getPlaceById(id);
            if(p.getEvents().size() > 0){
                throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "This place has already been used by an event!");
            }

            placeRepository.deleteById(id);
        }
        catch(EmptyResultDataAccessException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Place not found");
        }
    }

    
}

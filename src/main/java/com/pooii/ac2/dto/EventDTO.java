package com.pooii.ac2.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pooii.ac2.entities.Event;
import com.pooii.ac2.entities.Place;

public class EventDTO {
    
    @NotBlank(message = "Lugar do evento é obrigatório!")
    private List<Place> places = new ArrayList<>();
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate startDate;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate endDate;
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime startTime;
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime endTime;

    public EventDTO(){
        
    }
    
    public EventDTO(Event entity) {
        //this.place = entity.getPlaces();
        this.startDate = entity.getStartDate();
        this.endDate = entity.getEndDate();
        this.startTime = entity.getStartTime();
        this.endTime = entity.getEndTime();
    }

    public List<Place> getPlaces() {
        return places;
    }

    public void addPlace(Place place) {
        this.places.add(place);
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }
    
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
}

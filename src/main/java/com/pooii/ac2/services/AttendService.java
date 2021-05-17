package com.pooii.ac2.services;

import javax.persistence.EntityNotFoundException;

import com.pooii.ac2.entities.Attend;
import com.pooii.ac2.repositories.AttendRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AttendService {

    @Autowired
    public AttendRepository attendRepository;

    public Page<Attend> getAttendees(PageRequest pageRequest) {
        Page<Attend> attendees = attendRepository.find(pageRequest);
        return attendees;
    }

    public Attend insert(Attend attend) {
        Attend a = new Attend();

        a = attendRepository.save(attend);

        return a;
    }

    public Attend update(Long id, Attend attend) {

        try{
            Attend a = attendRepository.getOne(id);
            a.setName(attend.getName());
            a.setEmail(attend.getEmail());
            a.setBalance(attend.getBalance());

            a = attendRepository.save(a);
            return a;
        }
        catch(EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Attendee not found");
        }
    }

    public void delete(Long id) {
        try{
            attendRepository.deleteById(id);
        }
        catch(EmptyResultDataAccessException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Attendee not found");
        }
    }
    
}

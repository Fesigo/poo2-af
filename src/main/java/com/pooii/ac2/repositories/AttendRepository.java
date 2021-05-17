package com.pooii.ac2.repositories;

import com.pooii.ac2.entities.Attend;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendRepository extends JpaRepository <Attend, Long> {
    
}

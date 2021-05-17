package com.pooii.ac2.repositories;

import com.pooii.ac2.entities.Attend;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AttendRepository extends JpaRepository <Attend, Long> {
    
    @Query("SELECT a FROM Attend a")
    public Page<Attend> find(Pageable pageRequest);

}

package com.pooii.ac2.repositories;

import com.pooii.ac2.entities.Admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AdminRepository extends JpaRepository <Admin, Long> {

    @Query("SELECT a FROM Admin a")
    public Page<Admin> find(Pageable pageRequest);
    
}

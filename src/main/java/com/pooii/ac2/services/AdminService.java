package com.pooii.ac2.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import com.pooii.ac2.entities.Admin;
import com.pooii.ac2.repositories.AdminRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AdminService {
    
    @Autowired
    private AdminRepository adminRepository;

    public Page<Admin> getAdmins(PageRequest pageRequest) {
        Page<Admin> admins = adminRepository.find(pageRequest);
        return admins;
    }

    public Admin getAdminById(Long id){
        Optional<Admin> op = adminRepository.findById(id);

        Admin admin = op.orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin not found"));
        return admin;
    }

    public Admin insert(Admin admin) {
        Admin a = new Admin();

        a = adminRepository.save(admin);
        return a;
    }

    public void delete(Long id) {
        try{
            verificaEventosAdmin(id);
            adminRepository.deleteById(id);
        }
        catch(EmptyResultDataAccessException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin not found");
        }
    }

    public Admin update(Long id, Admin admin) {
        try{
            Admin a = adminRepository.getOne(id);
            a.setName(admin.getName());
            a.setEmail(admin.getEmail());
            a.setPhoneNumber(admin.getPhoneNumber());

            a = adminRepository.save(a);
            return a;
        }
        catch(EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin not found");
        }
    }

    public void verificaEventosAdmin(Long id){

        Admin admin = getAdminById(id);

        if(!admin.getEvents().isEmpty()){
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED,"Esse admin possui eventos cadastrados, portanto não pode ser deletado!");
        }

    }


}

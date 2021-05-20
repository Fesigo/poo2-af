package com.pooii.ac2.controllers;

import java.net.URI;

import javax.validation.Valid;

import com.pooii.ac2.entities.Admin;
import com.pooii.ac2.services.AdminService;

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
@RequestMapping("/admins")
public class AdminController {
    
    @Autowired
    private AdminService adminService;

    @GetMapping
    public ResponseEntity<Page<Admin>> getAdmins(

        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "linesPerPage", defaultValue = "6") Integer linesPerPage,
        @RequestParam(value = "direction", defaultValue = "ASC") String direction,
        @RequestParam(value = "orderBy", defaultValue = "id") String orderBy

    ){

        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);

        Page<Admin> admins = adminService.getAdmins(pageRequest);
        return ResponseEntity.ok(admins);

    }

    @PostMapping
    public ResponseEntity<Admin> insertAdmin (@RequestBody @Valid Admin admin){
        Admin a = adminService.insert(admin);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(a.getId()).toUri();
        return ResponseEntity.created(uri).body(a);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        adminService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Admin> updateAdmin(@PathVariable Long id, @RequestBody @Valid Admin admin){
        Admin a = adminService.update(id, admin);
        return ResponseEntity.ok().body(a);
    }

}

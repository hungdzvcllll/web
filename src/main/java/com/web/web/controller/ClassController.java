package com.web.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.web.service.impl.ClassService;

@RestController
@RequestMapping("/class")
public class ClassController {
    @Autowired
    ClassService service;
    @PostMapping("/create")
    public  ResponseEntity<?> create(@RequestParam("name") String name){
        try{
            service.createClass(name);
            return ResponseEntity.ok("success");
        }
        catch(Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
    @GetMapping("/findById")
    public  ResponseEntity<?> findById(@RequestParam("id")Integer id){
        try{
            
            return ResponseEntity.ok(service.findById(id));
        }
        catch(Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}

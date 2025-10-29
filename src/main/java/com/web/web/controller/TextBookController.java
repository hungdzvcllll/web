package com.web.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.web.service.impl.TextBookService;

@RestController
@RequestMapping("/textBook")
//sách giáo khoa,tạo bởi admin
public class TextBookController {
    @Autowired
    TextBookService service;
    @PostMapping("/add")//thêm sách
    public ResponseEntity<?> add(@RequestParam("name")String name){
        try{
            service.add(name);
            return ResponseEntity.ok("success");
        }
        catch(Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
    @GetMapping("/findById")//tìm theo id
    public ResponseEntity<?> findById(@RequestParam("id")Integer id){
        try{
            return ResponseEntity.ok(service.findById(id));
        }
         catch(Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
    @GetMapping("/findAll")//tìm full
    public ResponseEntity<?> findAll(Pageable pageable){
        try{
            return ResponseEntity.ok(service.findAll(pageable));
        }
        catch(Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}

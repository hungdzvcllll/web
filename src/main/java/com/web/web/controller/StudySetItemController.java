package com.web.web.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.web.dto.request.StudySetItemRequest;
import com.web.web.service.impl.StudySetItemService;

@RestController
@RequestMapping("/item")
//StudySetItem là các cặp khái niệm và định nghĩa
public class StudySetItemController {
    @Autowired
    StudySetItemService service;
    @PostMapping("/add")//thêm
    public ResponseEntity<?>create(@ModelAttribute("request") StudySetItemRequest request,
    @RequestParam("studySetId")Integer studySetId){
        try{
            //System.out.println("list size:"+request.size());
            service.addItems(request,studySetId);
            return ResponseEntity.ok("created");
        }
        catch(Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
    @DeleteMapping("/delete")//xóa dựa trên id
    public ResponseEntity<?>delete(@RequestParam("studySetId")Integer studySetId){
        try{
            service.deleteItems(studySetId);
            return ResponseEntity.ok("deleted");
        }
        catch(Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
    @PutMapping("/update")//update
    public ResponseEntity<?> update(@ModelAttribute StudySetItemRequest request,@RequestParam("id")Integer id){
         try{
            service.update(request, id);
            return ResponseEntity.ok("updated");
        }
        catch(Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
    @GetMapping("/findByStudySet")//tìm theo StudySet
    public ResponseEntity<?> findByStudySet(@RequestParam("studySetId")Integer studySetId,Pageable pageable){
         try{
            return ResponseEntity.ok(service.findByStudySet(studySetId, pageable));
        }
        catch(Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
    @GetMapping("/findById")// tìm theo id
    public ResponseEntity<?> findById(@RequestParam("id")Integer id){
         try{
            return ResponseEntity.ok(service.findById(id));
        }
        catch(Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
    
}

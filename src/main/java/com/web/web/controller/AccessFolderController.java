package com.web.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.web.service.impl.AccessFolderService;

@RestController
@RequestMapping("/accessFolder")
public class AccessFolderController {
    @Autowired
    AccessFolderService accessService;
    @PostMapping("/invite")
    public ResponseEntity<?> invite(@RequestParam("userId")Integer userId,@RequestParam("folderId")Integer folderId){   
        try{
            accessService.inviteUserFolder(userId,folderId);
            return ResponseEntity.ok("invited");
            
        }
        catch(Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
    @GetMapping("/yourFolders")
    public ResponseEntity<?> yourFolders(Pageable pageable){
         try{
           
            return ResponseEntity.ok( accessService.yourFolder(pageable));
            
        }
        catch(Exception e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}

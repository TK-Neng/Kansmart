package com.int221.backend.controller;

import com.int221.backend.dto.AnnounceDto;
import com.int221.backend.entities.Announce;
import com.int221.backend.services.AnnounceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/announcements")
public class AnnounceController {
    @Autowired
    private AnnounceService service;

    @GetMapping("")
    public List<Announce> getAnnounce(){
        return service.getAllAnnounce();
    }

    @GetMapping("/{id}")
    public Announce getAnnounce(@PathVariable Integer id) {
        return service.getAnnounce(id);
    }
}

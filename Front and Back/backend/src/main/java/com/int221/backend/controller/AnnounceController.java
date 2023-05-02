package com.int221.backend.controller;

import com.int221.backend.dto.AnnounceDto;
import com.int221.backend.entities.Announce;
import com.int221.backend.mapper.ListMapper;
import com.int221.backend.services.AnnounceService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pl1/announcements")
@CrossOrigin(origins = "http://localhost:5173")
public class AnnounceController {
    @Autowired
    private AnnounceService service;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ListMapper listMapper;


    @GetMapping("")
    public List<AnnounceDto> getAnnounceDto(){
        List<Announce> announceList = service.getAllAnnounce();
        return listMapper.mapList(announceList, AnnounceDto.class, modelMapper);
    }


    @GetMapping("/{id}")
    public AnnounceDto getAnnounce(@PathVariable Integer id) {
        return modelMapper.map(service.getAnnounce(id), AnnounceDto.class);
    }
}

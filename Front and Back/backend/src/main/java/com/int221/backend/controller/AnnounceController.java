package com.int221.backend.controller;

import com.int221.backend.dto.AnnounceDto;
import com.int221.backend.dto.OutputAnnouceDto;
import com.int221.backend.entities.Announce;
import com.int221.backend.entities.Category;
import com.int221.backend.mapper.ListMapper;
import com.int221.backend.repositories.CategoryRepository;
import com.int221.backend.services.AnnounceService;
import com.int221.backend.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/announcements")
@CrossOrigin(origins = {"http://ip22pl1.sit.kmutt.ac.th:5173","http://intproj22.sit.kmutt.ac.th"})
public class AnnounceController {
    @Autowired
    private AnnounceService service;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ListMapper listMapper;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("")
    public List<OutputAnnouceDto> getAnnounceDto(){
        List<Announce> announceList = service.getAllAnnounce();
        return listMapper.mapList(announceList, OutputAnnouceDto.class, modelMapper);
    }


    @GetMapping("/{id}")
    public OutputAnnouceDto getAnnounce(@PathVariable Integer id) {
        return modelMapper.map(service.getAnnounce(id), OutputAnnouceDto.class);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.OK)
    public OutputAnnouceDto addAnnounceDtoBody(@RequestBody AnnounceDto newAnnounceDto){
        Announce announce = modelMapper.map(newAnnounceDto, Announce.class);
        announce.setId(null);
        announce.setAnnouncementCategory(categoryService.getCategoryById(newAnnounceDto.getCategoryId()));
        service.addAnnounce(announce);
        return modelMapper.map(announce, OutputAnnouceDto.class);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.deleteAnnounce(id);
    }

    @PutMapping("/{id}")
    public OutputAnnouceDto updateAnnounce(@PathVariable Integer id, @RequestBody AnnounceDto newAnnounceDto){
        Announce announce = modelMapper.map(newAnnounceDto, Announce.class);
        announce.setAnnouncementCategory(categoryService.getCategoryById(newAnnounceDto.getCategoryId()));
        service.updateAnnounce(id, announce);
        return modelMapper.map(announce, OutputAnnouceDto.class);
    }



}

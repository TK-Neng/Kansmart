package com.int221.backend.controller;

import com.int221.backend.dto.AnnounceDto;
import com.int221.backend.entities.Announce;
import com.int221.backend.entities.Category;
import com.int221.backend.mapper.ListMapper;
import com.int221.backend.repositories.CategoryRepository;
import com.int221.backend.services.AnnounceService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/announcements")
@CrossOrigin(origins = {"http://localhost:5173","http://intproj22.sit.kmutt.ac.th"})
public class AnnounceController {
    @Autowired
    private AnnounceService service;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ListMapper listMapper;
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("")
    public List<AnnounceDto> getAnnounceDto(){
        List<Announce> announceList = service.getAllAnnounce();
        return listMapper.mapList(announceList, AnnounceDto.class, modelMapper);
    }


    @GetMapping("/{id}")
    public AnnounceDto getAnnounce(@PathVariable Integer id) {
        return modelMapper.map(service.getAnnounce(id), AnnounceDto.class);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public AnnounceDto addAnnounceDtoBody(@RequestBody AnnounceDto newAnnounceDto){
        Announce announce = modelMapper.map(newAnnounceDto, Announce.class);
        String category = newAnnounceDto.getAnnouncementCategory();
        Category categoryEntity = categoryRepository.findCategoryByCategoryName(category);
        announce.setAnnouncementCategory(categoryEntity);
        Announce announceAdd = service.addAnnounce(announce);
        return modelMapper.map(announceAdd, AnnounceDto.class);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.deleteAnnounce(id);
    }

    @PutMapping("/{id}")
    public AnnounceDto updateAnnounce(@PathVariable Integer id, @RequestBody AnnounceDto newAnnounceDto){
        Announce announce = modelMapper.map(newAnnounceDto, Announce.class);
        String category = newAnnounceDto.getAnnouncementCategory();
        Category categoryEntity = categoryRepository.findCategoryByCategoryName(category);
        announce.setAnnouncementCategory(categoryEntity);
        return modelMapper.map(service.updateAnnounce(id, announce), AnnounceDto.class);
    }



}

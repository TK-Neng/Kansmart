package com.int221.backend.controller;

import com.int221.backend.dto.AnnounceDto;
import com.int221.backend.entities.Announce;
import com.int221.backend.entities.Category;
import com.int221.backend.mapper.ListMapper;
import com.int221.backend.repositories.CategoryRepository;
import com.int221.backend.services.AnnounceService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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

    //เพิ่ม
    @GetMapping("")
    public Page<Announce> getAllAnnounce(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "5") Integer pageSize,
            @RequestParam(defaultValue = "publishDate") String sortBy
    ) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Announce> page = service.getAllAnnounce(pageable);

        boolean showPagination = page.getTotalPages() > 1 && page.getTotalPages() <= 10;

        if (!showPagination && page.getTotalElements() > 0) {
            pageSize = Math.toIntExact(page.getTotalElements());
            pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
            page = service.getAllAnnounce(pageable);
        }

        page = new PageImpl<>(page.getContent(), pageable, page.getTotalElements());

        return page;
    }




}
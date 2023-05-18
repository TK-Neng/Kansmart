package com.int221.backend.controller;

import ch.qos.logback.core.model.Model;
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
import org.springframework.data.domain.*;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/announcements")
@CrossOrigin
public class AnnounceController {
    @Autowired
    private AnnounceService service;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ListMapper listMapper;
    @Autowired
    private CategoryService categoryService;

    //นี่ไง

//
//    @GetMapping("")
//    public List<OutputAnnouceDto> getAnnounceDto() {
//        List<Announce> announceList = service.getAllAnnounce();
//        return listMapper.mapList(announceList, OutputAnnouceDto.class, modelMapper);
//    }

    @GetMapping("")
    public List<OutputAnnouceDto> getAnnouncementByMode(@RequestParam(value = "mode", defaultValue = "all") String mode) {
        List<Announce> announces = service.getAnnouncementByMode(mode);
        return listMapper.mapList(announces, OutputAnnouceDto.class, modelMapper);
    }




    @GetMapping("/{id}")
    public OutputAnnouceDto getAnnounce(@PathVariable Integer id) {
        return modelMapper.map(service.getAnnounce(id), OutputAnnouceDto.class);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.OK)
    public OutputAnnouceDto addAnnounceDtoBody(@RequestBody AnnounceDto newAnnounceDto) {
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
    public OutputAnnouceDto updateAnnounce(@PathVariable Integer id, @RequestBody AnnounceDto newAnnounceDto) {
        Announce announce = modelMapper.map(newAnnounceDto, Announce.class);
        announce.setAnnouncementCategory(categoryService.getCategoryById(newAnnounceDto.getCategoryId()));
        service.updateAnnounce(id, announce);
        return modelMapper.map(announce, OutputAnnouceDto.class);
    }

    //นี้ไง
//    @GetMapping("")
//    public String getAllAnnouncements() {
//        List<Announce> announces = AnnounceService.getAllAnnounce();
//        if (announces.isEmpty()) {
//            return "No Announcement";
//        }
//
//        Map<Category, List<Announce>> announceByCategory = new HashMap<>();
//        for (Announce announce : announces) {
//            Category category = announce.getAnnouncementCategory();
//            if (category != null) {
//                announceByCategory.computeIfAbsent(category, k -> new ArrayList<>()).add(announce);
//            }
//        }
//        return toString();
//
//
//
//
//    }
//
//    @GetMapping("")
//    public Page<Announce> getAllAnnounce(
//            @RequestParam(defaultValue = "0") Integer pageNo,
//            @RequestParam(defaultValue = "5") Integer pageSize,
//            @RequestParam(defaultValue = "publishDate") String sortBy
//    ) {
//        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
//        Page<Announce> page = service.getAllAnnounce(pageable);
//
//
//
//        boolean showPagination = page.getTotalPages() > 1 && page.getTotalPages() <= 10;
//
//        if (!showPagination && page.getTotalElements() > 0) {
//            pageSize = Math.toIntExact(page.getTotalElements());
//            pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
//            page = service.getAllAnnounce(pageable);
//        }
//
//        page = new PageImpl<>(page.getContent(), pageable, page.getTotalElements());
//
//        return page;
//    }

}

package com.int221.backend.services;

import com.int221.backend.entities.Announce;
import com.int221.backend.entities.Category;
import com.int221.backend.mapper.ListMapper;
import com.int221.backend.repositories.AnnounceRepository;
import com.int221.backend.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.server.ResponseStatusException;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AnnounceService {
    @Autowired
    private AnnounceRepository repository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ListMapper listMapper;

    public List<Announce> getAllAnnounce() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "publishDate","closeDate"));
    }

    public Announce getAnnounce(Integer id) {
        return repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Announcement id" + " does not exist")
        );
    }


    public Announce addAnnounce(Announce newAnnounce){
        return repository.saveAndFlush(newAnnounce);
    }

    public void deleteAnnounce(Integer id) {
        Optional<Announce> announce = repository.findById(id);
        if (!announce.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Announce not found");
        }
        repository.deleteById(id);
    }


    public Announce updateAnnounce(Integer id, Announce announce){
        Announce newAnnounce = repository.findById(id).orElseThrow(() ->
                new ResourceAccessException(id + " does not exist "));
        newAnnounce.setAnnouncementTitle(announce.getAnnouncementTitle());
        newAnnounce.setAnnouncementDescription(announce.getAnnouncementDescription());
        newAnnounce.setPublishDate(announce.getPublishDate());
        newAnnounce.setCloseDate(announce.getCloseDate());
        newAnnounce.setAnnouncementDisplay(announce.getAnnouncementDisplay());
        if (announce.getAnnouncementCategory() != null) {
            newAnnounce.setAnnouncementCategory(announce.getAnnouncementCategory());
        }
        return repository.saveAndFlush(newAnnounce);
    }



}

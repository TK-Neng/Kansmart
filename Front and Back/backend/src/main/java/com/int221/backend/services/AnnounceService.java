package com.int221.backend.services;

import com.int221.backend.entities.Announce;
import com.int221.backend.entities.Category;
import com.int221.backend.mapper.ListMapper;
import com.int221.backend.repositories.AnnounceRepository;
import com.int221.backend.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.*;

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
        return repository.findAll(Sort.by(Sort.Direction.DESC, "publishDate", "closeDate"));
    }

    public Announce getAnnounce(Integer id) {
        return repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Announcement id" + " does not exist")
        );
    }


    public Announce addAnnounce(Announce newAnnounce) {
        return repository.saveAndFlush(newAnnounce);
    }

    public void deleteAnnounce(Integer id) {
        Optional<Announce> announce = repository.findById(id);
        if (!announce.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Announce not found");
        }
        repository.deleteById(id);
    }


    public Announce updateAnnounce(Integer id, Announce announce) {
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



    public Page<Announce> getAnnounce(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        return repository.findAll(pageable);
    }

    public Page<Announce> getAllAnnounce(Pageable pageable) {
        return repository.findAll(pageable);
    }



    public List<Announce> getAnnouncementByMode(String mode) {
        if (mode.equals("active")) {
            List<Announce> announces = new ArrayList<>();
            announces.addAll(repository.findAllAnnounceByDisplay("Y", ZonedDateTime.parse(ZonedDateTime.now().toString())));
            announces.addAll(repository.findAllAnnounceByDisplayAndDate("Y", ZonedDateTime.parse(ZonedDateTime.now().toString())));
            announces.addAll(repository.findAllAnnounceByDisplayWithoutDate("Y"));
            announces.addAll(repository.findAllAnnounceByDisplayAndDateWithoutCategory("Y", ZonedDateTime.parse(ZonedDateTime.now().toString()), Sort.by(Sort.Direction.DESC, "publishDate", "closeDate")));

            Comparator<Announce> byPublishDateAndCloseDate = Comparator.comparing((Announce a) -> a.getPublishDate() != null)
                    .thenComparing((Announce a) -> a.getCloseDate() != null)
                    .thenComparing(Announce::getPublishDate, Comparator.nullsFirst(Comparator.reverseOrder()))
                    .thenComparing(Announce::getCloseDate, Comparator.nullsFirst(Comparator.reverseOrder()));

            announces.sort(byPublishDateAndCloseDate);
            return announces;
        } else if (mode.equals("close")) {
            return repository.findAllAnnounceByModeClose("Y", ZonedDateTime.parse(ZonedDateTime.now().toString()),Sort.by(Sort.Direction.DESC, "publishDate", "closeDate"));
        } else {
            return getAllAnnounce();
        }
    }



}

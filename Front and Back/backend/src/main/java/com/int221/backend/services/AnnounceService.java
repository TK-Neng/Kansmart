package com.int221.backend.services;

import com.int221.backend.dto.AnnounceDto;
import com.int221.backend.entities.Announce;
import com.int221.backend.repositories.AnnounceRepository;
import com.int221.backend.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.lang.module.ResolutionException;
import java.util.List;

@Service
public class AnnounceService {
    @Autowired
    private AnnounceRepository repository;
    

    public List<Announce> getAllAnnounce() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "publishDate","closeDate"));
    }

    public Announce getAnnounce(Integer id){
        return repository.findById(id).orElseThrow(() ->
                new ResourceAccessException(id + " does not exist "));
    }
}

package com.example.back.Services;

import com.example.back.Entity.Announces;
import com.example.back.Entity.Categories;
import com.example.back.Repositories.AnnounceRepositories;
import com.example.back.Utils.ListMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnnounceServices {
    @Autowired
    private AnnounceRepositories repository;
    private ModelMapper modelMapper;
    private ListMapper listMapper;

    public List<Announces> findAll() {
        List<Announces> announces = repository.findAll(Sort.by(Sort.Direction.DESC,"publishDate"));
        return listMapper.mapList(announces, Announces.class, modelMapper);
    }
}

package com.int221.backend.repositories;

import com.int221.backend.dto.AnnounceDto;
import com.int221.backend.entities.Announce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface AnnounceRepository extends JpaRepository<Announce, Integer> {

}
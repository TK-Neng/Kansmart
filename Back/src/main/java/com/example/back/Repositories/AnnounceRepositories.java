package com.example.back.Repositories;

import com.example.back.Entity.Announces;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnounceRepositories extends JpaRepository<Announces, Integer> {
}

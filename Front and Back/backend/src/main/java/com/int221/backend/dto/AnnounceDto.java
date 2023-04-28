package com.int221.backend.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.Instant;

/**
 * A DTO for the {@link com.int221.backend.entities.Announce} entity
 */
@Data
public class AnnounceDto implements Serializable {
    private final Integer id;
    private final String announcementTitle;
    private final String announcementDescription;
    private final String publishDate;
    private final String closeDate;
    private final String announcementDisplay;
    private final CategoryDto category;
}
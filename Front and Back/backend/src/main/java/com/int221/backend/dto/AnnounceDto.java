package com.int221.backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

import java.io.Serializable;
import java.time.Instant;

/**
 * A DTO for the {@link com.int221.backend.entities.Announce} entity
 */
@Data
public class AnnounceDto implements Serializable {
    private  Integer id;
    private  String announcementTitle;
    private  String announcementDescription;
    private  String publishDate;
    private  String closeDate;
    private  String announcementDisplay;

    @JsonIgnore
    private  CategoryDto category;
}
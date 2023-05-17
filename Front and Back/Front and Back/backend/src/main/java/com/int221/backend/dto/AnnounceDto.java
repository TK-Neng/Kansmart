package com.int221.backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

import java.io.Serializable;
import java.time.Instant;
import java.time.ZonedDateTime;

/**
 * A DTO for the {@link com.int221.backend.entities.Announce} entity
 */
@Data
@Getter
@Setter
@NoArgsConstructor
public class AnnounceDto implements Serializable {

//    private  Integer id;
    private  String announcementTitle;
    private  String announcementDescription;
    private ZonedDateTime publishDate;
    private  ZonedDateTime closeDate;
    private  String announcementDisplay;
    private Integer categoryId;
//    private  String announcementCategory;
}
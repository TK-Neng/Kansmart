package com.int221.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "announces")
public class Announce {
    @Id
    @Column(name = "announcementId", nullable = false)
    private Integer id;

    @Column(name = "announcementTitle", nullable = false, length = 200)
    private String announcementTitle;

    @Column(name = "announcementDescription", nullable = false, length = 10000)
    private String announcementDescription;

    @Column(name = "publishDate")
    private String publishDate;

    @Column(name = "closeDate")
    private String closeDate;

    @Column(name = "announcementDisplay", nullable = false, length = 1)
    private String announcementDisplay;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "categories_categoryId", nullable = false)
    private Category category;

}
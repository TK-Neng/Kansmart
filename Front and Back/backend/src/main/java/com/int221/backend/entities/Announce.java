package com.int221.backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

import java.time.ZonedDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "announces")
public class Announce {

    @Id
    @Column(name = "announcementId", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "announcementTitle", nullable = false, length = 200)
    private String announcementTitle;

    @Column(name = "announcementDescription", nullable = false, length = 10000)
    private String announcementDescription;


    @Column(name = "publishDate")
    private ZonedDateTime publishDate;

    @Column(name = "closeDate")
    private ZonedDateTime closeDate;

    @Column(name = "announcementDisplay", nullable = false, length = 1)
    private String announcementDisplay;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "categories_categoryId", nullable = false)
    private Category announcementCategory;


}
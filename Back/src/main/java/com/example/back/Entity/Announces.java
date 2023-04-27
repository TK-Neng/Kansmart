package com.example.back.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

@Getter
@Setter
@Entity
@Table(name = "announces")
public class Announces {
    @Id
    private Integer announcementId;
    private String announcementTitle;
    private String announcementDescription;
    private String publishDate;
    private String closeDate;
    private String announcementDisplay;

    public Integer getAnnouncementId() {
        return announcementId;
    }

    public void setAnnouncementId(Integer announcementId) {
        this.announcementId = announcementId;
    }

    public String getAnnouncementTitle() {
        return announcementTitle;
    }

    public void setAnnouncementTitle(String announcementTitle) {
        this.announcementTitle = announcementTitle;
    }

    public String getAnnouncementDescription() {
        return announcementDescription;
    }

    public void setAnnouncementDescription(String announcementDescription) {
        this.announcementDescription = announcementDescription;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(String closeDate) {
        this.closeDate = closeDate;
    }

    public String getAnnouncementDisplay() {
        return announcementDisplay;
    }

    public void setAnnouncementDisplay(String announcementDisplay) {
        this.announcementDisplay = announcementDisplay;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categories_categoryId")
    private Categories categories;
}

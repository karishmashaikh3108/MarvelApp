package com.example.mymarvelapp.Models;

import java.net.URL;
import java.util.List;

public class Comics {
    private long id;
    private long digitalId;
    private String title;
    private String description;
    private Thumbnail thumbnail;
    private String path;
    private String extension;

    public Comics(long id, long digitalId, String title, String description, String path, String extension) {
        this.id = id;
        this.digitalId = digitalId;
        this.title = title;
        this.description = description;
        this.path = path;
        this.extension = extension;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDigitalID() {
        return digitalId;
    }

    public void setDigitalID(long digitalID) {
        this.digitalId = digitalID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }
}


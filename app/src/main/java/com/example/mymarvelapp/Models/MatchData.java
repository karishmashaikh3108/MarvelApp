package com.example.mymarvelapp.Models;

import java.io.Serializable;

public class MatchData implements Serializable {
    private long id;
    private String name;
    private String description;
    private String modified;
    private Thumbnail thumbnail;
    private String resourceURI;
    private String path;
    private String extension;

    public MatchData(long id, String name, String description, String modified, String resourceURI, String path, String extension) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.modified = modified;
        this.thumbnail = thumbnail;
        this.resourceURI = resourceURI;
        this.path = path;
        this.extension = extension;
    }

    public long getID() {
        return id;
    }

    public void setID(long value) {
        this.id = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String value) {
        this.description = value;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String value) {
        this.modified = value;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Thumbnail value) {
        this.thumbnail = value;
    }

    public String getResourceURI() {
        return resourceURI;
    }

    public void setResourceURI(String value) {
        this.resourceURI = value;
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
}

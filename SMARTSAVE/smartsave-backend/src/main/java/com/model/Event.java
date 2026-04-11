package com.model;

public class Event {

    private Long id;
    private String title;
    private String location;
    private String date;

    public Event() {
    }

    public Event(Long id, String title, String location, String date) {
        this.id = id;
        this.title = title;
        this.location = location;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
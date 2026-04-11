package com.model;

import java.util.List;

public class Organization {

    private Long id;
    private String name;
    private String description;
    private List<Event> events;

    public Organization() {
    }

    public Organization(Long id, String name, String description, List<Event> events) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.events = events;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
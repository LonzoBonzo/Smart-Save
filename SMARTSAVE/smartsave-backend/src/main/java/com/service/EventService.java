package com.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import com.model.Event;

@Service
public class EventService {

    private List<Event> events = new ArrayList<>();

    public Event createEvent(Event event) {
        events.add(event);
        return event;
    }

    public List<Event> getAllEvents() {
        return events;
    }

    public Event getEventById(Long id) {
        for (Event e : events) {
            if (e.getId().equals(id)) {
                return e;
            }
        }
        return null;
    }

    public void deleteEvent(Long id) {
        events.removeIf(e -> e.getId().equals(id));
    }
}
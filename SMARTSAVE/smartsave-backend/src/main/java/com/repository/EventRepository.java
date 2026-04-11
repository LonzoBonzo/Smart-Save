package com.repository;

import java.util.List;
import com.model.Event;

public interface EventRepository {

    Event save(Event event);

    Event findById(Long id);

    List<Event> findAll();

    void delete(Long id);
}
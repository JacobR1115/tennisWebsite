package com.tennisWebsite.service;

import com.tennisWebsite.dto.EventDto;
import com.tennisWebsite.entity.Event;

import java.util.List;

public interface EventService {

    EventDto createEvent(Long clubId, EventDto eventDto);

    List<EventDto> findAllEvents();

    EventDto findByEventId(Long eventId);

    EventDto updateEvent(EventDto eventDto);

    void deleteEvent(long eventId);
}

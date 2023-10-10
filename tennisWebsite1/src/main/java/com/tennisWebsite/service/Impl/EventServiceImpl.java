package com.tennisWebsite.service.Impl;

import com.tennisWebsite.dao.ClubRepo;
import com.tennisWebsite.dao.EventRepo;
import com.tennisWebsite.dto.EventDto;
import com.tennisWebsite.entity.Club;
import com.tennisWebsite.entity.Event;
import com.tennisWebsite.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.tennisWebsite.mapper.EventMapper.mapToEvent;
import static com.tennisWebsite.mapper.EventMapper.mapToEventDto;

@Service
public class EventServiceImpl implements EventService {

    private EventRepo eventRepo;
    private ClubRepo clubRepo;

    @Autowired
    public EventServiceImpl(EventRepo eventRepo, ClubRepo clubRepo) {
        this.eventRepo = eventRepo;
        this.clubRepo = clubRepo;
    }

    @Override
    public EventDto createEvent(Long clubId, EventDto eventDto) {
        Club club = clubRepo.findById(clubId).get();
        Event event = mapToEvent(eventDto);
        event.setClub(club);
        eventRepo.save(event);
        return mapToEventDto(event);
    }

    @Override
    public List<EventDto> findAllEvents() {
        List<Event> events = eventRepo.findAll();
        return events.stream().map(event -> mapToEventDto(event)).collect(Collectors.toList());
    }

    @Override
    public EventDto findByEventId(Long eventId) {
        Event event = eventRepo.findById(eventId).get();
        return mapToEventDto(event);
    }

    @Override
    public EventDto updateEvent(EventDto eventDto) {
        Event event = mapToEvent(eventDto);
        eventRepo.save(event);
        return mapToEventDto(event);
    }

    @Override
    public void deleteEvent(long eventId) {
        eventRepo.deleteById(eventId);
    }
}

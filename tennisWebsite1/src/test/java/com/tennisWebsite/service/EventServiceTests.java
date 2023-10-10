package com.tennisWebsite.service;

import com.tennisWebsite.dao.ClubRepo;
import com.tennisWebsite.dao.EventRepo;
import com.tennisWebsite.dto.ClubDto;
import com.tennisWebsite.dto.EventDto;
import com.tennisWebsite.entity.Club;
import com.tennisWebsite.entity.Event;
import com.tennisWebsite.service.Impl.EventServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EventServiceTests {

    @Mock
    private ClubRepo clubRepo;
    @Mock
    private EventRepo eventRepo;
    @InjectMocks
    private EventServiceImpl eventService;
    private Club club;
    private ClubDto clubDto;
    private Event event;
    private EventDto eventDto;

    @BeforeEach
    public void init() {
        club = Club.builder()
                .title("test")
                .photoUrl("test.jpg")
                .content("test club")
                .build();
        clubDto = ClubDto.builder()
                .title("test")
                .photoUrl("test.jpg")
                .content("test club")
                .build();
        event = Event.builder()
                .name("test")
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now())
                .type("test")
                .photoUrl("test.jpg")
                .club(club)
                .build();
        eventDto = EventDto.builder()
                .name("test")
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now())
                .type("test")
                .photoUrl("test.jpg")
                .club(club)
                .build();
    }

    @Test
    public void EventService_CreateEvent_SavesEvent() {
        when(clubRepo.findById(club.getId())).thenReturn(Optional.of(club));
        when(eventRepo.save(Mockito.any(Event.class))).thenReturn(event);

        EventDto savedEvent = eventService.createEvent(club.getId(), eventDto);

        Assertions.assertThat(savedEvent).isNotNull();
    }

    @Test
    public void EventService_FindAllEvents_ReturnsAllEvents() {

        List<Event> events = new ArrayList<>();
        events.add(event);

        when(eventRepo.findAll()).thenReturn(events);

        List<EventDto> returnedEvents = eventService.findAllEvents();

        Assertions.assertThat(returnedEvents).isNotEmpty();
    }

    @Test
    public void EventService_FindEventById_ReturnsCorrectEvent() {
        when(eventRepo.findById(event.getId())).thenReturn(Optional.of(event));

        EventDto returnedEvent = eventService.findByEventId(event.getId());

        Assertions.assertThat(returnedEvent).isNotNull();
        Assertions.assertThat(returnedEvent.getId()).isEqualTo(eventDto.getId());
    }

    @Test
    public void EventService_UpdateEvent_ReturnsUpdatedEvent() {
        when(eventRepo.save(Mockito.any(Event.class))).thenReturn(event);

        EventDto updatedEvent = eventService.updateEvent(eventDto);

        Assertions.assertThat(updatedEvent).isNotNull();
        Assertions.assertThat(updatedEvent.getId()).isEqualTo(event.getId());
    }

    @Test
    public void EventService_DeleteEvent_DeletesEventWithId() {

        doNothing().when(eventRepo).deleteById(1L);

        assertAll(() -> eventService.deleteEvent(1));
    }
}

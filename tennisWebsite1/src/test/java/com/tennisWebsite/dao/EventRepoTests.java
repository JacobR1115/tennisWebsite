package com.tennisWebsite.dao;

import com.tennisWebsite.entity.Club;
import com.tennisWebsite.entity.Event;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

// DON'T NORMALLY TEST REPOSITORY LAYER, JUST FOR PRACTICE


@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class EventRepoTests {

    @Autowired
    private EventRepo eventRepo;

    @Autowired
    private ClubRepo clubRepo;

    @Test
    public void EventRepo_Save_ReturnsSavedEvent() {

        Club club = Club.builder()
                .title("test")
                .photoUrl("test.jpg")
                .content("test")
                .build();

        Club savedClub = clubRepo.save(club);

        Event event = Event.builder()
                .name("test")
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now())
                .type("test")
                .photoUrl("test.jpg")
                .club(savedClub)
                .build();

        Event savedEvent = eventRepo.save(event);

        Assertions.assertThat(savedEvent).isNotNull();
        Assertions.assertThat(savedEvent.getId()).isGreaterThan(0);
    }

    @Test
    public void EventRepo_FindAll_ReturnsAllEvents() {

        Club club = Club.builder()
                .title("test")
                .photoUrl("test.jpg")
                .content("test")
                .build();

        Club savedClub = clubRepo.save(club);

        Event event1 = Event.builder()
                .name("test")
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now())
                .type("test")
                .photoUrl("test.jpg")
                .club(savedClub)
                .build();

        Event event2 = Event.builder()
                .name("test")
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now())
                .type("test")
                .photoUrl("test.jpg")
                .club(savedClub)
                .build();

        eventRepo.save(event1);
        eventRepo.save(event2);

        List<Event> events = eventRepo.findAll();

        Assertions.assertThat(events).isNotEmpty();
        Assertions.assertThat(events.size()).isEqualTo(2);
    }

    @Test
    public void EventRepo_FindById_ReturnsEventWithCorrectId() {

        Club club = Club.builder()
                .title("test")
                .photoUrl("test.jpg")
                .content("test")
                .build();

        Club savedClub = clubRepo.save(club);

        Event event = Event.builder()
                .name("test")
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now())
                .type("test")
                .photoUrl("test.jpg")
                .club(savedClub)
                .build();

        eventRepo.save(event);

        Event returnedEvent =  eventRepo.findById(event.getId()).get();

        Assertions.assertThat(returnedEvent).isNotNull();
        Assertions.assertThat(returnedEvent.getId()).isEqualTo(event.getId());
    }

    @Test
    public void EventRepo_DeleteById_DeletesEvent() {

        Club club = Club.builder()
                .title("test")
                .photoUrl("test.jpg")
                .content("test")
                .build();

        Club savedClub = clubRepo.save(club);

        Event event = Event.builder()
                .name("test")
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now())
                .type("test")
                .photoUrl("test.jpg")
                .club(savedClub)
                .build();

        eventRepo.save(event);

        eventRepo.deleteById(event.getId());

        List<Event> events = eventRepo.findAll();

        Assertions.assertThat(events).isEmpty();
    }
}

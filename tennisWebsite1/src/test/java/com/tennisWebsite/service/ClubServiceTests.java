package com.tennisWebsite.service;

import com.tennisWebsite.dao.ClubRepo;
import com.tennisWebsite.dto.ClubDto;
import com.tennisWebsite.entity.Club;
import com.tennisWebsite.entity.Event;
import com.tennisWebsite.service.Impl.ClubServiceImpl;
import org.assertj.core.api.Assertions;
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
public class ClubServiceTests {

    @Mock
    private ClubRepo clubRepo;

    @InjectMocks
    private ClubServiceImpl clubService;

    @Test
    public void ClubService_FindAllClubs_ReturnsAllClubs() {

        Club club = Club.builder()
                .title("test")
                .photoUrl("test.jpg")
                .content("test club")
                .build();

        Event event = Event.builder()
                .name("test")
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now())
                .type("test")
                .photoUrl("test.jpg")
                .club(club)
                .build();

        List<Event> events = new ArrayList<>();
        events.add(event);

        club.setEvents(events);

        List<Club> clubs = new ArrayList<>();
        clubs.add(club);

        when(clubRepo.findAll()).thenReturn(clubs);

        List<ClubDto> clubDtoList = clubService.findAllClubs();

        Assertions.assertThat(clubDtoList).isNotEmpty();
        Assertions.assertThat(clubDtoList.size()).isEqualTo(1);
    }

    @Test
    public void ClubService_SaveClub_ReturnsSavedClub() {

        Club club = Club.builder()
                .id(1)
                .title("test")
                .photoUrl("test.jpg")
                .content("test club")
                .build();

        ClubDto clubDto = ClubDto.builder()
                .title("test")
                .photoUrl("test.jpg")
                .content("test club")
                .build();

        when(clubRepo.save(Mockito.any(Club.class))).thenReturn(club);

        Club returnedClub = clubService.saveClub(clubDto);

        Assertions.assertThat(returnedClub).isNotNull();
        Assertions.assertThat(returnedClub.getId()).isGreaterThan(0);
    }

    @Test
    public void ClubService_FindClubById_ReturnsClubWithCorrectId() {

        Club club = Club.builder()
                .id(1)
                .title("test")
                .photoUrl("test.jpg")
                .content("test club")
                .build();

        Event event = Event.builder()
                .name("test")
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now())
                .type("test")
                .photoUrl("test.jpg")
                .club(club)
                .build();

        List<Event> events = new ArrayList<>();
        events.add(event);

        club.setEvents(events);

        when(clubRepo.findById(Mockito.any(Long.class))).thenReturn(Optional.of(club));

        ClubDto returnedClubDto = clubService.findClubById(club.getId());

        Assertions.assertThat(returnedClubDto).isNotNull();
        Assertions.assertThat(returnedClubDto.getId()).isEqualTo(1);
    }

    @Test
    public void ClubService_UpdateClub_ReturnsUpdatedClub() {

        Club club = Club.builder()
                .title("test")
                .photoUrl("test.jpg")
                .content("test club")
                .build();

        ClubDto clubDto = ClubDto.builder()
                .title("test")
                .photoUrl("test.jpg")
                .content("test club")
                .build();

        when(clubRepo.save(Mockito.any(Club.class))).thenReturn(club);

        Club returnedClub = clubService.updateClub(clubDto);

        Assertions.assertThat(returnedClub).isNotNull();
        Assertions.assertThat(returnedClub).isEqualTo(club);
    }

    @Test
    public void ClubService_Delete_DeletesClubWithId() {

        Club club = Club.builder()
                .id(1)
                .title("test")
                .photoUrl("test.jpg")
                .content("test club")
                .build();

        doNothing().when(clubRepo).deleteById(1L);

        assertAll(() -> clubService.delete(1));
    }

    @Test
    public void ClubService_SearchClubs_ReturnsAllClubsWhereTitleLikeQuery() {

        Club club = Club.builder()
                .title("test")
                .photoUrl("test.jpg")
                .content("test club")
                .build();

        Event event = Event.builder()
                .name("test")
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now())
                .type("test")
                .photoUrl("test.jpg")
                .club(club)
                .build();

        List<Event> events = new ArrayList<>();
        events.add(event);

        club.setEvents(events);

        List<Club> clubs = new ArrayList<>();
        clubs.add(club);

        when(clubRepo.searchClubs("test")).thenReturn(clubs);

        List<ClubDto> clubDtoList = clubService.searchClubs("test");

        Assertions.assertThat(clubDtoList).isNotEmpty();
        Assertions.assertThat(clubDtoList.size()).isEqualTo(1);
    }
}

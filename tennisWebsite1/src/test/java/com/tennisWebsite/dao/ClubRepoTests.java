package com.tennisWebsite.dao;

import com.tennisWebsite.entity.Club;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

// DON'T NORMALLY TEST REPOSITORY LAYER, JUST FOR PRACTICE

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ClubRepoTests {

    @Autowired
    private ClubRepo clubRepo;

    @Test
    public void ClubRepo_Save_ReturnsSavedClub() {

        // Arrange
        Club club = Club.builder()
                .title("test")
                .photoUrl("test.jpg")
                .content("test club")
                .build();

        // Act
        Club savedClub = clubRepo.save(club);

        // Assert
        Assertions.assertThat(savedClub).isNotNull();
        Assertions.assertThat(savedClub.getId()).isGreaterThan(0);
    }

    @Test
    public void ClubRepo_FindAll_ReturnsAllClubs() {

        Club club1 = Club.builder()
                .title("test")
                .photoUrl("test.jpg")
                .content("test club 1")
                .build();

        Club club2 = Club.builder()
                .title("test")
                .photoUrl("test.jpg")
                .content("test club 2")
                .build();

        clubRepo.save(club1);
        clubRepo.save(club2);

        List<Club> clubs = clubRepo.findAll();

        Assertions.assertThat(clubs).isNotNull();
        Assertions.assertThat(clubs.size()).isEqualTo(2);
    }

    @Test
    public void ClubRepo_FindById_ReturnsClubWithCorrectId() {

        Club club = Club.builder()
                .title("test")
                .photoUrl("test.jpg")
                .content("test club 1")
                .build();

        clubRepo.save(club);

        Club returnedClub = clubRepo.findById(club.getId()).get();

        Assertions.assertThat(returnedClub).isNotNull();
        Assertions.assertThat(returnedClub.getId()).isEqualTo(club.getId());
    }

    @Test
    public void ClubRepo_DeleteById_DeletesClub() {

        Club club = Club.builder()
                .title("test")
                .photoUrl("test.jpg")
                .content("test club 1")
                .build();

        clubRepo.save(club);

        clubRepo.deleteById(club.getId());

        List<Club> clubs = clubRepo.findAll();

        Assertions.assertThat(clubs).isEmpty();
    }

    @Test
    public void ClubRepo_SearchClubs_ReturnsClubsWithTitleLikeQuery() {

        Club club1 = Club.builder()
                .title("test")
                .photoUrl("test.jpg")
                .content("test club 1")
                .build();

        Club club2 = Club.builder()
                .title("trial")
                .photoUrl("trial.jpg")
                .content("trial club 1")
                .build();

        clubRepo.save(club1);
        clubRepo.save(club2);

        List<Club> clubs = clubRepo.searchClubs("test");

        Assertions.assertThat(clubs).isNotEmpty();
        Assertions.assertThat(clubs.size()).isEqualTo(1);
    }
}

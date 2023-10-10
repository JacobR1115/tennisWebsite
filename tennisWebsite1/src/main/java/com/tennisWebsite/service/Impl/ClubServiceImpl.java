package com.tennisWebsite.service.Impl;

import com.tennisWebsite.dao.ClubRepo;
import com.tennisWebsite.dto.ClubDto;
import com.tennisWebsite.entity.Club;
import com.tennisWebsite.service.ClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.tennisWebsite.mapper.ClubMapper.mapToClub;
import static com.tennisWebsite.mapper.ClubMapper.mapToClubDto;

@Service
public class ClubServiceImpl implements ClubService {

    private ClubRepo clubRepo;

    @Autowired
    public ClubServiceImpl(ClubRepo clubRepo) {
        this.clubRepo = clubRepo;
    }


    // Want to return the dto instead of the entity
    @Override
    public List<ClubDto> findAllClubs() {
        List<Club> clubs = clubRepo.findAll();
        return clubs.stream().map((club) -> mapToClubDto(club)).collect(Collectors.toList());
    }

    @Override
    public Club saveClub(ClubDto clubDto) {
        Club club = mapToClub(clubDto);
        return clubRepo.save(club);
    }

    @Override
    public ClubDto findClubById(long clubId) {
        Club club = clubRepo.findById(clubId).get();
        return mapToClubDto(club);
    }

    @Override
    public Club updateClub(ClubDto clubDto) {
        Club club = mapToClub(clubDto);
        return clubRepo.save(club);
    }

    @Override
    public void delete(long clubId) {
        clubRepo.deleteById(clubId);
    }

    @Override
    public List<ClubDto> searchClubs(String query) {
        List<Club> clubs = clubRepo.searchClubs(query);
        return clubs.stream().map(club -> mapToClubDto(club)).collect(Collectors.toList());
    }
}

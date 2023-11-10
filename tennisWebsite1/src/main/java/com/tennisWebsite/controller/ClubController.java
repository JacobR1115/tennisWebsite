package com.tennisWebsite.controller;

import com.tennisWebsite.dto.ClubDto;
import com.tennisWebsite.entity.Club;
import com.tennisWebsite.entity.DistanceMatrixDetails;
import com.tennisWebsite.entity.DistanceMatrixElements;
import com.tennisWebsite.entity.DistanceMatrixResponse;
import com.tennisWebsite.service.ClubService;
import com.tennisWebsite.service.DistanceMatrixService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ClubController {

    private ClubService clubService;

    private DistanceMatrixService distanceMatrixService;

    @Autowired
    public ClubController(ClubService clubService, DistanceMatrixService distanceMatrixService) {
        this.clubService = clubService;
        this.distanceMatrixService = distanceMatrixService;
    }


    @GetMapping("/clubs")
    public String listClubs(Model model) {

        List<ClubDto> clubs = clubService.findAllClubs();
        model.addAttribute("clubs", clubs);

        return "clubs-list";
    }

    @GetMapping("/clubs/{clubId}")
    public String clubDetail(@PathVariable("clubId") long clubId,
                             Model model) {
        ClubDto clubDto = clubService.findClubById(clubId);
        model.addAttribute("club", clubDto);
        return "clubs-detail";
    }

    @GetMapping("/clubs/new")
    public String createClubForm(Model model) {
        Club club = new Club();
        model.addAttribute("club", club);
        return "clubs-create";
    }

    // Creating a new Club is not working, because the club_id value is null
    @PostMapping("/clubs/new")
    public String saveClub(@Valid @ModelAttribute("club") ClubDto clubDto,
                           BindingResult bindingResult,
                           Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("club", clubDto);
            return "clubs-create";
        }
        clubService.saveClub(clubDto);
        return "redirect:/clubs";
    }

    @GetMapping("/clubs/{clubId}/edit")
    public String editClubForm(@PathVariable("clubId") long clubId, Model model) {
        ClubDto club = clubService.findClubById(clubId);
        model.addAttribute("club", club);
        return "clubs-edit";
    }

    @PostMapping("/clubs/{clubId}/edit")
    public String updateClub(@PathVariable("clubId") long clubId,
                             @Valid @ModelAttribute("club") ClubDto club,
                             BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("club", club);
            return "clubs-edit";
        }
        club.setId(clubId);
        clubService.updateClub(club);
        return "redirect:/clubs";
    }

    @GetMapping("/clubs/{clubId}/delete")
    public String deleteClub(@PathVariable("clubId") long clubId) {
        clubService.delete(clubId);
        return "redirect:/clubs";
    }

    @GetMapping("/clubs/search")
    public String searchClub(@RequestParam(value = "query") String query, Model model) {
        List<ClubDto> clubs = clubService.searchClubs(query);
        model.addAttribute("clubs", clubs);

        return "clubs-list";
    }

    @GetMapping("/clubs/location")
    public String searchClubByLocation(@RequestParam("origin") String origin, Model model) {
        List<ClubDto> allClubs = clubService.findAllClubs();

        List<ClubDto> clubs = new ArrayList<>();

        for (ClubDto club: allClubs) {
            DistanceMatrixResponse response = distanceMatrixService.fetchDistanceByLocation(club.getCity(), origin);

            float distance = Integer.MAX_VALUE;

            List<DistanceMatrixElements> rows = response.getRows();
            for (DistanceMatrixElements elements: rows) {
                List<DistanceMatrixDetails> details = elements.getElements();
                for (DistanceMatrixDetails values: details) {
                    String str = values.getDistanceValue().getText();
                    String[] splitStr = str.split("\\s+");
                    distance = Float.parseFloat(splitStr[0]);
                }
            }

            if (distance < 100) clubs.add(club);
        }

        model.addAttribute("clubs", clubs);
        return "clubs-list";
    }
}

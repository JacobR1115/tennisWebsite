package com.tennisWebsite.controller;

import com.tennisWebsite.dto.ClubDto;
import com.tennisWebsite.dto.EventDto;
import com.tennisWebsite.entity.DistanceMatrixDetails;
import com.tennisWebsite.entity.DistanceMatrixElements;
import com.tennisWebsite.entity.DistanceMatrixResponse;
import com.tennisWebsite.entity.Event;
import com.tennisWebsite.service.DistanceMatrixService;
import com.tennisWebsite.service.EventService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class EventController {

    private EventService eventService;

    private DistanceMatrixService distanceMatrixService;

    @Autowired
    public EventController(EventService eventService, DistanceMatrixService distanceMatrixService) {
        this.eventService = eventService;
        this.distanceMatrixService = distanceMatrixService;
    }

    @GetMapping("/events")
    public String eventList(Model model) {
        List<EventDto> events = eventService.findAllEvents();
        model.addAttribute("events", events);
        return "events-list";
    }

    @GetMapping("/events/{eventId}")
    public String viewEvent(@PathVariable("eventId") Long eventId, Model model) {
        EventDto eventDto = eventService.findByEventId(eventId);
        model.addAttribute("event", eventDto);
        return "events-detail";
    }

    @GetMapping("/events/{clubId}/new")
    public String createEventForm(@PathVariable("clubId") Long clubId, Model model) {
        Event event = new Event();
        model.addAttribute("clubId", clubId);
        model.addAttribute("event", event);
        return "events-create";
    }

    @PostMapping("/events/{clubId}")
    public String createEvent(@PathVariable("clubId") Long clubId,
                              @ModelAttribute("event")EventDto eventDto,
                              BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("event", eventDto);
            return "clubs-create";
        }
        eventService.createEvent(clubId, eventDto);
        return "redirect:/clubs/" + clubId;
    }

    @GetMapping("/events/{eventId}/edit")
    public String editEventForm(@PathVariable("eventId") long eventId, Model model) {
        EventDto event = eventService.findByEventId(eventId);
        model.addAttribute("event", event);
        return "events-edit";
    }

    @PostMapping("/events/{eventId}/edit")
    public String updateEvent(@PathVariable("eventId") long eventId,
                             @Valid @ModelAttribute("event") EventDto event,
                             BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("event", event);
            return "events-edit";
        }
        EventDto eventDto = eventService.findByEventId(eventId);
        event.setId(eventId);
        event.setClub(eventDto.getClub());
        eventService.updateEvent(event);
        return "redirect:/events";
    }

    @GetMapping("/events/{eventId}/delete")
    public String deleteEvent(@PathVariable("eventId") long eventId) {
        eventService.deleteEvent(eventId);
        return "redirect:/events";
    }

    @GetMapping("/events/location")
    public String searchEventByLocation(@RequestParam("origin") String origin, Model model) {
        List<EventDto> allEvents = eventService.findAllEvents();

        List<EventDto> events = new ArrayList<>();

        for (EventDto event: allEvents) {
            DistanceMatrixResponse response = distanceMatrixService.fetchDistanceByLocation(event.getCity(), origin);

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

            if (distance < 100) events.add(event);
        }

        model.addAttribute("events", events);
        return "events-list";
    }
}

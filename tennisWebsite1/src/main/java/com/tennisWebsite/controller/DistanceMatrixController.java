package com.tennisWebsite.controller;

import com.tennisWebsite.entity.DistanceMatrixResponse;
import com.tennisWebsite.service.DistanceMatrixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class DistanceMatrixController {

    private DistanceMatrixService distanceMatrixService;

    @Autowired
    public DistanceMatrixController(DistanceMatrixService distanceMatrixService) {
        this.distanceMatrixService = distanceMatrixService;
    }

    @GetMapping(value = "/distance", consumes = MediaType.ALL_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public DistanceMatrixResponse GetDistanceBetweenLocations(@RequestParam String destination, @RequestParam String origin) {

        return distanceMatrixService.fetchDistanceByLocation(destination, origin);
    }
}

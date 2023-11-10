package com.tennisWebsite.service;

import com.tennisWebsite.entity.DistanceMatrixResponse;

import java.util.List;

public interface DistanceMatrixService {
    public DistanceMatrixResponse fetchDistanceByLocation(String destinations, String origin);
}

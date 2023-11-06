package com.tennisWebsite.service;

import com.tennisWebsite.entity.DistanceMatrixResponse;

public interface DistanceMatrixService {
    public DistanceMatrixResponse fetchDistanceByLocation(String city1, String city2);
}

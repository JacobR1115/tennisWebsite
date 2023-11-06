package com.tennisWebsite.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class DistanceMatrixDetails {

    @JsonProperty("distance")
    private DistanceMatrixValue distanceValue;

    @JsonProperty("duration")
    private DistanceMatrixValue durationValue;
}

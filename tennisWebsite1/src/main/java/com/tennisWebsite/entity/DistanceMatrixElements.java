package com.tennisWebsite.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Data
public class DistanceMatrixElements {

    @JsonProperty("elements")
    private List<DistanceMatrixDetails> elements;
}

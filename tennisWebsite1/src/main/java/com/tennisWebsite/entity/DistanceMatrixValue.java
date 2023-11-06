package com.tennisWebsite.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class DistanceMatrixValue {

    @JsonProperty("text")
    private String text;

    @JsonProperty("value")
    private Integer value;
}

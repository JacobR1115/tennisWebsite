package com.tennisWebsite.service.Impl;

import com.tennisWebsite.entity.DistanceMatrixDetails;
import com.tennisWebsite.entity.DistanceMatrixElements;
import com.tennisWebsite.entity.DistanceMatrixResponse;
import com.tennisWebsite.service.DistanceMatrixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class DistanceMatrixServiceImpl implements DistanceMatrixService {

    @Value("${api.key}")
    private String apiKey;

    private RestTemplate restTemplate;

    @Autowired
    public DistanceMatrixServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Override
    public DistanceMatrixResponse fetchDistanceByLocation(String destination, String origin) {

        String url = "https://maps.googleapis.com/maps/api/distancematrix/json" +
                "?destinations=" + destination +
                "&origins=" + origin +
                "&units=imperial" +
                "&key=" + apiKey;
        System.out.println("Url is" + url);

        ResponseEntity<DistanceMatrixResponse> distanceMatrixResponseEntity =
        restTemplate.getForEntity(url, DistanceMatrixResponse.class);

        System.out.println("Response Status Code is : " + distanceMatrixResponseEntity.getStatusCode());

        DistanceMatrixResponse response = distanceMatrixResponseEntity.getBody();

        List<DistanceMatrixElements> rows = response.getRows();
        for (DistanceMatrixElements elements: rows) {
            List<DistanceMatrixDetails> details = elements.getElements();
            for (DistanceMatrixDetails values: details) {
                System.out.println(values.getDistanceValue().getText());
            }
        }

        return response;
    }
}

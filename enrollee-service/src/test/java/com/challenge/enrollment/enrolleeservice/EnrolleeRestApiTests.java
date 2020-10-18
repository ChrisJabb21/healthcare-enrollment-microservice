package com.challenge.enrollment.enrolleeservice;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import io.restassured.RestAssured;
import io.restassured.response.Response;

@SpringBootTest
class EnrolleeRestApiTests {

    private final String ROOT_URI = "http://localhost:8080";

    @Test
    public void whenGetAllEnrollees_thenSuccess() {
        Response response = RestAssured.get(ROOT_URI + "/enrollees");
        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        Assert.assertNotNull(response.getBody());
    }

    
}

package com.challenge.enrollment.enrolleeservice;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;
import io.restassured.response.Response;

@RunWith(SpringRunner.class)
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

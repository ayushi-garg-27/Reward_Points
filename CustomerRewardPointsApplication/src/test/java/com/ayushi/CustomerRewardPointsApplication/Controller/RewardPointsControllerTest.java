package com.ayushi.CustomerRewardPointsApplication.Controller;

import com.ayushi.CustomerRewardPointsApplication.controller.RewardPointsController;
import com.ayushi.CustomerRewardPointsApplication.service.RewardPointsCalculator;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class RewardPointsControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Mock
    private RewardPointsCalculator rewardPointsCalculator;

    @InjectMocks
    private RewardPointsController rewardPointsController;

    private MockWebServer mockWebServer;

    @BeforeEach
    public void setup() {
        mockWebServer = new MockWebServer();
    }

    // Test for calculateTotalPoints
    @Test
    public void testCalculateTotalPoints() throws Exception {
        String customerId = "12345";
        int expectedPoints = 150;
        when(rewardPointsCalculator.calculateTotalPoints(customerId)).thenReturn(expectedPoints);

        mockWebServer.enqueue(new MockResponse().setBody("{\"points\":150}").setResponseCode(200));

        mockWebServer.start();

        String baseUrl = mockWebServer.url("/api/rewardpoints/calculate?customerId=" + customerId).toString();

        webTestClient.post()
                .uri(baseUrl)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.points").isEqualTo(expectedPoints);

        mockWebServer.shutdown();
    }


    @Test
    public void testCalculateMonthlyPoints() throws Exception {

        String customerId = "12345";
        String month = "January";
        int expectedPoints = 120;
        when(rewardPointsCalculator.calculateMonthlyPoints(customerId, month)).thenReturn(expectedPoints);

        mockWebServer.enqueue(new MockResponse().setBody("{\"points\":120}").setResponseCode(200));

        mockWebServer.start();

        String baseUrl = mockWebServer.url("/api/rewardpoints/calculate-month?customerId=" + customerId + "&month=" + month).toString();

        webTestClient.post()
                .uri(baseUrl)
                .exchange()
                .expectStatus().isOk()  // Check if the response status is OK
                .expectBody()  // Validate response body
                .jsonPath("$.points").isEqualTo(expectedPoints);

        mockWebServer.shutdown();
    }
}


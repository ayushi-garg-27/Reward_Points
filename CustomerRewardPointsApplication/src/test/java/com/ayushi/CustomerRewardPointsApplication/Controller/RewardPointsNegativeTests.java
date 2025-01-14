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
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class RewardPointsNegativeTests {

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

    @Test
    public void testCalculateTotalPoints_invalidCustomerId() throws Exception {
        String invalidCustomerId = "invalid12345";

        mockWebServer.enqueue(new MockResponse().setResponseCode(400)
                .setBody("Invalid customerId")
                .addHeader("Content-Type", "application/json"));

        mockWebServer.start();

        String baseUrl = mockWebServer.url("/api/rewardpoints/calculate?customerId=" + invalidCustomerId).toString();

        webTestClient.post()
                .uri(baseUrl)
                .exchange()
                .expectStatus().isBadRequest()  // Expect Bad Request due to invalid customerId
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(String.class).isEqualTo("Invalid customerId");

        mockWebServer.shutdown();
    }

    @Test
    public void testCalculateMonthlyPoints_invalidMonth() throws Exception {

        String customerId = "12345";
        String invalidMonth = "InvalidMonth";

        mockWebServer.enqueue(new MockResponse().setResponseCode(400)
                .setBody("Invalid month")
                .addHeader("Content-Type", "application/json"));

        mockWebServer.start();

        String baseUrl = mockWebServer.url("/api/rewardpoints/calculate-month?customerId=" + customerId + "&month=" + invalidMonth).toString();

        webTestClient.post()
                .uri(baseUrl)
                .exchange()
                .expectStatus().isBadRequest()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(String.class).isEqualTo("Invalid month");

        mockWebServer.shutdown();
    }

    @Test
    public void testCalculateTotalPoints_serverError() throws Exception {

        String customerId = "12345";

        mockWebServer.enqueue(new MockResponse().setResponseCode(500)
                .setBody("Internal Server Error")
                .addHeader("Content-Type", "application/json"));
        mockWebServer.start();

        String baseUrl = mockWebServer.url("/api/rewardpoints/calculate?customerId=" + customerId).toString();

        webTestClient.post()
                .uri(baseUrl)
                .exchange()
                .expectStatus().is5xxServerError()  // Expect Server Error (500)
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(String.class).isEqualTo("Internal Server Error");

        mockWebServer.shutdown();
    }

    @Test
    public void testCalculateTotalPoints_missingCustomerId() throws Exception {

        mockWebServer.enqueue(new MockResponse().setResponseCode(400)
                .setBody("Missing customerId")
                .addHeader("Content-Type", "application/json"));

        mockWebServer.start();

        String baseUrl = mockWebServer.url("/api/rewardpoints/calculate").toString();

        webTestClient.post()
                .uri(baseUrl)
                .exchange()
                .expectStatus().isBadRequest()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(String.class).isEqualTo("Missing customerId");

        mockWebServer.shutdown();
    }


    @Test
    public void testCalculateMonthlyPoints_missingMonth() throws Exception {

        String customerId = "12345";

        mockWebServer.enqueue(new MockResponse().setResponseCode(400)
                .setBody("Missing month")
                .addHeader("Content-Type", "application/json"));

        mockWebServer.start();

        String baseUrl = mockWebServer.url("/api/rewardpoints/calculate-month?customerId=" + customerId).toString();

        webTestClient.post()
                .uri(baseUrl)
                .exchange()
                .expectStatus().isBadRequest()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(String.class).isEqualTo("Missing month");

        mockWebServer.shutdown();
    }
}

package brawlstats_backend.client;

import brawlstats_backend.dto.ClubDetailsDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class ClubClient {

    private final RestClient restClient;

    public ClubClient(
            @Value("${brawlstars.api.url}") String apiUrl,
            @Value("${brawlstars.api.key}") String apiKey) {

        this.restClient = RestClient.builder()
                .baseUrl(apiUrl)
                .defaultHeader(
                        HttpHeaders.AUTHORIZATION,
                        "Bearer " + apiKey
                )
                .defaultHeader(
                        HttpHeaders.ACCEPT,
                        "application/json"
                )
                .build();
    }

    public ClubDetailsDto getClub(String clubTag) {
        return restClient.get()
                .uri("/clubs/{clubTag}", clubTag)
                .retrieve()
                .body(ClubDetailsDto.class);
    }
}
package brawlstats_backend.client;

import brawlstats_backend.dto.BrawlerInfoDto;
import brawlstats_backend.dto.BrawlerListDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class BrawlerClient {

    private final RestClient restClient;

    public BrawlerClient(
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

    public BrawlerListDto getBrawlers() {
        return restClient.get()
                .uri("/brawlers")
                .retrieve()
                .body(BrawlerListDto.class);
    }

    public BrawlerInfoDto getBrawler(int brawlerId) {
        return restClient.get()
                .uri("/brawlers/{brawlerId}", brawlerId)
                .retrieve()
                .body(BrawlerInfoDto.class);
    }
}
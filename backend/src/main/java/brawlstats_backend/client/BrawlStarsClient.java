package brawlstats_backend.client;

import brawlstats_backend.dto.BattleLogDto;
import brawlstats_backend.dto.PlayerDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class BrawlStarsClient {

    private final RestClient restClient;

    public BrawlStarsClient(
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

    public PlayerDto getPlayer(String playerTag) {
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/players/{playerTag}")
                        .build(playerTag))
                .retrieve()
                .body(PlayerDto.class);
    }

    public BattleLogDto getBattleLog(String playerTag) {
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/players/{playerTag}/battlelog")
                        .build(playerTag))
                .retrieve()
                .body(BattleLogDto.class);
    }
}
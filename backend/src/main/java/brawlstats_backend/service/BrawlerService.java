package brawlstats_backend.service;

import brawlstats_backend.client.BrawlerClient;
import brawlstats_backend.dto.BrawlerInfoDto;
import brawlstats_backend.dto.BrawlerListDto;
import org.springframework.stereotype.Service;

@Service
public class BrawlerService {

    private final BrawlerClient brawlerClient;

    public BrawlerService(BrawlerClient brawlerClient) {
        this.brawlerClient = brawlerClient;
    }

    public BrawlerListDto getBrawlers() {
        return brawlerClient.getBrawlers();
    }

    public BrawlerInfoDto getBrawler(int brawlerId) {
        if (brawlerId <= 0) {
            throw new IllegalArgumentException("Brawler ID must be greater than 0");
        }

        return brawlerClient.getBrawler(brawlerId);
    }
}
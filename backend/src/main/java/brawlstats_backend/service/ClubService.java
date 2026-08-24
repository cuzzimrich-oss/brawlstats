package brawlstats_backend.service;

import brawlstats_backend.client.ClubClient;
import brawlstats_backend.dto.ClubDetailsDto;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class ClubService {

    private final ClubClient clubClient;

    public ClubService(ClubClient clubClient) {
        this.clubClient = clubClient;
    }

    public ClubDetailsDto getClub(String clubTag) {
        String normalizedTag = normalizeClubTag(clubTag);
        return clubClient.getClub(normalizedTag);
    }

    private String normalizeClubTag(String clubTag) {
        if (clubTag == null || clubTag.isBlank()) {
            throw new IllegalArgumentException("Club tag must not be empty");
        }

        String normalizedTag = clubTag
                .trim()
                .toUpperCase(Locale.ROOT);

        if (normalizedTag.startsWith("%23")) {
            normalizedTag = normalizedTag.substring(3);
        }

        if (normalizedTag.startsWith("#")) {
            normalizedTag = normalizedTag.substring(1);
        }

        if (normalizedTag.isBlank()) {
            throw new IllegalArgumentException("Club tag must not be empty");
        }

        if (!normalizedTag.matches("[A-Z0-9]+")) {
            throw new IllegalArgumentException("Club tag contains invalid characters");
        }

        return "#" + normalizedTag;
    }
}
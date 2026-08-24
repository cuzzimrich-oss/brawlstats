package brawlstats_backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ClubDetailsDto {

    private String tag;
    private String name;
    private String description;

    private String type;

    private int badgeId;

    private int requiredTrophies;
    private int trophies;

    private List<ClubMemberDto> members;

    @JsonProperty("isFamilyFriendly")
    private boolean familyFriendly;
}
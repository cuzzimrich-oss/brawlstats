package brawlstats_backend.dto;

import lombok.Data;

@Data
public class ClubMemberDto {

    private String tag;
    private String name;
    private String nameColor;

    private int trophies;

    private String role;

    private IconDto icon;
}
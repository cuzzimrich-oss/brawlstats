package brawlstats_backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class PlayerDto {

    private String tag;
    private String name;
    private String nameColor;

    private IconDto icon;

    private int trophies;
    private int highestTrophies;
    private int totalPrestigeLevel;

    private int expLevel;
    private int expPoints;

    private boolean isQualifiedFromChampionshipChallenge;

    @JsonProperty("3vs3Victories")
    private int victories3vs3;

    private int soloVictories;
    private int duoVictories;

    private int bestRoboRumbleTime;
    private int bestTimeAsBigBrawler;

    private int rankedSeasonId;
    private int rankedRank;
    private String rankedRankName;
    private int rankedElo;

    private int highestSeasonRankedRank;
    private String highestSeasonRankedRankName;
    private int highestSeasonRankedElo;

    private int highestAllTimeRankedRank;
    private String highestAllTimeRankedRankName;
    private int highestAllTimeRankedElo;

    private ClubDto club;

    private List<BrawlerDto> brawlers;
}
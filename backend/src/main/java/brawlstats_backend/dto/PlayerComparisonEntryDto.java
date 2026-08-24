package brawlstats_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlayerComparisonEntryDto {

    private String tag;
    private String name;

    private int overallRank;
    private int overallPoints;

    private int trophies;
    private int trophiesRank;

    private int highestTrophies;
    private int highestTrophiesRank;

    private int victories3vs3;
    private int victories3vs3Rank;

    private int soloVictories;
    private int soloVictoriesRank;

    private int duoVictories;
    private int duoVictoriesRank;

    private int rankedElo;
    private int rankedEloRank;

    private int expLevel;
    private int expLevelRank;

    private int brawlerCount;
    private int brawlerCountRank;

    private double averageBrawlerTrophies;
    private int averageBrawlerTrophiesRank;

    private int highestBrawlerTrophies;
    private int highestBrawlerTrophiesRank;
}
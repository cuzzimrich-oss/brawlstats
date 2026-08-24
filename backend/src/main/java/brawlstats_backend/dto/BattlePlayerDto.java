package brawlstats_backend.dto;

import lombok.Data;

@Data
public class BattlePlayerDto {

    private String tag;
    private String name;

    private BattleBrawlerDto brawler;
}
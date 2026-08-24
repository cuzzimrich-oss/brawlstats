package brawlstats_backend.dto;

import lombok.Data;

@Data
public class BattleLogItemDto {

    private String battleTime;
    private BattleEventDto event;
    private BattleDto battle;
}
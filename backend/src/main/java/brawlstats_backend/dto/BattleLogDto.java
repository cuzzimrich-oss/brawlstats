package brawlstats_backend.dto;

import lombok.Data;

import java.util.List;

@Data
public class BattleLogDto {

    private List<BattleLogItemDto> items;
}
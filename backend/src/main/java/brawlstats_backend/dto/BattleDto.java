package brawlstats_backend.dto;

import lombok.Data;

import java.util.List;

@Data
public class BattleDto {

    private String mode;
    private String type;

    private String result;
    private Integer duration;
    private Integer trophyChange;

    private Integer rank;

    private BattlePlayerDto starPlayer;

    private List<List<BattlePlayerDto>> teams;

    private List<BattlePlayerDto> players;
}
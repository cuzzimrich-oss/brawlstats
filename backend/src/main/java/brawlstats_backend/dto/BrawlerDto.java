package brawlstats_backend.dto;

import lombok.Data;

import java.util.List;

@Data
public class BrawlerDto {

    private int id;
    private String name;

    private int power;
    private int rank;

    private int trophies;
    private int highestTrophies;

    private int prestigeLevel;

    private int currentWinStreak;
    private int maxWinStreak;

    private SkinDto skin;

    private List<ItemDto> gadgets;
    private List<GearDto> gears;
    private List<ItemDto> starPowers;
    private List<ItemDto> hyperCharges;

    private BuffiesDto buffies;
}
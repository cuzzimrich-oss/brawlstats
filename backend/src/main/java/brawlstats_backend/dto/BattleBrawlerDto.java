package brawlstats_backend.dto;

import lombok.Data;

@Data
public class BattleBrawlerDto {

    private int id;
    private String name;
    private int power;
    private int trophies;
}
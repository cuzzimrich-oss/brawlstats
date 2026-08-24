package brawlstats_backend.dto;

import lombok.Data;

import java.util.List;

@Data
public class BrawlerInfoDto {

    private int id;
    private String name;

    private List<ItemDto> starPowers;
    private List<ItemDto> gadgets;
}
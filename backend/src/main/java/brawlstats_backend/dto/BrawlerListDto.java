package brawlstats_backend.dto;

import lombok.Data;

import java.util.List;

@Data
public class BrawlerListDto {

    private List<BrawlerInfoDto> items;
}
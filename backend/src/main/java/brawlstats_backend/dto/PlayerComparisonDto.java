package brawlstats_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PlayerComparisonDto {

    private List<PlayerComparisonEntryDto> players;
}
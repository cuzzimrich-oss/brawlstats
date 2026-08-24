package brawlstats_backend.controller;

import brawlstats_backend.dto.BattleLogDto;
import brawlstats_backend.dto.PlayerComparisonDto;
import brawlstats_backend.dto.PlayerDto;
import brawlstats_backend.service.PlayerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/players")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @Operation(
            summary = "Compare players",
            description = "Compares multiple Brawl Stars players and returns rankings for different statistics."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Player comparison returned successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid player tags"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "One of the players was not found"
            ),
            @ApiResponse(
                    responseCode = "502",
                    description = "Brawl Stars API request failed"
            )
    })
    @GetMapping(
            value = "/compare",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public PlayerComparisonDto comparePlayers(
            @Parameter(
                    description = "Player tags to compare",
                    example = "C8U2VLRC,ABC123,XYZ456"
            )
            @RequestParam List<String> tags
    ) {
        return playerService.comparePlayers(tags);
    }

    @Operation(
            summary = "Get player",
            description = "Returns detailed information about a Brawl Stars player."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Player found"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid player tag"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Player not found"
            ),
            @ApiResponse(
                    responseCode = "502",
                    description = "Brawl Stars API request failed"
            )
    })
    @GetMapping(
            value = "/{playerTag}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public PlayerDto getPlayer(
            @Parameter(
                    description = "Brawl Stars player tag, with or without #",
                    example = "C8U2VLRC"
            )
            @PathVariable String playerTag
    ) {
        return playerService.getPlayer(playerTag);
    }

    @Operation(
            summary = "Get player battle log",
            description = "Returns the recent battle log of a Brawl Stars player."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Battle log found"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid player tag"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Player not found"
            ),
            @ApiResponse(
                    responseCode = "502",
                    description = "Brawl Stars API request failed"
            )
    })
    @GetMapping(
            value = "/{playerTag}/battlelog",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public BattleLogDto getBattleLog(
            @Parameter(
                    description = "Brawl Stars player tag, with or without #",
                    example = "C8U2VLRC"
            )
            @PathVariable String playerTag
    ) {
        return playerService.getBattleLog(playerTag);
    }
}
package brawlstats_backend.controller;

import brawlstats_backend.dto.ClubDetailsDto;
import brawlstats_backend.service.ClubService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clubs")
public class ClubController {

    private final ClubService clubService;

    public ClubController(ClubService clubService) {
        this.clubService = clubService;
    }

    @Operation(
            summary = "Get club",
            description = "Returns detailed information about a Brawl Stars club."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Club found"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid club tag"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Club not found"
            ),
            @ApiResponse(
                    responseCode = "502",
                    description = "Brawl Stars API request failed"
            )
    })
    @GetMapping(
            value = "/{clubTag}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ClubDetailsDto getClub(
            @Parameter(
                    description = "Brawl Stars club tag, with or without #",
                    example = "C8C20PLG"
            )
            @PathVariable String clubTag
    ) {
        return clubService.getClub(clubTag);
    }
}
package brawlstats_backend.controller;

import brawlstats_backend.dto.BrawlerInfoDto;
import brawlstats_backend.dto.BrawlerListDto;
import brawlstats_backend.service.BrawlerService;
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
@RequestMapping("/api/brawlers")
public class BrawlerController {

    private final BrawlerService brawlerService;

    public BrawlerController(BrawlerService brawlerService) {
        this.brawlerService = brawlerService;
    }

    @Operation(
            summary = "Get all brawlers",
            description = "Returns all available Brawl Stars brawlers."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Brawlers returned successfully"
            ),
            @ApiResponse(
                    responseCode = "502",
                    description = "Brawl Stars API request failed"
            )
    })
    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public BrawlerListDto getBrawlers() {
        return brawlerService.getBrawlers();
    }

    @Operation(
            summary = "Get brawler",
            description = "Returns detailed information about a specific Brawl Stars brawler."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Brawler found"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid brawler ID"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Brawler not found"
            ),
            @ApiResponse(
                    responseCode = "502",
                    description = "Brawl Stars API request failed"
            )
    })
    @GetMapping(
            value = "/{brawlerId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public BrawlerInfoDto getBrawler(
            @Parameter(
                    description = "Brawler ID",
                    example = "16000000"
            )
            @PathVariable int brawlerId
    ) {
        return brawlerService.getBrawler(brawlerId);
    }
}
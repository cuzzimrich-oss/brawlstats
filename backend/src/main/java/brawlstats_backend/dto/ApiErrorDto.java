package brawlstats_backend.dto;

public record ApiErrorDto(
        int status,
        String code,
        String message
) {
}
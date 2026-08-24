package brawlstats_backend.config;

import brawlstats_backend.dto.ApiErrorDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpClientErrorException.NotFound.class)
    public ResponseEntity<ApiErrorDto> handleNotFound(
            HttpClientErrorException.NotFound exception,
            HttpServletRequest request) {

        String code = "RESOURCE_NOT_FOUND";
        String message = "Requested resource was not found";

        if (request.getRequestURI().startsWith("/api/players/")) {
            code = "PLAYER_NOT_FOUND";
            message = "Player not found";
        } else if (request.getRequestURI().startsWith("/api/clubs/")) {
            code = "CLUB_NOT_FOUND";
            message = "Club not found";
        }

        ApiErrorDto error = new ApiErrorDto(
                HttpStatus.NOT_FOUND.value(),
                code,
                message
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }

    @ExceptionHandler(HttpClientErrorException.Forbidden.class)
    public ResponseEntity<ApiErrorDto> handleForbidden(
            HttpClientErrorException.Forbidden exception) {

        ApiErrorDto error = new ApiErrorDto(
                HttpStatus.BAD_GATEWAY.value(),
                "BRAWL_STARS_API_FORBIDDEN",
                "Brawl Stars API rejected the request"
        );

        return ResponseEntity
                .status(HttpStatus.BAD_GATEWAY)
                .body(error);
    }

    @ExceptionHandler(HttpClientErrorException.BadRequest.class)
    public ResponseEntity<ApiErrorDto> handleBadRequest(
            HttpClientErrorException.BadRequest exception) {

        ApiErrorDto error = new ApiErrorDto(
                HttpStatus.BAD_REQUEST.value(),
                "INVALID_REQUEST",
                "The request is invalid"
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    @ExceptionHandler(HttpClientErrorException.TooManyRequests.class)
    public ResponseEntity<ApiErrorDto> handleRateLimit(
            HttpClientErrorException.TooManyRequests exception) {

        ApiErrorDto error = new ApiErrorDto(
                HttpStatus.TOO_MANY_REQUESTS.value(),
                "RATE_LIMIT_EXCEEDED",
                "Too many requests to the Brawl Stars API"
        );

        return ResponseEntity
                .status(HttpStatus.TOO_MANY_REQUESTS)
                .body(error);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ApiErrorDto> handleBrawlStarsApiError(
            HttpClientErrorException exception) {

        ApiErrorDto error = new ApiErrorDto(
                HttpStatus.BAD_GATEWAY.value(),
                "BRAWL_STARS_API_ERROR",
                "Brawl Stars API request failed"
        );

        return ResponseEntity
                .status(HttpStatus.BAD_GATEWAY)
                .body(error);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorDto> handleIllegalArgument(
            IllegalArgumentException exception) {

        ApiErrorDto error = new ApiErrorDto(
                HttpStatus.BAD_REQUEST.value(),
                "INVALID_INPUT",
                exception.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorDto> handleUnexpectedError(
            Exception exception) {

        ApiErrorDto error = new ApiErrorDto(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "INTERNAL_SERVER_ERROR",
                "An unexpected error occurred"
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }
}
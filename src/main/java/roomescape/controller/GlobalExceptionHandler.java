package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import roomescape.NotFoundReservationException;
import roomescape.NotFoundTimeException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(value = {NotFoundReservationException.class, NotFoundTimeException.class})
    public ResponseEntity handleNotFoundException(RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}

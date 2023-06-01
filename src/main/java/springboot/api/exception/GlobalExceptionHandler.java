package springboot.api.exception;

import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import springboot.api.utils.Constantes;

import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FunctionalException.class)
    public ResponseEntity<ErrorDetails> functionalException(FunctionalException ex) {
        ErrorDetails errorDetails = new ErrorDetails(ex.getMessage(), ex.getStatus().value());
        return new ResponseEntity<>(errorDetails, ex.getStatus());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorDetails> functionalException(DataIntegrityViolationException ex) {
        ErrorDetails errorDetails = new ErrorDetails(Constantes.NAME_EXIST, HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> handleValidationErrors(MethodArgumentNotValidException ex) {
        ErrorDetails errorDetails = null;
        if (ex.getBindingResult().getAllErrors().stream().findFirst().isPresent()) {
            ObjectError error = ex.getBindingResult().getAllErrors().stream().findFirst().get();
            String message = switch (((FieldError) error).getField()){
                case "name", "birthdate", "country" -> ((FieldError) error).getField() + " " + Constantes.NOT_NULL;
                default -> Constantes.NOT_VALID;
            };
            errorDetails = new ErrorDetails(message, HttpStatus.BAD_REQUEST.value());
        }
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorDetails> globleExcpetionHandler(Exception ex) {
        String message = ex.getLocalizedMessage().contains( "LocalDate" ) ? Constantes.NOT_VALID_FORMAT
                : ex.getLocalizedMessage().contains( "Gender" ) ? Constantes.GENDER_INVALID : ex.getMessage();
        ErrorDetails errorDetails = new ErrorDetails(message, HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

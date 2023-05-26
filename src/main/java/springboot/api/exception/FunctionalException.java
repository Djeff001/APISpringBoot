package springboot.api.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class FunctionalException extends RuntimeException{
    private HttpStatus status;
    public FunctionalException(String message, HttpStatus status){

        super(message);
        this.status=status;
    }
}

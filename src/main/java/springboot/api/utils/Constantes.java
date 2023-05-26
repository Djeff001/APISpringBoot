package springboot.api.utils;

import java.time.format.DateTimeFormatter;

public interface Constantes {
    static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
    static final String ACCEPTED_COUNTRY = "France";
    static final String NOT_VALID_FORMAT = "Birthdate should be in this format d/MM/yyyy";
    static final String USER_NOT_ALLOWED = "Only adult French residents are allowed to create an account!";
    static final String USER_NOT_FOUND = "User not found";
    static final String NOT_VALID = "not valid";
    static final String NOT_NULL = "must be not null";

}


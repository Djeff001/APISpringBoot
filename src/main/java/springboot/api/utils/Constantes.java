package springboot.api.utils;

import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class Constantes {

    private Constantes(){}

    public final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
    public final static String ACCEPTED_COUNTRY = "France";
    public final static String NOT_VALID_FORMAT = "Birthdate should be in this format d/MM/yyyy";
    public final static String USER_NOT_ALLOWED = "Only adult French residents are allowed to create an account!";
    public final static String USER_NOT_FOUND = "User not found";
    public final static String NOT_VALID = "Phone number not valid";
    public final static String NOT_NULL = "must be not null";
    public final static String GENDER_INVALID = "Gender must be MALE or FEMALE";
    public final static String NAME_EXIST = "Name already exist";

}


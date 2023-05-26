package springboot.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import springboot.api.aspect.Log;
import springboot.api.converter.UserConverter;
import springboot.api.dto.UserDto;
import springboot.api.exception.FunctionalException;
import springboot.api.model.User;
import springboot.api.repository.UserDao;
import springboot.api.service.UserService;
import springboot.api.utils.Constantes;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;

@Service

public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @Log
    public UserDto createUser(UserDto userDto)  throws FunctionalException {
        LocalDate birthdate=null;
        try{
            birthdate = LocalDate.parse(userDto.getBirthdate(), Constantes.formatter);
        }catch (DateTimeParseException e){
            throw new FunctionalException( Constantes.NOT_VALID_FORMAT, HttpStatus.NOT_ACCEPTABLE );
        }
        if (!userDto.getCountry().equals(Constantes.ACCEPTED_COUNTRY) || Period.between(birthdate, LocalDate.now()).getYears()<18) {
            throw  new FunctionalException( Constantes.USER_NOT_ALLOWED , HttpStatus.NOT_ACCEPTABLE);
        }
        User savedUser = userDao.save( UserConverter.getUserFromUserDto( userDto ) );
        return UserConverter.getUserDtoFromUser( savedUser );
    }

    @Override
    @Log
    public UserDto findById(Long id)  throws FunctionalException {
        User user = userDao.findById( id )
                .orElseThrow(() -> new FunctionalException(Constantes.USER_NOT_FOUND, HttpStatus.NOT_FOUND));
        return UserConverter.getUserDtoFromUser( user );

    }
}

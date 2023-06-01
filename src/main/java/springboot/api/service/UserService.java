package springboot.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import springboot.api.aspect.Log;
import springboot.api.converter.UserConverter;
import springboot.api.dto.UserDto;
import springboot.api.exception.FunctionalException;
import springboot.api.model.User;
import springboot.api.repository.UserDao;
import springboot.api.utils.Constantes;
import java.time.LocalDate;
import java.time.Period;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;

    @Log
    public UserDto createUser(UserDto userDto)  throws FunctionalException {
        if (!userDto.getCountry().equals(Constantes.ACCEPTED_COUNTRY) || Period.between(userDto.getBirthdate(), LocalDate.now()).getYears()<18) {
            throw  new FunctionalException( Constantes.USER_NOT_ALLOWED , HttpStatus.BAD_REQUEST);
        }
        User savedUser = userDao.save( UserConverter.getUserFromUserDto( userDto ) );
        return UserConverter.getUserDtoFromUser( savedUser );
    }

    @Log
    public UserDto findById(Long id)  throws FunctionalException {
        User user = userDao.findById( id )
                .orElseThrow(() -> new FunctionalException(Constantes.USER_NOT_FOUND, HttpStatus.NOT_FOUND));
        return UserConverter.getUserDtoFromUser( user );

    }
}

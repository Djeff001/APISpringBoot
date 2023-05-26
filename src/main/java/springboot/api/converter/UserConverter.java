package springboot.api.converter;

import springboot.api.dto.UserDto;
import springboot.api.model.Gender;
import springboot.api.model.User;
import springboot.api.utils.Constantes;
import java.time.LocalDate;

public class UserConverter {

    public  static UserDto getUserDtoFromUser(User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getBirthdate().toString(),
                user.getCountry(),
                user.getPhone(),
                user.getGender()!=null ? user.getGender().toString() : null
        );
    }

    public  static User getUserFromUserDto(UserDto userDto) {
        return new User(
                userDto.getId(),
                userDto.getName(),
                LocalDate.parse(userDto.getBirthdate(), Constantes.formatter ),
                userDto.getCountry(),
                userDto.getPhone(),
                userDto.getGender()!=null ? Gender.valueOf(userDto.getGender()) : null
        );
    }
}

package springboot.api.converter;

import springboot.api.dto.UserDto;
import springboot.api.model.User;

public class UserConverter {

    private UserConverter(){}

    public static UserDto getUserDtoFromUser(User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getBirthdate(),
                user.getCountry(),
                user.getPhone(),
                user.getGender()
        );
    }

    public static User getUserFromUserDto(UserDto userDto) {
        return new User(
                userDto.getId(),
                userDto.getName(),
                userDto.getBirthdate(),
                userDto.getCountry(),
                userDto.getPhone(),
                userDto.getGender()
        );
    }
}

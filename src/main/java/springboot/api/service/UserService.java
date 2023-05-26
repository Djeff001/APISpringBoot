package springboot.api.service;

import springboot.api.dto.UserDto;

public interface UserService {
    UserDto createUser(UserDto userDto);

    UserDto findById(Long id);
}

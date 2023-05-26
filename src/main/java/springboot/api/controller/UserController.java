package springboot.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.api.dto.UserDto;
import springboot.api.exception.FunctionalException;
import springboot.api.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDto> newUser(@Valid @RequestBody UserDto userDto) throws FunctionalException {
        UserDto createdUser= userService.createUser(userDto);
        return ResponseEntity.status( HttpStatus.CREATED).body(createdUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable(value = "id") Long id) throws FunctionalException {
        UserDto userDto = userService.findById(id);
        return ResponseEntity.ok().body(userDto);
    }
}

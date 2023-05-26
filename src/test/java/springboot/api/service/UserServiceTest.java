package springboot.api.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import springboot.api.converter.UserConverter;
import springboot.api.dto.UserDto;
import springboot.api.exception.FunctionalException;
import springboot.api.model.User;
import springboot.api.repository.UserDao;
import springboot.api.service.impl.UserServiceImpl;
import springboot.api.utils.Constantes;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

class UserServiceTest {

    @Mock
    private UserDao userDao;
    private UserService underTest;

    UserDto userDto;
    @BeforeEach
    void setUp() {
        underTest = new UserServiceImpl(userDao);
        userDto = new UserDto("toto","01/01/2000","France");
    }

    @Test
    void createUser_without_Phone_And_Gender() throws FunctionalException {
        // Given
        Long userId = 1L;
        User user = UserConverter.getUserFromUserDto( userDto );
        user.setId( userId );

        // When
        when( userDao.save( Mockito.any() ) ).thenReturn( user );
        UserDto foundUser = underTest.createUser(userDto);

        // then
        assertThat(foundUser.getName()).isEqualTo(userDto.getName());
        assertThat(foundUser.getId()).isEqualTo(userId);
    }

    @Test
    void createUser_with_All_Args() throws FunctionalException {
        // Given
        Long userId = 1L;
        userDto.setPhone( "+33123456789" );
        userDto.setGender( "MALE" );
        User user = UserConverter.getUserFromUserDto( userDto );
        user.setId( userId );
        // When
        when( userDao.save( Mockito.any() ) ).thenReturn( user );
        UserDto foundUser = underTest.createUser(userDto);

        // then
        assertThat(foundUser.getName()).isEqualTo(userDto.getName());
        assertThat(foundUser.getId()).isEqualTo(userId);
    }

    @Test
    void createUser_will_Throw_When_Country_Not_France() {
        // Given
        userDto.setCountry( "Italie" );
        // When
        // then
        assertThatThrownBy(()->underTest.createUser(userDto))
                .isInstanceOf( FunctionalException.class)
                .hasMessageContaining( Constantes.USER_NOT_ALLOWED );
        verify(userDao, never()).save(any());
    }

    @Test
    void createUser_will_Throw_When_User_NotAdult() {
        // Given
        userDto.setBirthdate( "01/06/2005" );
        // When
        // then
        assertThatThrownBy(()->underTest.createUser(userDto))
                .isInstanceOf( FunctionalException.class)
                .hasMessageContaining( Constantes.USER_NOT_ALLOWED );
        verify(userDao, never()).save(any());
    }

    @Test
    void createUser_will_Throw_When_Birthdate_Not_Valid() {
        // Given
        userDto.setBirthdate( "01.06.2005" );
        // When
        // then
        assertThatThrownBy(()->underTest.createUser(userDto))
                .isInstanceOf( FunctionalException.class)
                .hasMessageContaining( Constantes.NOT_VALID_FORMAT );
        verify(userDao, never()).save(any());
    }

    @Test
    void findById_Should_Find_User_By_Id() throws FunctionalException {
        // Given
        Long userId = 1L;
        User user = UserConverter.getUserFromUserDto( userDto );
        user.setId( userId );

        //  when
        when( userDao.findById( userId) ).thenReturn( Optional.of( user ) );
        UserDto foundUser = underTest.findById(userId);

        // then
        assertThat(foundUser.getName()).isEqualTo(user.getName());

    }

    @Test
    void findeById_should_Throw_When_User_NotFound() throws FunctionalException {
        // Given
        Long userId = 1L;

        //  when
        // then
        assertThatThrownBy(()->underTest.findById(userId))
                .isInstanceOf( FunctionalException.class)
                .hasMessageContaining(Constantes.USER_NOT_FOUND);

    }
}
package springboot.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import springboot.api.converter.UserConverter;
import springboot.api.dto.UserDto;
import springboot.api.model.User;
import springboot.api.repository.UserDao;
import springboot.api.utils.Constantes;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private UserDao userDao;

    @Autowired
    private MockMvc mockMvc;

    UserDto userDto;

    @BeforeEach
    void setUp() {
        userDto = new UserDto("toto","01/01/2000","France");
    }

    @AfterEach
    void tearDown() {
        userDao.deleteAll();
    }

    @Test
    public void newUser_Should_Create_User() throws Exception {
        // Given UserDto
        // When
        ResultActions response = mockMvc.perform(post("/users").content( new ObjectMapper()
                .writeValueAsString( userDto )).contentType( MediaType.APPLICATION_JSON ));

        // Then
        response.andExpect(MockMvcResultMatchers.status().isCreated());
        response.andExpect( MockMvcResultMatchers.jsonPath( "$.name" ).value( "toto" ) );
    }

    @Test
    public void newUser_Should_Return_NotAcceptable_Not_French_Resident() throws Exception {
        // Given
        userDto.setCountry( "Italie" );

        // When
        ResultActions response = mockMvc.perform(post("/users").content( new ObjectMapper()
                .writeValueAsString( userDto )).contentType( MediaType.APPLICATION_JSON ));

        // Then
        response.andExpect(MockMvcResultMatchers.status().isNotAcceptable());
        response.andExpect( MockMvcResultMatchers.jsonPath( "$.message" ).value( Constantes.USER_NOT_ALLOWED ) );
    }

    @Test
    public void newUser_Should_Return_NotAcceptable_Not_Adult() throws Exception {
        // Given
        userDto.setBirthdate( "01/01/2010" );

        // When
        ResultActions response = mockMvc.perform(post("/users").content( new ObjectMapper()
                .writeValueAsString( userDto )).contentType( MediaType.APPLICATION_JSON ));

        // Then
        response.andExpect(MockMvcResultMatchers.status().isNotAcceptable());
        response.andExpect( MockMvcResultMatchers.jsonPath( "$.message" ).value( Constantes.USER_NOT_ALLOWED ) );
    }

    @Test
    public void newUser_Should_Return_BadRequest_Phone_Number_Not_Valid() throws Exception {

        // Given
        userDto.setPhone( "1234567" );

        // When
        ResultActions response = mockMvc.perform(post("/users").content( new ObjectMapper()
                .writeValueAsString( userDto )).contentType( MediaType.APPLICATION_JSON ));

        // Then
        response.andExpect(MockMvcResultMatchers.status().isBadRequest());
        response.andExpect( MockMvcResultMatchers.jsonPath( "$.message" ).value( "phone "+Constantes.NOT_VALID ) );
    }

    @Test
    public void newUser_Should_Return_NotAcceptable_Invalid_Birthdate() throws Exception {

        // Given
        userDto.setBirthdate( "01.02.2000" );

        // When
        ResultActions response = mockMvc.perform(post("/users").content( new ObjectMapper()
                .writeValueAsString( userDto )).contentType( MediaType.APPLICATION_JSON ));

        // Then
        response.andExpect(MockMvcResultMatchers.status().isNotAcceptable());
        response.andExpect( MockMvcResultMatchers.jsonPath( "$.message" ).value( Constantes.NOT_VALID_FORMAT ) );
    }

    @Test
    public void getUser_should_return_user() throws Exception {

        // Given
        Long userId = 1L;
        User user = UserConverter.getUserFromUserDto( userDto );
        user.setId( userId );

        // When
        userDao.save( user );

        // Then
        ResultActions response = mockMvc.perform(get("/users/"+userId));
        response.andExpect(MockMvcResultMatchers.status().isOk());
        response.andExpect( MockMvcResultMatchers.jsonPath( "$.name" ).value( "toto" ) );

    }

    @Test
    public void getUser_should_return_Exception_Not_found() throws Exception {

        // Given
        Long userId = 1L;

        // When
        // Then
        ResultActions response = mockMvc.perform(get("/users/"+userId));
        response.andExpect(MockMvcResultMatchers.status().isNotFound());
        response.andExpect( MockMvcResultMatchers.jsonPath( "$.message" ).value( Constantes.USER_NOT_FOUND ) );

    }
}
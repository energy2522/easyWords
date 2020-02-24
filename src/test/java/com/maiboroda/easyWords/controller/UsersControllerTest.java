package com.maiboroda.easyWords.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static com.maiboroda.easyWords.controller.ResponseBodyMatcher.responseBody;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maiboroda.easyWords.dto.UserDTO;
import com.maiboroda.easyWords.exception.UserNotFoundException;
import com.maiboroda.easyWords.service.DateService;
import com.maiboroda.easyWords.service.UserService;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = UsersController.class)
public class UsersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private DateService dateService;

    @BeforeEach
    public void init() {
        Assert.assertNotNull(mockMvc);
    }

    @Test
    public void testWrongUserIdType() throws Exception {
        final String wrongUserId = "test";
        final String expectedMessage = "Wrong argument type for value 'test'. Expected 'int'";

        mockMvc.perform(get("/users/{userId}", wrongUserId))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(responseBody().containsError(expectedMessage));
    }

    @Test
    public void testUserDoesNotExist() throws Exception {
        final int userId = 4;
        final String expectedMessage = "User with id 4 not found";

        when(userService.getById(userId)).thenThrow(new UserNotFoundException(userId));

        mockMvc.perform(get("/users/{userId}", userId))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(responseBody().containsError(expectedMessage));
    }

    @Test
    public void testMinUserIdValidation() throws Exception {
        final int userId = 0;
        final String expectedMessage = "userId must be equal or higher of 1 but got 0";

        mockMvc.perform(get("/users/{userId}", userId))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(responseBody().containsError(expectedMessage));
    }

    @Test
    public void testGetValidUserById() throws Exception {
        final int userId = 4;
        final UserDTO expectedUser = new UserDTO(userId, "test", null, LocalDateTime.now().toString());

        when(userService.getById(userId)).thenReturn(expectedUser);

        MvcResult mvcResult = mockMvc.perform(get("/users/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertThat(objectMapper.writeValueAsString(expectedUser)).isEqualToIgnoringWhitespace(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void testGetUsers() throws Exception {
        final UserDTO firstUser = new UserDTO(3, "test1", "test1", LocalDateTime.now().toString());
        final UserDTO secondUser = new UserDTO(6, "test2", "test2", LocalDateTime.now().toString());
        List<UserDTO> expectedUsers = Arrays.asList(firstUser, secondUser);

        when(userService.getAll()).thenReturn(expectedUsers);

        MvcResult mvcResult = mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertThat(objectMapper.writeValueAsString(expectedUsers)).isEqualToIgnoringWhitespace(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void testAddValidUser() throws Exception {
        final int userId = 4;
        final UserDTO expectedUser = new UserDTO(userId, "test", "test", LocalDateTime.now().toString());
        final UserDTO sendUser = new UserDTO();
        sendUser.setUsername("test");
        sendUser.setPassword("password");

        when(userService.add(sendUser)).thenReturn(expectedUser);

        MvcResult mvcResult = mockMvc.perform(
                post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sendUser)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        assertThat(objectMapper.writeValueAsString(expectedUser)).isEqualToIgnoringWhitespace(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void testAddUserWithWrongUserData() throws Exception {
        String[] expectedMessages = {
                "username must match \"[a-zA-z0-9]+\"",
                "username must be at least of 4 characters",
                "password must be at least of 6 characters"};
        final UserDTO send = new UserDTO();
        send.setUsername("---");
        send.setPassword("test");

        mockMvc.perform(
                post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(send)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(responseBody().containsErrors(expectedMessages));
    }

    @Test
    public void testAddUserWithBlankFields() throws Exception {
        final String[] expectedMessages = {
                "username must not be blank",
                "password must not be blank"
        };
        UserDTO sendUser = new UserDTO();

        mockMvc.perform(
                post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sendUser)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(responseBody().containsErrors(expectedMessages));
    }

    @Test
    public void testDeleteUser() throws Exception {
        final int userId = 5;

        mockMvc.perform(delete("/users/{userId}", userId))
                .andExpect(status().isNoContent());

        verify(userService).delete(userId);
    }

    @Test
    public void testDeleteUserWithWrongId() throws Exception {
        final int userId = -1;
        final String expectedMessage = "userId must be equal or higher of 1 but got -1";

        mockMvc.perform(delete("/users/{userId}", userId))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(responseBody().containsError(expectedMessage));
    }

    @Test
    public void testDeleteUserThatNotExist() throws Exception {
        final int userId = 4;
        final String expectedMessage = "User with id 4 not found";

        doThrow(new UserNotFoundException(userId)).when(userService).delete(userId);

        mockMvc.perform(delete("/users/{userId}", userId))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(responseBody().containsError(expectedMessage));
    }

    @Test
    public void testUpdateUser() throws Exception {
        final int userId = 3;
        UserDTO expectedUser = new UserDTO(userId, "test", null, LocalDateTime.now().toString());
        UserDTO sendUser = new UserDTO();
        sendUser.setUsername("test");
        sendUser.setPassword("password");

        when(userService.update(userId, sendUser)).thenReturn(expectedUser);

        MvcResult mvcResult = mockMvc.perform(
                put("/users/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sendUser)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(objectMapper.writeValueAsString(expectedUser)).isEqualToIgnoringWhitespace(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void testUpdateUserWithWrongId() throws Exception {
        final String expectedMessage = "userId must be equal or higher of 1 but got 0";
        final int userId = 0;
        UserDTO sendUser = new UserDTO();
        sendUser.setUsername("username");
        sendUser.setPassword("password");

        mockMvc.perform(
                put("/users/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sendUser)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(responseBody().containsError(expectedMessage));
    }

    @Test
    public void testUpdateUserWithWrongUserParameters() throws Exception {
        final int userId = 3;
        final String[] expectedMessages = {
                "username must match \"[a-zA-z0-9]+\"",
                "username must be at least of 4 characters",
                "password must be at least of 6 characters" };
        final UserDTO sendUser = new UserDTO();
        sendUser.setUsername("---");
        sendUser.setPassword("test");

        mockMvc.perform(
                put("/users/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sendUser)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(responseBody().containsErrors(expectedMessages));
    }

    @Test
    public void testUpdateUserThatNotExist() throws Exception {
        final int userId = 3;
        final String expectedMessage = "User with id 3 not found";
        UserDTO sendUser = new UserDTO();
        sendUser.setUsername("username");
        sendUser.setPassword("password");

        when(userService.update(userId, sendUser)).thenThrow(new UserNotFoundException(userId));

        mockMvc.perform(
                put("/users/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sendUser)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(responseBody().containsError(expectedMessage));
    }

    @Test
    public void testUpdateUserWithBlankFields() throws Exception {
        final int userId = 3;
        final String[] expectedMessages = {
                "username must not be blank",
                "password must not be blank"
        };
        UserDTO sendUser = new UserDTO();

        mockMvc.perform(
                put("/users/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sendUser)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(responseBody().containsErrors(expectedMessages));
    }
}

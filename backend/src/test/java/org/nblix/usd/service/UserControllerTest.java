package org.nblix.usd.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.nblix.usd.USDConstants;
import org.nblix.usd.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * Tests for the REST layer using Mockito to mock the service behind the
 * controller.
 */
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @Mock
    private UserService service;
    private User user;
    private List<User> userlist;

    @InjectMocks
    private UserController restController;

    // Use MockMVC for http request testing:
    @Autowired
    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        user = new User("Someone", "verySecurePassword");
        user.setId(1l);
        mvc = MockMvcBuilders.standaloneSetup(restController).build();
    }

    @AfterEach
    public void teardown() {
        user = null;
    }

    @Test
    public void testCreateUser() throws Exception {
        when(service.createUser(any(), any())).thenReturn(user.getId());
        mvc.perform( //
                post(USDConstants.API_USERS) //
                        .param("username", user.getUsername()) //
                        .param("password", user.getPassword())) //
                .andExpect(status().isCreated());
        verify(service, times(1)).createUser(any(), any());
    }

    @Test
    public void testGetAllUsers() throws Exception {
        when(service.getAllUsers()).thenReturn(userlist);
        mvc.perform(MockMvcRequestBuilders.get(USDConstants.API_USERS).contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print());

        verify(service).getAllUsers();
        verify(service, times(1)).getAllUsers();
    }

    @Test
    public void testGetUserById() throws Exception {
        when(service.getUserById(user.getId())).thenReturn(Optional.of(user));
        mvc.perform(MockMvcRequestBuilders.get(USDConstants.API_USERS + "/" + user.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testDeleteUserById() throws Exception {
        when(service.deleteUserById(user.getId())).thenReturn(true);
        mvc.perform(MockMvcRequestBuilders //
                .delete(USDConstants.API_USERS + "/" + user.getId())) //
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testAuthenticateUser() throws Exception {
        when(service.authenticateUser(any(), any())).thenReturn(user);
        mvc.perform(MockMvcRequestBuilders.get(USDConstants.API_USERS + "/auth") //
                .param("username", user.getUsername()) //
                .param("password", user.getPassword())) //
                .andExpect(MockMvcResultMatchers.status().isOk()) //
                .andDo(MockMvcResultHandlers.print());
    }
}

package com.app.test.facade.impl;

import com.app.test.exception.UserNotFoundException;
import com.app.test.models.User;
import com.app.test.service.UserRestClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class UserFacadeImplTest {
    @Mock
    UserRestClient userRestClient;

    @Mock
    private Environment env;

    @InjectMocks
    private UserFacadeImpl userFacade;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        when(env.getProperty("london" + ".lat")).thenReturn("123");
        when(env.getProperty("london" + ".lon")).thenReturn("123");
    }

    @Test
    void testUsersWithEmptyList() {
        when(userRestClient.getAllUsers()).thenReturn(new ArrayList<>());
        when(userRestClient.getAllCityUsers(anyString())).thenReturn(new ArrayList<>());

        assertThrows(UserNotFoundException.class, () -> userFacade.getUsersOfCityAndWithinRadius("London", 50));
    }

    @Test
    void testAllUsers(){
        when(userRestClient.getAllUsers()).thenReturn(getAllUserList());
        when(userRestClient.getAllCityUsers(anyString())).thenReturn(new ArrayList<>());
        List<User> users = userFacade.getUsersOfCityAndWithinRadius("London", 50);
        assertEquals(users.size(), 2);
        assertEquals(users.get(0).getId(), 1);
        assertEquals(users.get(1).getId(), 2);
//        assertEquals(userFacade.getUsersOfCityAndWithinRadius("London", 50).size(), 2);
    }

    @Test
    void testCityUsers(){
        when(userRestClient.getAllUsers()).thenReturn(new ArrayList<>());
        when(userRestClient.getAllCityUsers(anyString())).thenReturn(getAllUserList());
        List<User> users = userFacade.getUsersOfCityAndWithinRadius("London", 50);
        assertEquals(users.size(), 2);
        assertEquals(users.get(0).getId(), 1);
        assertEquals(users.get(1).getId(), 2);
    }

    @Test
    void testMergedUsersOverlapped(){
        when(userRestClient.getAllUsers()).thenReturn(getAllUserList());
        when(userRestClient.getAllCityUsers(anyString())).thenReturn(getAllUserList());
        List<User> users = userFacade.getUsersOfCityAndWithinRadius("London", 50);
        assertEquals(users.size(), 2);
        assertEquals(users.get(0).getId(), 1);
        assertEquals(users.get(1).getId(), 2);
    }

    @Test
    void testMergedUsersDistinct(){
        when(userRestClient.getAllUsers()).thenReturn(getAllUserList());
        when(userRestClient.getAllCityUsers(anyString())).thenReturn(getCityUserList());
        List<User> users = userFacade.getUsersOfCityAndWithinRadius("London", 50);
        assertEquals(users.size(), 4);
        assertEquals(users.get(0).getId(), 1);
        assertEquals(users.get(1).getId(), 2);
        assertEquals(users.get(2).getId(), 3);
        assertEquals(users.get(3).getId(), 4);
    }

    private List<User> getCityUserList() {
        User user1 = new User();
        user1.setId(3);
        user1.setFirst_name("Test1");
        user1.setLast_name("Case1");
        user1.setIp_address("198.121.0.1");
        user1.setEmail("abc@abc.com");
        user1.setLatitude(123);
        user1.setLongitude(123);

        User user2 = new User();
        user2.setId(4);
        user2.setFirst_name("Test2");
        user2.setLast_name("Case2");
        user2.setIp_address("198.121.0.1");
        user2.setEmail("abc@abc.com");
        user2.setLatitude(123);
        user2.setLongitude(123);

        return Arrays.asList(user1, user2);
    }

    private List<User> getAllUserList() {
        User user1 = new User();
        user1.setId(1);
        user1.setFirst_name("Test1");
        user1.setLast_name("Case1");
        user1.setIp_address("198.121.0.1");
        user1.setEmail("abc@abc.com");
        user1.setLatitude(123);
        user1.setLongitude(123);

        User user2 = new User();
        user2.setId(2);
        user2.setFirst_name("Test2");
        user2.setLast_name("Case2");
        user2.setIp_address("198.121.0.1");
        user2.setEmail("abc@abc.com");
        user2.setLatitude(123);
        user2.setLongitude(123);

        return Arrays.asList(user1, user2);
    }
}
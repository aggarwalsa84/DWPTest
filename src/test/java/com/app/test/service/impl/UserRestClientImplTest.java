package com.app.test.service.impl;

import com.app.test.exception.UserServiceException;
import com.app.test.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static com.app.test.constants.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class UserRestClientImplTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private Environment env;

    @InjectMocks
    UserRestClientImpl userRestClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllUsersClientErrorException() {
        when(env.getProperty(USERS_API)).thenReturn("URI");
        when(restTemplate.getForObject(anyString(), any())).thenThrow(HttpClientErrorException.class);

        assertThrows(UserServiceException.class, () -> userRestClient.getAllUsers());
    }

    @Test
    void testGetAllUsersServerErrorException() {
        when(env.getProperty(USERS_API)).thenReturn("URI");
        when(restTemplate.getForObject(anyString(), any())).thenThrow(HttpServerErrorException.class);

        assertThrows(UserServiceException.class, () -> userRestClient.getAllUsers());
    }

    @Test
    void testGetAllUsersRestClientException() {
        when(env.getProperty(USERS_API)).thenReturn("URI");
        when(restTemplate.getForObject(anyString(), any())).thenThrow(RestClientException.class);

        assertThrows(UserServiceException.class, () -> userRestClient.getAllUsers());
    }

    @Test
    void testGetAllUsersEmptyList() {
        when(env.getProperty(USERS_API)).thenReturn("URI");
        when(restTemplate.getForObject(anyString(), any())).thenReturn(new User[]{});

        assertEquals(userRestClient.getAllUsers(), Collections.EMPTY_LIST);
    }

    @Test
    void testGetAllUsers() {
        when(env.getProperty(USERS_API)).thenReturn("URI");
        when(restTemplate.getForObject(anyString(), any())).thenReturn(getAllUserList());
        List<User> users = userRestClient.getAllUsers();

        assertEquals(users.size(), 2);
        assertEquals(users.get(0).getId(), 1);
        assertEquals(users.get(1).getId(), 2);
    }

    @Test
    void testGetCityUsersClientErrorException() {
        when(env.getProperty(USERS_CITY_API)).thenReturn("URI");
        when(restTemplate.getForObject(anyString(), any(), anyMap())).thenThrow(HttpClientErrorException.class);

        assertThrows(UserServiceException.class, () -> userRestClient.getAllCityUsers("city"));
    }

    @Test
    void testGetCityUsersServerErrorException() {
        when(env.getProperty(USERS_CITY_API)).thenReturn("URI");
        when(restTemplate.getForObject(anyString(), any(), anyMap())).thenThrow(HttpServerErrorException.class);

        assertThrows(UserServiceException.class, () -> userRestClient.getAllCityUsers("city"));
    }

    @Test
    void testGetCityUsersRestClientException() {
        when(env.getProperty(USERS_CITY_API)).thenReturn("URI");
        when(restTemplate.getForObject(anyString(), any(), anyMap())).thenThrow(RestClientException.class);

        assertThrows(UserServiceException.class, () -> userRestClient.getAllCityUsers("city"));
    }

    @Test
    void testGetCityUsersEmptyList() {
        when(env.getProperty(USERS_CITY_API)).thenReturn("URI");
        when(restTemplate.getForObject(anyString(), any(), anyMap())).thenReturn(null);

        assertEquals(userRestClient.getAllCityUsers("city"), Collections.EMPTY_LIST);
    }

    @Test
    void testGetCityUsers() {
        when(env.getProperty(USERS_CITY_API)).thenReturn("URI");
        when(restTemplate.getForObject(anyString(), any(), anyMap())).thenReturn(getAllUserList());

        List<User> users = userRestClient.getAllCityUsers("city");

        assertEquals(users.size(), 2);
        assertEquals(users.get(0).getId(), 1);
        assertEquals(users.get(1).getId(), 2);
    }

    private User[] getAllUserList() {
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

        return new User[]{user1,user2};
    }
}
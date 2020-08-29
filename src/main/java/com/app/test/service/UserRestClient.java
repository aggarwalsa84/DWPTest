package com.app.test.service;

import com.app.test.exception.UserServiceException;
import com.app.test.models.User;

import java.util.List;

public interface UserRestClient {
    List<User> getAllUsers() throws UserServiceException;

    List<User> getAllCityUsers(String city) throws UserServiceException;
}

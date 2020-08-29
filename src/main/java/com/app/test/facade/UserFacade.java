package com.app.test.facade;

import com.app.test.exception.UserServiceException;
import com.app.test.models.User;

import java.util.List;

public interface UserFacade {

    List<User> getUsersOfCityAndWithinRadius(String city, int radius) throws UserServiceException;
}

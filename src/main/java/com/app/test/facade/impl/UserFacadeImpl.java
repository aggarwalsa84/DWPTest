package com.app.test.facade.impl;

import com.app.test.exception.UserNotFoundException;
import com.app.test.exception.UserServiceException;
import com.app.test.facade.UserFacade;
import com.app.test.models.User;
import com.app.test.service.UserRestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.app.test.util.LocationUtility.distanceTo;
import static com.app.test.util.LocationUtility.getCityCoordinates;

@Service
public class UserFacadeImpl implements UserFacade {
    @Autowired
    UserRestClient userRestClient;

    @Autowired
    private Environment env;

    private static final Logger LOG = LoggerFactory.getLogger(UserFacadeImpl.class);

    @Override
    public List<User> getUsersOfCityAndWithinRadius(String city, int radius) throws UserServiceException {
        List<User> users = userRestClient.getAllUsers();
        List<User> cityUsers = userRestClient.getAllCityUsers(city);

        List<User> userList =  mergeUsers(users, cityUsers, city, radius);
        if(!CollectionUtils.isEmpty(userList))
            return userList;
        else{
            LOG.error("No users found for city "+city+" and within radius "+radius);
            throw new UserNotFoundException("No users found");
        }
    }

    private List<User> mergeUsers(List<User> users, List<User> cityUsers, String city, int radius) {
        List<User> userList = getUsersWithinRadius(users, city, radius);
        userList.addAll(cityUsers.stream()
                .filter(londonUser -> !userList.contains(londonUser))
                .collect(Collectors.toList()));
        return userList;
    }

    private List<User> getUsersWithinRadius(List<User> users, String city, int radius) {
        List<User> userList = new ArrayList<>();
        double[] location = getCityCoordinates(env, city);
        LOG.info("City Latitude : "+location[0]);
        LOG.info("City Longitude : "+location[1]);
        for (User user : users) {
            if (distanceTo(location[0], location[1], user.getLatitude(), user.getLongitude(), radius))
                userList.add(user);
        }
        return userList;
    }
}

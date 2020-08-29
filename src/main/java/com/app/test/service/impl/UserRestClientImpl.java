package com.app.test.service.impl;

import com.app.test.exception.UserServiceException;
import com.app.test.models.User;
import com.app.test.service.UserRestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static com.app.test.constants.Constants.*;


@Service
public class UserRestClientImpl implements UserRestClient {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Environment env;

    private static final Logger LOG = LoggerFactory.getLogger(UserRestClientImpl.class);

    @Override
    public List<User> getAllUsers() throws UserServiceException {
        String uri = env.getProperty(USERS_API);
        User[] users;
        try {
            users = restTemplate.getForObject(uri, User[].class);
        } catch (HttpClientErrorException ex) {
            LOG.error(ex.getMessage());
            throw new UserServiceException(ex.getMessage());
        } catch (HttpServerErrorException ex) {
            LOG.error(ex.getMessage());
            throw new UserServiceException(ex.getMessage());
        } catch (RestClientException ex) {
            LOG.error(ex.getMessage());
            throw new UserServiceException(ex.getMessage());
        }

        LOG.info("Count of users found in herokuapp :"+users.length);
        return !Objects.isNull(users) ? Arrays.asList(users) : Collections.EMPTY_LIST;
    }

    @Override
    public List<User> getAllCityUsers(String city) throws UserServiceException {
        String uri = env.getProperty(USERS_CITY_API);
        Map<String, String> params = new HashMap();
        params.put(CITY, city);
        User[] cityUsers;
        try {
            cityUsers = restTemplate.getForObject(uri, User[].class, params);
        } catch (HttpClientErrorException ex) {
            LOG.error(ex.getMessage());
            throw new UserServiceException(ex.getMessage());
        } catch (HttpServerErrorException ex) {
            LOG.error(ex.getMessage());
            throw new UserServiceException(ex.getMessage());
        } catch (RestClientException ex) {
            LOG.error(ex.getMessage());
            throw new UserServiceException(ex.getMessage());
        }

        LOG.info("Count of users found in city " + city +" from herokuapp :"+cityUsers.length);
        return !Objects.isNull(cityUsers) ? Arrays.asList(cityUsers) : Collections.EMPTY_LIST;
    }
}

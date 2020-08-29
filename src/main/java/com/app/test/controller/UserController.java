package com.app.test.controller;

import com.app.test.dto.UserDTO;
import com.app.test.exception.UserNotFoundException;
import com.app.test.facade.UserFacade;
import com.app.test.models.User;
import com.app.test.populator.Populator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserFacade userFacade;
    @Autowired
    Populator userPopulator;

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @RequestMapping("/users")
    public ResponseEntity<List<UserDTO>> getUsers(@RequestParam String city, @RequestParam int radius) {
        LOG.info("Searching users found in city "+ city+" within radius "+radius+"miles");

        List<UserDTO> userDTOS = new ArrayList<>();
        List<User> users = userFacade.getUsersOfCityAndWithinRadius(city, radius);

        LOG.info("Count of users found in city "+ city+" within radius "+radius+"miles :"+ users.size());
        if (!CollectionUtils.isEmpty(users)) {
            for (User user : users) {
                UserDTO userDTO = new UserDTO();
                userPopulator.populate(user, userDTO);
                userDTOS.add(userDTO);
            }
        }else{
            LOG.info("No users found for city "+ city+" within radius "+radius+"miles");
            throw new UserNotFoundException();
        }
        return new ResponseEntity<>(userDTOS, HttpStatus.OK);
    }
}

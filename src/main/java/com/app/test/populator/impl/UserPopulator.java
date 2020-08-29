package com.app.test.populator.impl;

import com.app.test.dto.UserDTO;
import com.app.test.models.User;
import com.app.test.populator.Populator;
import org.springframework.stereotype.Service;

@Service
public class UserPopulator implements Populator<User, UserDTO> {
    @Override
    public void populate(User user, UserDTO userDTO) {
        userDTO.setFirst_name(user.getFirst_name());
        userDTO.setLast_name(user.getLast_name());
        userDTO.setEmail(user.getEmail());
        userDTO.setId(user.getId());
        userDTO.setIp_address(user.getIp_address());
        userDTO.setLatitude(user.getLatitude());
        userDTO.setLongitude(user.getLongitude());
    }
}

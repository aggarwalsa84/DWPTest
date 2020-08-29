package com.app.test.controller;


import com.app.test.TestApplication;
import com.app.test.facade.UserFacade;
import com.app.test.models.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {TestApplication.class})
public class UserControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private UserFacade userFacade;

    @Before
    public void setUp() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testRetrieveUsers() throws Exception {
        when(userFacade.getUsersOfCityAndWithinRadius("London", 50)).thenReturn(getAllUserList());

        final Integer[] expectedUserIds = getAllUserList().stream().map(User::getId)
                .collect(Collectors.toList()).toArray(new Integer[getAllUserList().size()]);

        final ResultActions result = mockMvc.perform(get("/users?city=London&radius=50")
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.length()").value(getAllUserList().size()));
        result.andExpect(jsonPath("$[*].id", containsInAnyOrder(expectedUserIds)));
    }

    @Test
    public void should_Return404_When_UserNotFound() throws Exception {
        when(userFacade.getUsersOfCityAndWithinRadius("London", 50)).thenReturn(Collections.EMPTY_LIST);
        mockMvc.perform(get("/users?city=London&radius=50")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
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
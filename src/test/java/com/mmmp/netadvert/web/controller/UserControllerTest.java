package com.mmmp.NetAdvert.web.controller;


import static com.mmmp.NetAdvert.constants.UserConstants.*;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import javax.annotation.PostConstruct;

import com.mmmp.netadvert.NetAdvertApplication;

import com.mmmp.NetAdvert.TestUtil;
import com.mmmp.NetAdvert.constants.UserConstants;
import com.mmmp.netadvert.model.Role;
import com.mmmp.netadvert.model.User;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


/**
 * Created by milosandric on 12/12/2016.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = NetAdvertApplication.class)
@WebIntegrationTest(randomPort = true)
@TestPropertySource(locations="classpath:test.properties")
public class UserControllerTest {
    private static final String URL_PREFIX = "/api";

    private MediaType contentType = new MediaType(
            MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @PostConstruct
    public void setup() {
        this.mockMvc = MockMvcBuilders.
                webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetAllStudents() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/allusers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(DB_COUNT)))
                .andExpect(jsonPath("$.[*].id").value(hasItem(UserConstants.DB_ID.intValue())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DB_EMAIL)))
                .andExpect(jsonPath("$.[*].first_name").value(hasItem(DB_FIRST_NAME)))
                .andExpect(jsonPath("$.[*].last_name").value(hasItem(DB_LAST_NAME)))
                .andExpect(jsonPath("$.[*].password").value(hasItem(DB_PASSWORD)))
                .andExpect(jsonPath("$.[*].user_rate").value(hasItem(UserConstants.DB_USER_RATE.doubleValue())))
                .andExpect(jsonPath("$.[*].role.id").value(hasItem(UserConstants.DB_USER_ROLE_ID.intValue())));
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testRegisterUser() throws Exception {
        User user = new User();
        user.setEmail("test@test");
        user.setFirst_name("fn");
        user.setLast_name("ln");
        user.setPassword("pass");
        user.setUser_rate(1);

        String json = TestUtil.json(user);
        System.out.print(json);
        mockMvc.perform(post(URL_PREFIX + "/register").contentType(contentType).content(json))
                .andExpect(status().isOk());
    }


    @Test
    @Transactional
    @Rollback(true)
    public void testUpdateUser() throws Exception {
        Role r = new Role();
        r.setId(1);
        r.setName("Admin");

        User logUser = new User();
        logUser.setId(1);
        logUser.setEmail("milosa942@gmail.com");
        logUser.setLast_name("Milos");
        logUser.setLast_name("Andric");
        logUser.setPassword("pass");
        logUser.setUser_rate(0);
        logUser.setRole(r);


        User user = new User();
        user.setEmail("test@testnew");
        user.setFirst_name("fnnew");
        user.setLast_name("lnnew");
        user.setPassword("passnew");
        user.setUser_rate(1);

        String json = TestUtil.json(user);
        System.out.print(json);
        mockMvc.perform(put(URL_PREFIX + "/user").sessionAttr("logedUser", logUser).contentType(contentType).content(json))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetUser() throws Exception {
        Role r = new Role();
        r.setId(1);
        r.setName("Admin");

        User logUser = new User();
        logUser.setId(1);
        logUser.setEmail("milosa942@gmail.com");
        logUser.setFirst_name("Milos");
        logUser.setLast_name("Andric");
        logUser.setPassword("pass");
        logUser.setUser_rate(1);
        logUser.setRole(r);

        mockMvc.perform(get(URL_PREFIX + "/user").sessionAttr("logedUser", logUser))
                .andExpect(content().contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("milosa942@gmail.com"))
                .andExpect(jsonPath("$.first_name").value("Milos"))
                .andExpect(jsonPath("$.last_name").value("Andric"))
                .andExpect(jsonPath("$.password").value("pass"))
                .andExpect(jsonPath("$.user_rate").value(1));
//                .andExpect(jsonPath("$.role.id").value(2));
    }

    @Test
    public void testLoginUser() throws Exception {
        mockMvc.perform(post(URL_PREFIX + "/login").contentType(contentType).param("email", "milosa942@gmail.com").param("password", "pass"))
                .andExpect(status().isOk());

    }

    @Test
    public void getAllUserAdvertsTest() throws Exception {
        Role r = new Role();
        r.setId(1);
        r.setName("Admin");

        User logUser = new User();
        logUser.setId(1);
        logUser.setEmail("milosa942@gmail.com");
        logUser.setLast_name("Milos");
        logUser.setLast_name("Andric");
        logUser.setPassword("pass");
        logUser.setUser_rate(0);
        logUser.setRole(r);

        mockMvc.perform(get(URL_PREFIX + "/user/1/adverts").sessionAttr("logedUser", logUser))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType));
    }

    @Test
    @Transactional
    @Rollback(true)
    public void updateAdvertExpireDateTest() throws Exception {
        Role r = new Role();
        r.setId(1);
        r.setName("Admin");

        User logUser = new User();
        logUser.setId(1);
        logUser.setEmail("milosa942@gmail.com");
        logUser.setLast_name("Milos");
        logUser.setLast_name("Andric");
        logUser.setPassword("pass");
        logUser.setUser_rate(0);
        logUser.setRole(r);

        mockMvc.perform(put(URL_PREFIX + "/user/2/advert/1/expiredate").sessionAttr("logedUser", logUser).contentType(contentType))
                .andExpect(status().isOk());

    }
}

package com.mmmp.netadvert.service;

import static com.mmmp.netadvert.constants.UserConstants.DB_COUNT;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mmmp.netadvert.NetAdvertApplication;
import com.mmmp.netadvert.model.User;

/**
 * Created by milosandric on 12/12/2016.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = NetAdvertApplication.class)
@WebIntegrationTest(randomPort = true)
@TestPropertySource(locations="classpath:test.properties")
public class UserServiceTest {

    private AdverService adverService;

    @Autowired
    public void setAdverService(AdverService ps) {
        this.adverService = ps;
    }

    @Test
    public void testFindAll() {
        List<User> users = adverService.getAllUsers();
        assertThat(users).hasSize(DB_COUNT);
    }
}

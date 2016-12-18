package com.mmmp.NetAdvert.web.controller;

import com.mmmp.netadvert.NetAdvertApplication;
import com.mmmp.NetAdvert.TestUtil;
import com.mmmp.netadvert.model.Company;
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

import javax.annotation.PostConstruct;
import java.nio.charset.Charset;

import static com.mmmp.NetAdvert.constants.ReportConstants.db_count_reports;
import static com.mmmp.NetAdvert.constants.ReportConstants.report_id;
import static com.mmmp.NetAdvert.constants.ReportConstants.text;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by milosandric on 16/12/2016.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = NetAdvertApplication.class)
@WebIntegrationTest(randomPort = true)
@TestPropertySource(locations="classpath:test.properties")
public class CompanyControllerTest {
    private static final String URL_PREFIX = "/api";

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @PostConstruct
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void getCompany() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/company/2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.company_name").value("TestComp"))
                .andExpect(jsonPath("$.user.id").value(1));
    }

    @Test
    public void getAllCompanyUsers() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/company/2/allusers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[*].id").value(hasItem(2)))
                .andExpect(jsonPath("$.[*].email").value(hasItem("milossm94@hotmail.com")))
                .andExpect(jsonPath("$.[*].first_name").value(hasItem("Milos")))
                .andExpect(jsonPath("$.[*].last_name").value(hasItem("Obradovic")))
                .andExpect(jsonPath("$.[*].password").value(hasItem("pass")))
                .andExpect(jsonPath("$.[*].user_rate").value(hasItem(4.0)))
                .andExpect(jsonPath("$.[*].role.id").value(hasItem(2)));
    }


    @Test
    @Transactional
    @Rollback(true)
    public void postNewCompany() throws Exception {
        Company cmp = new Company();
        cmp.setCompany_name("TestComp3");

        String json = TestUtil.json(cmp);
        mockMvc.perform(post(URL_PREFIX + "/company").contentType(contentType).param("u_id", "1").content(json))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void postUserForCompany() throws Exception {
        mockMvc.perform(post(URL_PREFIX + "/company/2/user/1").contentType(contentType))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void acceptUserTest() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/company/2/activate/2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType));
    }


}

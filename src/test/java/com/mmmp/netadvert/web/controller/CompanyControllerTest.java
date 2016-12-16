package com.mmmp.netadvert.web.controller;

import com.mmmp.netadvert.NetAdvertApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import java.nio.charset.Charset;

import static com.mmmp.netadvert.constants.ReportConstants.db_count_reports;
import static com.mmmp.netadvert.constants.ReportConstants.report_id;
import static com.mmmp.netadvert.constants.ReportConstants.text;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    private static final String URL_PREFIX = "/api/report";

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
    public void testAllReports() throws Exception {
        mockMvc.perform(get(URL_PREFIX)).andExpect(status().isOk()).andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(db_count_reports)))
                .andExpect(jsonPath("$.[*].id").value(hasItem(report_id)))
                .andExpect(jsonPath("$.[*].reportDescription").value(hasItem(text)));
    }


}

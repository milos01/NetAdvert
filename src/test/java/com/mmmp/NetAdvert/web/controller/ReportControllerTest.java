package com.mmmp.NetAdvert.web.controller;

import static com.mmmp.NetAdvert.constants.ReportConstants.db_count_reports;
import static com.mmmp.NetAdvert.constants.ReportConstants.text;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.nio.charset.Charset;

import javax.annotation.PostConstruct;

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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mmmp.netadvert.NetAdvertApplication;
import com.mmmp.netadvert.model.Advert;
import com.mmmp.netadvert.model.Report;
import com.mmmp.netadvert.model.Role;
import com.mmmp.netadvert.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = NetAdvertApplication.class)
@WebIntegrationTest(randomPort = true)
@TestPropertySource(locations="classpath:test.properties")
public class ReportControllerTest {

    private static final String URL_PREFIX = "/api/report";

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
    public void testAllReports() throws Exception {
        mockMvc.perform(get(URL_PREFIX))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(db_count_reports)));
//                .andExpect(jsonPath("$.[*].id").value(hasItem(report_id)));
//                .andExpect(jsonPath("$.[*].firstName").value(hasItem(DB_FIRST_NAME)))
//                .andExpect(jsonPath("$.[*].lastName").value(hasItem(DB_LAST_NAME)))
//                .andExpect(jsonPath("$.[*].cardNumber").value(hasItem(DB_CARD_NUMBER)));
    }
    
	@Test
	@Transactional
	@Rollback(true)
	public void testNewReport() throws Exception {

		Advert a = new Advert();
		a.setId(1); 
		User u = new User();
		u.setId(1);
		u.setEmail("doslicmm@live.com");
		u.setFirst_name("Mladen");
		u.setLast_name("Doslic");
		u.setPassword("123");
		a.setUser(u);
		Role r = new Role();
		r.setId(1);
		r.setName("regular");
		u.setRole(r);
		
		Report rep = new Report();
		rep.setAdvert(a);
		rep.setReportDescription(text);
    	rep.setUser(u);
    	rep.setVerified(0);
    	String object = json(rep);
    	
        this.mockMvc.perform(post(URL_PREFIX)
        		.contentType(contentType)
                .content(object))
				.andExpect(status().isOk());
////                .andExpect(jsonPath("$", hasSize(db_count_reports)));
////                .andExpect(jsonPath("$.[*].id").value(hasItem(report_id)));
////                .andExpect(jsonPath("$.[*].firstName").value(hasItem(DB_FIRST_NAME)))
////                .andExpect(jsonPath("$.[*].lastName").value(hasItem(DB_LAST_NAME)))
////                .andExpect(jsonPath("$.[*].cardNumber").value(hasItem(DB_CARD_NUMBER)));
//	
//    rep.setAdvert(null);
//    this.mockMvc.perform(post(URL_PREFIX)
//    		.contentType(contentType)
//            .content(object))
//			.andExpect(status().isNotFound());
//            .andExpect(jsonPath("$", hasSize(db_count_reports)));
//            .andExpect(jsonPath("$.[*].id").value(hasItem(report_id)));
//            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DB_FIRST_NAME)))
//            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DB_LAST_NAME)))
//            .andExpect(jsonPath("$.[*].cardNumber").value(hasItem(DB_CARD_NUMBER)));
}
	
	public static String json(Object object)
            throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        
        return mapper.writeValueAsString(object);
    }
}

package com.mmmp.NetAdvert.web.controller;

import static com.mmmp.NetAdvert.constants.LocationConstants.city;
import static com.mmmp.NetAdvert.constants.LocationConstants.location_id;
import static com.mmmp.NetAdvert.constants.LocationConstants.postal_code;
import static com.mmmp.NetAdvert.constants.LocationConstants.region;
import static com.mmmp.NetAdvert.constants.LocationConstants.street;
import static com.mmmp.NetAdvert.constants.LocationConstants.street_number;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.mmmp.NetAdvert.TestUtil;
import com.mmmp.netadvert.NetAdvertApplication;
import com.mmmp.netadvert.model.Location;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = NetAdvertApplication.class)
@WebIntegrationTest(randomPort = true)
@TestPropertySource(locations="classpath:test.properties")
public class LocationControllerTest {

    private static final String URL_PREFIX = "/api/location";

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
    @Transactional
    @Rollback(false)
    public void testNewLocation() throws Exception {
    	Location l = new Location();
    	l.setCity(city);
    	l.setPostalCode(postal_code);
    	l.setRegion(region);
    	l.setStreet(street);
    	l.setStreetNumber(street_number);
    	String object = TestUtil.json(l);
    	   this.mockMvc.perform(post(URL_PREFIX)
                   .contentType(contentType)
                   .content(object))
                   .andExpect(status().isOk());
    	   
        l.setCity(null);
       	String object2 = TestUtil.json(l);
    	   	this.mockMvc.perform(put(URL_PREFIX)
                   .contentType(contentType)
                   .content(object2))
                   .andExpect(status().isInternalServerError());
    	   	
    	l.setStreet("");
       	String object3 = TestUtil.json(l);
    	   	this.mockMvc.perform(put(URL_PREFIX)
                   .contentType(contentType)
                   .content(object3))
                   .andExpect(status().isInternalServerError());
    }
    
    @Test
    @Transactional
    @Rollback(true)
    public void testUpdateLocation() throws Exception {
    	Location l = new Location();
    	l.setId(location_id);
    	l.setCity(city);
    	l.setPostalCode(postal_code);
    	l.setRegion(region);
    	l.setStreet(street);
    	l.setStreetNumber(street_number);
    	String object = TestUtil.json(l);
    	   this.mockMvc.perform(put(URL_PREFIX)
                   .contentType(contentType)
                   .content(object))
                   .andExpect(status().isOk());
    	   
    	   
        l.setCity(null);
    	String object2 = TestUtil.json(l);
 	   	this.mockMvc.perform(put(URL_PREFIX)
                .contentType(contentType)
                .content(object2))
                .andExpect(status().isInternalServerError());
 	   	
 	   	l.setStreet("");
    	String object3 = TestUtil.json(l);
 	   	this.mockMvc.perform(put(URL_PREFIX)
                .contentType(contentType)
                .content(object3))
                .andExpect(status().isInternalServerError());
 	   	
    }
    
    @Test
    public void testFindLocation() throws Exception{
    	mockMvc.perform(get(URL_PREFIX+"?id=2"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(contentType))
        .andExpect(jsonPath("$.id").value(location_id))
        .andExpect(jsonPath("$.postalCode").value(postal_code))
        .andExpect(jsonPath("$.city").value(city))
        .andExpect(jsonPath("$.street").value(street));
    	
    	mockMvc.perform(get(URL_PREFIX+"?id=100"))
        .andExpect(status().isInternalServerError());
    }
    
}

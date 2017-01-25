package com.mmmp.NetAdvert.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.security.Principal;

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
import com.mmmp.netadvert.DTO.AdvertNewRate;
import com.mmmp.netadvert.DTO.UserDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = NetAdvertApplication.class)
@WebIntegrationTest(randomPort = true)
@TestPropertySource(locations = "classpath:test.properties")
public class AdvertRatingControllerTest {

	private static final String URL_PREFIX = "/api/advert";

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
	@Transactional
	@Rollback(true)
	public void testaddAdvertRating() throws Exception{
		
		UserDTO u = new UserDTO();
		u.setEmail("milan@gmail.com");
		
		Principal p =  new Principal() {
			
			@Override
			public String getName() {
				return u.getEmail();
			}
		};
		
		AdvertNewRate addedRating =  new AdvertNewRate();
		addedRating.setRate(3);
		
		String json = TestUtil.json(addedRating);
		u.setEmail("xxxx@gmail.com");
		
		this.mockMvc
		.perform(post(URL_PREFIX + "/" + 1 + "/" + "rating")
				.principal(p)
				.contentType(contentType).content(json))
				.andExpect(status().isForbidden());
		
		u.setEmail("milan@gmail.com");
		this.mockMvc
		.perform(post(URL_PREFIX + "/" + 99 + "/" + "rating")
				.principal(p)
				.contentType(contentType).content(json))
				.andExpect(status().isBadRequest());
		
		this.mockMvc
		.perform(post(URL_PREFIX + "/" + 1 + "/" + "rating")
				.principal(p)
				.contentType(contentType).content(json))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1));
		
		u.setEmail("milossm94@hotmail.com");
		this.mockMvc
		.perform(post(URL_PREFIX + "/" + 1 + "/" + "rating")
				.principal(p)
				.contentType(contentType).content(json))
				.andExpect(status().isBadRequest());
	}
	
	
	@Test
	public void testGetUserAndAdRait() throws Exception{
		
		this.mockMvc
		.perform(get(URL_PREFIX + "/findUserAdvertRait")
				.contentType(contentType).param("user_id", 2+"").param("advert_id", 1+""))
				.andExpect(status().isOk());
		
		this.mockMvc
		.perform(get(URL_PREFIX + "/findUserAdvertRait")
				.contentType(contentType).param("user_id", 5+"").param("advert_id", 1+""))
				.andExpect(status().isBadRequest());
	}
}

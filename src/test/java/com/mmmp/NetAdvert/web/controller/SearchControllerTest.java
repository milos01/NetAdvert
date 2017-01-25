package com.mmmp.NetAdvert.web.controller;

import static com.mmmp.NetAdvert.constants.AdvertConstants.advert_id;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.mmmp.netadvert.NetAdvertApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = NetAdvertApplication.class)
@WebIntegrationTest(randomPort = true)
@TestPropertySource(locations = "classpath:test.properties")
public class SearchControllerTest {

	private static final String URL_PREFIX = "/api/search";

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
	public void testSearchAdvert() throws Exception{
		//params and map params
		this.mockMvc
		.perform(get(URL_PREFIX).contentType(contentType).param("size", "2").param("page", "0").param("sort", "advertName, desc")
				.param("priceFrom", "10000").param("priceTo", "100000"))
		.andExpect(jsonPath("$.content", hasSize(2)))
		.andExpect(jsonPath("$.content.[*].id").value(hasItem(advert_id)))
		.andExpect(status().isOk());
		
		this.mockMvc
		.perform(get(URL_PREFIX).contentType(contentType).param("size", "2").param("page", "5").param("sort", "advertName, desc")
				.param("priceFrom", "10000").param("priceTo", "100000"))
		.andExpect(status().isNotFound());
	
	}
}

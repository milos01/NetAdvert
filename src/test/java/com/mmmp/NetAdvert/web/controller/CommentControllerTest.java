package com.mmmp.NetAdvert.web.controller;

import static com.mmmp.NetAdvert.constants.LocationConstants.city;
import static com.mmmp.NetAdvert.constants.LocationConstants.postal_code;
import static com.mmmp.NetAdvert.constants.LocationConstants.region;
import static com.mmmp.NetAdvert.constants.LocationConstants.street;
import static com.mmmp.NetAdvert.constants.LocationConstants.street_number;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static com.mmmp.NetAdvert.constants.AdvertConstants.contact;
import static com.mmmp.NetAdvert.constants.AdvertConstants.description;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static com.mmmp.NetAdvert.constants.CommentConstants.comment_text;
import static com.mmmp.NetAdvert.constants.CommentConstants.comment_id;
import static com.mmmp.NetAdvert.constants.CommentConstants.datum;
import static com.mmmp.NetAdvert.constants.CommentConstants.advert_id;
import static com.mmmp.NetAdvert.constants.CommentConstants.db_comments_count;
import java.nio.charset.Charset;
import java.sql.Date;

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
import com.mmmp.netadvert.model.Advert;
import com.mmmp.netadvert.model.Comment;
import com.mmmp.netadvert.model.Location;
import com.mmmp.netadvert.model.Realestate;
import com.mmmp.netadvert.model.RealestateCategory;
import com.mmmp.netadvert.model.RealestateType;
import com.mmmp.netadvert.model.Role;
import com.mmmp.netadvert.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = NetAdvertApplication.class)
@WebIntegrationTest(randomPort = true)
@TestPropertySource(locations = "classpath:test.properties")
public class CommentControllerTest {

	private static final String URL_PREFIX = "/api/comment";

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
	public void testCreateComment() throws Exception{
		Role r = new Role();
        r.setId(2);
        r.setName("Regular user");

        User u = new User();
        u.setId(2);
        u.setEmail("milossm94@hotmail.com");
        u.setLast_name("Milos");
        u.setLast_name("Obradovic");
        u.setPassword("pass");
        u.setUser_rate(0);
        u.setRole(r);

		RealestateCategory rc = new RealestateCategory();
		rc.setId(3);
		rc.setCategoryName("Lot");

		RealestateType rt = new RealestateType();
		rt.setId(1);
		rt.setTypeName("House");

		Location l = new Location();
		l.setCity(city);
		l.setPostalCode(postal_code);
		l.setRegion(region);
		l.setStreet(street);
		l.setStreetNumber(street_number);

		Realestate rls = new Realestate();
		rls.setId(5);
		rls.setCategory(rc);
		rls.setArea(40000);
		//rls.setCost(123);
		rls.setHeating(true);
		rls.setLocation(l);

		Advert a = new Advert();
		a.setId(advert_id);
		a.setUser(u);
		a.setAdvert_rate(2);
		a.setContact(contact);
		a.setDescription(description);
		a.setCreated_at(new Date(123455));
		a.setExpire_date(new Date(123455));
		a.setIs_deleted(false);
		a.setIs_sold(false);
		a.setRealestate(rls);
		a.setRent_sale(false);
		a.setUpdated_at(new Date(123455));
		
		Comment comment = new Comment();
		comment.setAdvert(a);
		comment.setUser(u);
		comment.setDate(datum);
		comment.setText(comment_text);
		
		String object = TestUtil.json(comment);
		
		this.mockMvc.perform(post(URL_PREFIX)
				.contentType(contentType).sessionAttr("logedUser", u).param("advert_id", a.getId()+"").param("comment", comment.getText()).content(object))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.text").value(comment.getText()));
		
		comment.setAdvert(null);
		String object2 = TestUtil.json(comment);
		this.mockMvc.perform(post(URL_PREFIX)
				.contentType(contentType).sessionAttr("logedUser", u).param("advert_id", 999+"").param("comment", comment.getText()).content(object2))
		.andExpect(status().isInternalServerError());
		
		comment.setText(null);
		String object3 = TestUtil.json(comment);
		this.mockMvc.perform(post(URL_PREFIX)
				.contentType(contentType).sessionAttr("logedUser", u).param("advert_id", a.getId()+"").param("comment", "").content(object3))
		.andExpect(status().isInternalServerError());
//		jsonPath("$.[*].id").value(hasItem(report_id))
	}
	
	@Test
	public void testFindComments() throws Exception {
		mockMvc.perform(get(URL_PREFIX + "/advert/"+999)).andExpect(status().isBadRequest());
		
		mockMvc.perform(get(URL_PREFIX + "/advert/"+advert_id)).andExpect(status().isOk()).andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$", hasSize(db_comments_count)))
				.andExpect(jsonPath("$.[*].id").value(hasItem(comment_id)))
				.andExpect(jsonPath("$.[*].text").value(hasItem(comment_text)));
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteComments() throws Exception{
		mockMvc.perform(delete(URL_PREFIX + "/delete/"+comment_id))
		.andExpect(status().isOk());
		
		mockMvc.perform(delete(URL_PREFIX + "/delete/"+1200))
		.andExpect(status().isInternalServerError());
//		  .andExpect(jsonPath("$", hasSize(db_comments_count)));
	}
}

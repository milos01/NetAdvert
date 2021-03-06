package com.mmmp.NetAdvert.web.controller;

import static com.mmmp.NetAdvert.constants.AdvertConstants.contact;
import static com.mmmp.NetAdvert.constants.AdvertConstants.description;
import static com.mmmp.NetAdvert.constants.CommentConstants.advert_id;
import static com.mmmp.NetAdvert.constants.CommentConstants.comment_id;
import static com.mmmp.NetAdvert.constants.CommentConstants.comment_text;
import static com.mmmp.NetAdvert.constants.CommentConstants.datum;
import static com.mmmp.NetAdvert.constants.CommentConstants.db_comments_count;
import static com.mmmp.NetAdvert.constants.LocationConstants.city;
import static com.mmmp.NetAdvert.constants.LocationConstants.postal_code;
import static com.mmmp.NetAdvert.constants.LocationConstants.region;
import static com.mmmp.NetAdvert.constants.LocationConstants.street;
import static com.mmmp.NetAdvert.constants.LocationConstants.street_number;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.security.Principal;
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
import com.mmmp.netadvert.DTO.AdvertDTO;
import com.mmmp.netadvert.DTO.CommentDTO;
import com.mmmp.netadvert.DTO.NewCommentDTO;
import com.mmmp.netadvert.DTO.UserDTO;
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
		a.setDeleted(false);
		a.setIs_sold(false);
		a.setRealestate(rls);
		a.setRent_sale(false);
		a.setUpdated_at(new Date(123455));
		
		Comment comment = new Comment();
		comment.setAdvert(a);
		comment.setUser(u);
		comment.setDate(datum);
		comment.setText(comment_text);
		
		Principal p =  new Principal() {
			
			@Override
			public String getName() {
				return u.getEmail();
			}
		};
		NewCommentDTO comm = new NewCommentDTO();
		comm.setText(comment_text);
		comm.setAdvert_id(a.getId());
		String object = TestUtil.json(comm);
		
		this.mockMvc.perform(post(URL_PREFIX)
				.contentType(contentType).principal(p).content(object))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.text").value(comment.getText()));
		
		comm.setAdvert_id(999);
		String object2 = TestUtil.json(comm);
		this.mockMvc.perform(post(URL_PREFIX)
				.contentType(contentType).principal(p).content(object2))
		.andExpect(status().isBadRequest());
		
		comm.setText(null);
		String object3 = TestUtil.json(comm);
		this.mockMvc.perform(post(URL_PREFIX)
				.contentType(contentType).principal(p).content(object3))
		.andExpect(status().isBadRequest());
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
		
	}
}

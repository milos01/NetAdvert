package com.mmmp.NetAdvert.web.controller;

import static com.mmmp.NetAdvert.constants.AdvertConstants.advert_id;
import static com.mmmp.NetAdvert.constants.AdvertConstants.advert_rate;
import static com.mmmp.NetAdvert.constants.AdvertConstants.contact;
import static com.mmmp.NetAdvert.constants.AdvertConstants.created_at;
import static com.mmmp.NetAdvert.constants.AdvertConstants.db_advert_count;
import static com.mmmp.NetAdvert.constants.AdvertConstants.description;
import static com.mmmp.NetAdvert.constants.AdvertConstants.expire_date;
import static com.mmmp.NetAdvert.constants.AdvertConstants.is_deleted;
import static com.mmmp.NetAdvert.constants.AdvertConstants.is_sold;
import static com.mmmp.NetAdvert.constants.AdvertConstants.rent_sale;
import static com.mmmp.NetAdvert.constants.AdvertConstants.updated_at;
import static com.mmmp.NetAdvert.constants.LocationConstants.city;
import static com.mmmp.NetAdvert.constants.LocationConstants.postal_code;
import static com.mmmp.NetAdvert.constants.LocationConstants.region;
import static com.mmmp.NetAdvert.constants.LocationConstants.street;
import static com.mmmp.NetAdvert.constants.LocationConstants.street_number;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.result.PrintingResultHandler;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.WebApplicationContext;

import com.mmmp.NetAdvert.TestUtil;
import com.mmmp.netadvert.NetAdvertApplication;
import com.mmmp.netadvert.model.Advert;
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
public class AdvertControllerTest {

	
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
	public void testGetAnAdvert() throws Exception{
		this.mockMvc
		.perform(get(URL_PREFIX + "/" + advert_id).contentType(contentType)).andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(advert_id))
				.andExpect(jsonPath("$.description").value(description))
				.andExpect(jsonPath("$.contact").value(contact));
		
		this.mockMvc
		.perform(get(URL_PREFIX + "/" + 1564).contentType(contentType)).andExpect(status().isBadRequest());
	}
	
	@Test
	public void testGetAllAdvert() throws Exception{
		this.mockMvc
		.perform(get(URL_PREFIX).contentType(contentType)).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(db_advert_count)))
				.andExpect(jsonPath("$.[*].id").value(hasItem(advert_id)))
				.andExpect(jsonPath("$.[*].description").value(hasItem(description)))
				.andExpect(jsonPath("$.[*].contact").value(hasItem(contact)));
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testBuyAdvert() throws Exception{
		Role r = new Role();
        r.setId(2);
        r.setName("Regular user");

        User logUser = new User();
        logUser.setId(3);
        logUser.setEmail("doslicmm@live.com");
        logUser.setLast_name("Mladen");
        logUser.setLast_name("Doslic");
        logUser.setPassword("123");
        logUser.setUser_rate(1);
        logUser.setRole(r);
        
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
		rls.setCost(123);
		rls.setHeating(true);
		rls.setLocation(l);

		Advert a = new Advert();
		a.setId(advert_id);
		a.setUser(logUser);
		a.setAdvert_rate(advert_rate);
		a.setContact(contact);
		a.setDescription(description);
		a.setCreated_at(created_at);
		a.setExpire_date(expire_date);
		a.setIs_deleted(is_deleted);
		a.setIs_sold(is_sold);
		a.setRealestate(rls);
		a.setRent_sale(rent_sale);
		a.setUpdated_at(updated_at);
		
		String object = TestUtil.json(a);
		
		this.mockMvc
		.perform(put(URL_PREFIX +"/"+ 1000+"/buy").sessionAttr("logedUser", logUser).contentType(contentType).content(object))
				.andExpect(status().isBadRequest());
        
		this.mockMvc
		.perform(put(URL_PREFIX +"/"+ advert_id+"/buy").sessionAttr("logedUser", logUser).contentType(contentType).content(object))
				.andExpect(status().isOk());
		
		a.setIs_sold(true);
		logUser.setId(3);
		
		String object2 = TestUtil.json(a);
		this.mockMvc
		.perform(put(URL_PREFIX +"/"+ advert_id+"/buy").sessionAttr("logedUser", logUser).contentType(contentType).content(object2))
				.andExpect(status().isBadRequest());
		
		a.setIs_deleted(true);
		a.setIs_sold(false);
		String object3 = TestUtil.json(a);
		this.mockMvc
		.perform(put(URL_PREFIX +"/"+ advert_id+"/buy").sessionAttr("logedUser", logUser).contentType(contentType).content(object3))
				.andExpect(status().isBadRequest());
		
		logUser.setId(2);
		a.setIs_deleted(false);
		a.setIs_sold(false);
		String object4 = TestUtil.json(a);
		this.mockMvc
		.perform(put(URL_PREFIX +"/"+ advert_id+"/buy").sessionAttr("logedUser", logUser).contentType(contentType).content(object4))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteAdvert() throws Exception{
		
		Role r = new Role();
		r.setId(2);
	    r.setName("Regular user");
		
        User logUser = new User();
        logUser.setId(2);
        logUser.setEmail("doslicmm@live.com");
        logUser.setLast_name("Mladen");
        logUser.setLast_name("Doslic");
        logUser.setPassword("123");
        logUser.setUser_rate(1);
        logUser.setRole(r);
        
		mockMvc
		.perform(delete(URL_PREFIX + "/" + 50).sessionAttr("logedUser", logUser).contentType(contentType)).andExpect(status().isInternalServerError());
		
		mockMvc
		.perform(delete(URL_PREFIX + "/" + advert_id).sessionAttr("logedUser", logUser).contentType(contentType)).andExpect(status().isOk());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testCreateAdvert() throws Exception{
		Role r = new Role();
		r.setId(2);
		r.setName("Regular user");

		User u = new User();
		u.setId(2);
		u.setEmail("doslicmm@live.com");
		u.setFirst_name("Mladen");
		u.setLast_name("Doslic");
		u.setPassword("123");
		u.setUser_rate(0);
		u.setRole(r);

		RealestateCategory rc = new RealestateCategory();
		rc.setId(1);
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
		rls.setRealestateName("ZUTA KUCA");
		rls.setCategory(rc);
		rls.setArea(40000);
		rls.setCost(123);
		rls.setHeating(true);
		rls.setLocation(l);

		Advert a = new Advert();
		a.setUser(u);
		a.setAdvert_rate(advert_rate);
		a.setContact(contact);
		a.setDescription(description);
		a.setIs_deleted(is_deleted);
		a.setIs_sold(is_sold);
		a.setRent_sale(rent_sale);
		
		List<Boolean> equpments = new ArrayList<Boolean>();
		equpments.add(false); equpments.add(true);  equpments.add(false); equpments.add(true); equpments.add(false); 
		String object = TestUtil.json(a);

		this.mockMvc
				.perform(post(URL_PREFIX).contentType(contentType).sessionAttr("logedUser", u).content(object)
						.param("contact", a.getContact())
						.param("description", a.getDescription())
						.param("rent_sale",false+"")
						.param("real_name", rls.getRealestateName())
						.param("real_type_id", rt.getId() + "")
						.param("real_cost", rls.getCost() + "")
						.param("real_area", rls.getArea() + "")
						.param("real_category_id", rc.getId() + "")
						.param("real_heating", rls.isHeating() + "")
						.param("loc_street", l.getStreet())
						.param("loc_street_number", l.getStreetNumber() + "")
						.param("loc_region", l.getRegion())
						.param("loc_city", l.getCity())
						.param("loc_postal_code", l.getPostalCode() + "")
						.param("equipments", equpments.get(0) + "")
						.param("equipments", equpments.get(1) + "")
						.param("equipments", equpments.get(2) + "")
						.param("equipments", equpments.get(3) + "")
						.param("equipments", equpments.get(4) + ""))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.contact").value(a.getContact()))
				.andExpect(jsonPath("$.description").value(a.getDescription()));
		
		rc = null;
		String object2 = TestUtil.json(a);
		this.mockMvc
				.perform(post(URL_PREFIX).contentType(contentType).sessionAttr("logedUser", u).content(object2)
						.param("contact", a.getContact())
						.param("description", a.getDescription())
						.param("rent_sale",false+"")
						.param("real_name", rls.getRealestateName())
						.param("real_type_id", rt.getId() + "")
						.param("real_cost", rls.getCost() + "")
						.param("real_area", rls.getArea() + "")
						.param("real_category_id", 9000+"")
						.param("real_heating", rls.isHeating() + "")
						.param("loc_street", l.getStreet())
						.param("loc_street_number", l.getStreetNumber() + "")
						.param("loc_region", l.getRegion())
						.param("loc_city", l.getCity())
						.param("loc_postal_code", l.getPostalCode() + "")
						.param("equipments", equpments.get(0) + "")
						.param("equipments", equpments.get(1) + "")
						.param("equipments", equpments.get(2) + "")
						.param("equipments", equpments.get(3) + "")
						.param("equipments", equpments.get(4) + ""))
				.andExpect(status().isBadRequest());
		
		
		this.mockMvc
		.perform(post(URL_PREFIX).contentType(contentType).sessionAttr("logedUser", u).content(object2)
				.param("contact", a.getContact())
				.param("description", a.getDescription())
				.param("rent_sale",false+"")
				.param("real_name", rls.getRealestateName())
				.param("real_type_id", rt.getId() + "")
				.param("real_cost", rls.getCost() + "")
				.param("real_area", rls.getArea() + "")
				.param("real_category_id", 9000+"")
				.param("real_heating", rls.isHeating() + "")
				.param("loc_street", l.getStreet())
				.param("loc_street_number", l.getStreetNumber() + "")
				.param("loc_region", l.getRegion())
				.param("loc_city", l.getCity())
				.param("loc_postal_code", l.getPostalCode() + "")
				.param("equipments", equpments.get(2) + "")
				.param("equipments", equpments.get(3) + "")
				.param("equipments", equpments.get(4) + ""))
		.andExpect(status().isBadRequest());
		
		
		rls.setArea(0);
		rls.setCost(0);
		rc = new RealestateCategory();
		rc.setId(1);
		rc.setCategoryName("Lot");
		String object3 = TestUtil.json(a);
		this.mockMvc
				.perform(post(URL_PREFIX).contentType(contentType).sessionAttr("logedUser", u).content(object3)
						.param("contact", a.getContact())
						.param("description", a.getDescription())
						.param("rent_sale",false+"")
						.param("real_name", rls.getRealestateName())
						.param("real_type_id", rt.getId() + "")
						.param("real_cost", rls.getCost() + "")
						.param("real_area", rls.getArea() + "")
						.param("real_category_id", rc.getId()+"")
						.param("real_heating", rls.isHeating() + "")
						.param("loc_street", l.getStreet())
						.param("loc_street_number", l.getStreetNumber() + "")
						.param("loc_region", l.getRegion())
						.param("loc_city", l.getCity())
						.param("loc_postal_code", l.getPostalCode() + "")
						.param("equipments", equpments.get(0) + "")
						.param("equipments", equpments.get(1) + "")
						.param("equipments", equpments.get(2) + "")
						.param("equipments", equpments.get(3) + "")
						.param("equipments", equpments.get(4) + ""))
				.andExpect(status().isBadRequest());
		
		rc = new RealestateCategory();
		rc.setId(3);
		rc.setCategoryName("Lot");
		rls.setArea(55);
		rls.setCost(123);
		String object4 = TestUtil.json(a);
		
		this.mockMvc
		.perform(post(URL_PREFIX).contentType(contentType).sessionAttr("logedUser", u).content(object4)
				.param("contact", a.getContact())
				.param("description", a.getDescription())
				.param("rent_sale",false+"")
				.param("real_name", rls.getRealestateName())
				.param("real_type_id", rt.getId() + "")
				.param("real_cost", rls.getCost() + "")
				.param("real_area", rls.getArea() + "")
				.param("real_category_id", rc.getId()+"")
				.param("real_heating", rls.isHeating() + "")
				.param("loc_street", l.getStreet())
				.param("loc_street_number", l.getStreetNumber() + "")
				.param("loc_region", l.getRegion())
				.param("loc_city", l.getCity())
				.param("loc_postal_code", l.getPostalCode() + "")
				.param("equipments", equpments.get(0) + "")
				.param("equipments", equpments.get(1) + "")
				.param("equipments", equpments.get(2) + "")
				.param("equipments", equpments.get(3) + "")
				.param("equipments", equpments.get(4) + ""))
		.andExpect(status().isBadRequest());
	}
	
	
	@Test
	@Transactional
	@Rollback(false)
	public void testUpdateAdvert() throws Exception{
		Role r = new Role();
		r.setId(2);
		r.setName("Regular user");

		User u = new User();
		u.setId(2);
		u.setEmail("doslicmm@live.com");
		u.setFirst_name("Mladen");
		u.setLast_name("Doslic");
		u.setPassword("123");
		u.setUser_rate(0);
		u.setRole(r);

		RealestateCategory rc = new RealestateCategory();
		rc.setId(1);
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
		rls.setRealestateName("ZUTA KUCA");
		rls.setCategory(rc);
		rls.setArea(40000);
		rls.setCost(123);
		rls.setHeating(true);
		rls.setLocation(l);

		Advert a = new Advert();
		a.setUser(u);
		a.setId(advert_id);
		a.setAdvert_rate(advert_rate);
		a.setContact(contact);
		a.setDescription(description);
		a.setIs_deleted(is_deleted);
		a.setIs_sold(is_sold);
		a.setRent_sale(rent_sale);
		
		List<Boolean> equpments = new ArrayList<Boolean>();
		equpments.add(false); equpments.add(true);  equpments.add(false); equpments.add(true); equpments.add(false); 
		String object = TestUtil.json(a);

		this.mockMvc
				.perform(put(URL_PREFIX).contentType(contentType).sessionAttr("logedUser", u).content(object)
						.param("advert_id", a.getId()+"")
						.param("contact", a.getContact())
						.param("description", a.getDescription())
						.param("rent_sale",false+"")
						.param("real_name", rls.getRealestateName())
						.param("real_type_id", rt.getId() + "")
						.param("real_cost", rls.getCost() + "")
						.param("real_area", rls.getArea() + "")
						.param("real_category_id", rc.getId() + "")
						.param("real_heating", rls.isHeating() + "")
						.param("loc_street", l.getStreet())
						.param("loc_street_number", l.getStreetNumber() + "")
						.param("loc_region", l.getRegion())
						.param("loc_city", l.getCity())
						.param("loc_postal_code", l.getPostalCode() + "")
						.param("equipments", equpments.get(0) + "")
						.param("equipments", equpments.get(1) + "")
						.param("equipments", equpments.get(2) + "")
						.param("equipments", equpments.get(3) + "")
						.param("equipments", equpments.get(4) + "")).andDo(CustomMockMvcResultHandlers.print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.contact").value(a.getContact()))
				.andExpect(jsonPath("$.description").value(a.getDescription()));
	
		String object2 = TestUtil.json(a);
		u.setId(1);
		u.setEmail("milossm94@hotmail.com");
		this.mockMvc
				.perform(put(URL_PREFIX).contentType(contentType).sessionAttr("logedUser", u).content(object2)
						.param("advert_id", a.getId()+"")
						.param("contact", a.getContact())
						.param("description", a.getDescription())
						.param("rent_sale",false+"")
						.param("real_name", rls.getRealestateName())
						.param("real_type_id", rt.getId() + "")
						.param("real_cost", rls.getCost() + "")
						.param("real_area", rls.getArea() + "")
						.param("real_category_id", rc.getId() + "")
						.param("real_heating", rls.isHeating() + "")
						.param("loc_street", l.getStreet())
						.param("loc_street_number", l.getStreetNumber() + "")
						.param("loc_region", l.getRegion())
						.param("loc_city", l.getCity())
						.param("loc_postal_code", l.getPostalCode() + "")
						.param("equipments", equpments.get(0) + "")
						.param("equipments", equpments.get(1) + "")
						.param("equipments", equpments.get(2) + "")
						.param("equipments", equpments.get(3) + "")
						.param("equipments", equpments.get(4) + "")).andDo(CustomMockMvcResultHandlers.print())
				.andExpect(status().isBadRequest());
		
		a.setIs_deleted(true);
		String object3 = TestUtil.json(a);
		this.mockMvc
		.perform(put(URL_PREFIX).contentType(contentType).sessionAttr("logedUser", u).content(object3)
				.param("advert_id", a.getId()+"")
				.param("contact", a.getContact())
				.param("description", a.getDescription())
				.param("rent_sale",false+"")
				.param("real_name", rls.getRealestateName())
				.param("real_type_id", rt.getId() + "")
				.param("real_cost", rls.getCost() + "")
				.param("real_area", rls.getArea() + "")
				.param("real_category_id", rc.getId() + "")
				.param("real_heating", rls.isHeating() + "")
				.param("loc_street", l.getStreet())
				.param("loc_street_number", l.getStreetNumber() + "")
				.param("loc_region", l.getRegion())
				.param("loc_city", l.getCity())
				.param("loc_postal_code", l.getPostalCode() + "")
				.param("equipments", equpments.get(0) + "")
				.param("equipments", equpments.get(1) + "")
				.param("equipments", equpments.get(2) + "")
				.param("equipments", equpments.get(3) + "")
				.param("equipments", equpments.get(4) + "")).andDo(CustomMockMvcResultHandlers.print())
		.andExpect(status().isBadRequest());
		
		a.setIs_deleted(false);
		String object4 = TestUtil.json(a);
		this.mockMvc
		.perform(put(URL_PREFIX).contentType(contentType).sessionAttr("logedUser", u).content(object4)
				.param("advert_id", a.getId()+"")
				.param("contact", a.getContact())
				.param("description", a.getDescription())
				.param("rent_sale",false+"")
				.param("real_name", rls.getRealestateName())
				.param("real_type_id", rt.getId() + "")
				.param("real_cost", rls.getCost() + "")
				.param("real_area", rls.getArea() + "")
				.param("real_category_id", 9999 + "")
				.param("real_heating", rls.isHeating() + "")
				.param("loc_street", l.getStreet())
				.param("loc_street_number", l.getStreetNumber() + "")
				.param("loc_region", l.getRegion())
				.param("loc_city", l.getCity())
				.param("loc_postal_code", l.getPostalCode() + "")
				.param("equipments", equpments.get(0) + "")
				.param("equipments", equpments.get(1) + "")
				.param("equipments", equpments.get(2) + "")
				.param("equipments", equpments.get(3) + "")
				.param("equipments", equpments.get(4) + "")).andDo(CustomMockMvcResultHandlers.print())
		.andExpect(status().isBadRequest());
		
		
		rc = new RealestateCategory();
		rc.setId(3);
		rc.setCategoryName("Lot");
		this.mockMvc
		.perform(post(URL_PREFIX).contentType(contentType).sessionAttr("logedUser", u).content(object4)
				.param("contact", a.getContact())
				.param("description", a.getDescription())
				.param("rent_sale",false+"")
				.param("real_name", rls.getRealestateName())
				.param("real_type_id", rt.getId() + "")
				.param("real_cost", rls.getCost() + "")
				.param("real_area", rls.getArea() + "")
				.param("real_category_id", rc.getId()+"")
				.param("real_heating", rls.isHeating() + "")
				.param("loc_street", l.getStreet())
				.param("loc_street_number", l.getStreetNumber() + "")
				.param("loc_region", l.getRegion())
				.param("loc_city", l.getCity())
				.param("loc_postal_code", l.getPostalCode() + "")
				.param("equipments", equpments.get(0) + "")
				.param("equipments", equpments.get(1) + "")
				.param("equipments", equpments.get(2) + "")
				.param("equipments", equpments.get(3) + "")
				.param("equipments", equpments.get(4) + ""))
		.andExpect(status().isBadRequest());
		
		this.mockMvc
		.perform(post(URL_PREFIX).contentType(contentType).sessionAttr("logedUser", u).content(object4)
				.param("contact", a.getContact())
				.param("description", a.getDescription())
				.param("rent_sale",false+"")
				.param("real_name", rls.getRealestateName())
				.param("real_type_id", rt.getId() + "")
				.param("real_cost", rls.getCost() + "")
				.param("real_area", rls.getArea() + "")
				.param("real_category_id", rc.getId()+"")
				.param("real_heating", rls.isHeating() + "")
				.param("loc_street", l.getStreet())
				.param("loc_street_number", l.getStreetNumber() + "")
				.param("loc_region", l.getRegion())
				.param("loc_city", l.getCity())
				.param("loc_postal_code", l.getPostalCode() + "")
				.param("equipments", equpments.get(2) + "")
				.param("equipments", equpments.get(3) + "")
				.param("equipments", equpments.get(4) + ""))
		.andExpect(status().isBadRequest());
	}
	
	
    public static class CustomMockMvcResultHandlers {

        public static ResultHandler print() {
            return new ConsolePrintingResultHandler();
        }


        /**
         * Have to copy this class from spring
         */
        private static class ConsolePrintingResultHandler extends PrintingResultHandler {

            public ConsolePrintingResultHandler() {
                super(new ResultValuePrinter() {

                    @Override
                    public void printHeading(String heading) {
                        System.out.println();
                        System.out.println(String.format("%20s:", heading));
                    }

                    @Override
                    public void printValue(String label, Object value) {
                        if (value != null && value.getClass().isArray()) {
                            value = CollectionUtils.arrayToList(value);
                        }
                        System.out.println(String.format("%20s = %s", label, value));
                    }


                });


            }

            @Override
            protected void printRequest(MockHttpServletRequest request) throws Exception {
                super.printRequest(request);
                getPrinter().printValue("Body", getContentAsString(request));
            }

            private String getContentAsString(MockHttpServletRequest request) throws IOException {
                BufferedReader reader = request.getReader();

                StringBuilder builder = new StringBuilder();
                String aux;

                while ((aux = reader.readLine()) != null) {
                    builder.append(aux);
                }

                return builder.toString();
            }
        }
    }
}

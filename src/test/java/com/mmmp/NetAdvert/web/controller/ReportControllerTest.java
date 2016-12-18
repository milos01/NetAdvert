package com.mmmp.NetAdvert.web.controller;

import static com.mmmp.NetAdvert.constants.LocationConstants.city;
import static com.mmmp.NetAdvert.constants.LocationConstants.postal_code;
import static com.mmmp.NetAdvert.constants.LocationConstants.region;
import static com.mmmp.NetAdvert.constants.LocationConstants.street;
import static com.mmmp.NetAdvert.constants.LocationConstants.street_number;
import static com.mmmp.NetAdvert.constants.ReportConstants.db_count_reports;
import static com.mmmp.NetAdvert.constants.ReportConstants.report_id;
import static com.mmmp.NetAdvert.constants.ReportConstants.text;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import com.mmmp.netadvert.model.Location;
import com.mmmp.netadvert.model.Realestate;
import com.mmmp.netadvert.model.RealestateCategory;
import com.mmmp.netadvert.model.RealestateType;
import com.mmmp.netadvert.model.Report;
import com.mmmp.netadvert.model.Role;
import com.mmmp.netadvert.model.User;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = NetAdvertApplication.class)
@WebIntegrationTest(randomPort = true)
@TestPropertySource(locations = "classpath:test.properties")
public class ReportControllerTest {

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

	@Test
	@Transactional
	@Rollback(true)
	public void testNewReport() throws Exception {
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
		a.setId(4);
		a.setUser(u);
		a.setAdvert_rate(2);
		a.setContact("sss");
		a.setDescription("haha");
		a.setCreated_at(new Date(123455));
		a.setExpire_date(new Date(123455));
		a.setIs_deleted(false);
		a.setIs_sold(false);
		a.setRealestate(rls);
		a.setRent_sale(false);
		a.setUpdated_at(new Date(123455));

		Report rep = new Report();
		rep.setAdvert(a);
		rep.setReportDescription(text);
		rep.setUser(u);
		rep.setVerified(0);
		String object = TestUtil.json(rep);

		this.mockMvc.perform(post(URL_PREFIX)
				.contentType(contentType).sessionAttr("logedUser", u).param("reportDescription", rep.getReportDescription()).param("advert_id", a.getId()+"").content(object)).andExpect(status().isOk());

		rep.setAdvert(null);
		String object2 = TestUtil.json(rep);
		this.mockMvc.perform(post(URL_PREFIX)
				.contentType(contentType).sessionAttr("logedUser", u).param("reportDescription", rep.getReportDescription()).param("advert_id", 111+"").content(object2)).andExpect(status().isInternalServerError());

		rep.setAdvert(a);
		rep.setReportDescription("");
		String object3 = TestUtil.json(rep);
		this.mockMvc.perform(post(URL_PREFIX)
				.contentType(contentType).sessionAttr("logedUser", u).param("reportDescription", "").param("advert_id", a.getId()+"").content(object3)).andExpect(status().isInternalServerError());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testUpdateReport() throws Exception{
		
		Role r = new Role();
		r.setId(3);
		r.setName("Verifier");

		User u = new User();
		u.setId(2);
		u.setEmail("doslicmm@live.com");
		u.setFirst_name("Mladen");
		u.setLast_name("Doslic");
		u.setPassword("123");
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
		rls.setCost(123);
		rls.setHeating(true);
		rls.setLocation(l);

		Advert a = new Advert();
		a.setId(4);
		a.setUser(u);
		a.setAdvert_rate(2);
		a.setContact("sss");
		a.setDescription("haha");
		a.setCreated_at(new Date(123455));
		a.setExpire_date(new Date(123455));
		a.setIs_deleted(false);
		a.setIs_sold(false);
		a.setRealestate(rls);
		a.setRent_sale(false);
		a.setUpdated_at(new Date(123455));

		Report rep = new Report();
		
		rep.setAdvert(a);
		rep.setReportDescription(text);
		rep.setUser(u);
		rep.setVerified(0);
		rep.setId(555);
		String object2 = TestUtil.json(rep);
		this.mockMvc.perform(put(URL_PREFIX+"/update")
				.contentType(contentType).sessionAttr("logedUser", u).param("report_id", rep.getId()+"").param("verify", 1+"").content(object2)).andExpect(status().isInternalServerError());

		rep.setId(1);
		rep.setVerified(1);
		String object3 = TestUtil.json(rep);
		this.mockMvc.perform(put(URL_PREFIX+"/update")
				.contentType(contentType).sessionAttr("logedUser", u).param("report_id", rep.getId()+"").param("verify", 1+"").content(object3)).andExpect(status().isInternalServerError());

		Role roleN = new Role();
		roleN.setId(2);
		roleN.setName("Regular user");
		u.setRole(roleN);
		rep.setId(2);
		rep.setVerified(0);
		String object4 = TestUtil.json(rep);
		this.mockMvc.perform(put(URL_PREFIX+"/update")
				.contentType(contentType).sessionAttr("logedUser", u).param("report_id", rep.getId()+"").param("verify", 1+"").content(object4)).andExpect(status().isForbidden());
		
		
		rep.setId(2);
		rep.setVerified(0);
		u.setRole(r);
		String object = TestUtil.json(rep);
		this.mockMvc.perform(put(URL_PREFIX+"/update")
				.contentType(contentType).sessionAttr("logedUser", u).param("report_id", rep.getId()+"").param("verify", 1+"").content(object)).andExpect(status().isOk());
		
	}
	

}

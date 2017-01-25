package com.mmmp.NetAdvert.web.controller;

import static com.mmmp.NetAdvert.constants.AdvertConstants.advert_id;
import static com.mmmp.NetAdvert.constants.AdvertConstants.db_advert_count;
import static com.mmmp.NetAdvert.constants.AdvertConstants.advert_rate;
import static com.mmmp.NetAdvert.constants.AdvertConstants.contact;
import static com.mmmp.NetAdvert.constants.AdvertConstants.created_at;
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
import static com.mmmp.NetAdvert.constants.LocationConstants.location_id;
import static com.mmmp.NetAdvert.constants.LocationConstants.street_number;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.hasItem;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.Principal;
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
import com.mmmp.netadvert.DTO.AdvertDTO;
import com.mmmp.netadvert.DTO.LocationDTO;
import com.mmmp.netadvert.DTO.RealestateCategoryDTO;
import com.mmmp.netadvert.DTO.RealestateDTO;
import com.mmmp.netadvert.DTO.RealestateTypeDTO;
import com.mmmp.netadvert.DTO.RoleDTO;
import com.mmmp.netadvert.DTO.UserDTO;
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
		.perform(get(URL_PREFIX).contentType(contentType).param("size", 2+"").param("page", "0").param("sort", "advertName, desc")).andExpect(status().isOk())
				.andExpect(jsonPath("$.content", hasSize(1)))
				.andExpect(jsonPath("$.content.[*].id").value(hasItem(advert_id)))
				.andExpect(jsonPath("$.content.[*].description").value(hasItem(description)))
				.andExpect(jsonPath("$.content.[*].contact").value(hasItem(contact)));
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testBuyAdvert() throws Exception{
		Role r = new Role();
        r.setId(2);
        r.setName("Regular user");

        User logUser = new User();
        logUser.setId(2);
        logUser.setEmail("milossm94@hotmail.com");
        logUser.setLast_name("Milos");
        logUser.setLast_name("Obradovic");
        logUser.setPassword("pass");
        logUser.setUser_rate(0);
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
		//rls.setCost(123);
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
		a.setDeleted(is_deleted);
		a.setIs_sold(is_sold);
		a.setRealestate(rls);
		a.setRent_sale(rent_sale);
		a.setUpdated_at(updated_at);
		
		Principal p =  new Principal() {
			
			@Override
			public String getName() {
				return logUser.getEmail();
			}
		};
		this.mockMvc
		.perform(put(URL_PREFIX +"/"+ 1000+"/buy").principal(p).contentType(contentType))
				.andExpect(status().isBadRequest());
        
		logUser.setEmail("xxx@gmail.com");
		this.mockMvc
		.perform(put(URL_PREFIX +"/"+ advert_id+"/buy").principal(p).contentType(contentType))
				.andExpect(status().isForbidden());
		
		logUser.setId(4);
		logUser.setEmail("milan@gmail.com");
		this.mockMvc
		.perform(put(URL_PREFIX +"/"+ advert_id+"/buy").principal(p).contentType(contentType))
				.andExpect(status().isOk());
		
		a.setIs_sold(true);
		logUser.setId(2);
		
		this.mockMvc
		.perform(put(URL_PREFIX +"/"+ advert_id+"/buy").principal(p).contentType(contentType))
				.andExpect(status().isBadRequest());
		
		a.setDeleted(true);
		a.setIs_sold(false);
		this.mockMvc
		.perform(put(URL_PREFIX +"/"+ advert_id+"/buy").principal(p).contentType(contentType))
				.andExpect(status().isBadRequest());
		
		logUser.setId(2);
		a.setDeleted(false);
		a.setIs_sold(false);
		this.mockMvc
		.perform(put(URL_PREFIX +"/"+ advert_id+"/buy").principal(p).contentType(contentType))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testCreateAdvert() throws Exception{
		RoleDTO r = new RoleDTO();
		r.setId(2);
		r.setName("Regular user");

        UserDTO logUser = new UserDTO();
        logUser.setId(2);
        logUser.setEmail("milossm94@hotmail.com");
        logUser.setLast_name("Milos");
        logUser.setLast_name("Obradovic");
        logUser.setPassword("pass");
        logUser.setUser_rate(0);
        logUser.setRole(r);

		RealestateCategoryDTO rc = new RealestateCategoryDTO();
		rc.setRealestateCategoryId(1);
		rc.setCategoryName("Residential buildings");

		RealestateTypeDTO rt = new RealestateTypeDTO();
		rt.setRealestateTypeId(1);
		rt.setTypeName("House");

		LocationDTO l = new LocationDTO();
		l.setLocationId(location_id);
		l.setCity(city);
		l.setPostalCode(postal_code);
		l.setRegion(region);
		l.setStreet(street);
		l.setStreetNumber(street_number);

		RealestateDTO rls = new RealestateDTO();
		rls.setRealestateId(1);
		rls.setRealestateName("ZUTA KUCA");
		rls.setCategory(rc);
		rls.setArea(40000);
		rls.setHeating(true);
		rls.setLocation(l);

		List<Boolean> equpments = new ArrayList<Boolean>();
		equpments.add(false); equpments.add(true);  equpments.add(true); equpments.add(false); equpments.add(true); 
		rls.setType(rt);
		rls.setEquipments(equpments);
		
		List<Boolean> equpments2 = new ArrayList<Boolean>();
		equpments2 = new ArrayList<Boolean>();
		equpments2.add(true); equpments2.add(false); equpments2.add(true); 
		
		
		AdvertDTO a = new AdvertDTO();
		a.setAdvertId(3);
		a.setAdvertUser(logUser);
		a.setAdvert_rate(advert_rate);
		a.setContact(contact);
		a.setDescription(description);
		a.setIs_deleted(is_deleted);
		a.setIs_sold(is_sold);
		a.setRent_sale(rent_sale);
		a.setRealestate(rls);
		a.setAdvertName("aaa");
		a.setCost(22222);
		a.setUpdated_at(updated_at);
		a.setExpire_date(expire_date);
		a.setCreated_at(created_at);
		Principal p =  new Principal() {
			
			@Override
			public String getName() {
				return logUser.getEmail();
			}
		};
		
		String object = TestUtil.json(a);
		System.out.println(object);
		this.mockMvc
				.perform(post(URL_PREFIX)
						.contentType(contentType)
						.content(object)
						.principal(p))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.contact").value(a.getContact()))
				.andExpect(jsonPath("$.description").value(a.getDescription()));
		
		a.getRealestate().getCategory().setRealestateCategoryId(999);
		String object2 = TestUtil.json(a);
		this.mockMvc
				.perform(post(URL_PREFIX).contentType(contentType).principal(p).content(object2)
						).andExpect(status().isBadRequest());
		
		a.getRealestate().getCategory().setRealestateCategoryId(1);
		a.getRealestate().setEquipments(equpments2);
		String object3 = TestUtil.json(a);
		this.mockMvc
		.perform(post(URL_PREFIX).contentType(contentType).principal(p).content(object3)
	).andExpect(status().isBadRequest());
		
		a.getRealestate().setEquipments(equpments);
		rls.setArea(0);
		String object4 = TestUtil.json(a);
		this.mockMvc
				.perform(post(URL_PREFIX).contentType(contentType).principal(p).content(object4)
				).andExpect(status().isBadRequest());
		
		a.getRealestate().setArea(44);
		a.getRealestate().getLocation().setCity(null);
		String object5 = TestUtil.json(a);
		this.mockMvc
		.perform(post(URL_PREFIX).contentType(contentType).principal(p).content(object5)
		).andExpect(status().isBadRequest());
	}
	
	
	@Test
	@Transactional
	@Rollback(true)
	public void testUpdateAdvert() throws Exception{

		RoleDTO r = new RoleDTO();
		r.setId(2);
		r.setName("Regular user");

        UserDTO logUser = new UserDTO();
        logUser.setId(2);
        logUser.setEmail("milossm94@hotmail.com");
        logUser.setLast_name("Milos");
        logUser.setLast_name("Obradovic");
        logUser.setPassword("pass");
        logUser.setUser_rate(0);
        logUser.setRole(r);

		RealestateCategoryDTO rc = new RealestateCategoryDTO();
		rc.setRealestateCategoryId(1);
		rc.setCategoryName("Residential buildings");

		RealestateTypeDTO rt = new RealestateTypeDTO();
		rt.setRealestateTypeId(1);
		rt.setTypeName("House");

		LocationDTO l = new LocationDTO();
		l.setLocationId(location_id);
		l.setCity(city);
		l.setPostalCode(postal_code);
		l.setRegion(region);
		l.setStreet(street);
		l.setStreetNumber(street_number);

		RealestateDTO rls = new RealestateDTO();
		rls.setRealestateId(1);
		rls.setRealestateName("ZUTA KUCA");
		rls.setCategory(rc);
		rls.setArea(40000);
		rls.setHeating(true);
		rls.setLocation(l);

		List<Boolean> equpments = new ArrayList<Boolean>();
		equpments.add(false); equpments.add(true);  equpments.add(true); equpments.add(false); equpments.add(true); 
		rls.setType(rt);
		rls.setEquipments(equpments);
		
		AdvertDTO a = new AdvertDTO();
		a.setAdvertId(1);
		a.setAdvertUser(logUser);
		a.setAdvert_rate(advert_rate);
		a.setContact(contact);
		a.setDescription(description);
		a.setIs_deleted(is_deleted);
		a.setIs_sold(is_sold);
		a.setRent_sale(rent_sale);
		a.setRealestate(rls);
		a.setAdvertName("aaa");
		a.setCost(22222);
		a.setUpdated_at(updated_at);
		a.setExpire_date(expire_date);
		a.setCreated_at(created_at);
		Principal p =  new Principal() {
			
			@Override
			public String getName() {
				return logUser.getEmail();
			}
		};
		
		String object = TestUtil.json(a);

		this.mockMvc
				.perform(put(URL_PREFIX).contentType(contentType).principal(p).content(object)
				.param("equipments", equpments.get(4) + ""))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.contact").value(a.getContact()))
				.andExpect(jsonPath("$.description").value(a.getDescription()));
	
		String object2 = TestUtil.json(a);
		logUser.setId(1);
		logUser.setEmail("milan@gmail.com");
		
		this.mockMvc
				.perform(put(URL_PREFIX).contentType(contentType).principal(p).content(object2)
		).andExpect(status().isBadRequest());
		
		logUser.setEmail("milossm94@hotmail.com");
		a.setAdvertId(2);
		String object3 = TestUtil.json(a);
		this.mockMvc
		.perform(put(URL_PREFIX).contentType(contentType).principal(p).content(object3)
	).andExpect(status().isBadRequest());
		
		a.setAdvertId(1);
		String object4 = TestUtil.json(a);
		logUser.setEmail("xxx@gmail.com");
		this.mockMvc
		.perform(put(URL_PREFIX).contentType(contentType).principal(p).content(object4)
		).andExpect(status().isForbidden());
		
		logUser.setEmail("milossm94@hotmail.com");
	
		a.getRealestate().setRealestateId(2);
		String object5 = TestUtil.json(a);
		this.mockMvc
		.perform(put(URL_PREFIX).contentType(contentType).principal(p).content(object5)
		).andExpect(status().isBadRequest());
		
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
        logUser.setEmail("milossm94@hotmail.com");
        logUser.setLast_name("Milos");
        logUser.setLast_name("Obradovic");
        logUser.setPassword("pass");
        logUser.setUser_rate(0);
        logUser.setRole(r);
        
        
		Principal p =  new Principal() {
			
			@Override
			public String getName() {
				return logUser.getEmail();
			}
		};
        
    	mockMvc
		.perform(delete(URL_PREFIX + "/" + 50).principal(p).contentType(contentType)).andExpect(status().isInternalServerError());
		
        
    	mockMvc
		.perform(delete(URL_PREFIX + "/" + advert_id).principal(p).contentType(contentType)).andExpect(status().isOk());
	
    	logUser.setEmail("xxxx@hotmail.com");
      	mockMvc
    		.perform(delete(URL_PREFIX + "/" + advert_id).principal(p).contentType(contentType)).andExpect(status().isForbidden());
    	
	}
	
	@Test
	public void testGetAdvertMainPicture() throws Exception{
		this.mockMvc
		.perform(get(URL_PREFIX + "/" + 50+ "/"+"mainPicture").contentType(contentType)).andExpect(status().isBadRequest());
		
		this.mockMvc
		.perform(get(URL_PREFIX + "/" + 1+ "/"+"mainPicture").contentType(contentType)).andExpect(status().isOk());
		
	}
	
	@Test
	public void testAdvertPictures() throws Exception{
		this.mockMvc
		.perform(get(URL_PREFIX + "/" + 1+ "/"+"pictures").contentType(contentType)).andExpect(status().isOk());
	}
	
	@Test
	public void testGetAllAdvertsOfUser() throws Exception{
		this.mockMvc
		.perform(get(URL_PREFIX + "/" + "user"+ "/"+2).contentType(contentType)).andExpect(jsonPath("$", hasSize(2))).andExpect(status().isOk());
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

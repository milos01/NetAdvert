package com.mmmp.NetAdvert.web.controller;

import static com.mmmp.NetAdvert.constants.AdvertConstants.advert_id;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.annotation.PostConstruct;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.PrintingResultHandler;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.WebApplicationContext;

import com.mmmp.netadvert.NetAdvertApplication;
import com.mmmp.netadvert.model.Role;
import com.mmmp.netadvert.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = NetAdvertApplication.class)
@WebIntegrationTest(randomPort = true)
@TestPropertySource(locations="classpath:test.properties")
public class ImageControllerTest {
	
	private static final String URL_PREFIX = "/api";
	
	public String image_name;

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
	@Rollback(true)
	public void testUploadImage() throws Exception { 
		Role r = new Role();
        r.setId(2);
        r.setName("Regular user");

        User logUser = new User();
        logUser.setId(8);
        logUser.setEmail("milossm94@hotmail.com");
        logUser.setLast_name("Milos");
        logUser.setLast_name("Obradovic");
        logUser.setPassword("pass");
        logUser.setUser_rate(1);
        logUser.setRole(r);
        
        String ss = System.getenv("SystemDrive") + File.separator + "NetAdvertTestImages";
		if (Files.notExists(Paths.get(ss))){
			try {
				Files.createDirectories(Paths.get(ss));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		File f = new File(ss+File.separator+"Tulips.jpg");
		FileInputStream fi1 = new FileInputStream(f);
		MockMultipartFile fstmp = new MockMultipartFile("file", f.getName(), "multipart/form-data",fi1);
		MvcResult result = this.mockMvc
		.perform(MockMvcRequestBuilders.fileUpload(URL_PREFIX +"/upload").file(fstmp).sessionAttr("logedUser", logUser).param("realestate", "1").param("is_profile", "false"))
				.andExpect(status().isOk()).andReturn();
		
		String content = result.getResponse().getContentAsString();
        this.image_name = content.split("\"")[5];
        this.mockMvc
		.perform(MockMvcRequestBuilders.fileUpload(URL_PREFIX +"/upload").file(fstmp).sessionAttr("logedUser", logUser).param("realestate", "999").param("is_profile", "false"))
				.andExpect(status().isBadRequest());
        
        r.setId(1);
        logUser.setRole(r);
        this.mockMvc
		.perform(MockMvcRequestBuilders.fileUpload(URL_PREFIX +"/upload").file(fstmp).sessionAttr("logedUser", logUser).param("realestate", "999").param("is_profile", "false"))
				.andExpect(status().isBadRequest());
        
        f=new File(ss+File.separator+"text.txt");
        fi1 = new FileInputStream(f);
		fstmp = new MockMultipartFile("file", f.getName(), "multipart/form-data",fi1);
        this.mockMvc
		.perform(MockMvcRequestBuilders.fileUpload(URL_PREFIX +"/upload").file(fstmp).sessionAttr("logedUser", logUser).param("realestate", "1").param("is_profile", "false"))
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

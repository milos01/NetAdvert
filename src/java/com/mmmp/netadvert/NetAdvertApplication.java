package com.mmmp.netadvert;

import com.mmmp.netadvert.config.CsrfHeaderFilter;
import com.mmmp.netadvert.config.InterceptorConfig;
import com.mmmp.netadvert.service.SecurityUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.annotation.Order;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@SpringBootApplication
//@ImportResource("classpath:/spring/spring-config.xml")
public class NetAdvertApplication{



	@Autowired
	private SecurityUserDetailService userDetailsService;

	@Configuration
	@EnableWebSecurity
	@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
	protected class SecurityConfiguration extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(AuthenticationManagerBuilder auth)
				throws Exception {
			auth.userDetailsService(userDetailsService);
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
					.httpBasic()
					.and()
					.authorizeRequests()
					.antMatchers("/api/advert", "/api/advert/{\\d+}", "/api/advert/{\\d+}/mainPicture", "/img/gallery/**","/font-awesome/**","/core/index.html", "/core/views/login.html", "/core/views/home.html", "/core/views/**" , "/", "/core/scripts/**", "/bower_components/**", "/node-modules/**", "/", "/css/**", "/core/plugins/**", "/js/**","/node_modules/**","/api/comment/advert/{\\d+}").permitAll()
					.anyRequest().authenticated()
					.and()
					.addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class)
					.csrf().csrfTokenRepository(csrfTokenRepository());
		}
	}

	private static CsrfTokenRepository csrfTokenRepository() {
		HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
		repository.setHeaderName("X-XSRF-TOKEN");
		return repository;
	}

	@Bean
	public HibernateJpaSessionFactoryBean sessionFactory() {
		return new HibernateJpaSessionFactoryBean();
	}
	public static void main(String[] args) {
		SpringApplication.run(new Object[]{NetAdvertApplication.class, InterceptorConfig.class}, args);
	}
}

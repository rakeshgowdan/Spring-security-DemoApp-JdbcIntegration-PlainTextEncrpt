package com.rakesh.springsecurity.demo.config;




import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;




@Configuration
@EnableWebSecurity
public class DemoSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private DataSource secureDS;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(secureDS);
	}
	
	/*
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		UserBuilder users=User.withDefaultPasswordEncoder();
		auth.inMemoryAuthentication()
		.withUser(users.username("rakesh").password("1234").roles("Employee","ADMIN"))
		.withUser(users.username("Manager").password("1234").roles("Employee","Manager"))
		.withUser(users.username("Employee").password("1234").roles("Employee"));
		
	}
*/
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/").hasRole("EMPLOYEE")
		.antMatchers("/leaders/**").hasRole("MANGER")
		.antMatchers("/sysadmin/**").hasRole("ADMIN")
		.and().formLogin().loginPage("/showLoginPage")
		.loginProcessingUrl("/authenticateTheUser")
		.permitAll()
		.and()
		.logout().permitAll()
		.and()
		.exceptionHandling()
		.accessDeniedPage("/access-denied")
		;
	}

	
	
}

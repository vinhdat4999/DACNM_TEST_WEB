package com.blas.fish.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.blas.fish.authentication.AuthenticationService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	AuthenticationService authenticationService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(authenticationService);

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable();

		http.authorizeRequests()
				.antMatchers("/admin/**")// 
				.access("hasAnyRole('ROLE_ADMIN')");
		http.authorizeRequests().antMatchers("/admin/login").permitAll();
		//http.authorizeRequests().antMatchers("").access("hasRole('ROLE_ADMIN')");

		http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");

		http.authorizeRequests().and().formLogin()//

				.loginProcessingUrl("/j_spring_security_check") // Submit URL
				.loginPage("/login")//
				.defaultSuccessUrl("/admin/order-list")//
				.failureUrl("/login?error=true")//
				.usernameParameter("username")//
				.passwordParameter("password").and().logout().logoutUrl("/admin/logout").logoutSuccessUrl("/admin/order-list");

	}
}

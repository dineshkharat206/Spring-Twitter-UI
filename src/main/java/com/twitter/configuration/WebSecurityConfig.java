package com.twitter.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebSecurityConfig implements WebMvcConfigurer{
	
	@Override
	 public void addViewControllers(ViewControllerRegistry registry) {
		 registry.addViewController("/login").setViewName("login");
		 registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
	 }
	
	@Configuration
	protected static class ApplicationSecurity extends WebSecurityConfigurerAdapter{
		
		
		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception{
			auth.inMemoryAuthentication()
			.withUser("user1").password("{noop}user1").authorities("user_role").and()
			.withUser("user2").password("{noop}user2").authorities("user_role");
		}
		
		 @Override
		    public void configure(WebSecurity web) throws Exception {
		        web
		            .ignoring()
		            .antMatchers("/h2-ui/**");
		    }
		 
		 
		 @Override
		protected void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests()
				.anyRequest().fullyAuthenticated()
				.and()
				.formLogin().loginPage("/login").failureUrl("/login?error").permitAll()
				.and()
				.logout().permitAll();
		}
		 
		
	}

}

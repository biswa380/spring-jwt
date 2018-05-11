package com.biswa.springjwt.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.biswa.springjwt.security.RestAuthenticationEntryPoint;
import com.biswa.springjwt.security.TokenAuthenticationFilter;
import com.biswa.springjwt.security.TokenHelper;
import com.biswa.springjwt.services.CustomUserDetailsService;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.resource.PathResourceResolver;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Autowired
    private CustomUserDetailsService jwtUserDetailsService;

    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    @Autowired
    public void configureGlobal( AuthenticationManagerBuilder auth ) throws Exception {
        auth.userDetailsService( jwtUserDetailsService )
            .passwordEncoder( passwordEncoder() );
    }
    
    @Autowired
    TokenHelper tokenHelper;
    
    
    //Configured to allow specific requests
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        
                .sessionManagement().sessionCreationPolicy( SessionCreationPolicy.STATELESS ).and()
                .exceptionHandling().authenticationEntryPoint( restAuthenticationEntryPoint ).and()
                .authorizeRequests()
                .antMatchers("/index.html", "/home.html", "/login.html", "/","/login", "/resources/**").permitAll()
                .antMatchers("/auth/**").permitAll()
                .anyRequest().authenticated().and()
                .addFilterBefore(new TokenAuthenticationFilter(tokenHelper, jwtUserDetailsService), BasicAuthenticationFilter.class);

        http.csrf().disable();
    }
    
    @Override
    public void configure(WebSecurity web) throws Exception {
        // TokenAuthenticationFilter will ignore the below paths
        web.ignoring().antMatchers(
                HttpMethod.POST,
                "/auth/login"
        );
        web.ignoring().antMatchers("/*.css");
		web.ignoring().antMatchers("/*.js");
		web.ignoring().antMatchers("/*.html");
		web.ignoring().antMatchers("/*.woff2");
		web.ignoring().antMatchers("/assets/*");
		web.ignoring().antMatchers("/*.jpg");
		web.ignoring().antMatchers("/*.ttf");

    }
}

@Component
class WebMvcConfig extends WebMvcConfigurerAdapter {

	   @Override
	   public void addResourceHandlers(ResourceHandlerRegistry registry) {

	     registry.addResourceHandler("/**/*")
	       .addResourceLocations("classpath:/static/")
	       .resourceChain(true)
	       .addResolver(new PathResourceResolver() {
	           @Override
	           protected Resource getResource(String resourcePath,
	               Resource location) throws IOException {
	               Resource requestedResource = location.createRelative(resourcePath);
	               return requestedResource.exists() && requestedResource.isReadable() ? requestedResource
	               : new ClassPathResource("/static/index.html");
	           }
	       });
	   }

}


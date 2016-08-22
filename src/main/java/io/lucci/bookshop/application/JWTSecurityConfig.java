 package io.lucci.bookshop.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

@Configuration
@Profile("security-jwt")
@ComponentScan({ "io.lucci.bookshop.security", "io.lucci.bookshop.security.jwt" })
@EnableWebSecurity

/* This annotation (@EnableGlobalMethodSecurity) provides AOP security on methods 
 * some of annotation it will enable are PreAuthorize PostAuthorize also 
 * it has support for JSR-250 
 */
@EnableGlobalMethodSecurity(prePostEnabled=true, jsr250Enabled = true)
public class JWTSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
    private UserDetailsService userDetailsService;
	
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new StandardPasswordEncoder();
    }
	
    /*
     * configure the authentication mechanism. (db, in-memory, etc)
     */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.userDetailsService(userDetailsService)
			.passwordEncoder(passwordEncoder());
	}

	/*
	 * configure the security at resource level
	 */
	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.anyRequest().authenticated()
			.and().httpBasic()
			.and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.NEVER)
			.and().csrf().disable();
	}
	
	/*
	 * configure the security at application level. Defines the secure and the pulbic urls
	 */
    @Override
    public void configure(WebSecurity web) throws Exception {

        web.ignoring()
			.antMatchers("/api/register")
			.antMatchers("/api/activate")
			.antMatchers("/api/lostpassword")
			.antMatchers("/api/resetpassword");
    }
    
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}

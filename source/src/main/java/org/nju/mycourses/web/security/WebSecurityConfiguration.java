package org.nju.mycourses.web.security;

import org.nju.mycourses.data.LogDAO;
import org.nju.mycourses.data.UserDAO;
import org.nju.mycourses.web.security.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.nju.mycourses.web.security.WebSecurityConstants.ADMIN_AUTHORITY;
import static org.nju.mycourses.web.security.WebSecurityConstants.TEACHER_AUTHORITY;

@EnableWebSecurity
@EnableGlobalMethodSecurity(jsr250Enabled = true)
public class WebSecurityConfiguration {
//	@Configuration
//	@Order(Ordered.LOWEST_PRECEDENCE)
//	public static class ApiWebSecurityConfiguration extends WebSecurityConfigurerAdapter {
//		@Override
//		protected void configure(HttpSecurity http) throws Exception {
//			http.antMatcher("/api/**")
//					.authorizeRequests()
//					.anyRequest().authenticated()
//					.and()
//					.httpBasic()
//					.and()
//					.csrf().disable()
//					.cors();
//		}
//	}

	@Configuration
	public static class DefaultWebSecurityConfiguration extends WebSecurityConfigurerAdapter {
		private final UserDetailsService userDetailsService;
//		private final LoginRedirectHandler loginRedirectHandler;
		@Autowired
		private UserDAO userDAO;
		@Autowired
		private LogDAO logDAO;
		@Autowired
		public DefaultWebSecurityConfiguration(UserDetailsServiceImpl userDetailsService) {
			this.userDetailsService = userDetailsService;
//			this.loginRedirectHandler = loginRedirectHandler;
		}
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			JwtLoginFilter loginFilter = new JwtLoginFilter(authenticationManager());
        	loginFilter.setUserDAO(userDAO);
			loginFilter.setLogDAO(logDAO);
        	loginFilter.setAuthenticationFailureHandler(
                new AuthFailHandler());
        	loginFilter.setRequiresAuthenticationRequestMatcher(
                new AntPathRequestMatcher("/api/login", "POST"));
			JwtAuthenticationFilter jwtAuthenticationFilter =
					new JwtAuthenticationFilter(authenticationManager(),
							userDetailsService, new RestAuthorEntry());
			http.headers().frameOptions().disable();
			http.authorizeRequests()
					.antMatchers("/api/admin/register","/api/student/register","/api/teacher/register","/api/login","/h2-console/**", "/internal/**", "/webjars/**", "/js/**", "/css/**", "/favicon.ico").permitAll()
			 		.antMatchers("/file/**").hasAnyAuthority(TEACHER_AUTHORITY.getAuthority(), ADMIN_AUTHORITY.getAuthority())
					.antMatchers("/api/**").authenticated()
					.and()
					.addFilter(loginFilter)
					.addFilter(jwtAuthenticationFilter)
//					.and()
//					.formLogin().loginPage("/login.html").defaultSuccessUrl("/").failureHandler(loginRedirectHandler).permitAll()
//					.and()
//					.logout().logoutUrl("/logout.html").logoutSuccessHandler(loginRedirectHandler).permitAll()
//					.and()
//					.rememberMe().rememberMeParameter("remember").rememberMeCookieName("REMEMBER").userDetailsService(userDetailsService)
					.cors()
					.and()
					.csrf().disable()
			;
		}
	}
}

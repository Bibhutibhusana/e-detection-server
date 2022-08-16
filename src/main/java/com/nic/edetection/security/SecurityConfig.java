//package com.nic.edetection.security;
//
//import java.util.Arrays;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//	//private final UserDetailsService userDetailsService = null;
//	//private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
////
////	@Override
////	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
////		
////		//auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
////	}
//
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.cors().and().csrf().disable()
//		.authorizeHttpRequests()
//		.antMatchers("/api/v1/getByUserIdAndPassword/").hasRole("admin").anyRequest()
//		.authenticated()
//		.and()
//		.formLogin();
////		super.configure(http);
//	}
//	
////	@Bean
////    CorsConfigurationSource corsConfigurationSource() {
////        CorsConfiguration configuration = new CorsConfiguration();
////        configuration.setAllowedOrigins(Arrays.asList("*"));
////        configuration.setAllowedMethods(Arrays.asList("*"));
////        configuration.setAllowedHeaders(Arrays.asList("*"));
////        configuration.setAllowCredentials(true);
////        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
////        source.registerCorsConfiguration("/**", configuration);
////        return source;
////    }
//}
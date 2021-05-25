package org.callservice.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
//for method like payment use @Secured("ADMIN")
//@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService convertRole;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/user/**").authenticated()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .and()
                .formLogin()
                .and()
                .logout().logoutSuccessUrl("/");
    }

//  //  In-Memory
//    @Bean
//    public UserDetailsService users() {
////        UserDetails user = User.builder()
////                .username("user")
////                .password("{bcrypt}$2y$12$0./a1TvfPwxj.OTlQLF1HONCpHXHtrcrn7CR5sPE7UHSWLbkPjxZG")
////                .roles("USER")
////                .build();
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password("{bcrypt}$2y$12$0./a1TvfPwxj.OTlQLF1HONCpHXHtrcrn7CR5sPE7UHSWLbkPjxZG")
//                .roles("ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(admin);
//    }


//    @Autowired
//    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(users()).passwordEncoder(passwordEncoder());
//    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(convertRole);
        return authenticationProvider;
    }
}

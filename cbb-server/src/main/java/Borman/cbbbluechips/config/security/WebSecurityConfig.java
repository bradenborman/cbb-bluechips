package Borman.cbbbluechips.config.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.servlet.http.Cookie;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new PlainTextPasswordEncoder());
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();//Used for auto login of create user
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        logger.info("Setting up security");
        http.csrf().disable();

        http.authorizeRequests()
                .antMatchers("/login", "/user/login", "/user/logout")
                .permitAll();

        http.authorizeRequests()
                .antMatchers("/admin", "/admin/**")
                .hasAnyAuthority("CBB_ADMIN");


        //TODO add "/api"
        http.authorizeRequests()
                .antMatchers(
                        "/",
                        "/portfolio",
                        "/leaderboard",
                        "/market",
                        "/transactions",
                        "/settings",
                        "/settings/**",
                        "/comments",
                        "/comments/**",
                        "/trade/**",
                        "/trade-action/**",
                        "/data/**",
                        "/group/**",
                        "/transactions",
                        "/transactions/**"
                )
                .hasAnyAuthority("CBB_USER");

        http.authorizeRequests()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/user/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .successHandler((req, resp, auth) -> {
                    logger.info("GRANTED");
                    resp.setStatus(HttpStatus.OK.value());
                    Cookie frontEndLoggedInFlag = new Cookie("_live", "true");
                    frontEndLoggedInFlag.setMaxAge(10000000);
                    frontEndLoggedInFlag.setPath("/");
                    resp.addCookie(frontEndLoggedInFlag);
                }) // success authentication
                .failureHandler((req, resp, ex) -> {
                    logger.info("FORBIDDEN");
                    resp.setStatus(HttpStatus.FORBIDDEN.value());
                })
                .and()
                .logout()
                .logoutUrl("/user/logout")
                .deleteCookies("JSESSIONID", "remember-me-auto-login", "_live")
                .logoutSuccessUrl("/login");

        http.rememberMe()
                .alwaysRemember(true)
                .key("1063")
                .rememberMeCookieName("remember-me-auto-login")
                .tokenValiditySeconds(10000000);

    }

}
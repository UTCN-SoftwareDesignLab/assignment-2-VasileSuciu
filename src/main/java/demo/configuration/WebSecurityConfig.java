package demo.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private AuthSuccessHandler authSuccessHandler;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception
    {
        auth
                .jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("select user.username,user.password,true from user where username=?")
                .authoritiesByUsernameQuery("select username,roles from user where username=?")
                .rolePrefix("ROLE_")
                .passwordEncoder(new BCryptPasswordEncoder());
    }
    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception
    {
        httpSecurity
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/accessDenied").permitAll()
                .antMatchers("/employee/**","/bookAPI/**").hasRole("employee")
                .antMatchers("/**").hasRole("administrator")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .successHandler(authSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler);
    }
    @Override
    public void configure(WebSecurity webSecurity) throws Exception
    {
        webSecurity
                .ignoring()
                .antMatchers("/resources/**");
    }
    /*
    private AuthenticationSuccessHandler getAuthenticationSuccessHandler()
    {
        return new AuthSuccessHandler();
    }

    private AuthenticationFailureHandler getAuthenticationFailureHandler()
    {
        return new AuthFailureHandler();
    }
    */

}

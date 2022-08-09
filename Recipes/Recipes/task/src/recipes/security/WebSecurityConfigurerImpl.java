package recipes.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class WebSecurityConfigurerImpl extends WebSecurityConfigurerAdapter {
    @Autowired
    UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(getEncoder());

        auth
                .inMemoryAuthentication()
                .withUser("Admin").password("Admin").roles("ADMIN")
                .and().passwordEncoder(getEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/register").permitAll()
                .antMatchers("/actuator/shutdown").permitAll() //for testing reasons
                .mvcMatchers("/actuator/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .csrf().disable().headers().frameOptions().disable() // for testing via Postman
                .and()
                .httpBasic();

        http.csrf().disable().headers().frameOptions().disable();
    }

    @Bean()
    public PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }
}

package by.itechart.flowerty.configuration;

import by.itechart.flowerty.security.CsrfHeaderFilter;
import by.itechart.flowerty.security.CustomAuthenticationProvider;
import by.itechart.flowerty.security.TokenBasedRememberMeServices;
import by.itechart.flowerty.security.handler.AuthFailure;
import by.itechart.flowerty.security.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Rostislav on 26-Mar-15
 */

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthFailure authFailure;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .authenticationProvider(authenticator())
        ;
    }

    @Bean
    public AuthenticationProvider authenticator() {
        return new CustomAuthenticationProvider();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Transactional
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()

                .antMatchers("/user/profile")
                .authenticated()
                .antMatchers("/user/role/**")
                .access("hasAnyRole('ROLE_ORDERS_MANAGER', 'ROLE_DELIVERY_MANAGER', 'ROLE_ORDERS_PROCESSOR', 'ROLE_SUPERVISOR')")
                .antMatchers("/user/**")
                .access("hasRole('ROLE_ADMIN')")

                .antMatchers("/contact/partial-search/**")
                .access("hasAnyRole('ROLE_ADMIN', 'ROLE_ORDERS_MANAGER')")
                .antMatchers("/contact/**")
                .access("hasAnyRole('ROLE_SUPERVISOR', 'ROLE_ORDERS_MANAGER')")

                .antMatchers("/order/save")
                .access("hasRole('ROLE_ORDERS_MANAGER')")
                .antMatchers("/order/create/bundle")
                .access("hasRole('ROLE_ORDERS_MANAGER')")
                .antMatchers("/order/**")
                .access("hasAnyRole('ROLE_ORDERS_MANAGER', 'ROLE_DELIVERY_MANAGER', 'ROLE_ORDERS_PROCESSOR', 'ROLE_SUPERVISOR')")

                .antMatchers("/goods/add")
                .access("hasRole('ROLE_ADMIN')")
            .and()
                .rememberMe()
                .rememberMeServices(rememberMeServices())
                .key(KEY)
            .and()
                .formLogin()
                .loginPage("/#/login")
                .loginProcessingUrl("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/login")
                .failureHandler(authFailure)
            .and()
                .logout()
            .and()
                .csrf()
                .csrfTokenRepository(csrfTokenRepository())
            .and()
                .addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class)
        ;
    }

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    private final String KEY = "e9862d10db8e7de7877e9d28ef8153b4f0e209b8";

    @Bean
    public RememberMeServices rememberMeServices() {
        TokenBasedRememberMeServices services = new TokenBasedRememberMeServices(KEY, userDetailsService);
        services.setAlwaysRemember(true);
        return services;
    }

    @Bean
    public CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-XSRF-TOKEN");
        return repository;
    }
}
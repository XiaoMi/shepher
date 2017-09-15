package com.xiaomi.shepher.configuration;

import com.xiaomi.shepher.util.ShepherConstants;
import org.jasig.cas.client.authentication.AuthenticationFilter;
import org.jasig.cas.client.validation.Cas20ProxyReceivingTicketValidationFilter;
import org.jasig.cas.client.validation.Cas20ServiceTicketValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configures login filters of CAS and LDAP.
 *
 * Created by zhangpeng on 16-11-15.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${server.login.type}")
    private String loginType;
    @Value("${cas.server.url.prefix}")
    private String casServerUrlPrefix;
    @Value("${cas.login.url}")
    private String casServerLoginUrl;
    @Value("${server.url}")
    private String serverUrl;
    @Value("${ldap.url}")
    private String ldapUrl;
    @Value("${ldap.password}")
    private String ldapPassword;
    @Value("${ldap.dn}")
    private String ldapDn;

    @Value("${demo.admin.name}")
    private String demoAdminName;
    @Value("${demo.admin.password}")
    private String demoAdminPassword;

    public AuthenticationFilter casAuthenticationFilter() {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter();
        authenticationFilter.setCasServerLoginUrl(casServerLoginUrl);
        authenticationFilter.setServerName(serverUrl);
        authenticationFilter.setEncodeServiceUrl(true);
        return authenticationFilter;
    }

    public Cas20ProxyReceivingTicketValidationFilter getCas20ProxyReceivingTicketValidationFilter() {
        Cas20ProxyReceivingTicketValidationFilter cas20ProxyReceivingTicketValidationFilter = new Cas20ProxyReceivingTicketValidationFilter();
        cas20ProxyReceivingTicketValidationFilter.setServerName(serverUrl);
        cas20ProxyReceivingTicketValidationFilter.setTicketValidator(new Cas20ServiceTicketValidator(casServerUrlPrefix));
        cas20ProxyReceivingTicketValidationFilter.setRedirectAfterValidation(true);
        return cas20ProxyReceivingTicketValidationFilter;
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/fonts/**").antMatchers("/images/**").antMatchers("/scripts/**")
                .antMatchers("/styles/**").antMatchers("/views/**").antMatchers("/i18n/**").antMatchers("/static/**")
                .antMatchers("/logout");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        if (ShepherConstants.LOGIN_TYPE_LDAP.equals(loginType.toUpperCase())) {
            http.csrf().disable()
                    .authorizeRequests()
                    .anyRequest()
                    .fullyAuthenticated()
                    .and()
                    .formLogin();
        } else if (ShepherConstants.LOGIN_TYPE_CAS.equals(loginType.toUpperCase())) {
            http.csrf().disable().addFilter(new UsernamePasswordAuthenticationFilter())
                    .addFilterBefore(casAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                    .addFilterAfter(getCas20ProxyReceivingTicketValidationFilter(), AuthenticationFilter.class);
        } else if (ShepherConstants.LOGIN_TYPE_DEMO.equals(loginType.toUpperCase())) {
            http.csrf().disable()
                    .authorizeRequests()
                    .anyRequest().hasRole("USER")
                    .and()
                    .formLogin()
                    .loginPage("/login")
                    .passwordParameter("password")
                    .usernameParameter("username")
                    .permitAll()
                    .and()
                    .logout()
                    .permitAll();
        }
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        if (ShepherConstants.LOGIN_TYPE_LDAP.equals(loginType.toUpperCase())) {
            auth.ldapAuthentication()
                    .userDnPatterns("uid={0},ou=people")
                    .groupSearchBase("ou=groups")
                    .contextSource()
                    .url(ldapUrl)
                    .managerPassword(ldapPassword)
                    .managerDn(ldapDn);
        } else if (ShepherConstants.LOGIN_TYPE_DEMO.equals(loginType.toUpperCase())) {
            auth.inMemoryAuthentication()
                    .withUser(demoAdminName)
                    .password(demoAdminPassword)
                    .roles("USER");
        }
    }
}

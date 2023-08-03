package project_pet_backEnd.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.PortResolver;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import project_pet_backEnd.filter.ManagerJWTFilter;
import project_pet_backEnd.filter.UserJWTFilter;
import project_pet_backEnd.utils.ManagerJwtUtil;
import project_pet_backEnd.utils.UserJwtUtil;
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class SecurityConfig   extends WebSecurityConfigurerAdapter {
    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;
    @Autowired
    private UserJWTFilter userJWTFilter;
    @Autowired
    private ManagerJWTFilter managerJWTFilter;
    @Bean
    public PasswordEncoder bCryptPasswordEncoder(){

        return  new BCryptPasswordEncoder();
    };
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/user/login").permitAll()
                .antMatchers("/user/customerSignUp").permitAll()
                .antMatchers("/user/generateCaptcha").permitAll()
                .antMatchers("/customer/**").permitAll()
                .antMatchers("/manager/login").permitAll()
                .antMatchers("/test/**").permitAll()
                .antMatchers("/user/googleLogin").permitAll()
                .anyRequest().authenticated();
        http.addFilterBefore(userJWTFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(managerJWTFilter, UsernamePasswordAuthenticationFilter.class);
        //配置異常處理
        http.exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}

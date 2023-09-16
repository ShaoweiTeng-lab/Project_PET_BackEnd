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
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import project_pet_backEnd.filter.IpRequestMapApiKeyFilter;
import project_pet_backEnd.filter.ManagerJWTFilter;
import project_pet_backEnd.filter.UserJWTFilter;
import project_pet_backEnd.filter.UserPointFilter;

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
    @Autowired
    private UserPointFilter userPointFilter;
    @Autowired
    private IpRequestMapApiKeyFilter ipRequestMapApiKeyFilter;
    @Bean
    public PasswordEncoder bCryptPasswordEncoder(){

        return  new BCryptPasswordEncoder();
    };
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //http.cors().and().csrf().disable();
        http    .cors()
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/user/login").permitAll()
                .antMatchers("/user/signUp").permitAll()
                .antMatchers("/user/generateCaptcha").permitAll()
                .antMatchers("/user/forgetPassword/**").permitAll()
                .antMatchers("/user/checkAccountIsSignUp").permitAll()
                .antMatchers("/customer/**").permitAll()
                .antMatchers("/manager/login").permitAll()
                .antMatchers("/doc.html").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/swagger-resources").permitAll()
                .antMatchers("/v2/api-docs").permitAll()
                .antMatchers("/test/**").permitAll()
                .antMatchers("/user/googleLogin").permitAll()
                .antMatchers("/websocket/**").permitAll()
                .antMatchers("/user/successPay/**").permitAll()
                .antMatchers("/chat/insert").permitAll()
                .antMatchers("/chat/update").permitAll()
                .antMatchers("/chat/delete").permitAll()
                .antMatchers("/chat/list").permitAll()
                .antMatchers("/chat/userList").permitAll()
                .antMatchers("/portfolio/insert").permitAll()
                .antMatchers("/portfolio/update").permitAll()
                .antMatchers("/portfolio/delete").permitAll()
                .antMatchers("/portfolio/detail").permitAll()
                .antMatchers("/portfolio/save").permitAll()
                .antMatchers("/portfolio/list").permitAll()
                .antMatchers("/collect/insert").permitAll()
                .antMatchers("/collect/update").permitAll()
                .antMatchers("/collect/delete").permitAll()
                .antMatchers("/collect/detail").permitAll()
                .antMatchers("/collect/list").permitAll()
                .antMatchers("/portfolio/addNewsPic").permitAll()
                .antMatchers("/portfolio/updateNewsPic").permitAll()
                .antMatchers("/portfolio/deleteNewsPic").permitAll()
                .antMatchers("/portfolio/findNewsPic").permitAll()
                .antMatchers("/picture/list").permitAll()
                .anyRequest().authenticated();
        http.addFilterBefore(userJWTFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(managerJWTFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterAfter(userPointFilter,UserJWTFilter.class);
        http.addFilterBefore(ipRequestMapApiKeyFilter,UsernamePasswordAuthenticationFilter.class);
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

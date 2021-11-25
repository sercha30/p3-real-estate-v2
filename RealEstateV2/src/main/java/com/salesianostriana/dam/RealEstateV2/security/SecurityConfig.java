package com.salesianostriana.dam.RealEstateV2.security;

import com.salesianostriana.dam.RealEstateV2.security.jwt.JwtAccessDeniedHandler;
import com.salesianostriana.dam.RealEstateV2.security.jwt.JwtAuthenticationEntryPoint;
import com.salesianostriana.dam.RealEstateV2.security.jwt.JwtAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthorizationFilter filter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                    .exceptionHandling()
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                    .accessDeniedHandler(jwtAccessDeniedHandler)
                .and()
                    .sessionManagement()
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .authorizeRequests()
                        .antMatchers(HttpMethod.POST,"/auth/login").anonymous()
                        .antMatchers(HttpMethod.POST,"/auth/register/user").anonymous()
                        .antMatchers(HttpMethod.GET,"auth/me").anonymous()
                        .antMatchers(HttpMethod.POST,"/auth/register/gestor").hasRole("ADMIN")
                        .antMatchers(HttpMethod.POST,"/auth/register/admin").hasRole("ADMIN")
                        .antMatchers(HttpMethod.GET,"/propietario/**","/vivienda/**").authenticated()
                        .antMatchers(HttpMethod.POST,"/vivienda/").hasRole("PROPIETARIO")
                        .antMatchers(HttpMethod.PUT,"/vivienda/**")
                            .hasAnyRole("ADMIN","PROPIETARIO")
                        .antMatchers(HttpMethod.DELETE,"/vivienda/**")
                            .hasAnyRole("ADMIN","PROPIETARIO")
                        .antMatchers(HttpMethod.POST,"/vivienda/{id}/**")
                            .hasAnyRole("ADMIN","PROPIETARIO")
                        .antMatchers(HttpMethod.DELETE,"/vivienda/{id}/**")
                            .hasAnyRole("ADMIN","PROPIETARIO")
                        .antMatchers(HttpMethod.POST,"/inmobiliaria/").hasRole("ADMIN")
                        .antMatchers(HttpMethod.DELETE,"/inmobiliaria/gestor/{id}")
                            .hasAnyRole("ADMIN","GESTOR")
                        .antMatchers(HttpMethod.POST,"/inmobiliaria/{id}/gestor")
                            .hasAnyRole("ADMIN","GESTOR")
                        .antMatchers(HttpMethod.GET,"/inmobiliaria/{id}/gestor")
                            .hasAnyRole("ADMIN","GESTOR")
                        .antMatchers(HttpMethod.GET,"/inmobiliaria/","/inmobiliaria/{id}")
                            .authenticated()
                        .antMatchers(HttpMethod.DELETE,"/inmobiliaria/{id}").hasRole("ADMIN")
                        .antMatchers("/h2-console/**").permitAll()
                .anyRequest().authenticated();

        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

        http.headers().frameOptions().disable();

    }
}

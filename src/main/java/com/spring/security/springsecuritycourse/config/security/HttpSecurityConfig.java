package com.spring.security.springsecuritycourse.config.security;


import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.spring.security.springsecuritycourse.config.security.filter.JwtAuthenticationFilter;
import com.spring.security.springsecuritycourse.persistence.util.RoleEnumm;
import com.spring.security.springsecuritycourse.persistence.util.RolePermissionEnumm;


@Configuration
@EnableWebSecurity //configura por default los elementos de se seguridad(authenticationConfiguration etc)
//@EnableMethodSecurity(
//    prePostEnabled = true
//)
public class HttpSecurityConfig {

    @Autowired
    private AuthenticationProvider daoAuthProvider;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Autowired
    private AuthorizationManager<RequestAuthorizationContext> authorizationManager;
    
    //HttpSecurity -> permite personalizar como se gestionan y protegen las peticiones http
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        SecurityFilterChain filterChain = http
                .cors(Customizer.withDefaults())
                .csrf(csrfConfig -> csrfConfig.disable()) // desactiva el uso de tokens ya que usaremos Jwt
                .sessionManagement(sessMagConfig -> sessMagConfig
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // sesion sin estado
                .authenticationProvider(daoAuthProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                //.authorizeHttpRequests(authReqConfig -> buildRequestMatchersAuthorities(authReqConfig))// Con Authorities
                .authorizeHttpRequests(authReqConfig -> {
                        authReqConfig.anyRequest().access(authorizationManager);
                })
                .exceptionHandling(exceptionConfig -> {
                        exceptionConfig.authenticationEntryPoint(authenticationEntryPoint);
                        exceptionConfig.accessDeniedHandler(accessDeniedHandler);
                })
                //.authorizeHttpRequests(authReqConfig -> buildRequestMatchersRoles(authReqConfig)) // con roles
                //.authorizeHttpRequests(authReqConfig -> buildRequestMatchersRolesV2(authReqConfig)) // con roles
                .build();

            return filterChain;

    }
    
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("https://www.google.com","http://127.0.0.1:5500/"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    private void buildRequestMatchersRolesV2(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authReqConfig) {
        
        authReqConfig.requestMatchers(HttpMethod.POST, "/customers").permitAll();
        authReqConfig.requestMatchers(HttpMethod.POST, "/auth/authenticate").permitAll();
        authReqConfig.requestMatchers(HttpMethod.GET,"/auth/validate-token").permitAll();
        //authReqConfig.requestMatchers(HttpMethod.POST, "/auth/**");
        authReqConfig.anyRequest().authenticated();
    }

    
    private void buildRequestMatchersRoles(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authReqConfig) {
        /*
        * Autorizacion de endpoints de productos
        */
        authReqConfig.requestMatchers(HttpMethod.GET, "/products")
                .hasAnyRole(RoleEnumm.ADMINISTRATOR.name(), RoleEnumm.ASSISTANT_ADMINISTRATOR.name());

        authReqConfig.requestMatchers(HttpMethod.GET, "/products/{productId}")
                .hasAnyRole(RoleEnumm.ADMINISTRATOR.name(), RoleEnumm.ASSISTANT_ADMINISTRATOR.name());
                
        authReqConfig.requestMatchers(HttpMethod.POST, "/products")
                .hasRole(RoleEnumm.ADMINISTRATOR.name());
                
        authReqConfig.requestMatchers(HttpMethod.PUT, "/products/{productId}")
                .hasAnyRole(RoleEnumm.ADMINISTRATOR.name(), RoleEnumm.ASSISTANT_ADMINISTRATOR.name());
                
        authReqConfig.requestMatchers(HttpMethod.PUT, "/products/{productId}/disabled")
                .hasRole(RoleEnumm.ADMINISTRATOR.name());
        /*
        * Autorizacion de endpoints de categories
        */
        authReqConfig.requestMatchers(HttpMethod.GET, "/categories")
                .hasAnyRole(RoleEnumm.ADMINISTRATOR.name(), RoleEnumm.ASSISTANT_ADMINISTRATOR.name());

        authReqConfig.requestMatchers(HttpMethod.GET, "/categories/{categoryId}")
                .hasAnyRole(RoleEnumm.ADMINISTRATOR.name(), RoleEnumm.ASSISTANT_ADMINISTRATOR.name());

        authReqConfig.requestMatchers(HttpMethod.POST, "/categories")
                .hasRole(RoleEnumm.ADMINISTRATOR.name());

        authReqConfig.requestMatchers(HttpMethod.PUT, "/categories/{categoryId}")
                .hasAnyRole(RoleEnumm.ADMINISTRATOR.name(), RoleEnumm.ASSISTANT_ADMINISTRATOR.name());

        authReqConfig.requestMatchers(HttpMethod.PUT, "/categories/{categoryId}/disabled")
                .hasRole(RoleEnumm.ADMINISTRATOR.name());

        authReqConfig.requestMatchers(HttpMethod.GET, "/auth/profile")
                .hasAnyRole(RoleEnumm.ADMINISTRATOR.name(), RoleEnumm.ASSISTANT_ADMINISTRATOR.name(),RoleEnumm.CUSTOMER.name());


        authReqConfig.requestMatchers(HttpMethod.POST, "/customers").permitAll();
        authReqConfig.requestMatchers(HttpMethod.POST, "/auth/authenticate").permitAll();
        authReqConfig.requestMatchers(HttpMethod.GET,"/auth/validate-token").permitAll();
        //authReqConfig.requestMatchers(HttpMethod.POST, "/auth/**");
        authReqConfig.anyRequest().authenticated();
    }


    //de acurdo a authorities

    private void buildRequestMatchersAuthorities(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authReqConfig) {
        /*
         * Autorizacion de endpoints de productos
         */
        authReqConfig.requestMatchers(HttpMethod.GET, "/products")
                .hasAuthority(RolePermissionEnumm.READ_ALL_PRODUCTS.name());
        authReqConfig.requestMatchers(HttpMethod.GET, "/products/{productId}")
                .hasAuthority(RolePermissionEnumm.READ_ONE_PRODUCT.name());
        authReqConfig.requestMatchers(HttpMethod.POST, "/products")
                .hasAuthority(RolePermissionEnumm.CREATE_ONE_PRODUCT.name());
        authReqConfig.requestMatchers(HttpMethod.PUT, "/products/{productId}")
                .hasAuthority(RolePermissionEnumm.UPDATE_ONE_PRODUCT.name());
        authReqConfig.requestMatchers(HttpMethod.PUT, "/products/{productId}/disabled")
                .hasAuthority(RolePermissionEnumm.DISABLED_ONE_PRODUCT.name());
        /*
         * Autorizacion de endpoints de categories
         */
        authReqConfig.requestMatchers(HttpMethod.GET, "/categories")
                .hasAuthority(RolePermissionEnumm.READ_ALL_CATEGORIES.name());
        authReqConfig.requestMatchers(HttpMethod.GET, "/categories/{categoryId}")
                .hasAuthority(RolePermissionEnumm.READ_ONE_CATEGORY.name());
        authReqConfig.requestMatchers(HttpMethod.POST, "/categories")
                .hasAuthority(RolePermissionEnumm.CREATE_ONE_CATEGORY.name());
        authReqConfig.requestMatchers(HttpMethod.PUT, "/categories/{categoryId}")
                .hasAuthority(RolePermissionEnumm.UPDATE_ONE_CATEGORY.name());
        authReqConfig.requestMatchers(HttpMethod.PUT, "/categories/{categoryId}/disabled")
                .hasAuthority(RolePermissionEnumm.DISABLED_ONE_CATEGORY.name());
        authReqConfig.requestMatchers(HttpMethod.GET, "/auth/profile")
                .hasAuthority(RolePermissionEnumm.READ_MY_PROFILE.name());


        authReqConfig.requestMatchers(HttpMethod.POST, "/customers").permitAll();
        authReqConfig.requestMatchers(HttpMethod.POST, "/auth/authenticate").permitAll();
        authReqConfig.requestMatchers(HttpMethod.GET,"/auth/validate-token").permitAll();
        //authReqConfig.requestMatchers(HttpMethod.POST, "/auth/**");
        authReqConfig.anyRequest().authenticated();
    }
    
}
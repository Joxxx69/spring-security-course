package com.spring.security.springsecuritycourse.config.security.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.spring.security.springsecuritycourse.exception.ObjectNotFoundException;
import com.spring.security.springsecuritycourse.persistence.entity.security.User;
import com.spring.security.springsecuritycourse.service.UserService;
import com.spring.security.springsecuritycourse.service.auth.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        System.out.println("ENTRO EN EL FILTRO JWT AUTHENTICATION FILTER");
        //1- Obtner encabezado http llamado Authorization
        String authorizationHeader = request.getHeader("Authorization");
        if (!StringUtils.hasText(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")) {
            // para que siga con los demas filtros, encaso de que el header no tenga el token
            filterChain.doFilter(request, response);
            return; // retorna el control a quien mando a llamar el metodo actual
        }
            

        //2- Obtener token JWT desde el header(encabezado)

        String jwt = authorizationHeader.split(" ")[1];
        //String jwt2 = authorizationHeader.substring(7);

        //3- Obtener el subject/username desde el token
        // esta accion a su vez valida el formato del token, firma y fecha de expiracion 
        String username = jwtService.extractUsername(jwt);


        //4- Setear objeto authentication(usuario logeado) dentro de security contex holder
        User user = userService.findOneByUsername(username)
                .orElseThrow(() -> new ObjectNotFoundException("User not found Username: " + username));
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username,null, user.getAuthorities());

        authToken.setDetails(new WebAuthenticationDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
        //5- Ejecutar el registro de filtros

        filterChain.doFilter(request, response);
    }
    
}

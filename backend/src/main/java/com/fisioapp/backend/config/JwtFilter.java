package com.fisioapp.backend.config;

import io.jsonwebtoken.Jwts;


import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.ArrayList;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException, java.io.IOException {

        System.out.println(">>> Petición recibida en: " + request.getRequestURI());

        // miramos si la peticion tiene token (suele venir en una cabecera llamada authorization
        String cabecera = request.getHeader("Authorization");

        System.out.println(">>> Cabecera Authorization: " + cabecera);

        // si existe esa cabecera y empieza por la palabra Bearer que es como viene en forma
        if (cabecera != null && cabecera.startsWith("Bearer ")){
            // quitamos la palabra bearer para quedarnos solo con el codigo
            String token = cabecera.replace("Bearer ", "");

            try{
                //intentamos leer el token
                String email = Jwts.parserBuilder()
                        .setSigningKey(JwtUtil.SECRET_KEY)
                        .build()
                        .parseClaimsJws(token)
                        .getBody()
                        .getSubject();
                // si el email existe
                if (email != null){

                    System.out.println(">>> ¡Pulsera válida! Dejando pasar a: " + email);

                    UsernamePasswordAuthenticationToken permiso = new UsernamePasswordAuthenticationToken(email, null, new ArrayList<>());
                    SecurityContextHolder.getContext().setAuthentication(permiso);
                }
            }catch (Exception e){
                // si el token ha caducao o es falso
                System.out.println("Token invalido o caducado");
            }
        }
        // dejamos que la peticion siga su camino hasta el controlador
        filterChain.doFilter(request, response);
    }
}

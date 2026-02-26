package com.fisioapp.backend.config;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component // para decir a spring que es una herramienta que se usa en cualquier lado
public class JwtUtil {
    //generar clave secreta y segura
    public static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    //tiempo que dura la key en caducar y pedir otra
    private static final long TIEMPO_EXPIRACION = 86400000; //24h en milisecs

    //metodo para crear la contraseña pasando el mail de user
    public String generarToken(String email){
        return Jwts.builder()
                .setSubject(email) //a quien pertenece la clave
                .setIssuedAt(new Date()) // fecha de creacion
                .setExpiration(new Date(System.currentTimeMillis() + TIEMPO_EXPIRACION)) //fecha de caducidad
                .signWith(SECRET_KEY) // firmamos la key para usar
                .compact(); // la convierte en String
    }

}

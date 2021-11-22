package com.salesianostriana.dam.RealEstateV2.security.jwt;

import com.salesianostriana.dam.RealEstateV2.users.model.Usuario;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@Log
@Service
public class JwtProvider {

    public static final String TOKEN_TYPE = "JWT";
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    @Value("${jwt.secret:realEstateV2123456789JWTSecretsAreAHeadache}")
    private String jwtSecret;

    @Value("${jwt.duration:86400}")
    private int jwtLifeInSeconds;

    private JwtParser jwtParser;

    @PostConstruct
    public void init(){
        jwtParser = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                .build();
    }

    public String generateToken(Authentication authentication){

        Usuario usuario = (Usuario) authentication.getPrincipal();

        Date tokenExpDate = Date
                .from(LocalDateTime
                        .now()
                        .plusSeconds(jwtLifeInSeconds)
                        .atZone(ZoneId.systemDefault()).toInstant());

        return Jwts.builder()
                .setHeaderParam("typ",TOKEN_TYPE)
                .setSubject(usuario.getId().toString())
                .setIssuedAt(tokenExpDate)
                .claim("nombre",usuario.getNombre())
                .claim("apellidos",usuario.getApellidos())
                .claim("rol",usuario.getRol().name())
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                .compact();

    }

    public boolean validateToken(String token){
        try{
            jwtParser.parseClaimsJws(token);
        }catch(SignatureException | MalformedJwtException | ExpiredJwtException
                | UnsupportedJwtException | IllegalArgumentException exception){
            log.info("Error con el token" + exception.getMessage());
        }
        return false;
    }

    public UUID getUserIdFromJwt(String token){
        return UUID.fromString(jwtParser.parseClaimsJws(token).getBody().getSubject());
    }
}

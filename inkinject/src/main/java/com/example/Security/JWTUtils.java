package com.example.Security;

import com.example.Entity.Actor;
import com.example.Enums.Roles;
import com.example.Repository.ActorRepository;
import com.example.Service.ActorService;
import com.example.Service.TatuadorService;
import com.example.Service.ClienteService;
import com.example.Service.AdministradorService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Optional;

@Component
public class JWTUtils {

    @Autowired
    private ActorService actorService;

    @Autowired
    @Lazy
    private TatuadorService tatuadorService;

    @Autowired
    @Lazy
    private ClienteService clienteService;

    @Autowired
    @Lazy
    private AdministradorService administradorService;

    @Value("${jwt.secret}")
    private String JWT_FIRMA;

    @Value("${jwt.expiration}")
    private long JWT_EXPIRACION;

    public String getToken(HttpServletRequest request) {
        String tokenBearer = request.getHeader("Authorization");
        if (StringUtils.hasText(tokenBearer) && tokenBearer.startsWith("Bearer ")) {
            return tokenBearer.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(JWT_FIRMA).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            throw new AuthenticationCredentialsNotFoundException("JWT ha expirado o no es valido");
        }
    }

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date fechaActual = new Date();
        Date fechaExpiracion = new Date(fechaActual.getTime() + JWT_EXPIRACION);
        String rol = authentication.getAuthorities().iterator().next().getAuthority();

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(fechaActual)
                .setExpiration(fechaExpiracion)
                .claim("rol", rol)
                .signWith(SignatureAlgorithm.HS512, JWT_FIRMA)
                .compact();
    }

    public String getUsernameOfToken(String token) {
        return Jwts.parser()
                .setSigningKey(JWT_FIRMA)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public <T> T userLogin() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!StringUtils.hasText(username)) {
            return null;
        }

        Optional<Actor> actorO = actorService.findByUsername(username);
        if (actorO.isEmpty()) {
            return null;
        }

        Actor actor = actorO.get();
        switch (actor.getRol()) {
            case ADMINISTRADOR:
                return (T) administradorService.findByUsername(username).orElse(null);
            case TATUADOR:
                return (T) tatuadorService.findByUsername(username).orElse(null);
            case CLIENTE:
                return (T) clienteService.findByUsername(username).orElse(null);
            default:
                return null;
        }
    }
}
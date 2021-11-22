package com.salesianostriana.dam.RealEstateV2.security;

import com.salesianostriana.dam.RealEstateV2.security.jwt.JwtProvider;
import com.salesianostriana.dam.RealEstateV2.security.jwt.JwtUserResponse;
import com.salesianostriana.dam.RealEstateV2.users.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @PostMapping("/login")
    public ResponseEntity<?> doLogin(@RequestBody LoginDto loginDto){

        Authentication authentication =
                authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getEmail(),
                            loginDto.getPassword()
                    )
                );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateToken(authentication);

        Usuario usuario = (Usuario) authentication.getPrincipal();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                    convertUserToJwtUserResponse(usuario,jwt)
                );
    }

    @GetMapping("/me")
    public ResponseEntity<?> identifyUser(@AuthenticationPrincipal Usuario usuario){
        return ResponseEntity.ok(convertUserToJwtUserResponse(usuario,null));
    }

    private JwtUserResponse convertUserToJwtUserResponse(Usuario usuario, String jwt){
        return JwtUserResponse.builder()
                .nombre(usuario.getNombre())
                .apellidos(usuario.getApellidos())
                .email(usuario.getEmail())
                .avatar(usuario.getAvatar())
                .rol(usuario.getRol().name())
                .token(jwt)
                .build();
    }
}

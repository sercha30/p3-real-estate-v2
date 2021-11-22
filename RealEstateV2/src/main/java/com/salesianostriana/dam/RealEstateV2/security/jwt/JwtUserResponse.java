package com.salesianostriana.dam.RealEstateV2.security.jwt;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class JwtUserResponse {

    private String nombre;
    private String apellidos;
    private String email;
    private String avatar;
    private String rol;
    private String token;
}

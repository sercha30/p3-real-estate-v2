package com.salesianostriana.dam.RealEstateV2.users.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class GetUsuarioDto {

    private String nombre;
    private String apellidos;
    private String avatar;
    private String email;
    private String rol;
}

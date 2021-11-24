package com.salesianostriana.dam.RealEstateV2.users.dto.usuario;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
public class GetUsuarioDto {

    private UUID id;
    private String nombre;
    private String apellidos;
    private String avatar;
    private String email;
    private String rol;
}

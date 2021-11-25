package com.salesianostriana.dam.RealEstateV2.users.dto.usuario;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
public class CreateUsuarioDto {

    private String nombre;
    private String apellidos;
    private String avatar;
    private String email;
    private String telefono;
    private String direccion;
    private String password;
    private String password2;
}

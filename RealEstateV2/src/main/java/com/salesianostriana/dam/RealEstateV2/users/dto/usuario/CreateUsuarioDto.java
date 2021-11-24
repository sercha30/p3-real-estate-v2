package com.salesianostriana.dam.RealEstateV2.users.dto.usuario;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
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

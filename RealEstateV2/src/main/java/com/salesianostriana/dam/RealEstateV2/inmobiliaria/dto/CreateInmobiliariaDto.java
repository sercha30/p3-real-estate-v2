package com.salesianostriana.dam.RealEstateV2.inmobiliaria.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class CreateInmobiliariaDto {

    private String nombre;
    private String email;
    private String telefono;
}

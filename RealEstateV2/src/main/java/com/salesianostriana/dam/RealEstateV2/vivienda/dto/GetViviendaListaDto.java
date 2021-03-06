package com.salesianostriana.dam.RealEstateV2.vivienda.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
public class GetViviendaListaDto {

    private UUID id;
    private String titulo;
    private String avatar;
    private String tipo;
    private Double precio;
    private String nombre_propietario;
    private String avatar_propietario;
}

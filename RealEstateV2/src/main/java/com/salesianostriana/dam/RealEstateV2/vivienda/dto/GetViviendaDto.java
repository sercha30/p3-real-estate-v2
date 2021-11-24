package com.salesianostriana.dam.RealEstateV2.vivienda.dto;

import lombok.*;

import java.util.UUID;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class GetViviendaDto {

    private UUID id;
    private String titulo;
    private String avatar;
    private String tipo;
    private Double precio;

}

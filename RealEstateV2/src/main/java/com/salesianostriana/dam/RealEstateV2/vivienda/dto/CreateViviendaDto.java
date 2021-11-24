package com.salesianostriana.dam.RealEstateV2.vivienda.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class CreateViviendaDto {

    private String titulo;
    private String descripcion;
    private String avatar;
    private String latlng;
    private String direccion;
    private String codigoPostal;
    private String poblacion;
    private String provincia;
    private String tipo;
    private Double precio;
    private Integer numHabitaciones;
    private Double metrosCuadrados;
    private Integer numBanyos;
    private boolean tienePiscina;
    private boolean tieneAscensor;
    private boolean tieneGaraje;
}

package com.salesianostriana.dam.RealEstateV2.inmobiliaria.dto;

import com.salesianostriana.dam.RealEstateV2.vivienda.dto.GetViviendaPropietarioDto;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class GetInmobiliariaDto {

    private String nombre;
    private String email;
    private String telefono;

    @Builder.Default
    private List<GetViviendaPropietarioDto> viviendas = new ArrayList<>();
}

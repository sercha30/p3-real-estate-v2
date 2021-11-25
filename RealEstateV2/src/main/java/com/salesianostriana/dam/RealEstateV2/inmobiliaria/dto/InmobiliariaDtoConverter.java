package com.salesianostriana.dam.RealEstateV2.inmobiliaria.dto;

import com.salesianostriana.dam.RealEstateV2.inmobiliaria.model.Inmobiliaria;
import com.salesianostriana.dam.RealEstateV2.vivienda.dto.ViviendaPropietarioDtoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class InmobiliariaDtoConverter {

    private final ViviendaPropietarioDtoConverter viviendaPropietarioDtoConverter;

    public GetInmobiliariaDto convertInmobiliariaToGetInmobiliariaDto(Inmobiliaria inmobiliaria){
        return GetInmobiliariaDto.builder()
                .nombre(inmobiliaria.getNombre())
                .email(inmobiliaria.getEmail())
                .telefono(inmobiliaria.getTelefono())
                .viviendas(inmobiliaria.getListaViviendas()
                        .stream()
                        .map(viviendaPropietarioDtoConverter::convertViviendaToViviendaDto)
                        .collect(Collectors.toList()))
                .build();
    }
}

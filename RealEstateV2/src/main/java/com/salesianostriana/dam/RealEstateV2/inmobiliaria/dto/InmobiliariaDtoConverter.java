package com.salesianostriana.dam.RealEstateV2.inmobiliaria.dto;

import com.salesianostriana.dam.RealEstateV2.inmobiliaria.model.Inmobiliaria;
import com.salesianostriana.dam.RealEstateV2.users.dto.usuario.UsuarioDtoConverter;
import com.salesianostriana.dam.RealEstateV2.vivienda.dto.ViviendaPropietarioDtoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class InmobiliariaDtoConverter {

    private final ViviendaPropietarioDtoConverter viviendaPropietarioDtoConverter;
    private final UsuarioDtoConverter usuarioDtoConverter;

    public GetInmobiliariaDto convertInmobiliariaToGetInmobiliariaDto(Inmobiliaria inmobiliaria){
        return GetInmobiliariaDto.builder()
                .id(inmobiliaria.getId())
                .nombre(inmobiliaria.getNombre())
                .email(inmobiliaria.getEmail())
                .telefono(inmobiliaria.getTelefono())
                .viviendas(inmobiliaria.getListaViviendas()
                        .stream()
                        .map(viviendaPropietarioDtoConverter::convertViviendaToViviendaDto)
                        .collect(Collectors.toList()))
                .gestores(inmobiliaria.getGestores()
                        .stream()
                        .map(usuarioDtoConverter::convertUsuarioToUsuarioDto)
                        .collect(Collectors.toList()))
                .build();
    }
}

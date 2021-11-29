package com.salesianostriana.dam.RealEstateV2.vivienda.dto;

import com.salesianostriana.dam.RealEstateV2.vivienda.model.Vivienda;
import org.springframework.stereotype.Component;

@Component
public class ViviendaConInteresDtoConverter {

    public GetViviendaConInteresDto convertViviendaToGetViviendaConInteresDto(Vivienda vivienda){
        return GetViviendaConInteresDto.builder()
                .id(vivienda.getId())
                .titulo(vivienda.getTitulo())
                .avatar(vivienda.getAvatar())
                .precio(vivienda.getPrecio())
                .nombre_propietario(vivienda.getPropietario().getNombre())
                .avatar_propietario(vivienda.getPropietario().getAvatar())
                .build();
    }
}

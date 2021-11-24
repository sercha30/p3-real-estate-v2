package com.salesianostriana.dam.RealEstateV2.vivienda.dto;

import com.salesianostriana.dam.RealEstateV2.vivienda.model.Vivienda;
import org.springframework.stereotype.Component;

@Component
public class ViviendaListaDtoConverter {

    public GetViviendaListaDto convertViviendaToGetViviendaListaDto(Vivienda vivienda){
        return GetViviendaListaDto.builder()
                .id(vivienda.getId())
                .titulo(vivienda.getTitulo())
                .avatar(vivienda.getAvatar())
                .precio(vivienda.getPrecio())
                .tipo(vivienda.getTipo())
                .nombre_propietario(vivienda.getPropietario().getNombre())
                .avatar_propietario(vivienda.getPropietario().getAvatar())
                .build();
    }
}

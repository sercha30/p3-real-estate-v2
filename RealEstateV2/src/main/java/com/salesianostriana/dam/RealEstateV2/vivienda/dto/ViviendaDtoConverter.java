package com.salesianostriana.dam.RealEstateV2.vivienda.dto;

import com.salesianostriana.dam.RealEstateV2.vivienda.model.Vivienda;
import org.springframework.stereotype.Component;

@Component
public class ViviendaDtoConverter {

    public GetViviendaDto convertViviendaToViviendaDto(Vivienda vivienda){
        return GetViviendaDto.builder()
                .id(vivienda.getId())
                .titulo(vivienda.getTitulo())
                .avatar(vivienda.getAvatar())
                .tipo(vivienda.getTipo().name())
                .precio(vivienda.getPrecio())
                .build();
    }
}

package com.salesianostriana.dam.RealEstateV2.vivienda.dto;

import com.salesianostriana.dam.RealEstateV2.vivienda.model.Vivienda;
import org.springframework.stereotype.Component;

@Component
public class ViviendaSimpleDtoConverter {

    public GetViviendaSimpleDto convertViviendaToViviendaDto(Vivienda vivienda){
        return GetViviendaSimpleDto.builder()
                .id(vivienda.getId())
                .titulo(vivienda.getTitulo())
                .avatar(vivienda.getAvatar())
                .tipo(vivienda.getTipo().name())
                .precio(vivienda.getPrecio())
                .build();
    }
}

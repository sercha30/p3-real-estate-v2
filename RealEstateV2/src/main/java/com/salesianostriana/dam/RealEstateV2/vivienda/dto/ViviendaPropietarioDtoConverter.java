package com.salesianostriana.dam.RealEstateV2.vivienda.dto;

import com.salesianostriana.dam.RealEstateV2.vivienda.model.Vivienda;
import org.springframework.stereotype.Component;

@Component
public class ViviendaPropietarioDtoConverter {

    public GetViviendaPropietarioDto convertViviendaToViviendaDto(Vivienda vivienda){
        return GetViviendaPropietarioDto.builder()
                .id(vivienda.getId())
                .titulo(vivienda.getTitulo())
                .avatar(vivienda.getAvatar())
                .tipo(vivienda.getTipo())
                .precio(vivienda.getPrecio())
                .build();
    }
}

package com.salesianostriana.dam.RealEstateV2.users.dto.interesa;

import com.salesianostriana.dam.RealEstateV2.users.model.Interesa;
import org.springframework.stereotype.Component;

@Component
public class InteresaDtoConverter {

    public GetInteresaDto convertInteresaToInteresaDto(Interesa interesa){
        return GetInteresaDto.builder()
                .id(interesa.getId())
                .titulo_vivienda(interesa.getVivienda().getTitulo())
                .nombre_interesado(interesa.getInteresado().getNombre())
                .createdDate(interesa.getCreatedDate())
                .mensaje(interesa.getMensaje())
                .build();
    }
}

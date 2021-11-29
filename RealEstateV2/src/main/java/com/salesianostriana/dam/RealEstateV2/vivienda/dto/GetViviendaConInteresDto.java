package com.salesianostriana.dam.RealEstateV2.vivienda.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
public class GetViviendaConInteresDto extends GetViviendaListaDto{

    private Boolean interesa;
}

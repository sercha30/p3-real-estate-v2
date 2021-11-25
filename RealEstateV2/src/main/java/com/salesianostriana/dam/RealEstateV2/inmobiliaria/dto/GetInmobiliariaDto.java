package com.salesianostriana.dam.RealEstateV2.inmobiliaria.dto;

import com.salesianostriana.dam.RealEstateV2.users.dto.usuario.GetUsuarioDto;
import com.salesianostriana.dam.RealEstateV2.vivienda.dto.GetViviendaPropietarioDto;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class GetInmobiliariaDto {

    private UUID id;
    private String nombre;
    private String email;
    private String telefono;

    @Builder.Default
    private List<GetViviendaPropietarioDto> viviendas = new ArrayList<>();

    @Builder.Default
    private List<GetUsuarioDto> gestores = new ArrayList<>();
}

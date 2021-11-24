package com.salesianostriana.dam.RealEstateV2.users.dto.propietario;

import com.salesianostriana.dam.RealEstateV2.users.dto.usuario.GetUsuarioDto;
import com.salesianostriana.dam.RealEstateV2.vivienda.dto.GetViviendaPropietarioDto;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
public class GetPropietarioDto extends GetUsuarioDto {

    private String direccion;
    private String telefono;

    @Builder.Default
    private List<GetViviendaPropietarioDto> viviendasEnPropiedad = new ArrayList<>();

}

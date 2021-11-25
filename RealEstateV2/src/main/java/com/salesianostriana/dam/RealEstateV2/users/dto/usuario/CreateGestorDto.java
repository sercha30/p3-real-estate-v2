package com.salesianostriana.dam.RealEstateV2.users.dto.usuario;

import com.salesianostriana.dam.RealEstateV2.inmobiliaria.model.Inmobiliaria;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
public class CreateGestorDto extends CreateUsuarioDto{

    private Inmobiliaria inmobiliaria;
}

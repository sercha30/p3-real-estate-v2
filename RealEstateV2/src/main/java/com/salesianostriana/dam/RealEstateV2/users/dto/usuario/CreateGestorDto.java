package com.salesianostriana.dam.RealEstateV2.users.dto.usuario;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
public class CreateGestorDto extends CreateUsuarioDto{

    private UUID inmobiliaria_id;
}

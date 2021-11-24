package com.salesianostriana.dam.RealEstateV2.users.dto.propietario;

import com.salesianostriana.dam.RealEstateV2.users.model.Usuario;
import com.salesianostriana.dam.RealEstateV2.vivienda.dto.ViviendaDtoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PropietarioDtoConverter {

    private ViviendaDtoConverter viviendaDtoConverter;

    public GetPropietarioDto convertPropietarioToPropietarioDto(Usuario usuario){
        return GetPropietarioDto.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .apellidos(usuario.getApellidos())
                .avatar(usuario.getAvatar())
                .rol(usuario.getRol().name())
                .direccion(usuario.getDireccion())
                .telefono(usuario.getTelefono())
                .email(usuario.getEmail())
                .viviendasEnPropiedad(usuario.
                        getViviendas()
                        .stream()
                        .map(viviendaDtoConverter::convertViviendaToViviendaDto)
                        .collect(Collectors.toList()))
                .build();
    }
}

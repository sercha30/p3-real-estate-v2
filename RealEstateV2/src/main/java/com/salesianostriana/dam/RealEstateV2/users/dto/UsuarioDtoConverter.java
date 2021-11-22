package com.salesianostriana.dam.RealEstateV2.users.dto;

import com.salesianostriana.dam.RealEstateV2.users.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioDtoConverter {

    public GetUsuarioDto convertUsuarioToUsuarioDto(Usuario usuario){
        return GetUsuarioDto.builder()
                .nombre(usuario.getNombre())
                .apellidos(usuario.getApellidos())
                .avatar(usuario.getAvatar())
                .email(usuario.getEmail())
                .rol(usuario.getRol().name())
                .build();
    }
}
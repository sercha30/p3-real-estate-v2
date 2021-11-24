package com.salesianostriana.dam.RealEstateV2.users.dto.usuario;

import com.salesianostriana.dam.RealEstateV2.users.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioDtoConverter {

    public GetUsuarioDto convertUsuarioToUsuarioDto(Usuario usuario){
        return GetUsuarioDto.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .apellidos(usuario.getApellidos())
                .avatar(usuario.getAvatar())
                .email(usuario.getEmail())
                .rol(usuario.getRol().name())
                .build();
    }
}

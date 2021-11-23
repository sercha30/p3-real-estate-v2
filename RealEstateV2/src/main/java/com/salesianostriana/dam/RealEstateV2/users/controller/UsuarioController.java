package com.salesianostriana.dam.RealEstateV2.users.controller;

import com.salesianostriana.dam.RealEstateV2.users.dto.CreateUsuarioDto;
import com.salesianostriana.dam.RealEstateV2.users.dto.GetUsuarioDto;
import com.salesianostriana.dam.RealEstateV2.users.dto.UsuarioDtoConverter;
import com.salesianostriana.dam.RealEstateV2.users.model.Usuario;
import com.salesianostriana.dam.RealEstateV2.users.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioDtoConverter usuarioDtoConverter;

    @PostMapping("/register/user")
    public ResponseEntity<GetUsuarioDto> nuevoUsuario(@RequestBody CreateUsuarioDto nuevoUsuario){
        Usuario guardado = usuarioService.savePropietario(nuevoUsuario);

        if(guardado == null){
            return ResponseEntity.badRequest().build();
        }else{
            return ResponseEntity.ok(usuarioDtoConverter.convertUsuarioToUsuarioDto(guardado));
        }
    }
}

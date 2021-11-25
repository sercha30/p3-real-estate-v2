package com.salesianostriana.dam.RealEstateV2.users.controller;

import com.salesianostriana.dam.RealEstateV2.users.dto.usuario.CreateUsuarioDto;
import com.salesianostriana.dam.RealEstateV2.users.dto.usuario.GetUsuarioDto;
import com.salesianostriana.dam.RealEstateV2.users.dto.usuario.UsuarioDtoConverter;
import com.salesianostriana.dam.RealEstateV2.users.model.Usuario;
import com.salesianostriana.dam.RealEstateV2.users.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<GetUsuarioDto> nuevoPropietario(@RequestBody CreateUsuarioDto nuevoPropietario){
        Usuario guardado = usuarioService.savePropietario(nuevoPropietario);

        if(guardado == null){
            return ResponseEntity.badRequest().build();
        }else{
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(usuarioDtoConverter.convertUsuarioToUsuarioDto(guardado));
        }
    }

    @PostMapping("/register/gestor")
    public ResponseEntity<GetUsuarioDto> nuevoGestor(@RequestBody CreateUsuarioDto nuevoGestor){
        Usuario guardado = usuarioService.saveGestor(nuevoGestor);

        if(guardado == null){
            return ResponseEntity.badRequest().build();
        }else{
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(usuarioDtoConverter.convertUsuarioToUsuarioDto(guardado));
        }
    }

    @PostMapping("/register/admin")
    public ResponseEntity<GetUsuarioDto> nuevoAdmin(@RequestBody CreateUsuarioDto nuevoAdmin){
        Usuario guardado = usuarioService.saveAdmin(nuevoAdmin);

        if(guardado == null){
            return ResponseEntity.badRequest().build();
        }else{
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(usuarioDtoConverter.convertUsuarioToUsuarioDto(guardado));
        }
    }

}

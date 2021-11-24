package com.salesianostriana.dam.RealEstateV2.users.controller;

import com.salesianostriana.dam.RealEstateV2.users.dto.GetUsuarioDto;
import com.salesianostriana.dam.RealEstateV2.users.dto.UsuarioDtoConverter;
import com.salesianostriana.dam.RealEstateV2.users.model.Usuario;
import com.salesianostriana.dam.RealEstateV2.users.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/propietario")
public class PropietarioController {

    private final UsuarioService usuarioService;
    private final UsuarioDtoConverter usuarioDtoConverter;

    @GetMapping("/")
    public ResponseEntity<List<GetUsuarioDto>> listarPropietarios(){
        List<Usuario> resultado = usuarioService.findAllPropietarios();

        if(resultado == null || resultado.isEmpty()){
            return ResponseEntity
                    .noContent()
                    .build();
        }else{
            return ResponseEntity.ok()
                    .body(resultado
                            .stream()
                            .map(usuarioDtoConverter::convertUsuarioToUsuarioDto)
                            .collect(Collectors.toList()));
        }
    }
}

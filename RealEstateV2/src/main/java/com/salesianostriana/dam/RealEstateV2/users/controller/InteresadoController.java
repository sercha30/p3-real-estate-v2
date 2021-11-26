package com.salesianostriana.dam.RealEstateV2.users.controller;

import com.salesianostriana.dam.RealEstateV2.users.dto.usuario.GetUsuarioDto;
import com.salesianostriana.dam.RealEstateV2.users.dto.usuario.UsuarioDtoConverter;
import com.salesianostriana.dam.RealEstateV2.users.model.Usuario;
import com.salesianostriana.dam.RealEstateV2.users.service.UsuarioService;
import com.salesianostriana.dam.RealEstateV2.vivienda.model.Vivienda;
import com.salesianostriana.dam.RealEstateV2.vivienda.service.ViviendaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/interesado")
public class InteresadoController {

    private final UsuarioDtoConverter usuarioDtoConverter;
    private final ViviendaService viviendaService;
    private final UsuarioService usuarioService;

    @GetMapping("/")
    public ResponseEntity<List<GetUsuarioDto>> listarInteresados(){

        List<Vivienda> viviendas = viviendaService.findAll();
        List<Usuario> interesados = usuarioService.buscarInteresados();

        if(viviendas.isEmpty()){
            return ResponseEntity.noContent().build();
        }else if(interesados != null){
            return ResponseEntity.ok(
                    interesados.stream()
                            .map(usuarioDtoConverter::convertUsuarioToUsuarioDto)
                            .collect(Collectors.toList())
            );
        }

        return ResponseEntity.badRequest().build();
    }
}

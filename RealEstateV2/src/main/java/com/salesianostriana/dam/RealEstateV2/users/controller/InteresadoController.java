package com.salesianostriana.dam.RealEstateV2.users.controller;

import com.salesianostriana.dam.RealEstateV2.users.dto.usuario.GetUsuarioDto;
import com.salesianostriana.dam.RealEstateV2.users.dto.usuario.UsuarioDtoConverter;
import com.salesianostriana.dam.RealEstateV2.users.model.UserRole;
import com.salesianostriana.dam.RealEstateV2.users.model.Usuario;
import com.salesianostriana.dam.RealEstateV2.users.service.UsuarioService;
import com.salesianostriana.dam.RealEstateV2.vivienda.model.Vivienda;
import com.salesianostriana.dam.RealEstateV2.vivienda.service.ViviendaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
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

    @GetMapping("/{id}")
    public ResponseEntity<GetUsuarioDto> buscarInteresado(@PathVariable UUID id,
                                                          @AuthenticationPrincipal Usuario usuario){

        List<Vivienda> viviendas = viviendaService.findAll();
        Usuario interesado = usuarioService.buscarInteresadoPorID(id);

        if(viviendas.isEmpty()){
            return ResponseEntity.noContent().build();
        }else if(interesado != null
                &&(usuario.getRol().equals(UserRole.ADMIN) || usuario.getId().equals(interesado.getId()))){
            return ResponseEntity.ok(usuarioDtoConverter.convertUsuarioToUsuarioDto(interesado));
        }

        return ResponseEntity.badRequest().build();
    }
}

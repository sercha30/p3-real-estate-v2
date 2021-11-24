package com.salesianostriana.dam.RealEstateV2.users.controller;

import com.salesianostriana.dam.RealEstateV2.users.dto.propietario.PropietarioDtoConverter;
import com.salesianostriana.dam.RealEstateV2.users.dto.usuario.GetUsuarioDto;
import com.salesianostriana.dam.RealEstateV2.users.dto.usuario.UsuarioDtoConverter;
import com.salesianostriana.dam.RealEstateV2.users.model.UserRole;
import com.salesianostriana.dam.RealEstateV2.users.model.Usuario;
import com.salesianostriana.dam.RealEstateV2.users.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/propietario")
public class PropietarioController {

    private final UsuarioService usuarioService;
    private final UsuarioDtoConverter usuarioDtoConverter;
    private final PropietarioDtoConverter propietarioDtoConverter;

    @GetMapping("/")
    public ResponseEntity<List<GetUsuarioDto>> listarPropietarios(){
        List<Usuario> resultado = usuarioService.findAllPropietarios();

        if(resultado == null){
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

    @GetMapping("/{id}")
    public ResponseEntity<GetUsuarioDto> buscarPropietario(@PathVariable UUID id,
                                                           @AuthenticationPrincipal Usuario usuario){
        if(usuario.getRol().equals(UserRole.ADMIN) || usuario.getId().equals(id)){
            return ResponseEntity.ok()
                    .body(
                            propietarioDtoConverter.convertPropietarioToPropietarioDto(
                                    usuarioService.findPropietarioById(id)
                            )
                    );
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarPropietario(@PathVariable UUID id,
                                                 @AuthenticationPrincipal Usuario usuario){
        if(usuario.getRol().equals(UserRole.ADMIN) || usuario.getId().equals(id)){
            usuarioService.deleteById(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}

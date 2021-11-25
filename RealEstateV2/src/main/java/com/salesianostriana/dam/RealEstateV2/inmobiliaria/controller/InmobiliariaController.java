package com.salesianostriana.dam.RealEstateV2.inmobiliaria.controller;

import com.salesianostriana.dam.RealEstateV2.inmobiliaria.dto.CreateInmobiliariaDto;
import com.salesianostriana.dam.RealEstateV2.inmobiliaria.dto.GetInmobiliariaDto;
import com.salesianostriana.dam.RealEstateV2.inmobiliaria.dto.InmobiliariaDtoConverter;
import com.salesianostriana.dam.RealEstateV2.inmobiliaria.model.Inmobiliaria;
import com.salesianostriana.dam.RealEstateV2.inmobiliaria.service.InmobiliariaService;
import com.salesianostriana.dam.RealEstateV2.users.model.UserRole;
import com.salesianostriana.dam.RealEstateV2.users.model.Usuario;
import com.salesianostriana.dam.RealEstateV2.users.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/inmobiliaria")
public class InmobiliariaController {

    private final InmobiliariaService inmobiliariaService;
    private final UsuarioService usuarioService;
    private final InmobiliariaDtoConverter inmobiliariaDtoConverter;

    @PostMapping("/")
    public ResponseEntity<GetInmobiliariaDto> addNuevaInmobiliaria(
            @RequestBody CreateInmobiliariaDto nuevaInmobiliaria){

        Inmobiliaria inmobiliaria = inmobiliariaService.saveInmobiliaria(nuevaInmobiliaria);

        if(inmobiliariaService.findById(inmobiliaria.getId()).isEmpty()){
            return ResponseEntity.badRequest().build();
        }else{
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(inmobiliariaDtoConverter.convertInmobiliariaToGetInmobiliariaDto(inmobiliaria));
        }
    }

    @DeleteMapping("/gestor/{id}")
    public ResponseEntity<?> eliminarGestor(@PathVariable UUID id,
                                            @AuthenticationPrincipal Usuario usuario){

        Usuario gestorLog = usuarioService.findGestorById(usuario.getId());
        Usuario gestor = usuarioService.findGestorById(id);

        if(usuario.getRol().equals(UserRole.ADMIN) && gestor != null){
            gestor.getInmobiliaria().getGestores().remove(gestor);
            usuarioService.delete(gestor);
            return ResponseEntity.noContent().build();
        } else if(gestorLog != null &&
                (gestor != null && gestor.getInmobiliaria().getId().equals(gestorLog.getInmobiliaria().getId()))){
            gestor.getInmobiliaria().getGestores().remove(gestor);
            usuarioService.delete(gestor);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}

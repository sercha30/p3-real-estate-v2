package com.salesianostriana.dam.RealEstateV2.inmobiliaria.controller;

import com.salesianostriana.dam.RealEstateV2.inmobiliaria.dto.CreateInmobiliariaDto;
import com.salesianostriana.dam.RealEstateV2.inmobiliaria.dto.GetInmobiliariaDto;
import com.salesianostriana.dam.RealEstateV2.inmobiliaria.dto.InmobiliariaDtoConverter;
import com.salesianostriana.dam.RealEstateV2.inmobiliaria.model.Inmobiliaria;
import com.salesianostriana.dam.RealEstateV2.inmobiliaria.service.InmobiliariaService;
import com.salesianostriana.dam.RealEstateV2.users.dto.usuario.CreateGestorDto;
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
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/inmobiliaria")
public class InmobiliariaController {

    private final InmobiliariaService inmobiliariaService;
    private final UsuarioService usuarioService;
    private final InmobiliariaDtoConverter inmobiliariaDtoConverter;
    private final UsuarioDtoConverter usuarioDtoConverter;

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

    @PostMapping("/{id}/gestor")
    public ResponseEntity<GetInmobiliariaDto> addGestor(@PathVariable UUID id,
                                                        @RequestBody CreateGestorDto nuevoGestor,
                                                        @AuthenticationPrincipal Usuario usuario){

        Optional<Inmobiliaria> inmobiliaria = inmobiliariaService.findById(id);
        Usuario gestorLog = usuarioService.findGestorById(usuario.getId());

        if((gestorLog != null || usuario.getRol().equals(UserRole.ADMIN)) && inmobiliaria.isPresent()){
            Usuario guardado = usuarioService.saveGestor(nuevoGestor);
            guardado.addToInmobiliaria(inmobiliaria.get());
            usuarioService.edit(guardado);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(inmobiliariaDtoConverter
                            .convertInmobiliariaToGetInmobiliariaDto(inmobiliaria.get()));
        }else if(inmobiliaria.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @DeleteMapping("/gestor/{id}")
    public ResponseEntity<?> eliminarGestor(@PathVariable UUID id,
                                            @AuthenticationPrincipal Usuario usuario){

        Usuario gestorLog = usuarioService.findGestorById(usuario.getId());
        Usuario gestor = usuarioService.findGestorById(id);

        if(gestor == null){
            return ResponseEntity.notFound().build();
        } else if(usuario.getRol().equals(UserRole.ADMIN) || gestor.getInmobiliaria()
                                                                        .getId()
                                                                        .equals(gestorLog.getInmobiliaria()
                                                                                .getId())){
            gestor.removeFromInmobiliaria(gestor.getInmobiliaria());
            usuarioService.delete(gestor);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @GetMapping("/{id}/gestor/")
    public ResponseEntity<List<GetUsuarioDto>> listarGestores(@PathVariable UUID id,
                                                              @AuthenticationPrincipal Usuario usuario){

        Optional<Inmobiliaria> inmobiliaria = inmobiliariaService.findById(id);

        if(inmobiliaria.isEmpty()){
            return ResponseEntity.notFound().build();
        }else if(usuario.getRol().equals(UserRole.ADMIN) || usuario.getInmobiliaria()
                                                                        .getId()
                                                                        .equals(inmobiliaria.get().getId())){
            return ResponseEntity.ok(
                    inmobiliaria.get().getGestores()
                            .stream()
                            .map(usuarioDtoConverter::convertUsuarioToUsuarioDto)
                            .collect(Collectors.toList())
            );
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @GetMapping("/")
    public ResponseEntity<List<GetInmobiliariaDto>> listarInmobiliarias(){

        List<Inmobiliaria> inmobiliarias = inmobiliariaService.findAll();

        if(inmobiliarias.isEmpty()){
            return ResponseEntity.noContent().build();
        } else{
            return ResponseEntity.ok(
                    inmobiliarias.stream()
                            .map(inmobiliariaDtoConverter::convertInmobiliariaToGetInmobiliariaDto)
                            .collect(Collectors.toList())
            );
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetInmobiliariaDto> buscarInmobiliaria(@PathVariable UUID id){

        Optional<Inmobiliaria> inmobiliaria = inmobiliariaService.findById(id);

        if(inmobiliaria.isEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(inmobiliariaDtoConverter
                    .convertInmobiliariaToGetInmobiliariaDto(inmobiliaria.get()));
        }
    }
}

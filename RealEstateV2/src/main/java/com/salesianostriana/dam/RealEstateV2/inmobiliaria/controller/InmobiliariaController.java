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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Crea una nueva inmobiliaria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha creado la inmobiliaria correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Inmobiliaria.class))}),
            @ApiResponse(responseCode = "400",
                    description = "La inmobiliaria no se ha creado correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Inmobiliaria.class))})
    })
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

    @Operation(summary = "Crea un usuario gestor y lo añade a la inmobiliaria especificada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha creado el gestor y se ha añadido correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Inmobiliaria.class))}),
            @ApiResponse(responseCode = "404",
                    description = "La inmobiliaria no se ha encontrado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Inmobiliaria.class))}),
            @ApiResponse(responseCode = "403",
                    description = "Acceso denegado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Inmobiliaria.class))})
    })
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

    @Operation(summary = "Elimina un gestor de la inmobiliaria especificada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400",
                    description = "Se ha eliminado al gestor correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Inmobiliaria.class))}),
            @ApiResponse(responseCode = "404",
                    description = "El gestor no se ha encontrado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Inmobiliaria.class))}),
            @ApiResponse(responseCode = "403",
                    description = "Acceso denegado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Inmobiliaria.class))})
    })
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

    @Operation(summary = "Muestra una lista de gestores de la inmobiliaria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se muestran los gestores correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Inmobiliaria.class))}),
            @ApiResponse(responseCode = "404",
                    description = "La inmobiliaria no se ha encontrado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Inmobiliaria.class))}),
            @ApiResponse(responseCode = "403",
                    description = "Acceso denegado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Inmobiliaria.class))})
    })
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

    @Operation(summary = "Muestra una lista de las inmobiliaria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se muestran las inmobiliarias correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Inmobiliaria.class))}),
            @ApiResponse(responseCode = "204",
                    description = "La lista de inmobiliarias está vacía",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Inmobiliaria.class))}),
            @ApiResponse(responseCode = "403",
                    description = "Acceso denegado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Inmobiliaria.class))})
    })
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

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarInmobiliaria(@PathVariable UUID id){

        Optional<Inmobiliaria> inmobiliaria = inmobiliariaService.findById(id);

        if(inmobiliaria.isEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            inmobiliaria.get()
                    .getListaViviendas()
                            .forEach(vivienda -> vivienda
                                    .removeFromInmobiliaria(inmobiliaria.get()));
            inmobiliariaService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
    }
}

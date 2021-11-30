package com.salesianostriana.dam.RealEstateV2.vivienda.controller;

import com.salesianostriana.dam.RealEstateV2.inmobiliaria.model.Inmobiliaria;
import com.salesianostriana.dam.RealEstateV2.inmobiliaria.service.InmobiliariaService;
import com.salesianostriana.dam.RealEstateV2.pagination.PaginationUtilsLinks;
import com.salesianostriana.dam.RealEstateV2.users.dto.interesa.CreateInteresaDto;
import com.salesianostriana.dam.RealEstateV2.users.dto.interesa.CreateInteresaSimpleDto;
import com.salesianostriana.dam.RealEstateV2.users.dto.interesa.GetInteresaDto;
import com.salesianostriana.dam.RealEstateV2.users.dto.interesa.InteresaDtoConverter;
import com.salesianostriana.dam.RealEstateV2.users.model.UserRole;
import com.salesianostriana.dam.RealEstateV2.users.model.Usuario;
import com.salesianostriana.dam.RealEstateV2.users.service.InteresaService;
import com.salesianostriana.dam.RealEstateV2.vivienda.dto.*;
import com.salesianostriana.dam.RealEstateV2.vivienda.model.Vivienda;
import com.salesianostriana.dam.RealEstateV2.vivienda.service.ViviendaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vivienda")
public class ViviendaController {

    private final ViviendaService viviendaService;
    private final InmobiliariaService inmobiliariaService;
    private final InteresaService interesaService;
    private final ViviendaDtoConverter viviendaDtoConverter;
    private final ViviendaListaDtoConverter viviendaListaDtoConverter;
    private final ViviendaConInteresDtoConverter viviendaConInteresDtoConverter;
    private final InteresaDtoConverter interesaDtoConverter;

    private final PaginationUtilsLinks paginationUtilsLinks;

    @PostMapping("/")
    public ResponseEntity<GetViviendaDto> addNuevaVivienda(@RequestBody CreateViviendaDto nuevaVivienda,
                                                           @AuthenticationPrincipal Usuario usuario){
        Vivienda vivienda = viviendaService.saveVivienda(nuevaVivienda, usuario);

        if(vivienda == null){
            return ResponseEntity.badRequest().build();
        }else{
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(viviendaDtoConverter.convertViviendaToViviendaDto(vivienda));
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> listarViviendasConFiltro(
            @RequestParam("tipo") Optional<String> tipo,
            @RequestParam("ciudad") Optional<String> ciudad,
            @RequestParam("codigoPostal") Optional<String> codigoPostal,
            @RequestParam("provincia") Optional<String> provincia,
            @RequestParam("numHabitaciones") Optional<Integer> numHabitaciones,
            @RequestParam("metrosCuadradosMin") Optional<Double> metrosCuadradosMin,
            @RequestParam("metrosCuadradosMax") Optional<Double> metrosCuadradosMax,
            @RequestParam("precioMin") Optional<Double> precioMin,
            @RequestParam("precioMax") Optional<Double> precioMax,
            @PageableDefault() Pageable pageable, HttpServletRequest request) {

        Page<Vivienda> result = viviendaService.findByArgs(tipo, ciudad, codigoPostal, provincia,
                numHabitaciones, metrosCuadradosMin, metrosCuadradosMax, precioMin, precioMax, pageable);

        if (result.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            UriComponentsBuilder uriBuilder = UriComponentsBuilder
                    .fromHttpUrl(request.getRequestURL().toString());
            return ResponseEntity
                    .ok()
                    .header("link", paginationUtilsLinks.createLinkHeader(result, uriBuilder))
                    .body(result.stream()
                            .map(viviendaListaDtoConverter::convertViviendaToGetViviendaListaDto)
                            .collect(Collectors.toList()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetViviendaDto> buscarVivienda(@PathVariable UUID id){
        Optional<Vivienda> vivienda = viviendaService.findById(id);

        if(vivienda.isEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity
                    .ok()
                    .body(viviendaDtoConverter.convertViviendaToViviendaDto(vivienda.get()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetViviendaDto> editarVivienda(@PathVariable UUID id,
                                                         @RequestBody CreateViviendaDto vivienda,
                                                         @AuthenticationPrincipal Usuario usuario){

        Optional<Vivienda> viviendaOptional = viviendaService.findById(id);

        if(viviendaOptional.isEmpty()){
            return ResponseEntity.notFound().build();
            }else {
                if (usuario.getRol().equals(UserRole.ADMIN)
                        || viviendaOptional.get().getPropietario().getId().equals(usuario.getId())) {
                    GetViviendaDto viviendaDto = GetViviendaDto.builder()
                                                    .id(id)
                                                    .titulo(vivienda.getTitulo())
                                                    .tipo(vivienda.getTipo())
                                                    .avatar(vivienda.getAvatar())
                                                    .precio(vivienda.getPrecio())
                                                    .codigoPostal(vivienda.getCodigoPostal())
                                                    .descripcion(vivienda.getDescripcion())
                                                    .direccion(vivienda.getDireccion())
                                                    .latlng(vivienda.getLatlng())
                                                    .metrosCuadrados(vivienda.getMetrosCuadrados())
                                                    .numBanyos(vivienda.getNumBanyos())
                                                    .numHabitaciones(vivienda.getNumHabitaciones())
                                                    .poblacion(vivienda.getPoblacion())
                                                    .provincia(vivienda.getProvincia())
                                                    .tieneAscensor(vivienda.isTieneAscensor())
                                                    .tieneGaraje(vivienda.isTieneGaraje())
                                                    .tienePiscina(vivienda.isTienePiscina())
                                                    .nombre_propietario(viviendaOptional.get().getPropietario().getNombre())
                                                    .avatar_propietario(viviendaOptional.get().getPropietario().getAvatar())
                                                    .email_propietario(viviendaOptional.get().getPropietario().getEmail())
                                                    .telefono_propietario(viviendaOptional.get().getPropietario().getTelefono())
                                                    .build();

                    viviendaService.edit(viviendaDtoConverter.CreateViviendaDtoToVivienda(vivienda,viviendaOptional.get()));

                    return ResponseEntity.status(HttpStatus.CREATED).body(viviendaDto);
                }
            }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarVivienda(@PathVariable UUID id,
                                              @AuthenticationPrincipal Usuario usuario) {
        Optional<Vivienda> viviendaOptional = viviendaService.findById(id);

        if (viviendaOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            if (usuario.getRol().equals(UserRole.ADMIN)
                    || viviendaOptional.get().getPropietario().getId().equals(usuario.getId())) {
                viviendaService.deleteById(id);
                return ResponseEntity.noContent().build();
            }
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PostMapping("/{id}/inmobiliaria/{id2}")
    public ResponseEntity<GetViviendaDto> establecerGestionInmobiliaria(@PathVariable UUID id,
                                                                        @PathVariable UUID id2,
                                                                        @AuthenticationPrincipal Usuario usuario) {
        Optional<Vivienda> viviendaOptional = viviendaService.findById(id);
        Optional<Inmobiliaria> inmobiliariaOptional = inmobiliariaService.findById(id2);

        if (viviendaOptional.isEmpty() || inmobiliariaOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            if (usuario.getRol().equals(UserRole.ADMIN)
                    || viviendaOptional.get().getPropietario().getId().equals(usuario.getId())) {
                Vivienda viviendaGestionada = viviendaService
                        .addGestionInmobiliaria(viviendaOptional.get(),inmobiliariaOptional.get());
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(viviendaDtoConverter.convertViviendaToViviendaDto(viviendaGestionada));
            }
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @DeleteMapping("/{id}/inmobiliaria/{id2}")
    public ResponseEntity<GetViviendaDto> eliminarGestionInmobiliaria(@PathVariable UUID id,
                                                                        @PathVariable UUID id2,
                                                                        @AuthenticationPrincipal Usuario usuario) {
        Optional<Vivienda> viviendaOptional = viviendaService.findById(id);
        Optional<Inmobiliaria> inmobiliariaOptional = inmobiliariaService.findById(id2);

        if (viviendaOptional.isEmpty() || inmobiliariaOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            if (usuario.getRol().equals(UserRole.ADMIN)
                    || viviendaOptional.get().getPropietario().getId().equals(usuario.getId())) {
                Vivienda viviendaNoGestionada = viviendaService
                        .removeGestionInmobiliaria(viviendaOptional.get(),inmobiliariaOptional.get());
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(viviendaDtoConverter.convertViviendaToViviendaDto(viviendaNoGestionada));
            }
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PostMapping("/{id}/meinteresa")
    public ResponseEntity<GetInteresaDto> addInteres(@PathVariable UUID id,
                                                     @RequestBody CreateInteresaSimpleDto mensaje,
                                                     @AuthenticationPrincipal Usuario usuario,
                                                     CreateInteresaDto nuevoInteres){
        Optional<Vivienda> vivienda = viviendaService.findById(id);

        if(vivienda.isEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(
                            interesaDtoConverter.convertInteresaToInteresaDto(
                                    interesaService.saveInteresa(nuevoInteres,mensaje,
                                            usuario,vivienda.get()))
                    );
        }
    }

    @DeleteMapping("/{id}/meinteresa/")
    public ResponseEntity<?> eliminarInteres(@PathVariable UUID id,
                                             @AuthenticationPrincipal Usuario usuario){

        Optional<Vivienda> vivienda = viviendaService.findById(id);

        if(vivienda.isEmpty()){
            return ResponseEntity.notFound().build();
        }else if(usuario.getRol().equals(UserRole.ADMIN)){
            vivienda.get().getListInteresa().clear();
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @GetMapping("/top")
    public ResponseEntity<List<GetViviendaListaDto>> top10Viviendas(){

        List<Vivienda> viviendas = viviendaService.top10ViviendasConMasInteres();

        if(viviendas.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(
                    viviendas.stream()
                            .map(viviendaListaDtoConverter::convertViviendaToGetViviendaListaDto)
                            .collect(Collectors.toList())
            );
        }
    }

    @GetMapping("/propietario")
    public ResponseEntity<List<GetViviendaListaDto>> listarViviendasPropietario(@AuthenticationPrincipal Usuario usuario){
        Optional<List<Vivienda>> viviendas = viviendaService.viviendasPropietario(usuario);

        if(viviendas.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(
                    viviendas.get().stream()
                            .map(viviendaListaDtoConverter::convertViviendaToGetViviendaListaDto)
                            .collect(Collectors.toList())
            );
        }
    }

    @GetMapping("/interesado")
    public ResponseEntity<List<GetViviendaConInteresDto>> listarViviendasConInteres(@AuthenticationPrincipal Usuario usuario){
        Optional<List<Vivienda>> viviendas = viviendaService.viviendasConInteres(usuario);

        if(viviendas.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(
                    viviendas.get().stream()
                            .map(viviendaConInteresDtoConverter::convertViviendaToGetViviendaConInteresDto)
                            .collect(Collectors.toList())
            );
        }
    }

}

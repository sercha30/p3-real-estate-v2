package com.salesianostriana.dam.RealEstateV2.vivienda.controller;

import com.salesianostriana.dam.RealEstateV2.pagination.PaginationUtilsLinks;
import com.salesianostriana.dam.RealEstateV2.users.model.Usuario;
import com.salesianostriana.dam.RealEstateV2.vivienda.dto.CreateViviendaDto;
import com.salesianostriana.dam.RealEstateV2.vivienda.dto.GetViviendaDto;
import com.salesianostriana.dam.RealEstateV2.vivienda.dto.ViviendaDtoConverter;
import com.salesianostriana.dam.RealEstateV2.vivienda.dto.ViviendaListaDtoConverter;
import com.salesianostriana.dam.RealEstateV2.vivienda.model.Vivienda;
import com.salesianostriana.dam.RealEstateV2.vivienda.service.ViviendaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vivienda")
public class ViviendaController {

    private final ViviendaService viviendaService;
    private final ViviendaDtoConverter viviendaDtoConverter;
    private final ViviendaListaDtoConverter viviendaListaDtoConverter;
    private final PaginationUtilsLinks paginationUtilsLinks;

    @PostMapping("/")
    public ResponseEntity<GetViviendaDto> addNuevaVivienda(@RequestBody CreateViviendaDto nuevaVivienda,
                                                           @AuthenticationPrincipal Usuario usuario){
        Vivienda vivienda = viviendaService.saveVivienda(nuevaVivienda, usuario);

        if(vivienda == null){
            return ResponseEntity.badRequest().build();
        }else{
            return ResponseEntity.ok(viviendaDtoConverter.convertViviendaToViviendaDto(vivienda));
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

}

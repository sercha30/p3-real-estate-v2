package com.salesianostriana.dam.RealEstateV2.vivienda.controller;

import com.salesianostriana.dam.RealEstateV2.users.model.Usuario;
import com.salesianostriana.dam.RealEstateV2.vivienda.dto.CreateViviendaDto;
import com.salesianostriana.dam.RealEstateV2.vivienda.dto.GetViviendaDto;
import com.salesianostriana.dam.RealEstateV2.vivienda.dto.ViviendaDtoConverter;
import com.salesianostriana.dam.RealEstateV2.vivienda.model.Vivienda;
import com.salesianostriana.dam.RealEstateV2.vivienda.service.ViviendaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vivienda")
public class ViviendaController {

    private final ViviendaService viviendaService;
    private final ViviendaDtoConverter viviendaDtoConverter;

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
}

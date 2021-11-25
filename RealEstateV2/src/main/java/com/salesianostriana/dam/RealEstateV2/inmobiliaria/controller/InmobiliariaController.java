package com.salesianostriana.dam.RealEstateV2.inmobiliaria.controller;

import com.salesianostriana.dam.RealEstateV2.inmobiliaria.dto.CreateInmobiliariaDto;
import com.salesianostriana.dam.RealEstateV2.inmobiliaria.dto.GetInmobiliariaDto;
import com.salesianostriana.dam.RealEstateV2.inmobiliaria.dto.InmobiliariaDtoConverter;
import com.salesianostriana.dam.RealEstateV2.inmobiliaria.model.Inmobiliaria;
import com.salesianostriana.dam.RealEstateV2.inmobiliaria.service.InmobiliariaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/inmobiliaria")
public class InmobiliariaController {

    private final InmobiliariaService inmobiliariaService;
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
}

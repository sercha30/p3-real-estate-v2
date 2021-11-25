package com.salesianostriana.dam.RealEstateV2.inmobiliaria.controller;

import com.salesianostriana.dam.RealEstateV2.inmobiliaria.service.InmobiliariaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/inmobiliaria")
public class InmobiliariaController {

    private final InmobiliariaService inmobiliariaService;


}

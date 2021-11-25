package com.salesianostriana.dam.RealEstateV2.inmobiliaria.service;

import com.salesianostriana.dam.RealEstateV2.inmobiliaria.dto.CreateInmobiliariaDto;
import com.salesianostriana.dam.RealEstateV2.inmobiliaria.model.Inmobiliaria;
import com.salesianostriana.dam.RealEstateV2.inmobiliaria.repos.InmobiliariaRepository;
import com.salesianostriana.dam.RealEstateV2.users.service.base.BaseService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InmobiliariaService extends BaseService<Inmobiliaria, UUID, InmobiliariaRepository> {

    public Inmobiliaria saveInmobiliaria(CreateInmobiliariaDto nuevaInmobiliaria){
        Inmobiliaria inmobiliaria = Inmobiliaria.builder()
                .nombre(nuevaInmobiliaria.getNombre())
                .email(nuevaInmobiliaria.getEmail())
                .telefono(nuevaInmobiliaria.getTelefono())
                .build();
        return save(inmobiliaria);
    }
}

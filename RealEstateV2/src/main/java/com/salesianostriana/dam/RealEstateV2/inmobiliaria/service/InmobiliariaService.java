package com.salesianostriana.dam.RealEstateV2.inmobiliaria.service;

import com.salesianostriana.dam.RealEstateV2.inmobiliaria.model.Inmobiliaria;
import com.salesianostriana.dam.RealEstateV2.inmobiliaria.repos.InmobiliariaRepository;
import com.salesianostriana.dam.RealEstateV2.users.service.base.BaseService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InmobiliariaService extends BaseService<Inmobiliaria, UUID, InmobiliariaRepository> {
}

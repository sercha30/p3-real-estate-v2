package com.salesianostriana.dam.RealEstateV2.inmobiliaria.repos;

import com.salesianostriana.dam.RealEstateV2.inmobiliaria.model.Inmobiliaria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InmobiliariaRepository extends JpaRepository<Inmobiliaria, UUID> {
}

package com.salesianostriana.dam.RealEstateV2.vivienda.repos;

import com.salesianostriana.dam.RealEstateV2.vivienda.model.Vivienda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ViviendaRepository extends JpaRepository<Vivienda, UUID> {
}

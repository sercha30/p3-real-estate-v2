package com.salesianostriana.dam.RealEstateV2.vivienda.service;

import com.salesianostriana.dam.RealEstateV2.inmobiliaria.model.Inmobiliaria;
import com.salesianostriana.dam.RealEstateV2.users.model.Usuario;
import com.salesianostriana.dam.RealEstateV2.vivienda.dto.CreateViviendaDto;
import com.salesianostriana.dam.RealEstateV2.vivienda.model.Vivienda;
import com.salesianostriana.dam.RealEstateV2.vivienda.repos.ViviendaRepository;
import com.salesianostriana.dam.RealEstateV2.vivienda.service.base.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ViviendaService extends BaseService<Vivienda, UUID, ViviendaRepository> {

    public Vivienda saveVivienda(CreateViviendaDto nuevaVivienda, Usuario usuario){
        Vivienda vivienda = Vivienda.builder()
                .avatar(nuevaVivienda.getAvatar())
                .codigoPostal(nuevaVivienda.getCodigoPostal())
                .descripcion(nuevaVivienda.getDescripcion())
                .direccion(nuevaVivienda.getDireccion())
                .latlng(nuevaVivienda.getLatlng())
                .metrosCuadrados(nuevaVivienda.getMetrosCuadrados())
                .numBanyos(nuevaVivienda.getNumBanyos())
                .numHabitaciones(nuevaVivienda.getNumHabitaciones())
                .poblacion(nuevaVivienda.getPoblacion())
                .precio(nuevaVivienda.getPrecio())
                .provincia(nuevaVivienda.getProvincia())
                .tieneAscensor(nuevaVivienda.isTieneAscensor())
                .tieneGaraje(nuevaVivienda.isTieneGaraje())
                .tienePiscina(nuevaVivienda.isTienePiscina())
                .propietario(usuario)
                .tipo(nuevaVivienda.getTipo())
                .titulo(nuevaVivienda.getTitulo())
                .build();
        return save(vivienda);
    }

    public Page<Vivienda> findByArgs(final Optional<String> tipo,
                                     final Optional<String> ciudad,
                                     final Optional<String> codigoPostal,
                                     final Optional<String> provincia,
                                     final Optional<Integer> numHabitaciones,
                                     final Optional<Double> metrosCuadradosMin,
                                     final Optional<Double> metrosCuadradosMax,
                                     final Optional<Double> precioMin,
                                     final Optional<Double> precioMax,
                                     Pageable pageable) {

        Specification<Vivienda> specTipoVivienda = (root, query, criteriaBuilder) -> {
            if (tipo.isPresent()) {
                return criteriaBuilder.like(criteriaBuilder.lower(root.get("tipo")),
                        "%" + tipo.get() + "%");
            } else {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
        };

        Specification<Vivienda> specCiudadVivienda = (root, query, criteriaBuilder) -> {
            if (ciudad.isPresent()) {
                return criteriaBuilder.like(criteriaBuilder.lower(root.get("ciudad")),
                        "%" + ciudad.get() + "%");
            } else {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
        };

        Specification<Vivienda> specCodigoPostalVivienda = (root, query, criteriaBuilder) -> {
            if (codigoPostal.isPresent()) {
                return criteriaBuilder.like(criteriaBuilder.lower(root.get("codigoPostal")),
                        "%" + codigoPostal.get() + "%");
            } else {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
        };

        Specification<Vivienda> specProvinciaVivienda = (root, query, criteriaBuilder) -> {
            if (provincia.isPresent()) {
                return criteriaBuilder.like(criteriaBuilder.lower(root.get("provincia")),
                        "%" + provincia.get() + "%");
            } else {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
        };

        Specification<Vivienda> specNumHabitacionesMenorQue = (root, query, criteriaBuilder) -> {
            if (numHabitaciones.isPresent()) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("numHabitaciones"), numHabitaciones.get());
            } else {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
        };

        Specification<Vivienda> specNumHabitacionesMayorQue = (root, query, criteriaBuilder) -> {
            if (numHabitaciones.isPresent()) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("numHabitaciones"), numHabitaciones.get());
            } else {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
        };

        Specification<Vivienda> specMetrosCuadradosMenorQue = (root, query, criteriaBuilder) -> {
            if (metrosCuadradosMax.isPresent()) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("metrosCuadrados"), metrosCuadradosMax.get());
            } else {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
        };

        Specification<Vivienda> specMetrosCuadradosMayorQue = (root, query, criteriaBuilder) -> {
            if (metrosCuadradosMin.isPresent()) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("metrosCuadrados"), metrosCuadradosMin.get());
            } else {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
        };

        Specification<Vivienda> specPrecioMenorQue = (root, query, criteriaBuilder) -> {
            if (precioMax.isPresent()) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("precio"), precioMax.get());
            } else {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
        };


        Specification<Vivienda> specPrecioMayorQue = (root, query, criteriaBuilder) -> {
            if(precioMin.isPresent()){
                return criteriaBuilder.greaterThanOrEqualTo(root.get("precio"),precioMin.get());
            }
            else{
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
        };

        Specification<Vivienda> todos = specCiudadVivienda
                .and(specCodigoPostalVivienda)
                .and(specMetrosCuadradosMayorQue)
                .and(specMetrosCuadradosMenorQue)
                .and(specNumHabitacionesMayorQue)
                .and(specNumHabitacionesMenorQue)
                .and(specPrecioMayorQue)
                .and(specPrecioMenorQue)
                .and(specProvinciaVivienda)
                .and(specTipoVivienda);

        return this.repositorio.findAll(todos,pageable);

    }

    public Vivienda addGestionInmobiliaria(Vivienda vivienda,Inmobiliaria inmobiliaria){
        vivienda.addToInmobiliaria(inmobiliaria);

        return save(vivienda);
    }

    public Vivienda removeGestionInmobiliaria(Vivienda vivienda,Inmobiliaria inmobiliaria){
        vivienda.removeFromInmobiliaria(inmobiliaria);

        return save(vivienda);
    }

}

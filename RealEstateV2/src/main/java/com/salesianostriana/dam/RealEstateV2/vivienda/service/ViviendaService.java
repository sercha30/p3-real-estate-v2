package com.salesianostriana.dam.RealEstateV2.vivienda.service;

import com.salesianostriana.dam.RealEstateV2.users.model.Usuario;
import com.salesianostriana.dam.RealEstateV2.vivienda.dto.CreateViviendaDto;
import com.salesianostriana.dam.RealEstateV2.vivienda.model.Vivienda;
import com.salesianostriana.dam.RealEstateV2.vivienda.repos.ViviendaRepository;
import com.salesianostriana.dam.RealEstateV2.vivienda.service.base.BaseService;
import org.springframework.stereotype.Service;

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
}

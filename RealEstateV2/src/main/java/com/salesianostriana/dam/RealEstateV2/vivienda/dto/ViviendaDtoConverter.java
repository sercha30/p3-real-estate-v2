package com.salesianostriana.dam.RealEstateV2.vivienda.dto;

import com.salesianostriana.dam.RealEstateV2.vivienda.model.Vivienda;
import org.springframework.stereotype.Component;

@Component
public class ViviendaDtoConverter {

    public GetViviendaDto convertViviendaToViviendaDto(Vivienda vivienda){
        return GetViviendaDto.builder()
                .titulo(vivienda.getTitulo())
                .tipo(vivienda.getTipo())
                .avatar(vivienda.getAvatar())
                .id(vivienda.getId())
                .precio(vivienda.getPrecio())
                .codigoPostal(vivienda.getCodigoPostal())
                .descripcion(vivienda.getDescripcion())
                .direccion(vivienda.getDireccion())
                .latlng(vivienda.getLatlng())
                .metrosCuadrados(vivienda.getMetrosCuadrados())
                .numBanyos(vivienda.getNumBanyos())
                .numHabitaciones(vivienda.getNumHabitaciones())
                .poblacion(vivienda.getPoblacion())
                .provincia(vivienda.getProvincia())
                .tieneAscensor(vivienda.isTieneAscensor())
                .tieneGaraje(vivienda.isTieneGaraje())
                .tienePiscina(vivienda.isTienePiscina())
                .avatar_propietario(vivienda.getPropietario().getAvatar())
                .nombre_propietario(vivienda.getPropietario().getNombre() + " " + vivienda.getPropietario().getApellidos())
                .telefono_propietario(vivienda.getPropietario().getTelefono())
                .email_propietario(vivienda.getPropietario().getEmail())
                .build();
    }
}

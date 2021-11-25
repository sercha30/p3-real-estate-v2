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
                .nombre_inmobiliaria(vivienda.getInmobiliaria() == null ?
                        "Particular" : vivienda.getInmobiliaria().getNombre())
                .build();
    }

    public Vivienda CreateViviendaDtoToVivienda(CreateViviendaDto createViviendaDto, Vivienda vivienda){
        return new Vivienda(
                vivienda.getId(),
                createViviendaDto.getTitulo(),
                createViviendaDto.getAvatar(),
                createViviendaDto.getDescripcion(),
                createViviendaDto.getCodigoPostal(),
                createViviendaDto.getLatlng(),
                createViviendaDto.getDireccion(),
                createViviendaDto.getPoblacion(),
                createViviendaDto.getProvincia(),
                createViviendaDto.getTipo(),
                createViviendaDto.getPrecio(),
                createViviendaDto.getNumHabitaciones(),
                createViviendaDto.getMetrosCuadrados(),
                createViviendaDto.getNumBanyos(),
                createViviendaDto.isTieneAscensor(),
                createViviendaDto.isTieneGaraje(),
                createViviendaDto.isTienePiscina(),
                vivienda.getInmobiliaria(),
                vivienda.getPropietario(),
                vivienda.getListInteresa()
        );
    }
}

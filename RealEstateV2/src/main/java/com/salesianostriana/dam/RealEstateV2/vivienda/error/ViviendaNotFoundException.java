package com.salesianostriana.dam.RealEstateV2.vivienda.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ViviendaNotFoundException extends RuntimeException{

    public ViviendaNotFoundException(String message) {
        super(message);
    }

}

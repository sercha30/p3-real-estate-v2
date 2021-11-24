package com.salesianostriana.dam.RealEstateV2.users.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class InteresaPK implements Serializable {

    private UUID vivienda_id;
    private UUID usuario_id;
}

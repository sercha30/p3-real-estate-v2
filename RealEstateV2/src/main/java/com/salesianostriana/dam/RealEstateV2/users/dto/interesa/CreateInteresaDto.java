package com.salesianostriana.dam.RealEstateV2.users.dto.interesa;

import com.salesianostriana.dam.RealEstateV2.users.model.Usuario;
import com.salesianostriana.dam.RealEstateV2.vivienda.model.Vivienda;
import lombok.*;

import javax.persistence.Lob;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class CreateInteresaDto {

    private Vivienda vivienda;
    private Usuario interesado;
    private LocalDateTime createdDate;

    @Lob
    private String mensaje;
}

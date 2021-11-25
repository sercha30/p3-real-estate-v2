package com.salesianostriana.dam.RealEstateV2.users.dto.interesa;

import com.salesianostriana.dam.RealEstateV2.users.model.InteresaPK;
import lombok.*;

import javax.persistence.Lob;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class GetInteresaDto {

    private InteresaPK id;
    private String titulo_vivienda;
    private String nombre_interesado;
    private LocalDateTime createdDate;

    @Lob
    private String mensaje;
}

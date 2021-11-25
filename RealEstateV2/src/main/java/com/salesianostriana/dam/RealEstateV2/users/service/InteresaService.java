package com.salesianostriana.dam.RealEstateV2.users.service;

import com.salesianostriana.dam.RealEstateV2.users.dto.interesa.CreateInteresaDto;
import com.salesianostriana.dam.RealEstateV2.users.dto.interesa.CreateInteresaSimpleDto;
import com.salesianostriana.dam.RealEstateV2.users.model.Interesa;
import com.salesianostriana.dam.RealEstateV2.users.model.InteresaPK;
import com.salesianostriana.dam.RealEstateV2.users.model.Usuario;
import com.salesianostriana.dam.RealEstateV2.users.repos.InteresaRepository;
import com.salesianostriana.dam.RealEstateV2.users.service.base.BaseService;
import com.salesianostriana.dam.RealEstateV2.vivienda.model.Vivienda;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class InteresaService extends BaseService<Interesa, InteresaPK, InteresaRepository> {

    public Interesa saveInteresa(CreateInteresaDto nuevoInteres,
                                 CreateInteresaSimpleDto mensaje,
                                 Usuario usuario,
                                 Vivienda vivienda){
        Interesa interesa = Interesa.builder()
                .interesado(usuario)
                .vivienda(vivienda)
                .createdDate(LocalDateTime.now())
                .mensaje(mensaje.getMensaje())
                .build();
        return save(interesa);
    }
}

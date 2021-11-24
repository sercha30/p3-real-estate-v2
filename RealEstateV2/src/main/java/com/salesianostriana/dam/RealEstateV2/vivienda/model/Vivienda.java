package com.salesianostriana.dam.RealEstateV2.vivienda.model;

import com.salesianostriana.dam.RealEstateV2.inmobiliaria.model.Inmobiliaria;
import com.salesianostriana.dam.RealEstateV2.users.model.Interesa;
import com.salesianostriana.dam.RealEstateV2.users.model.Usuario;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Vivienda implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator",
            parameters = {
                    @Parameter(
                            name = "uuid_gen_strategy_class",
                            value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
                    )
            }
    )
    private UUID id;

    private String titulo;

    @Lob
    private String descripcion;

    private String avatar;

    private String latlng;

    private String direccion;

    private String codigoPostal;

    private String poblacion;

    private String provincia;

    private ViviendaTipo tipo;

    private Double precio;

    private Integer numHabitaciones;

    private Double metrosCuadrados;

    private Integer numBanyos;

    private boolean tienePiscina;

    private boolean tieneAscensor;

    private boolean tieneGaraje;

    @ManyToOne
    private Inmobiliaria inmobiliaria;

    @ManyToOne
    private Usuario propietario;

    @Builder.Default
    @OneToMany(mappedBy = "vivienda")
    private List<Interesa> listInteresa = new ArrayList<>();


}

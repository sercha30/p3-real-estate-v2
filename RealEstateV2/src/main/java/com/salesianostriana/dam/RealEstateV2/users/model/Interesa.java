package com.salesianostriana.dam.RealEstateV2.users.model;

import com.salesianostriana.dam.RealEstateV2.vivienda.model.Vivienda;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Interesa implements Serializable {

    @Builder.Default
    @EmbeddedId
    private InteresaPK id = new InteresaPK();

    @ManyToOne
    @MapsId("vivienda_id")
    @JoinColumn(name = "vivienda_id")
    private Vivienda vivienda;

    @ManyToOne
    @MapsId("usuario_id")
    @JoinColumn(name = "usuario_id")
    private Usuario interesado;

    @CreatedDate
    private LocalDateTime createdDate;

    @Lob
    private String mensaje;

    //HELPERS
    //**************************************************

    public void addToVivienda(Vivienda v){
        this.vivienda = v;
        v.getListInteresa().add(this);
    }

    public void deleteFromVivienda(Vivienda v){
        v.getListInteresa().remove(this);
        this.vivienda = null;
    }

    public void addToInteresado(Usuario u){
        this.interesado = u;
        u.getListInteresa().add(this);
    }

    public void deleteFromInteresado(Usuario u){
        u.getListInteresa().remove(this);
        this.interesado = null;
    }

    public void addViviendaToInteresado(Vivienda v,Usuario u){
        addToVivienda(v);
        addToInteresado(u);
    }

    public void deleteViviendaFromInteresado(Vivienda v,Usuario u){
        deleteFromVivienda(v);
        deleteFromInteresado(u);
    }

    //******************************************************
}

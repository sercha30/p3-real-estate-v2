package com.salesianostriana.dam.RealEstateV2.vivienda.repos;

import com.salesianostriana.dam.RealEstateV2.vivienda.model.Vivienda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ViviendaRepository extends JpaRepository<Vivienda, UUID>, JpaSpecificationExecutor<Vivienda> {

    @Query(value = """
                SELECT *
                FROM Vivienda v
                WHERE v.id IN (SELECT v1.id
                               FROM Vivienda v1 JOIN Interesa i ON v1.id = i.vivienda_id
                               GROUP BY v1.id
                               ORDER BY COUNT(*) DESC
                               LIMIT 10)
                """
    ,nativeQuery = true)
    List<Vivienda> viviendasMasInteresantes();

    @Query(value = """
            SELECT *
            FROM Vivienda v
            WHERE v.propietario_id = :usuario_id
            """
    ,nativeQuery = true)
    Optional<List<Vivienda>> viviendasPropietario(@Param("usuario_id") UUID usuario_id);

    @Query(value = """
            SELECT com.salesianostriana.dam.RealEstateV2.vivienda.dto.GetViviendaConInteresDto(
                v.id,v.titulo,v.avatar,v.precio,v.propietario.nombre,v.propietario.avatar,(
                    SELECT COUNT(*)
                    FROM Interesa i
                    WHERE i.vivienda.id = v.id AND i.usuario.id = :usuario_id))
                FROM Vivienda v
            """)
    Optional<List<Vivienda>> viviendasConInteres(@Param("usuario_id") UUID usuario_id);
}

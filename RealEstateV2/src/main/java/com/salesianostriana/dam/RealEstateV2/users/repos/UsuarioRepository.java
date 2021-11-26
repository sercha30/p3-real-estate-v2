package com.salesianostriana.dam.RealEstateV2.users.repos;

import com.salesianostriana.dam.RealEstateV2.users.model.UserRole;
import com.salesianostriana.dam.RealEstateV2.users.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    Optional<Usuario> findFirstByEmail(String email);

    Optional<List<Usuario>> findAllByRol(UserRole rol);

    @Query(value = """
            SELECT distinct u
            FROM Usuario u
            WHERE u.id in(SELECT interesado.id
                            FROM Interesa i)
            """
    )
    Optional<List<Usuario>> findAllInteresados();

    @Query(value = """
            SELECT u
            FROM Usuario u
            WHERE u.id = :interesado_id
            """)
    Optional<Usuario> findInteresado(@Param("interesado_id") UUID id);
}

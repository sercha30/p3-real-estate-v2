package com.salesianostriana.dam.RealEstateV2.users.repos;

import com.salesianostriana.dam.RealEstateV2.users.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    Optional<Usuario> findFirstByEmail(String email);
}

package com.salesianostriana.dam.RealEstateV2.users.service;

import com.salesianostriana.dam.RealEstateV2.users.dto.CreateUsuarioDto;
import com.salesianostriana.dam.RealEstateV2.users.model.UserRole;
import com.salesianostriana.dam.RealEstateV2.users.model.Usuario;
import com.salesianostriana.dam.RealEstateV2.users.repos.UsuarioRepository;
import com.salesianostriana.dam.RealEstateV2.users.service.base.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service("userDetailsService")
@RequiredArgsConstructor
public class UsuarioService extends BaseService<Usuario, UUID, UsuarioRepository> implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.repositorio.findFirstByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email + " no encontrado."));
    }

    public Usuario save(CreateUsuarioDto nuevoUsuario) {
        if(nuevoUsuario.getPassword().contentEquals(nuevoUsuario.getPassword2())){
            Usuario usuario = Usuario.builder()
                    .password(passwordEncoder.encode(nuevoUsuario.getPassword()))
                    .apellidos(nuevoUsuario.getApellidos())
                    .avatar(nuevoUsuario.getAvatar())
                    .email(nuevoUsuario.getEmail())
                    .nombre(nuevoUsuario.getNombre())
                    .rol(UserRole.PROPIETARIO)
                    .build();
            return save(usuario);
        }else{
            return null;
        }
    }
}

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

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service("userDetailsService")
@RequiredArgsConstructor
public class UsuarioService extends BaseService<Usuario, UUID, UsuarioRepository> implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.repositorio.findFirstByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email + " no encontrado."));
    }

    public Usuario savePropietario(CreateUsuarioDto nuevoPropietario) {
        if(nuevoPropietario.getPassword().contentEquals(nuevoPropietario.getPassword2())){
            Usuario usuario = Usuario.builder()
                    .password(passwordEncoder.encode(nuevoPropietario.getPassword()))
                    .apellidos(nuevoPropietario.getApellidos())
                    .avatar(nuevoPropietario.getAvatar())
                    .email(nuevoPropietario.getEmail())
                    .nombre(nuevoPropietario.getNombre())
                    .rol(UserRole.PROPIETARIO)
                    .build();
            return save(usuario);
        }else{
            return null;
        }
    }

    public Usuario saveGestor(CreateUsuarioDto nuevoGestor) {
        if(nuevoGestor.getPassword().contentEquals(nuevoGestor.getPassword2())){
            Usuario usuario = Usuario.builder()
                    .password(passwordEncoder.encode(nuevoGestor.getPassword()))
                    .apellidos(nuevoGestor.getApellidos())
                    .avatar(nuevoGestor.getAvatar())
                    .email(nuevoGestor.getEmail())
                    .nombre(nuevoGestor.getNombre())
                    .rol(UserRole.GESTOR)
                    .build();
            return save(usuario);
        }else{
            return null;
        }
    }

    public Usuario saveAdmin(CreateUsuarioDto nuevoAdmin) {
        if(nuevoAdmin.getPassword().contentEquals(nuevoAdmin.getPassword2())){
            Usuario usuario = Usuario.builder()
                    .password(passwordEncoder.encode(nuevoAdmin.getPassword()))
                    .apellidos(nuevoAdmin.getApellidos())
                    .avatar(nuevoAdmin.getAvatar())
                    .email(nuevoAdmin.getEmail())
                    .nombre(nuevoAdmin.getNombre())
                    .rol(UserRole.ADMIN)
                    .build();
            return save(usuario);
        }else{
            return null;
        }
    }

    public List<Usuario> findAllPropietarios() {
        if(repositorio.findAll().isEmpty()){
            return null;
        }else {
            return repositorio.findAll()
                    .stream()
                    .filter(usuario -> usuario.getRol().name().contentEquals("PROPIETARIO"))
                    .collect(Collectors.toList());
        }
    }

}

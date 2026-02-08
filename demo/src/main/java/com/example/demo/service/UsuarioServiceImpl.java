package com.example.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.modelo.Usuario;
import com.example.demo.modelo.Rol;
import com.example.demo.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Usuario crear(String nombre, String contrasenaEnClaro, Rol rol) {
        if (usuarioRepository.findByNombre(nombre) != null) {
            throw new IllegalArgumentException("Ya existe un usuario con ese nombre");
        }
        Usuario u = new Usuario();
        u.setNombre(nombre);
        u.setContrasena(passwordEncoder.encode(contrasenaEnClaro));
        u.setRol(rol);
        return usuarioRepository.save(u);
    }

    @Override
    public Usuario obtenerPorNombre(String nombre) {
        return usuarioRepository.findByNombre(nombre);
    }

    @Override
    public Usuario obtenerPorId(Long id) {
        return usuarioRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con id=" + id));
    }

    @Override
    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    @Override
    public Page<Usuario> listar(Pageable pageable) {
        return usuarioRepository.findAll(pageable);
    }

    @Override
    public Usuario actualizar(Long id, String nuevoNombre, Rol nuevoRol) {
        Usuario u = obtenerPorId(id);
        u.setNombre(nuevoNombre);
        u.setRol(nuevoRol);
        return usuarioRepository.save(u);
    }

    @Override
    public void cambiarContrasena(Long id, String contrasenaActualEnClaro, String nuevaContrasenaEnClaro) {
        Usuario u = obtenerPorId(id);
        if (!passwordEncoder.matches(contrasenaActualEnClaro, u.getContrasena())) {
            throw new IllegalArgumentException("La contraseña actual no es correcta");
        }
        u.setContrasena(passwordEncoder.encode(nuevaContrasenaEnClaro));
        usuarioRepository.save(u);
    }

    @Override
    public void eliminar(Long id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public Usuario obtenerUsuarioConectado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null
                && !(authentication instanceof AnonymousAuthenticationToken)
                && authentication.isAuthenticated()) {

            String nombreUsuarioConectado = authentication.getName();
            return usuarioRepository.findByNombre(nombreUsuarioConectado);
        }
        return null;
    }
}

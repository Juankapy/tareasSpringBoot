package com.example.demo.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.demo.modelo.Album;
import com.example.demo.modelo.Usuario;
import com.example.demo.modelo.Rol;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.service.AlbumService;

@Component
public class IniciarDatos implements CommandLineRunner {

    private final AlbumService albumService;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public IniciarDatos(AlbumService albumService,
                        UsuarioRepository usuarioRepository,
                        PasswordEncoder passwordEncoder) {
        this.albumService = albumService;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private void crearUsuarioSiNoExiste(String nombre, String contrasenaEnClaro, Rol rol) {
        if (usuarioRepository.findByNombre(nombre) != null) return;

        Usuario u = new Usuario();
        u.setNombre(nombre);
        u.setContrasena(passwordEncoder.encode(contrasenaEnClaro));
        u.setRol(rol);

        usuarioRepository.save(u);
    }

    @Override
    public void run(String... args) throws Exception {

        // Crear usuarios
        crearUsuarioSiNoExiste("admin", "admin123", Rol.ADMIN);
        crearUsuarioSiNoExiste("manager", "manager123", Rol.MANAGER);
        crearUsuarioSiNoExiste("usuario", "usuario123", Rol.USUARIO);

        // Crear algunos albums si está vacío (opcional, para tener datos)
        if (albumService.listarTodos().isEmpty()) {
            Album a1 = new Album();
            a1.setTitulo("Abbey Road");
            a1.setArtista("The Beatles");
            a1.setGenero("Rock");
            albumService.guardar(a1);

            Album a2 = new Album();
            a2.setTitulo("Thriller");
            a2.setArtista("Michael Jackson");
            a2.setGenero("Pop");
            albumService.guardar(a2);
        }
    }
}

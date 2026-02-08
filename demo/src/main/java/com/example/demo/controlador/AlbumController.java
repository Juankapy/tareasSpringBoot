package com.example.demo.controlador;

import com.example.demo.modelo.Album;
import com.example.demo.modelo.Usuario;
import com.example.demo.service.AlbumService;
import com.example.demo.service.GeneroService;
import com.example.demo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Controller
@RequestMapping("/albums")
public class AlbumController {

    private final AlbumService albumService;
    private final GeneroService generoService;
    private final UsuarioService usuarioService;

    @Autowired
    public AlbumController(AlbumService albumService, GeneroService generoService, UsuarioService usuarioService) {
        this.albumService = albumService;
        this.generoService = generoService;
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String listar(@RequestParam(value = "texto", required = false) String texto,
                         @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                         @RequestParam(value = "size", required = false, defaultValue = "5") int size,
                         Model model) {

        Usuario usuario = usuarioService.obtenerUsuarioConectado();
        model.addAttribute("usuario", usuario);

        Pageable pageable = PageRequest.of(page, size);
        Page<Album> pageAlbums;

        if (texto == null || texto.isEmpty()) {
            pageAlbums = albumService.listarPaginado(pageable);
        } else {
            pageAlbums = albumService.buscarPorTituloPaginado(texto, pageable);
            model.addAttribute("texto", texto);
        }

        model.addAttribute("listado", pageAlbums);
        return "lista_albums";
    }

    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        Usuario usuario = usuarioService.obtenerUsuarioConectado();
        model.addAttribute("usuario", usuario);
        model.addAttribute("album", new Album());
        model.addAttribute("listaGeneros", generoService.listarTodos());
        return "form_album";
    }

    @PostMapping("/guardar")
    public String guardarAlbum(@ModelAttribute Album album) {
        albumService.guardar(album);
        return "redirect:/albums";
    }

    @GetMapping("/editar/{id}")
    public String editarFormulario(@PathVariable Long id, Model model) {
        Usuario usuario = usuarioService.obtenerUsuarioConectado();
        model.addAttribute("usuario", usuario);
        Album album = albumService.buscarPorId(id);
        model.addAttribute("album", album);
        model.addAttribute("listaGeneros", generoService.listarTodos());
        return "form_album";
    }

    @GetMapping("/borrar/{id}")
    public String borrarAlbum(@PathVariable Long id) {
        albumService.eliminar(id);
        return "redirect:/albums";
    }
}

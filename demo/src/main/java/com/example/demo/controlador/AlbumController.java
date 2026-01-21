package com.example.demo.controlador;

import com.example.demo.modelo.Album;
import com.example.demo.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/albums")
public class AlbumController {

    @Autowired
    private AlbumService albumService;
    @GetMapping
    public String listar(@RequestParam(value = "texto", required = false) String texto, Model model) {
        List<Album> todosLosDiscos = albumService.listarTodos();
        if (texto == null || texto.isEmpty()) {
            model.addAttribute("listaAlbums", todosLosDiscos);
            return "lista_albums";
        }
        List<Album> listaFiltrada = new ArrayList<>();

        for (Album album : todosLosDiscos) {
            if (album.getTitulo().toLowerCase().contains(texto.toLowerCase())) {
                listaFiltrada.add(album);
            }
        }

        model.addAttribute("listaAlbums", listaFiltrada);
        return "lista_albums";
    }

    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("album", new Album());
        return "form_album";
    }

    @PostMapping("/guardar")
    public String guardarAlbum(@ModelAttribute Album album) {
        albumService.guardar(album);
        return "redirect:/albums";
    }

    @GetMapping("/editar/{id}")
    public String editarFormulario(@PathVariable Long id, Model model) {
        Album album = albumService.buscarPorId(id);
        model.addAttribute("album", album);
        return "form_album";
    }

    @GetMapping("/borrar/{id}")
    public String borrarAlbum(@PathVariable Long id) {
        albumService.eliminar(id);
        return "redirect:/albums";
    }
}

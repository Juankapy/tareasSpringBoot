package com.example.demo.controlador;

import com.example.demo.modelo.Album;
import com.example.demo.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/albums")
public class AlbumController {

    @Autowired
    private AlbumService albumService;
    @GetMapping
    public String listar(@RequestParam(value = "texto", required = false) String texto,
                         @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                         @RequestParam(value = "size", required = false, defaultValue = "5") int size,
                         Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Album> pageAlbums;

        if (texto == null || texto.isEmpty()) {
            pageAlbums = albumService.listarPaginado(pageable);
        } else {
            pageAlbums = albumService.buscarPorTituloPaginado(texto, pageable);
            model.addAttribute("texto", texto);
        }

        model.addAttribute("listaAlbums", pageAlbums.getContent());
        model.addAttribute("currentPage", pageAlbums.getNumber());
        model.addAttribute("totalPages", pageAlbums.getTotalPages());
        model.addAttribute("totalItems", pageAlbums.getTotalElements());
        model.addAttribute("pageSize", pageAlbums.getSize());

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

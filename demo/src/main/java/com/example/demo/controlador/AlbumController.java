// language: java
package com.example.demo.controlador;

import com.example.demo.modelo.Album;
import com.example.demo.service.AlbumService;
import com.example.demo.service.GeneroService;
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

    @Autowired
    private AlbumService albumService;

    @Autowired
    private GeneroService generoService;

    @GetMapping
    public String listar(@RequestParam(value = "texto", required = false) String texto,
                         @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                         @RequestParam(value = "size", required = false, defaultValue = "5") int size,
                         Model model) {

        Pageable pageableForTotal = PageRequest.of(0, size);
        Page<Album> albumPageForTotals;
        if (texto == null || texto.isEmpty()) {
            albumPageForTotals = albumService.listarPaginado(pageableForTotal);
        } else {
            albumPageForTotals = albumService.buscarPorTituloPaginado(texto, pageableForTotal);
        }
        int albumTotalPages = albumPageForTotals.getTotalPages();

        int displayedTotalPages = Math.max(albumTotalPages, 1) + 1;

        model.addAttribute("pageSize", size);
        model.addAttribute("totalItems", albumPageForTotals.getTotalElements());
        model.addAttribute("totalPages", displayedTotalPages);
        model.addAttribute("currentPage", page);

        model.addAttribute("isGenresPage", false);

        if (page == 1) {
            model.addAttribute("isGenresPage", true);
            model.addAttribute("listaGeneros", generoService.listarTodos());
            if (texto != null && !texto.isEmpty()) {
                model.addAttribute("texto", texto);
            }
            return "lista_albums";
        }

        int pageIndexForAlbums = (page > 1) ? (page - 1) : page;
        if (pageIndexForAlbums < 0) pageIndexForAlbums = 0;

        Pageable pageable = PageRequest.of(pageIndexForAlbums, size);
        Page<Album> pageAlbums;

        if (texto == null || texto.isEmpty()) {
            pageAlbums = albumService.listarPaginado(pageable);
        } else {
            pageAlbums = albumService.buscarPorTituloPaginado(texto, pageable);
            model.addAttribute("texto", texto);
        }

        model.addAttribute("listaAlbums", pageAlbums.getContent());
        return "lista_albums";
    }

    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("album", new Album());
        model.addAttribute("listaGeneros", generoService.listarTodos());
        return "form_album";
    }

    @PostMapping("/guardar")
    public String guardarAlbum(@ModelAttribute Album album) {
        albumService.guardar(album);
        // redirige a la página principal de albums (página 0)
        return "redirect:/albums?page=0";
    }

    @GetMapping("/editar/{id}")
    public String editarFormulario(@PathVariable Long id, Model model) {
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

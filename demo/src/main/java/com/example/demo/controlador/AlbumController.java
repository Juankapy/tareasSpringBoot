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

        // calculamos total de páginas de albums según size, respetando filtro de texto si existe
        Pageable pageableForTotal = PageRequest.of(0, size);
        Page<Album> albumPageForTotals;
        if (texto == null || texto.isEmpty()) {
            albumPageForTotals = albumService.listarPaginado(pageableForTotal);
        } else {
            albumPageForTotals = albumService.buscarPorTituloPaginado(texto, pageableForTotal);
        }
        int albumTotalPages = albumPageForTotals.getTotalPages();

        // Insertamos una página EXTRA en la segunda posición (índice 1) para mostrar géneros
        int displayedTotalPages = Math.max(albumTotalPages, 1) + 1; // asegurar al menos 2 páginas visibles (albums + géneros)

        model.addAttribute("pageSize", size);
        model.addAttribute("totalItems", albumPageForTotals.getTotalElements());
        model.addAttribute("totalPages", displayedTotalPages);
        model.addAttribute("currentPage", page);

        // Si la página solicitada es la segunda página visible (page == 1), mostramos solo los géneros
        if (page == 1) {
            model.addAttribute("isGenresPage", true);
            model.addAttribute("listaGeneros", generoService.listarTodos());
            if (texto != null && !texto.isEmpty()) {
                model.addAttribute("texto", texto);
            }
            return "lista_albums";
        }

        // Mapear el page solicitado a la página real de albums
        int pageIndexForAlbums = (page > 1) ? (page - 1) : page; // si page>1, restamos 1
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
        // currentPage ya está puesto según la página visible solicitada
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
        return "redirect:/albums";
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

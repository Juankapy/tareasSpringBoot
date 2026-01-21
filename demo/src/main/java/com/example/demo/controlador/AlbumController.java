package com.example.demo.controlador;
import com.example.demo.modelo.Album;
import com.example.demo.repository.AlbumRepository; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/albums")
public class AlbumController {

    @Autowired
    private AlbumRepository albumRepository;

    // Listar todos los álbumes
    // Ruta: GET /albums
    @GetMapping
    public String listarAlbums(Model model) {
        // Buscamos todos los álbumes en la BD y los guardamos en el modelo
        model.addAttribute("listaAlbums", albumRepository.findAll());
        return "lista_albums"; // Esto busca el archivo lista_albums.html en templates
    }

    // Crear Album
    // Ruta: GET /albums/nuevo
    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("album", new Album());
        return "form_album";
    }

    // Editar Album
    // Ruta: GET /albums/editar/{id}
    @GetMapping("/editar/{id}")
    public String editarFormulario(@PathVariable Long id, Model model) {
        Album album = albumRepository.findById(id).orElse(null);
        model.addAttribute("album", album);
        return "form_album";
    }

    // Guardar el álbum
    // Ruta: POST /albums/guardar
    @PostMapping("/guardar")
    public String guardarAlbum(@ModelAttribute Album album) {
        albumRepository.save(album);
        return "redirect:/albums"; // Vuelve a la lista
    }

    //  Borrar un álbum
    // Ruta: GET /albums/borrar/{id}
    @GetMapping("/borrar/{id}")
    public String borrarAlbum(@PathVariable Long id) {
        albumRepository.deleteById(id);
        return "redirect:/albums";
    }
}

// language: java
package com.example.demo.controlador;

import com.example.demo.modelo.Genero;
import com.example.demo.service.GeneroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/generos")
public class GeneroController {

    @Autowired
    private GeneroService generoService;

    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("genero", new Genero());
        return "form_genero";
    }

    @PostMapping("/guardar")
    public String guardarGenero(@ModelAttribute Genero genero) {
        generoService.guardar(genero);
        // redirige a la "página de géneros" dentro del paginador de albums (page=1)
        return "redirect:/albums?page=1";
    }
}

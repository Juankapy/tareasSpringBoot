package com.example.demo.controlador;

import com.example.demo.modelo.Genero;
import com.example.demo.service.GeneroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/generos")
public class GeneroController {

    @Autowired
    private GeneroService generoService;

    @GetMapping
    public String listar(@RequestParam(value = "page", defaultValue = "0") int page,
                         @RequestParam(value = "size", defaultValue = "2") int size,
                         Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Genero> listado = generoService.listarPaginado(pageable);
        model.addAttribute("listado", listado);
        return "lista_generos";
    }

    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("genero", new Genero());
        return "form_genero";
    }

    @PostMapping("/guardar")
    public String guardarGenero(@ModelAttribute Genero genero) {
        generoService.guardar(genero);
        return "redirect:/generos";
    }
}

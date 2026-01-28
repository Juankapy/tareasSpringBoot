package com.example.demo.controlador;

import com.example.demo.modelo.Genero;
import com.example.demo.service.GeneroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/generos")
public class    GeneroController {

    
    @Autowired
    private GeneroService generoService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("listaGeneros", generoService.listarTodos());
        return "lista_generos";
    }

    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("genero", new Genero());
        return "form_genero";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Genero genero) {
        generoService.guardar(genero);
        return "redirect:/generos";
    }

    @GetMapping("/borrar/{id}")
    public String borrar(@PathVariable Long id) {
        generoService.eliminar(id);
        return "redirect:/generos";
    }
}

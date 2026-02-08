package com.example.demo.service;

import com.example.demo.modelo.Genero;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface GeneroService {
    List<Genero> listarTodos();
    Page<Genero> listarPaginado(Pageable pageable);
    Genero buscarPorId(Long id);
    void guardar(Genero genero);
    void eliminar(Long id);
}

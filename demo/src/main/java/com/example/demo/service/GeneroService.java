package com.example.demo.service;

import com.example.demo.modelo.Genero;

import java.util.List;

public interface GeneroService {
    List<Genero> listarTodos();
    Genero buscarPorId(Long id);
    void guardar(Genero genero);
    void eliminar(Long id);
}

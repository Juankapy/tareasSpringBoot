package com.example.demo.service;

import com.example.demo.modelo.Genero;
import com.example.demo.repository.GeneroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeneroServiceImpl implements GeneroService {

    @Autowired
    private GeneroRepository generoRepository;

    @Override
    public List<Genero> listarTodos() {
        return generoRepository.findAll();
    }

    @Override
    public Genero buscarPorId(Long id) {
        return generoRepository.findById(id).orElse(null);
    }

    @Override
    public void guardar(Genero genero) {
        generoRepository.save(genero);
    }

    @Override
    public void eliminar(Long id) {
        generoRepository.deleteById(id);
    }
}

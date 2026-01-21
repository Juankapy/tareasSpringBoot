package com.example.demo.service;

import com.example.demo.modelo.Album;
import com.example.demo.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AlbumService {

    @Autowired
    private AlbumRepository albumRepository;

    public List<Album> listarTodos() {
        return albumRepository.findAll();
    }

    public List<Album> buscarPorTitulo(String texto) {
        return albumRepository.findByTituloContainingIgnoreCase(texto);
    }

    public void guardar(Album album) {
        albumRepository.save(album);
    }

    public Album buscarPorId(Long id) {
        return albumRepository.findById(id).orElse(null);
    }

    public void eliminar(Long id) {
        albumRepository.deleteById(id);
    }
}

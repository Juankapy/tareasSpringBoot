package com.example.demo.service;

import com.example.demo.modelo.Album;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AlbumService {

    List<Album> listarTodos();

    List<Album> buscarPorTitulo(String texto);

    void guardar(Album album);

    Album buscarPorId(Long id);

    void eliminar(Long id);

    Page<Album> listarPaginado(Pageable pageable);

    Page<Album> buscarPorTituloPaginado(String texto, Pageable pageable);
}

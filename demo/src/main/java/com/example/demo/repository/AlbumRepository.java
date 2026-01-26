package com.example.demo.repository;

import com.example.demo.modelo.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
    List<Album> findByTituloContainingIgnoreCase(String texto);

    Page<Album> findByTituloContainingIgnoreCase(String texto, Pageable pageable);
}

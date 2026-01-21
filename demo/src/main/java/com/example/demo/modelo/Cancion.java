package com.example.demo.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "canciones")
public class Cancion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private Integer duracionSegundos;

    // Relación: Muchas canciones pertenecen a un álbum
    @ManyToOne
    @JoinColumn(name = "album_id") // Esto crea la Foreign Key en la BD
    private Album album;

    public Cancion() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public Integer getDuracionSegundos() { return duracionSegundos; }
    public void setDuracionSegundos(Integer duracionSegundos) { this.duracionSegundos = duracionSegundos; }
    public Album getAlbum() { return album; }
    public void setAlbum(Album album) { this.album = album; }
}

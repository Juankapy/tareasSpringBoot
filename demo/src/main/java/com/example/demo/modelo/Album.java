package com.example.demo.modelo;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "albums")
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String artista;
    private String genero;

    // Relación: Un álbum tiene muchas canciones
    // 'mappedBy' debe coincidir con el nombre de la variable en Cancion.java
    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cancion> canciones;

    public Album() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getArtista() { return artista; }
    public void setArtista(String artista) { this.artista = artista; }
    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }
    public List<Cancion> getCanciones() { return canciones; }
    public void setCanciones(List<Cancion> canciones) { this.canciones = canciones; }
}

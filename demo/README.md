# CRUD MVC con Thymeleaf — RA3

## 1) Datos del alumno/a
- Musica

## 2) Repositorio (fork) y gestión de versiones
- Repositorio base: https://github.com/profeInformatica101/tareasSpringBoot
- Enlace a MI fork: [https://github.com/Juankapy/tareasSpringBoot]
- Nº de commits realizados: (mínimo 5)

## 3) Arquitectura
Explica brevemente cómo has organizado:
- Controller:
- Service:
- Repository:
- Entity:

## 4) Base de datos elegida (marca una)
- [ ] H2
- [x] MySQL
- [ ] PostgreSQL

## 5) Configuración de la base de datos
### 5.1 Dependencias añadidas
- Spring Web
- Thymeleaf
- Spring Data JPA
- Validation
- Spring Boot Dev Tools
- MySQL Driver

### 5.2 application.properties / application.yml
#### 1. La URL de conexión (cambia 'musica_db' por el nombre real de tu base de datos)
spring.datasource.url=jdbc:mysql://localhost:3307/musica_db?useSSL=false&serverTimezone=UTC

##### 2. Tus credenciales de MySQL
spring.datasource.username=root
spring.datasource.password=

##### 3. Configuración de JPA (Para que Spring gestione las tablas)
##### #'update': crea/actualiza tablas automáticamente según tus entidades Java
spring.jpa.hibernate.ddl-auto=update

###### 'show-sql': Muestra en la consola las consultas que hace Spring (útil para aprender)
spring.jpa.show-sql=true

###### Especificar el dialecto (Opcional en versiones nuevas, pero recomendado para evitar errores)
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect


### 5.3 Pasos para crear la BD (si aplica)
- MySQL:
- CREATE DATABASE IF NOT EXISTS musica_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
  USE musica_db;
- CREATE TABLE IF NOT EXISTS albums (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  titulo VARCHAR(255) NOT NULL,
  artista VARCHAR(255) NOT NULL,
  anio_lanzamiento INT,
  genero VARCHAR(100),
  -- Restricción para evitar álbumes duplicados (mismo artista y título)
  CONSTRAINT uk_titulo_artista UNIQUE (titulo, artista)
  ) ENGINE=InnoDB;

- CREATE TABLE IF NOT EXISTS canciones (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  titulo VARCHAR(255) NOT NULL,
  duracion_segundos INT COMMENT 'Duración en segundos',
  track_number INT COMMENT 'Número de pista en el álbum',
  album_id BIGINT NOT NULL,
  -- Clave Foránea: Relación Muchos a Uno con albums
  -- ON DELETE CASCADE: Si se borra el álbum, se borran sus canciones
  CONSTRAINT fk_canciones_album FOREIGN KEY (album_id)
  REFERENCES albums(id)
  ON DELETE CASCADE
  ) ENGINE=InnoDB;

- CREATE INDEX idx_cancion_album ON canciones(album_id);


## 6) Cómo ejecutar el proyecto
1. Requisitos ()
2. Comando de arranque:
3. URL de acceso:
   - http://localhost:8080/albums

## 7) Pantallas / Rutas MVC
- GET /entidad (listar) -> GET /albums
- GET /entidad/nuevo (formulario alta) -> GET /albums/nuevo
- GET /entidad/{id}/editar (editar) -> GET /albums/editar/{id}
- POST /entidad/{id}/borrar (eliminar) -> GET /albums/borrar/{id}
- POST /entidad/{id}/guardar (guardar) ->  GET /albums/borrar/{id}


## 8) Mejoras extra (opcional)
- Validaciones
- Estilos Bootstrap ✔️
- Búsqueda
- Pruebas
- Paginación

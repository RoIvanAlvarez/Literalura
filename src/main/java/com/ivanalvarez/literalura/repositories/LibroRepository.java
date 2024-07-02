package com.ivanalvarez.literalura.repositories;

import com.ivanalvarez.literalura.model.Idiomas;
import com.ivanalvarez.literalura.model.Libros;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibroRepository extends JpaRepository<Libros, Long> {
    Optional<Libros> findByTituloContainsIgnoreCase(String titulo);
    List<Libros> findByIdioma(Idiomas idioma);
    List<Libros> findTop10ByOrderByDescargasDesc();
}

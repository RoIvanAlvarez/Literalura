package com.ivanalvarez.literalura.repositories;

import com.ivanalvarez.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> findByNombreContainsIgnoreCase(String nombreAutor);
    @Query("SELECT a FROM Autor a WHERE a.fFall = :fFall")
    List<Autor> findAutorFechaFall(int fFall);
}

package com.ivanalvarez.literalura.services;

import com.ivanalvarez.literalura.model.Idiomas;
import com.ivanalvarez.literalura.model.Libros;
import com.ivanalvarez.literalura.repositories.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    public void save(Libros libro){
        libroRepository.save(libro);
    }

    public List<Libros> findByIdioma(String idiomaBuscado){
        Idiomas idioma = Idiomas.fromString(idiomaBuscado);
        return libroRepository.findByIdioma(idioma);
    }
}

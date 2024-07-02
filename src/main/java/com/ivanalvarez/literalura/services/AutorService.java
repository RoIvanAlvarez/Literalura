package com.ivanalvarez.literalura.services;

import com.ivanalvarez.literalura.model.Autor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ivanalvarez.literalura.repositories.AutorRepository;


import java.util.List;
import java.util.Optional;

@Service
public class AutorService {

    @Autowired
    private AutorRepository autorRepository;

    public void save(Autor autor){
        autorRepository.save(autor);
    }

    public List<Autor> findAutorFechaFall(int fFall){
        return autorRepository.findAutorFechaFall(fFall);
    }
}

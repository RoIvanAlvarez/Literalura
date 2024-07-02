package com.ivanalvarez.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibrosResultado(
        @JsonAlias("results") List<DatosLibro> libros
        ) {
}

package com.ivanalvarez.literalura.model;

import jakarta.persistence.*;

@Entity
public class Libros {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    @ManyToOne
    @JoinColumn(name = "autor_id", nullable = false)
    private Autor autor;
    @Enumerated(EnumType.STRING)
    private Idiomas idioma;
    private int descargas;

    public Libros(){

    }

    public Libros(DatosLibro datosLibro){
        this.titulo = datosLibro.titulo();
        this.autor = new Autor(datosLibro.autores().get(0));
        this.idioma = Idiomas.fromString(datosLibro.idioma().get(0));
        this.descargas = datosLibro.descargas();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Idiomas getIdioma() {
        return idioma;
    }

    public void setIdioma(Idiomas idioma) {
        this.idioma = idioma;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public int getDescargas() {
        return descargas;
    }

    public void setDescargas(int descargas) {
        this.descargas = descargas;
    }

    @Override
    public String toString() {
        return
                "titulo='" + titulo + '\'' +
                ", autor= " + autor +
                ", idioma= " + idioma +
                ", descargas= " + descargas;
    }
}

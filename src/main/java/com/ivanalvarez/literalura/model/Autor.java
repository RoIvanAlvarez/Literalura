package com.ivanalvarez.literalura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private int fNac;
    private int fFall;
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libros> libros = new ArrayList<>();

    public Autor(){

    }

    public Autor(DatosAutor autor){
        this.nombre = autor.nombre();
        this.fNac = autor.fNac();
        this.fFall = autor.fFall();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getfNac() {
        return fNac;
    }

    public void setfNac(int fNac) {
        this.fNac = fNac;
    }

    public int getfFall() {
        return fFall;
    }

    public void setfFall(int fFall) {
        this.fFall = fFall;
    }

    public List<Libros> getLibros() {
        return libros;
    }

    public void setLibros(List<Libros> libros) {
        libros.forEach(l -> l.setAutor(this));
        this.libros = libros;
    }

    @Override
    public String toString() {
        return
                "nombre='" + nombre + '\'' +
                ", fNac= " + fNac +
                ", fFall= " + fFall +
                ", libros= " + libros;
    }
}

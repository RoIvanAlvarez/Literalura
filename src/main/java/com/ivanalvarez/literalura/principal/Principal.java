package com.ivanalvarez.literalura.principal;

import com.ivanalvarez.literalura.model.*;
import com.ivanalvarez.literalura.repositories.AutorRepository;
import com.ivanalvarez.literalura.repositories.LibroRepository;
import com.ivanalvarez.literalura.services.ConsumoAPI;
import com.ivanalvarez.literalura.services.ConvertirDatos;
import org.springframework.beans.factory.annotation.Autowired;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private Scanner sc = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private final String URL = "https://gutendex.com/books/?search=";
    private ConvertirDatos conversor = new ConvertirDatos();
    @Autowired
    private LibroRepository libroRepository;
    @Autowired
    private AutorRepository autorRepository;

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void muestraMenu() {

        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    \n
                    1.- Buscar libro por titulo 
                    2.- Mostrar libros registrados
                    3.- Mostrar autores registrados
                    4.- Mostrar autores vivos en determinado año
                    5.- Mostrar libros por idioma     
                    6.- Top 10 más descargados     
                    0.- Salir
                    """;
            System.out.println(menu);
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibro();
                    break;
                case 2:
                    mostrarLibrosRegistrados();
                    break;
                case 3:
                    mostrarAutoresRegistrados();
                    break;
                case 4:
                    mostrarAutoresVivosPorFecha();
                    break;
                case 5:
                    busquedaPorIdioma();
                    break;
                case 6:
                    top10Descargas();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    private void buscarLibro() {
        System.out.println("Escribe el nombre del libro que deseas guardar:");
        var nombreLibro = sc.nextLine();

        try{
            String encodeParam = URLEncoder.encode(nombreLibro, "UTF-8");
            String json = consumoAPI.obtenerDatos(URL + encodeParam);
            var datos = conversor.obtenerDatos(json, DatosLibrosResultado.class);
            Optional<DatosLibro> libroBuscado = datos.libros().stream()
                    .filter(e -> e.titulo().toUpperCase().contains(nombreLibro.toUpperCase())).findFirst();
            System.out.println(libroBuscado);
            if (libroBuscado.isPresent()){
                try {
                    List<Libros> libroEncontrado = libroBuscado.stream().map(Libros::new).collect(Collectors.toList());
                    Autor autorLibro = libroBuscado.stream().flatMap(l -> l.autores().stream().map(Autor::new)).
                            findFirst().orElse(null);
                    if (autorLibro == null){
                        System.out.println("No se encontro autor del libro");
                        return;
                    }
                    Optional<Autor> autorEncontrado = autorRepository.findByNombreContainsIgnoreCase(autorLibro.getNombre());
                    Optional<Libros> libroRegistrado = libroRepository.findByTituloContainsIgnoreCase(nombreLibro);

                    if (libroRegistrado.isPresent()){
                        System.out.println("El libro ya esta registrado");
                    } else {
                        Autor autor;
                        if (autorEncontrado.isPresent()){
                            autor = autorEncontrado.get();
                            System.out.println("El autor ya esta registrado");
                        } else {
                            autor = autorLibro;
                            autorRepository.save(autor);
                        }
                        for (Libros libro : libroEncontrado){
                            libro.setAutor(autor);
                            libroRepository.save(libro);
                            System.out.printf("\n Titulo: %s " + "\n" + " Autor: %s " + "\n" +
                                            " Idioma: %s " + "\n" + " Descargas: %s"  + "\n",
                                    libro.getTitulo(), libro.getAutor().getNombre(), libro.getIdioma(), libro.getDescargas());
                        }
                    }
                } catch (Exception e){
                    System.out.println("ERROR: " + e.getMessage());
                }
            } else {
                System.out.println("Libro no encontrado");
            }
        } catch (UnsupportedEncodingException e){
            throw new RuntimeException(e);
        }
    }

    public void mostrarLibrosRegistrados(){
        List<Libros> libros = libroRepository.findAll();
        libros.stream()
                .sorted(Comparator.comparing(Libros::getTitulo))
                .forEach(l -> System.out.printf("\n Titulo: %s " + "\n" + " Autor: %s " + "\n" +
                                " Idioma: %s " + "\n" + " Descargas: %s"  + "\n",
                        l.getTitulo(), l.getAutor().getNombre(), l.getIdioma(), l.getDescargas()));
    }

    public void mostrarAutoresRegistrados(){
        List<Autor> autores = autorRepository.findAll();
        autores.stream()
                .sorted(Comparator.comparing(Autor::getNombre))
                .forEach(a -> System.out.printf("\n Nombre: %s " + "\n" + " Año de nacimiento: %s " + "\n" +
                                " Año de fallecimiento: %s " + "\n",
                        a.getNombre(), a.getfNac(), a.getfFall()));
    }

    public void mostrarAutoresVivosPorFecha(){
        System.out.println("Ingrese el año por el cual desea buscar los autores: ");
        var fecha = sc.nextInt();
        sc.nextLine();

        List<Autor> autores = autorRepository.findAutorFechaFall(fecha);
        autores.stream()
                .sorted(Comparator.comparing(Autor::getNombre))
                .forEach(a -> System.out.printf("\n Nombre: %s " + "\n" + " Año de nacimiento: %s " + "\n" +
                                " Año de fallecimiento: %s " + "\n",
                        a.getNombre(), a.getfNac(), a.getfFall()));
    }

    public void busquedaPorIdioma(){
        System.out.println("""
                Escribe el idioma de los libros que desea buscar, use :
                es para español
                en para ingles
                fr para frances
                pt para portugues
                """);
        var idioma = sc.nextLine();
        var idiomaBuscado = Idiomas.fromString(idioma);
        List<Libros> librosPorIdioma = libroRepository.findByIdioma(idiomaBuscado);
        System.out.println("Los libros en el idioma " + idiomaBuscado + " son:");
        librosPorIdioma.forEach(l -> System.out.printf("\n Titulo: %s " + "\n" + " Autor: %s " + "\n" +
                        " Idioma: %s " + "\n" + " Descargas: %s"  + "\n",
                l.getTitulo(), l.getAutor().getNombre(), l.getIdioma(), l.getDescargas()));
    }

    public void top10Descargas(){
        List<Libros> topLibros = libroRepository.findTop10ByOrderByDescargasDesc();
        topLibros.forEach(l -> System.out.printf("\n Titulo: %s " + "\n" + " Autor: %s " + "\n" +
                        " Idioma: %s " + "\n" + " Descargas: %s"  + "\n",
                l.getTitulo(), l.getAutor().getNombre(), l.getIdioma(), l.getDescargas()));
    }

}

package prog3.tpe.entrega1.arraystest;

import prog3.tpe.entrega1.LibroInterface;

import java.util.ArrayList;
import java.util.LinkedList;

public class LibroWithArray implements LibroInterface {

    private String titulo;
    private String autor;
    private String cantidadPaginas;
    private ArrayList<String> generos;

    public LibroWithArray (String titulo, String autor, String cantidadPagina) {
        this.titulo = titulo;
        this.autor = autor;
        this.cantidadPaginas = cantidadPagina;
        this.generos = new ArrayList<String>();
    }

    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return this.autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getCantidadPaginas() {
        return this.cantidadPaginas;
    }

    public void setCantidadPaginas(String cantidadPaginas) {
        this.cantidadPaginas = cantidadPaginas;
    }

    public ArrayList<String> getGeneros() {
        return this.generos;
    }

    public void addGenero(String genero) {
        this.generos.add(genero);
    }
}

package prog3.tpe.entrega1;

import java.util.LinkedList;

public class Genero {

	private String genero;
	private Genero left;
	private Genero right;
	private LinkedList<Libro> libros;

	public Genero(String genero) {
		this.genero = genero;
		this.left = null;
		this.right = null;
		this.libros = new LinkedList<>();
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public Genero getLeft() {
		return left;
	}

	public void setLeft(Genero left) {
		this.left = left;
	}

	public Genero getRight() {
		return right;
	}

	public void setRight(Genero right) {
		this.right = right;
	}
	
	public LinkedList<Libro> getLibros() {
		return this.libros;
	}
	
	public void addLibro(Libro libro) {
		this.libros.add(libro);
	}
	
	
	
}

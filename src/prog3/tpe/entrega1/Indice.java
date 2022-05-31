package prog3.tpe.entrega1;

import java.util.LinkedList;

public class Indice {
	
	private Genero generoRoot;
	
	public Indice() {
		this.generoRoot = null;
	}
	
	public Indice(String genero) {
		this.generoRoot = new Genero(genero);
	}	
	
	public void insertarLibro(Libro libro) {
		for (String genero: libro.getGeneros()) {
			if(this.isEmpty()) {
				this.generoRoot = new Genero(genero);
				generoRoot.addLibro(libro);
			} else {
				this.insertarLibro(libro, this.generoRoot, genero);
			}
		}	
	}
	
	
	private void insertarLibro(Libro libro, Genero generoActual, String genero) {
		if (generoActual.getGenero().compareTo(genero) < 0) {
			if (generoActual.getLeft() == null) { 
				Genero temp = new Genero(genero);
				temp.addLibro(libro);
				generoActual.setLeft(temp);
			} else {
				insertarLibro(libro, generoActual.getLeft(), genero);
			}
			
		} else if (generoActual.getGenero().compareTo(genero) > 0) {
			if (generoActual.getRight() == null) { 
				Genero temp = new Genero(genero);
				temp.addLibro(libro);
				generoActual.setRight(temp);
			} else {
				insertarLibro(libro, generoActual.getRight(), genero);
			}
		} else if(generoActual.getGenero().equals(genero)) {
			generoActual.addLibro(libro);
		}
	}
	
	public boolean isEmpty() {		
		return this.generoRoot == null;
	}
	
	
	//RECORRIDO PREORDER --> imprimo, izquierda, derecha
	public void printPreOrder(){
		printPreOrder(this.generoRoot);
	}
	
	private void printPreOrder(Genero genero){
		if (genero == null) {
			System.out.println(" - ");
		} else {
			String libros = "";
			for (Libro libro : genero.getLibros()) {
				libros += libro.getTitulo()+", ";
			}
			System.out.println(genero.getGenero()+" ");
			System.out.println("Libros: "+libros);
			printPreOrder(genero.getLeft());
			printPreOrder(genero.getRight());
		}
	}
	
	public LinkedList<Libro> getLibrosPorGenero(String genero){
		LinkedList<Libro> libros = new LinkedList<>();
		if(!this.isEmpty()) {
			libros = getLibrosPorGenero(genero, this.generoRoot);
		}
		
		return libros;
	}
	
	private LinkedList<Libro> getLibrosPorGenero(String genero, Genero generoActual){
		LinkedList<Libro> libros = new LinkedList<>();
				
		if(generoActual.getGenero().equals(genero)) {
			libros = generoActual.getLibros();
		} else if(generoActual.getGenero().compareTo(genero) < 0){
			if(generoActual.getLeft() != null) {
				libros = getLibrosPorGenero(genero, generoActual.getLeft());
			} 
		} else if(generoActual.getGenero().compareTo(genero) > 0) {
			if(generoActual.getRight() != null) {
				libros = getLibrosPorGenero(genero, generoActual.getRight());
			}
		}				
		return libros;
	}

}

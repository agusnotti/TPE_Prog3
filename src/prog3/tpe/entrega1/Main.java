package prog3.tpe.entrega1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String csvFile = "C:\\Users\\meagu\\Desktop\\TUDAI\\[PROG 3]\\PROG3 - 2022\\TPE - 2022\\datasets\\dataset1.csv";
		LinkedList<Libro> books = readFile(csvFile);
		
		Indice indice = new Indice();
		
		for (Libro libro : books) {
			indice.insertarLibro(libro);
		}
		
		//indice.printPreOrder();
		
		System.out.println(" ");
		
		LinkedList<Libro> booksitos = new LinkedList<Libro>();
		booksitos = indice.getLibrosPorGenero("thriller");
		
		Iterator<Libro> iterator = booksitos.iterator();
		
		System.out.println("Titulo libros de thriller: ");
		while(iterator.hasNext()) {
			System.out.println(iterator.next().getTitulo());
		}
		
		

	}

	public static LinkedList<Libro> readFile(String pathFile){
		
		LinkedList<Libro> books = new LinkedList<Libro>();
		String line = "";
		String cvsSplitBy = ",";

		try (BufferedReader br = new BufferedReader(new FileReader(pathFile))) {
			//Salta la primer linea que contiene los titulos
        	br.readLine();
			while ((line = br.readLine()) != null) {

				String[] items = line.split(cvsSplitBy);
				Libro book = new Libro(items[0], items[1],items[2]);
				String[] genres = items[3].split(" ");
				for (String genre : genres) {
					book.addGenero(genre);
				}

				books.add(book);

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return books;
	}

}

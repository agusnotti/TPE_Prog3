package prog3.tpe.entrega1;

import prog3.tpe.entrega1.arraystest.LibroWithArray;
import prog3.tpe.utils.Timer;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Main {

	static BufferedWriter bw = null;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String csvFile = "D:\\05-TUDAI\\Progra 3\\datasets\\dataset2.csv";
//		String csvFile = "C:\\Users\\meagu\\Desktop\\TUDAI\\[PROG 3]\\PROG3 - 2022\\TPE - 2022\\datasets\\dataset1.csv";
		String genero = "thriller";
		asLinkedListTest(csvFile, genero);
		asArrayTest(csvFile, genero);

	}

	public static void asLinkedListTest(String filePath, String generoBuscado) {
		LinkedList<Libro> books = readFile(filePath);
		Indice indice = new Indice();
		Timer timer = new Timer();
		System.out.println(" ");

		timer.start();
		for (Libro libro : books) {
			indice.insertarLibro(libro);
		}
		double time = timer.stop();
		System.out.println("LinkedList insertion time: " + time);

		//indice.printPreOrder();


		LinkedList<LibroInterface> booksitos = new LinkedList<LibroInterface>();
		timer.start();
		booksitos = indice.getLibrosPorGenero(generoBuscado);
		time = timer.stop();
		System.out.println("LinkedList iteration time: " + time);

		Iterator<LibroInterface> iterator = booksitos.iterator();

//		System.out.println("Titulo libros de " + generoBuscado + ": ");
//		while(iterator.hasNext()) {
//			System.out.println(iterator.next().getTitulo());
//		}
		try {
			generateCsv(booksitos);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void asArrayTest(String filePath, String generoBuscado) {
		ArrayList<LibroWithArray> books = readFileAsArray(filePath);
		List<LibroInterface> filteredBooks = new ArrayList<>();
		Timer timer = new Timer();
		timer.start();
		for (LibroWithArray book: books) {
			for (String gen: book.getGeneros()) {
				if (gen.equals(generoBuscado)) {
					filteredBooks.add(book);
				}
			}
		}
		double time = timer.stop();
		System.out.println("ArrayList iteration time: " + time);

		try {
			generateCsv(filteredBooks);
		} catch (Exception e) {
			e.printStackTrace();
		}

		ArrayList<LibroWithArray> booksCopy = new ArrayList<>();
		timer.start();
		for (LibroWithArray book: books) {
			booksCopy.add(book);
		}
		time = timer.stop();
		System.out.println("ArrayList insertion time: " + time);

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

	public static ArrayList<LibroWithArray> readFileAsArray(String pathFile){

		ArrayList<LibroWithArray> books = new ArrayList<>();
		String line = "";
		String cvsSplitBy = ",";

		try (BufferedReader br = new BufferedReader(new FileReader(pathFile))) {
			//Salta la primer linea que contiene los titulos
			br.readLine();
			while ((line = br.readLine()) != null) {
				String[] items = line.split(cvsSplitBy);
				LibroWithArray book = new LibroWithArray(items[0], items[1],items[2]);
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

	public static String convertToCSV(LibroInterface data) {
		String gens = "";
		for (String gen: data.getGeneros()) {
			gens += gen + " ";
		}
		return data.getAutor() + "," + data.getTitulo() + "," + data.getCantidadPaginas() + "," + gens;
	}

	public static void generateCsv(List<LibroInterface> dataLines) throws IOException {
		File file = new File("output.csv");
		try (PrintWriter pw = new PrintWriter(file)) {
			dataLines.stream()
					.map(Main::convertToCSV)
					.forEach(pw::println);
		}
	}

}

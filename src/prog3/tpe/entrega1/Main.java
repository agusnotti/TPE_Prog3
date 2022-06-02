package prog3.tpe.entrega1;

import org.w3c.dom.Node;
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

		String context = "datasets/";
		File folder = new File(context);
		String[] files = folder.list();
		String genero = "thriller";
		assert files != null;
		for (String file: files) {
			String csvFile = context + file;
			System.out.println("********************");
			System.out.println("READING FILE: " + file);
			System.out.println("Total elements: " + readFile(csvFile).size());
			asLinkedListTest(csvFile, genero);
			asArrayTest(csvFile, genero);
		}
		Timer t = new Timer();
		t.start();
		final Node<String> newNode = new Node<>(null, null, null);
		double time = t.stop();
		System.out.println("\n\nLinkedList estimated creation time for each node: " + time);

	}

	private static class Node<E> {
		E item;
		Node<E> next;
		Node<E> prev;

		Node(Node<E> prev, E element, Node<E> next) {
			this.item = element;
			this.next = next;
			this.prev = prev;
		}
	}

	public static void asLinkedListTest(String filePath, String generoBuscado) {
		LinkedList<Libro> books = readFile(filePath);
		Indice indice = new Indice();
		LinkedList<Libro> booksCopy = new LinkedList<>();
		Timer timer = new Timer();
		timer.start();
		for (Libro libro : books) {
//			indice.insertarLibro(libro);
			booksCopy.add(libro);
		}
		double time = timer.stop();
		System.out.println("LinkedList insertion time: " + time);

		LinkedList<LibroInterface> booksitos = new LinkedList<LibroInterface>();
		timer.start();
		booksitos = indice.getLibrosPorGenero(generoBuscado);
		time = timer.stop();
		System.out.println("LinkedList iteration time: " + time);

		Iterator<LibroInterface> iterator = booksitos.iterator();

		try {
			generateCsv(booksitos, filePath);
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
			generateCsv(filteredBooks, filePath);
		} catch (Exception e) {
			e.printStackTrace();
		}

		ArrayList<LibroWithArray> booksCopy = new ArrayList<>(1);
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
		StringBuilder gens = new StringBuilder();
		for (String gen: data.getGeneros()) {
			gens.append(gen).append(" ");
		}
		return data.getAutor() + "," + data.getTitulo() + "," + data.getCantidadPaginas() + "," + gens;
	}

	public static void generateCsv(List<LibroInterface> dataLines, String filePath) throws IOException {
		BufferedWriter bw = null;
		try {
			String outputFile = filePath.split("/")[1].replace(".csv", "") + "_output.csv";
			File file = new File(outputFile);
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file);
			bw = new BufferedWriter(fw);
			for (LibroInterface book: dataLines) {
				String str = convertToCSV(book);
				bw.write(str);
				bw.newLine();
			}
			System.out.println("Output file: " + outputFile);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				if (bw != null)
					bw.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

}

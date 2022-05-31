package prog3.tpe.entrega1;

import prog3.tpe.utils.Timer;

import java.io.*;
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
			searchAndFilter(csvFile, genero);
		}
	}

	public static void searchAndFilter(String filePath, String generoBuscado) {
		LinkedList<Libro> books = readFile(filePath);
		Indice indice = new Indice();
		Timer timer = new Timer();

		timer.start();
		for (Libro libro : books) {
			indice.insertarLibro(libro);
		}
		double time = timer.stop();
		System.out.println("LinkedList insertion time: " + time);

		//indice.printPreOrder();

		LinkedList<Libro> booksitos = new LinkedList<>();
		timer.start();
		booksitos = indice.getLibrosPorGenero(generoBuscado);
		time = timer.stop();
		System.out.println("LinkedList iteration time: " + time);

		try {
			generateCsv(booksitos, filePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static LinkedList<Libro> readFile(String pathFile){
		System.out.println("************");
		System.out.println("READING FILE: " + pathFile.split("/")[1]);
		
		LinkedList<Libro> books = new LinkedList<Libro>();
		String line = "";
		String csvSplitBy = ",";

		try (BufferedReader br = new BufferedReader(new FileReader(pathFile))) {
			//Salta la primer linea que contiene los titulos
        	br.readLine();
			while ((line = br.readLine()) != null) {
				String[] items = line.split(csvSplitBy);
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
		System.out.println("Total elements: " + books.size());
		return books;
	}

	public static String convertToCSV(Libro data) {
		StringBuilder gens = new StringBuilder();
		for (String gen: data.getGeneros()) {
			gens.append(gen).append(" ");
		}
		return data.getAutor() + "," + data.getTitulo() + "," + data.getCantidadPaginas() + "," + gens;
	}

	public static void generateCsv(List<Libro> dataLines, String filePath) throws IOException {
		BufferedWriter bw = null;
		try {
			String outputFile = filePath.split("/")[1].replace(".csv", "") + "_output.csv";
			File file = new File(outputFile);
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file);
			bw = new BufferedWriter(fw);
			for (Libro book: dataLines) {
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

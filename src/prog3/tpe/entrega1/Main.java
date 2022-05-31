package prog3.tpe.entrega1;

import prog3.tpe.utils.Timer;

import java.io.*;
import java.util.LinkedList;

public class Main {

	static BufferedWriter bw = null;

	public static void main(String[] args) throws IOException {
		
	 
		System.out.println("LEER ARCHIVO DE DATOS");
		String folder = obtenerPath();
		
		String fileName = obtenerFileName();	
		File pathFile = new File(folder,fileName+".csv");
		
		
		//Genera una lista de Libros a partir de la lectura de un archivo
		LinkedList<Libro> coleccionLibros = readFile(pathFile);		
		
		//Genera Indice y lo carga con la coleccion de libros
		Indice indice = new Indice();		
		cargarIndice(indice, coleccionLibros);
		
		
		
		//Obtiene los generos que contiene el indice
		System.out.println("Lista de Generos que contiene el Indice");
		System.out.println("");
		LinkedList<String> generosIndice = indice.getGenerosIndice();		
		for (String genero : generosIndice) {
			System.out.println(genero);;
		}
		
		
		System.out.println("");		
		//Pide al usuario un Genero para hacer la busqueda de libros por genero
		String genero = obtenerGenero();
		
		
		//Filtra los libros por el genero ingresado por el usuario		
		LinkedList<Libro> librosPorGenero = filtrarLibros(indice, genero);
		
		System.out.println("");
				
		System.out.println("GENERAR ARCHIVO DE DATOS");
		System.out.println("");
		String pathOutputFile = obtenerPath();		 
		String nameOutputFile = obtenerFileName();		
		File file = new File(pathOutputFile,nameOutputFile+".csv");
		
		//Genera el archivo de salida
		generateCsv(librosPorGenero, file);
		
		
		/*		
		Para testear la performance llamar al metodo testPerformance(), con el genero como parametro: 
		Ejemplo: testPerformance("thriller");
		*/
	}
	
	
	private static void testPerformance(String genero) throws IOException {
		System.out.println("CARPETA DE DATASETS PARA TESTEAR");
		String context = obtenerPath();
		File folderPath = new File(context);
		String[] files = folderPath.list();
		assert files != null;
		for (String file: files) {
			File csvFile = new File(context,file);
			LinkedList<Libro> coleccionLibros = readFile(csvFile);	
			Indice indice = new Indice();		
			cargarIndice(indice, coleccionLibros);	
			filtrarLibros(indice, genero);
		}
	}
	
	
	private static String obtenerGenero() throws IOException {			
		System.out.println("Ingrese el genero buscado: ");
		BufferedReader inputGenre = new BufferedReader(new InputStreamReader(System.in)); 
		String genre = inputGenre.readLine();		
		System.out.println("");
		
		return genre;
	}
	
	
	
	private static String obtenerPath() throws IOException {
		System.out.println("Ingrese ruta de la carpeta: ");
		BufferedReader inputPathFolder = new BufferedReader(new InputStreamReader(System.in)); 
		String pathFolder = inputPathFolder.readLine();				
		System.out.println("");
		
		return pathFolder;
	}	
	
	
	private static String obtenerFileName() throws IOException {
		System.out.println("Ingrese nombre del archivo: ");
		BufferedReader inputFileName = new BufferedReader(new InputStreamReader(System.in)); 
		String fileName = inputFileName.readLine();
		System.out.println("");
		
		return fileName;
	}
	
	
		
	private static void cargarIndice(Indice indice, LinkedList<Libro> coleccionLibros ) {
		//Inserta los libros en el Indice
			
		Timer timer = new Timer();

		timer.start();
		for (Libro libro : coleccionLibros) {
			indice.insertarLibro(libro);
		}
		double time = timer.stop();
		System.out.println("LinkedList insertion time: " + time);
	}
	
	
	private static LinkedList<Libro> filtrarLibros(Indice indice, String genero) throws IOException {			
			//Filtra los libros por genero
			Timer timer = new Timer();			
			timer.start();
			LinkedList<Libro> librosPorGenero = indice.getLibrosPorGenero(genero);
			double time = timer.stop();
			System.out.println("LinkedList iteration time: " + time);
			
		 
		 return librosPorGenero;
	}	
	

	private static LinkedList<Libro> readFile(File pathFile) throws IOException{
		
		System.out.println("************");
		//System.out.println("READING FILE: " + pathFile.split("/")[1]);
		System.out.println("READING FILE: " + pathFile.getName());
		
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
	

	private static String convertToCSV(Libro data) {
		StringBuilder gens = new StringBuilder();
		for (String gen: data.getGeneros()) {
			gens.append(gen).append(" ");
		}
		return data.getAutor() + "," + data.getTitulo() + "," + data.getCantidadPaginas() + "," + gens;
	}

		
	private static void generateCsv(LinkedList<Libro> dataLines, File file) throws IOException {		
		
		BufferedWriter bw = null;
		try {
			//String outputFile = filePath.getName().replace(".csv", "") + "_output.csv";
			//File file = new File(filePath);
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file);
			bw = new BufferedWriter(fw);
			// Escribo la primer linea del archivo
			String headers = "Titulo,Autor,Paginas,Generos";
			bw.write(headers);
			bw.newLine();
			
			for (Libro book: dataLines) {
				String str = convertToCSV(book);
				bw.write(str);
				bw.newLine();
			}
			System.out.println("Output file: " + file.getName() +". Se guardo en la siguiente ruta: "+ file.getAbsolutePath());
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

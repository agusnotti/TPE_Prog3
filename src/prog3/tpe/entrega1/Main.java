package prog3.tpe.entrega1;

import prog3.tpe.utils.Timer;

import java.io.*;
import java.util.LinkedList;

public class Main {

	public static void main(String[] args) throws IOException {
	 
		System.out.println("LEER ARCHIVO DE DATOS\nIngrese solo el nombre (sin \".csv\"");
		String folder = obtenerPath();
		
		String fileName = obtenerFileName();	
		File pathFile = new File(folder,fileName+".csv");

		//Genera una lista de Libros a partir de la lectura de un archivo
		LinkedList<Libro> coleccionLibros = FileHandler.readFile(pathFile);
		
		//Genera Indice y lo carga con la coleccion de libros
		Indice indice = new Indice();		
		cargarIndice(indice, coleccionLibros);
		
		//Obtiene los generos que contiene el indice
		System.out.println("Lista de Generos que contiene el Indice\n");
		LinkedList<String> generosIndice = indice.getGenerosIndice();		
		for (String genero : generosIndice) {
			System.out.println(genero);
		}
		
		System.out.println();
		//Pide al usuario un Genero para hacer la busqueda de libros por genero
		String genero = obtenerGenero();
		
		
		//Filtra los libros por el genero ingresado por el usuario		
		LinkedList<Libro> librosPorGenero = filtrarLibros(indice, genero);

				
		System.out.println("\nGENERAR ARCHIVO DE DATOS\n");
		String pathOutputFile = obtenerPath();		 
		String nameOutputFile = obtenerFileName();
		File file = new File(pathOutputFile,nameOutputFile+".csv");
//		String output = pathOutputFile + nameOutputFile + ".csv";

		if (new File(pathOutputFile).mkdirs()) {
			System.out.println("Generando directorios...");
		}
		
		//Genera el archivo de salida
		FileHandler.generateCsv(librosPorGenero, file);
		
		
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
			LinkedList<Libro> coleccionLibros = FileHandler.readFile(csvFile);
			Indice indice = new Indice();		
			cargarIndice(indice, coleccionLibros);	
			filtrarLibros(indice, genero);
		}
	}
	
	
	private static String obtenerGenero() throws IOException {			
		System.out.println("Ingrese el genero buscado: ");
		BufferedReader inputGenre = new BufferedReader(new InputStreamReader(System.in)); 
		String genre = inputGenre.readLine();		
		System.out.println();
		
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
	
	
	private static LinkedList<Libro> filtrarLibros(Indice indice, String genero) {
		//Filtra los libros por genero
		Timer timer = new Timer();
		timer.start();
		LinkedList<Libro> librosPorGenero = indice.getLibrosPorGenero(genero);
		double time = timer.stop();
		System.out.println("LinkedList iteration time: " + time);
		 
		 return librosPorGenero;
	}

}

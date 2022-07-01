package prog3.tpe.entrega2;

import prog3.tpe.utils.Timer;

import java.io.*;
import java.util.Iterator;

public class Main {
    
	public static void main(String[] args) throws IOException {

        System.out.println("LEER ARCHIVO DE DATOS\nIngrese solo el nombre (sin \".csv\"");
		String folder = obtenerPath();

		String fileName = obtenerFileName();
		File pathFile = new File(folder,fileName+".csv");

        Indice indice = new Indice();         

        indice.cargarBusquedas(pathFile);

        String gen = obtenerGenero();
        int n = obtenerN();

        System.out.println("Mas buscado luego de: "+indice.obtenerNMasBuscadosLuegoDe(gen, n));
        System.out.println("Secuencia mayor valor: "+indice.encontrarSecuenciaMayorValor(gen));
        System.out.println("Grafo generos afines: ");
        GrafoGeneros g = indice.obtenerGenerosAfines(gen);
        printGraph(g);

    }


////METODOS AUXILIARES

    public static void printGraph(GrafoGeneros grafo) {
        Indice indice = new Indice(grafo);
        Iterator<String> itGeneros = indice.obtenerGeneros();
        while(itGeneros.hasNext()){
            String genero = itGeneros.next();
            Iterator<ArcoGeneros> itArcos = indice.obtenerArcos(genero);
            while(itArcos.hasNext()){
                ArcoGeneros arco = itArcos.next();
                System.out.println(arco.getGeneroOrigen() + " -> " + arco.getGeneroDestino() + "[label = " + arco.getCantBusquedas() + "];"); // para usar la herramienta de grafos
            }
        }
    }

    private static int obtenerN() throws IOException {
        System.out.println("Ingrese N: ");
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        return Integer.parseInt(input.readLine());
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



    
}



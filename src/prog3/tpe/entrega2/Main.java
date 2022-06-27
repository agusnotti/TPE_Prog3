package prog3.tpe.entrega2;

import java.io.*;
import java.util.Iterator;

public class Main {
    
	public static void main(String[] args) throws IOException {

        /* DESCOMENTAR AL ENTREGAR

         System.out.println("LEER ARCHIVO DE DATOS\nIngrese solo el nombre (sin \".csv\"");
		String folder = obtenerPath();
		
		String fileName = obtenerFileName();	
		File pathFile = new File(folder,fileName+".csv");

        */  

        File pathFile = new File("Datasets2daEtapa/dataset1.csv");

        Indice indice = new Indice();         

        indice.cargarBusquedas(pathFile);

        System.out.println("GENEROS\n");
        Iterator<String> itGeneros = indice.obtenerGeneros();
        while(itGeneros.hasNext()){
            String genero = itGeneros.next();
//            System.out.println("GENERO: " + genero);
            Iterator<ArcoGeneros> itArcos = indice.obtenerArcos(genero);
            while(itArcos.hasNext()){
                ArcoGeneros arco = itArcos.next();
                System.out.println("GENERO ORIGEN: " + arco.getGeneroOrigen()
                + "\nGENERO DESTINO: " + arco.getGeneroDestino()
                + "\nCANTIDAD BUSQUEDAS: "+ String.valueOf(arco.getCantBusquedas())+"\n");
//                System.out.println(arco.getGeneroOrigen() + " -> " + arco.getGeneroDestino() + "[label = " + arco.getCantBusquedas() + "];"); // para usar la herramienta de grafos
            }
        }

        System.out.println(indice.obtenerMasBuscadosLuegoDe("periodismo", 5));
        System.out.println(indice.encontrarSecuenciaMayorValor("periodismo"));

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



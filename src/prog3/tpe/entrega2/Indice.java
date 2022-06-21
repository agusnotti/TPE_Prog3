package prog3.tpe.entrega2;

import java.io.*;
import java.util.Iterator;

// IMPLEMENTAR SERVICIOS
/*
 * Utilizando este grafo como estructura se deberán implementar los siguientes servicios:
      ● Obtener los N géneros más buscados luego de buscar por el género A.
      ● A partir de un género A encontrar, en tiempo polinomial, la secuencia de géneros que más alto valor de búsqueda 
      posee. Vamos a definir el valor de búsqueda de una secuencia como la suma de los arcos que la componen.
      ● Obtener el grafo únicamente con los géneros afines a un género A; es decir que, partiendo del género A, 
      se consiguió una vinculación cerrada entre uno o más géneros que permitió volver al género de inicio.
 */
public class Indice {

   private GrafoGeneros buscador;

   public Indice() {

      this.buscador = new GrafoGeneros();
   }

   //Metodo encargado de cargar las busquedas a partir de los datasets
   public void cargarBusquedas(File pathFile) {
      System.out.println("************");
      System.out.println("READING FILE: " + pathFile.getName()+"\n");
 
      String line = "";
      String cvsSplitBy = ",";

      try (BufferedReader br = new BufferedReader(new FileReader(pathFile))) {
         br.readLine();
         while ((line = br.readLine()) != null) {
            String[] generos = line.split(cvsSplitBy);
            for (int i=0;i<generos.length;i++) {
               if(i == generos.length-1){
                  buscador.agregarGenero(generos[i]);
               } else {
                  buscador.agregarGenero(generos[i]);
                  buscador.agregarGenero(generos[i+1]); //Agrego el próximo genero
                  buscador.agregarArco(generos[i], generos[i+1]);
               }               
            }
         }
      }catch(IOException e){
         e.printStackTrace();
      }
   }

   //METODOS PARA TESTEAR
   public Iterator<String> obtenerGeneros(){
      return this.buscador.obtenerGeneros();
   }

   public Iterator<ArcoGeneros> obtenerArcos(String genero){
      return this.buscador.obtenerArcos(genero);
   }
}

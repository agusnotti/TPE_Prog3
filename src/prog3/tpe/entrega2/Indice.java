package prog3.tpe.entrega2;

import java.io.*;
import java.util.ArrayList;
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
                  this.cargarGrafo(generos[i], null);
                  //this.buscador.agregarGenero(generos[i]);
               } else {
                  this.cargarGrafo(generos[i], generos[i+1]);

                  //this.buscador.agregarGenero(generos[i]);
                  //this.buscador.agregarGenero(generos[i+1]); //Agrego el próximo genero
                  //this.buscador.agregarArco(generos[i], generos[i+1]);
               }               
            }
         }
      }catch(IOException e){
         e.printStackTrace();
      }
   }

   public void cargarGrafo(String genero, String proxGenero){
      if (proxGenero == null) {
         cargarGrafo(genero);
      } else {
         this.buscador.agregarGenero(genero);
         this.buscador.agregarGenero(proxGenero);
         this.buscador.agregarArco(genero, proxGenero);
      }
   }

   private void cargarGrafo (String genero){
      this.buscador.agregarGenero(genero);
   }



   /* Obtener los N géneros más buscados luego de buscar por el género A,
    donde genero es A y cantidad es N  
    */
   public ArrayList<String> obtenerNMasBuscadosLuegoDe(String genero, int cantidad) {
      ArrayList<ArcoGeneros> arcos = new ArrayList<>();
      buscador.obtenerArcos(genero).forEachRemaining(arcos::add);
      arcos.sort((a, b) -> b.getCantBusquedas() - a.getCantBusquedas());
      //System.out.println(arcos);
      ArrayList<String> result = new ArrayList<>();
      for (int i = 0; i < cantidad && i < arcos.size(); i++) {
         result.add(arcos.get(i).getGeneroDestino());
      }
      return result;
   }

   /* A partir de un género A encontrar, en tiempo polinomial, la secuencia de géneros
      que más alto valor de búsqueda posee. Vamos a definir el valor de búsqueda de
      una secuencia como la suma de los arcos que la componen.
   */
   public ArrayList<String> encontrarSecuenciaMayorValor(String genero) {
      ArrayList<String> solucion = new ArrayList<>();
      solucion.add(genero);
      Iterator<ArcoGeneros> itCandidatos = buscador.obtenerArcos(genero);
      ArrayList<ArcoGeneros> candidatos = iteratorToArray(itCandidatos);

      while (!candidatos.isEmpty()) {
         ArcoGeneros arco = obtenerArcoMayorValor(candidatos, solucion);
         /* De no haber un candidato valido, arco sera null, por lo cual corto el ciclo */
         if (arco != null) {
            solucion.add(arco.getGeneroDestino());
            candidatos = iteratorToArray(buscador.obtenerArcos(arco.getGeneroDestino()));
         } else {
            break;
         }
      }
      return solucion;
   }

   public ArcoGeneros obtenerArcoMayorValor(ArrayList<ArcoGeneros> arcos, ArrayList<String> solucion) {
      ArcoGeneros mayorValor = null;
      int size = arcos.size();
      if (size > 0) {
         int index = -1;
         /* selecciono el primer candidato cuyo destino no este en la solucion */
         for (int i = 0; i < size; i++) {
            if (!solucion.contains(arcos.get(i).getGeneroDestino())) {
               mayorValor = arcos.get(i);
               index = i;
               break;
            }
         }
         /* si el ultimo elemento con el cual se inicializo mayorValor no es -1 ni es igual a size -1
          * (es decir, el primer FOR llego al final del arreglo de candidatos), no comparo nuevamente */
         if (index < size - 1 && index != -1) {
            for (int i = index; i < size; i++) {
               if (arcos.get(i).getCantBusquedas() > mayorValor.getCantBusquedas() && !solucion.contains(arcos.get(i).getGeneroDestino())) {
                  mayorValor = arcos.get(i);
               }
            }
         }
      }
      return mayorValor;
   }

   public ArrayList<ArcoGeneros> iteratorToArray(Iterator<ArcoGeneros> iterator) {
      ArrayList<ArcoGeneros> result = new ArrayList<>();
      iterator.forEachRemaining(result::add);
      return result;
   }

   public int obtenerValorDeBusqueda(ArrayList<ArcoGeneros> arcos) {
      int suma = 0;
      for (ArcoGeneros arco : arcos) {
         suma += arco.getCantBusquedas();
      }
      return suma;
   }

   //METODOS PARA TESTEAR
   public Iterator<String> obtenerGeneros(){
      return this.buscador.obtenerGeneros();
   }

   public Iterator<ArcoGeneros> obtenerArcos(String genero){
      return this.buscador.obtenerArcos(genero);
   }
}

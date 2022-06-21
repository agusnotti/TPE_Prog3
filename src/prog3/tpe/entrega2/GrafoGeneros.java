package prog3.tpe.entrega2;

/*
 * La clase Grafo almacena los generos buscados. 
 * Cada Vertice representa un género que fue incluído en alguna búsqueda y cada uno de los vertices tiene un 
 * conjuntos de arcos que comunica con los generos adyacentes.
 */

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class GrafoGeneros {	
	
    ////VARIABLES
    //HashMap: Clave: genero (String), Valor: lista de arcos destino a otros generos adyacentes (LinkedList<ArcosGenero>)
	private HashMap<String, LinkedList<ArcoGeneros>> generos;
	//private int cantidadArcos;
	
	//// CONSTRUCTOR
	public GrafoGeneros() {
		this.generos = new HashMap<String, LinkedList<ArcoGeneros>>();
		//this.cantidadArcos = 0;
	}

    ////METODOS
	public void agregarGenero(String genero) { 
		//Verifico que ese genero no exista
		if(!this.contieneGenero(genero)){	
			this.generos.put(genero, new LinkedList<ArcoGeneros>());	
		}
	}

	public void agregarArco(String genero1, String genero2) {		
		//verifico que existan los vertices a conectar
		if (contieneGenero(genero1) && (contieneGenero(genero2))) {
			//verifico que el Arco a agregar no exista
			if(!existeArco(genero1,genero2)) {
				ArcoGeneros arcoNuevo = new ArcoGeneros(genero1, genero2);
				generos.get(genero1).add(arcoNuevo);
				//cantidadArcos++;
			} else {
				ArcoGeneros arco = this.obtenerArco(genero1, genero2);
				arco.aumentarCantBusquedas();
			}
		}
	}

	
	// funcion containsKey devuelve un booleano si el hashmap contiene la clave que se pasa por parametro
	public boolean contieneGenero(String genero) {
		return generos.containsKey(genero);
	}


	//funcion keySet devuelve todas las claves del hashmap
	public Iterator<String> obtenerGeneros() {
		return this.generos.keySet().iterator();
	}


	public Iterator<ArcoGeneros> obtenerArcos(String genero) {
		LinkedList<ArcoGeneros> arcosList = new LinkedList<ArcoGeneros>();
		
		//verifico si existe el genero
		if (this.contieneGenero(genero)) {
			//obtengo la lista de ArcoGeneros a partir de la busqueda por la clave del hashmap
			arcosList = this.generos.get(genero);
		}
		
		return arcosList.iterator();
	}


	public ArcoGeneros obtenerArco(String genero1, String genero2) {
		ArcoGeneros salida = null;
		//obtengo los arcos del genero1	
		Iterator <ArcoGeneros> itArcos = obtenerArcos(genero1);
		
		while (itArcos.hasNext()) { 
			ArcoGeneros arcoActual = itArcos.next();
			//si el verticeDestino del ArcoGeneros es igual al verticeId2
			if (arcoActual.getGeneroDestino().equals(genero2)) {
				salida = arcoActual;			
			}
		}	
		
		return salida;
	}


	public Iterator<ArcoGeneros> obtenerArcos() {
		LinkedList<ArcoGeneros> lstArcosGeneross = new LinkedList<ArcoGeneros>();
		//obtengo los valores del hashmap (listas de ArcoGeneros)
		Iterator<LinkedList<ArcoGeneros>> itListasArcos = this.generos.values().iterator();
				
		while (itListasArcos.hasNext()) {
			lstArcosGeneross.addAll(itListasArcos.next());	
		}
		
		return lstArcosGeneross.iterator(); 
	}


	public int cantidadGeneros() {
		return this.generos.size();
	}

	/*
	 public int cantidadArcos() {
		return this.cantidadArcos;	
	}
	 */

	public boolean existeArco(String genero1, String genero2) {
		boolean existeArco = false;
		if(contieneGenero(genero1)) { 
			Iterator<ArcoGeneros> arcos = obtenerArcos(genero1);
			while(arcos.hasNext()) {	 
				if(arcos.next().getGeneroDestino().equals(genero2)) { 	
					existeArco = true;
				}
			}
		}	
		return existeArco;
	}
	

	public void borrarGenero(String genero) {
		if(this.contieneGenero(genero)){
			this.borrarArcoGenerosAdyacentes(genero);
			this.generos.remove(genero);
		}
	}	


	//Eliminar los ArcoGeneross que tengan como destino el vertice que viene de parametro
	public void borrarArcoGenerosAdyacentes(String genero){
		//obtengo iterador de todos los Arcos del grafo
		Iterator<ArcoGeneros> itArcos = this.obtenerArcos();

		while (itArcos.hasNext()) {
			ArcoGeneros arcoGeneros = itArcos.next();
			//si el vertice Destino del ArcoGeneros actual es igual al vertice que viene de parametro
			if (arcoGeneros.getGeneroDestino().equals(genero)) {
				this.borrarArco(arcoGeneros.getGeneroOrigen(), arcoGeneros.getGeneroDestino());
			}
		}
	}

	
	public void borrarArco(String generoOrigen, String generoDestino) {
		if (this.existeArco(generoOrigen, generoDestino)) {
			ArcoGeneros arcoGeneros = this.obtenerArco(generoOrigen, generoDestino);
			this.generos.get(generoOrigen).remove(arcoGeneros);
			//cantidadArcos--;
		}
	}


	public Iterator<String> obtenerAdyacentes(String genero) {
		Iterator<ArcoGeneros> arcoGeneros = this.obtenerArcos(genero);
		LinkedList<String> adyacentes = new LinkedList<String>();
		while(arcoGeneros.hasNext()) {
			adyacentes.add(arcoGeneros.next().getGeneroDestino());
		}
		return adyacentes.iterator();
	}
}

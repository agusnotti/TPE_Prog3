package prog3.tpe.entrega2;

/*
 * Esta clase representa un arco que comunica dos vértices. El arco contiene una etiqueta donde se indicará 
 * la cantidad de veces que luego de buscar el primer género inmediatamente a continuación se buscó por el 
 * segundo género.
 */

public class ArcoGeneros {
    private String generoOrigen;
	private String generoDestino;
	private int cantBusquedas;

	public ArcoGeneros(String generoOrigen, String generoDestino) {
		this.generoOrigen = generoOrigen;
		this.generoDestino = generoDestino;
		this.cantBusquedas = 1;  //se inicializa en 1, ya que cuando se agrega un nuevo arco, se considera que se realizo la primera busqueda de un genero B, inmediatamente seguido de un genero A
	}
	
	public String getGeneroOrigen() {
		return this.generoOrigen;
	}
	
	public String getGeneroDestino() {
		return this.generoDestino;
	}

	public int getCantBusquedas() {
		return this.cantBusquedas;
	}

	public void aumentarCantBusquedas(){
		this.cantBusquedas++;
	}

	@Override
	public String toString() {
		return "ArcoGeneros{" +
				"generoOrigen='" + generoOrigen + '\'' +
				", generoDestino='" + generoDestino + '\'' +
				", cantBusquedas=" + cantBusquedas +
				'}';
	}
}

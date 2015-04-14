

import java.io.*;

/**
 * @author josecastillo
 *
 */
public class Tipodato<String, Misqlobject> implements java.io.Serializable, Comparable<Tipodato>{
	private String nombre_dato;
	private Misqlobject value;
	public Tipodato(){
		this.value = null;
	}
	public Tipodato(String tipo, Misqlobject v){
		this.nombre_dato = tipo;
		this.value = v;
	}
	
	public String getString(){
		return this.nombre_dato;
	}
	
	public Misqlobject getValue(){
		return this.value;
	}
	
	public void setString(String nombre_nuevo){
		this.nombre_dato = nombre_nuevo;
	}
	
	public void setMisqlobject(Misqlobject nuevo_objeto){
		this.value = nuevo_objeto;
	}
	@Override
	public int compareTo(Tipodato o) {
		if(this.nombre_dato == o.getString() && this.value == o){
			return 100;
		}
		return 0;
	}


}

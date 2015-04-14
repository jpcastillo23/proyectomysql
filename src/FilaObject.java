import java.util.ArrayList;


public class FilaObject {
	private ArrayList<Tipodato> Tupla = new ArrayList<Tipodato>();
	private int cantidad_datos;
	private Tipodato temporalmn2;
	public FilaObject(){
		cantidad_datos =0;
	}
	
	public void addTipodato(Tipodato a_agregar){
		this.Tupla.add(a_agregar);
	}
	public Tipodato getTipodato(int Columna){
		return this.Tupla.get(Columna);
	}
	
	public boolean setTipodato(Tipodato nuevo, int index){
		try{
			this.Tupla.set(index, nuevo);
			return true;
		}
		catch(Exception e ){
			return false;
		}
	}
	public boolean deleteTipodato(int index){
		Object nuevo = null;
		try{
			Tupla.set(index, new Tipodato(((Tipodato)Tupla.get(index)).getString(),nuevo));
			return true;
		}
		catch(Exception e ){
			return false;
		}
	}

}

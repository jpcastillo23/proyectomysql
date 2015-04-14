import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import net.sf.json.*;

public class Mitable implements java.io.Serializable {
	
	private JSONObject tabla_json = new JSONObject();
	private JSONObject sub_tabla_esquema= new JSONObject();
	@SuppressWarnings("unused")
	private JSONObject sub_tabla_datos = new JSONObject();
	private String nombre_tabla;
	
	public Mitable(String nombre_tabla ){
		this.tabla_json = null;
		this.sub_tabla_esquema = null;
		this.sub_tabla_datos = null;
	}

/**
 * SERELIAZADOS
 * */	
	 public void saveObject( String direccion, String table_name){
		 FileWriter file = null;
		 try
	      {
			 file = new FileWriter( direccion + table_name );
	         file.write(this.tabla_json.toString());
	         System.out.println("Serialized data is saved in "+ direccion + table_name );
	      }catch(IOException i)
	      {
	    	  System.out.println	 ("no se pudo");  
	    	  i.printStackTrace();
	      }
		 try {
			file.flush();
			file.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	 public JSONObject getObject(String direccion, String table_name){
		 
		 ObjectInputStream in;
		 FileInputStream fileIn;
		 JSONObject temp_table =null;
		 System.out.println(direccion+table_name);
		 try
	      {
	         fileIn = new FileInputStream(direccion+table_name);
	         in = new ObjectInputStream(fileIn);
	         temp_table =(JSONObject)in.readObject();
	         in.close();
	         fileIn.close();
	         System.out.println("Serialized data is saved in "+ direccion + table_name );
	      }catch(IOException i)
	      {
	    	  	System.out.println("saber");
	          i.printStackTrace();
	      }catch(ClassNotFoundException c)
	      {
	          System.out.println("Employee class not found");
	          c.printStackTrace();
	          return null;
	       }
		 return temp_table;
	 }
	 

}

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class DLL_manager {
	
	private static  String OS;
	private  String dir;
	private Calendar instance_date= null; 
	
	public DLL_manager(){
		instance_date = Calendar.getInstance();
	}
	//CREAR CARPETAROOT DE LAS BASES DE DATOS
	public boolean compileMYDBFolder(){
		String separador = separatorOSDirectory();
		File filen = new File(dir + separador +"MyDB"+ separador);
		return filen.mkdirs();
	}
	//SOBRE LA UBICACION DEL ARCHIVO QUE INDIQUEMOS, ESCRIBIR LA LISTA DE OPCIONES
	public boolean writeInFile(String ubication, List<String> lineas) throws IOException{
	   FileWriter fw = new FileWriter(ubication);
	   BufferedWriter bw = new BufferedWriter(fw);
	   try{
	    		for ( String unica_linean : lineas ){
	    			bw.append(unica_linean.toString());
	    		}
	    		bw.close();
    		    fw.close();
    		    return true;
	    }catch(Exception e){
	    		System.out.println("No se pudo imprimir");
	    }
		return false;
	}
	//CREAR NUESTRO REGISTRO DE BASES DE DATOS ACTIVAS Y GUARDADAS
	public boolean Crear_directorio(String donde){
		File filen = new File(donde);
		return filen.mkdirs();
	}
	//Devuelve la carpeta root de db_registry.reg
	public String getcarpetaRootMYDBReg(){
		String separador = separatorOSDirectory();
		return new String(dir + separador +"MyDB"+ separador + "db_registry.reg");
	}
	//DEVUELVE EL FORMATO DE SEPARADORES DEPENDIENDO EL SISTEM_OPERATIVO
	public String separatorOSDirectory(){
		OS = System.getProperty("os.name");
		dir = System.getProperty("user.dir");
		String separator = null;
		if (isWindows()){
			separator = "\\";
			}
		if (isMac()){
			separator = "/";
		}
		return separator;
	}
	public boolean Crear_Directorio_base_datos(String Data_base_name){
		String separator = separatorOSDirectory();
		String nueva_direccion = " ";
		boolean efectuo_cambio = false;
		nueva_direccion=dir + separator +"MyDB"+ separator + Data_base_name + separator;
		File folder = new File(nueva_direccion);
		if (folder.exists()){
			folder.delete();
		};
		if (folder.mkdirs()){  //crear nuevamente el directorio
			efectuo_cambio=true;
		}else{System.out.println("Base de Datos no ha sido Creado");};
		//getcarpetaRootMYDBReg()
		return efectuo_cambio && Crear_directorio( getcarpetaRootMYDBReg() );

		
	}
	
	public boolean Crear_Directorio_base_datos_if(String Data_base_name){
		String separator = separatorOSDirectory();
		String nueva_direccion = " ";
		List<String> mi_lista = new ArrayList<String>();
		mi_lista.add(Data_base_name+","+ 0);
		boolean efectuo_cambio = false;
		nueva_direccion=dir + separator +"MyDB"+ separator + Data_base_name + separator;	
		File folder = new File(nueva_direccion);
		if (!folder.exists()){
			if (!folder.mkdirs()){  //crea y ademas manda un boolean de verificacion
				System.out.println("Base de Datos no ha sido Creado");
			}else{
			System.out.println("Base de Datos ha sido Creado");
			try {
				writeInFile(getcarpetaRootMYDBReg(), mi_lista);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("No se pudo crear el archivo en :"+getcarpetaRootMYDBReg() );
				e.printStackTrace();
			}
			efectuo_cambio = true;
			};
		}else{
			System.out.println("Folder Ya existia");
		}
		return efectuo_cambio;

	}
	
	public static boolean isWindows() { 
		return (OS.indexOf("win") >= 0);
	}
	public static boolean isMac() {
		return (OS.indexOf("Mac") >= 0);
	}
	public static boolean isUnix() {
		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );
	}
	public static boolean isSolaris() {
		return (OS.indexOf("sunos") >= 0);
	}
	
}

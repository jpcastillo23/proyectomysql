import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.*;
import java.util.*;

public class DLL_manager {
	
	private static  String OS;
	private  String dir;
	private 	 Calendar instance_date= null; 
	
	public DLL_manager(){
		instance_date = Calendar.getInstance();
		separatorOSDirectory();
	}
	//CREAR CARPETAROOT DE LAS BASES DE DATOS
	public boolean compileMYDBFolder(){
		String separador = separatorOSDirectory();
		File filen = new File(dir + separador +"MyDB"+ separador);
		return filen.mkdirs();
	}
	//SOBRE LA UBICACION DEL ARCHIVO QUE INDIQUEMOS, ESCRIBIR LA LISTA DE OPCIONES
	//ASUME QUE YA TIENEN CREADO EL ARCHIVO
	public boolean writeInFileFilas(String archivo, List<String> lineas) {
	   FileWriter fw = null;
		try {
			fw = new FileWriter(archivo);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	   BufferedWriter bw = new BufferedWriter(fw);
	   try{
	    		for ( Object unica_linean : lineas ){
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
	public boolean writeInFileFila(String archivo, Object linea) {
		File prueba = new File(archivo)   ;
		FileWriter fw = null;
		   BufferedWriter bw = null;
			try {
				fw = new FileWriter(prueba);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			bw = new BufferedWriter(fw);
		   try{
		    		bw.append(linea.toString());
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
	//devuelve un numero negativo si no encuentra el arvhico
	public int buscarTextoenArchivo(String archivo, String palabra){
		int numero = -1;
		FileReader fw = null;
		BufferedReader bw = null;
		BufferedReaderIterable bri = null;
		try {
			System.out.println(archivo);
			fw = new FileReader(archivo);
			bw = new BufferedReader( fw);
			bri = new BufferedReaderIterable( bw );
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	   try{
	    		for ( String leyendo_linea : bri ){
	    			if(palabra.toLowerCase().contains(palabra.toLowerCase())){
	    				numero = numero +2 ;
	    			};
	    		}
	    		bw.close();
    		    fw.close();
    		    return numero;
	    }catch(Exception e){
	    		System.out.println("No se pudo imprimir");
	    }
		return numero;
	}
	//Devuelve la carpeta root de db_registry.txt
	public String getcarpetaRootMYDBReg(){
		String separador = separatorOSDirectory();
		String envio = dir + separador +"MyDB"+ separador + "db_registry.txt" ;
		System.out.println(envio);
		return envio;
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
	//Elimina y vuelve a crear el directorio
	public void inicializarRegistroDB() throws Exception{
		String a_enviar_vacio = " ";
		File folder =null;
		try{
			folder = new File(getcarpetaRootMYDBReg());
		}catch(Exception h){ System.out.println("puchica");};
		folder.createNewFile();
		folder.canRead();
		System.out.println( folder.canRead() + " Se puede leer y escribir" + folder.canWrite() );
		System.out.println( "debugeando");
		writeInFileFila(getcarpetaRootMYDBReg(),a_enviar_vacio) ;
	}
	public boolean existeRegistroDB(){
		File folder = new File(getcarpetaRootMYDBReg());
		return folder.exists();
	}
	public boolean escribirenRegistroDB(Object Data_name){
		return 	writeInFileFila(getcarpetaRootMYDBReg(),(String)Data_name);
	}
	public boolean existe_Carpeta_base_datos(String Data_base_name){
		String separator = separatorOSDirectory();
		String nueva_direccion = " ";
		boolean efectuo_cambio = false;
		nueva_direccion=dir + separator +"MyDB"+ separator + Data_base_name + separator;
		File folder = new File(nueva_direccion);
		return folder.exists();
	}
	public boolean CrearCarpeta_base_datos(String Data_base_name){
		String separator = separatorOSDirectory();
		String nueva_direccion = " ";
		boolean efectuo_cambio = false;
		nueva_direccion=dir + separator +"MyDB"+ separator + Data_base_name + separator;
		File folder = new File(nueva_direccion);
		if (folder.mkdirs()){ 		//crear nuevamente el directorio
			efectuo_cambio=true;
		}else{System.out.println("Base de Datos no ha sido Creado");};
		return efectuo_cambio;		
	}
	public boolean Inicializar_Carpeta_base_datos(String Data_base_name){
		return CrearCarpeta_base_datos(Data_base_name) && Crear_directorio( getcarpetaRootMYDBReg() );		
	}
	public boolean existDataBaseFile(String database_name){
		String separator = separatorOSDirectory();
		File folder = new File(dir + separator +"MyDB"+separator);
		if (!folder.exists()){
			System.out.println("System Error: No existe database.txt");
			return false;
		};
		return true;
	}
	// MIRA SI EXISTE MYDB// 
	//TAMBIEN CREA LA CONECCION SI NO HAY, 
	//LUEGO ESCRIBE SOBRE EL REGISTRO, NO PREGUNTA SI EXISTE UNO
	public boolean Crear_y_agragar_Directorio_base_datos_if(String Data_base_name){
		String separator = separatorOSDirectory();
		String nueva_direccion = " ";
		List<String> mi_lista = new ArrayList<String>();
		mi_lista.add(Data_base_name+".db ,"+ 0);
		boolean efectuo_cambio = false;
		nueva_direccion=dir + separator +"MyDB"+ separator + Data_base_name + separator;	
		File folder = new File(nueva_direccion);
		if (!folder.exists()){
			if (!folder.mkdirs()){  //crea y ademas manda un boolean de verificacion
				System.out.println("Base de Datos no ha sido Creado");
			}else{
				System.out.println("Base de Datos ha sido Creado");
				try {
					//crea la base de datos y tambien  REGISTRO
					writeInFileFilas(getcarpetaRootMYDBReg(), mi_lista);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("No se pudo crear el archivo REGISTRO en :"+getcarpetaRootMYDBReg() );
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

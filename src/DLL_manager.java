import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static java.nio.file.StandardCopyOption.*;

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
		    		bw.append(linea.toString()+"");
		    		bw.append(" ");
		    		bw.close();
	    		    fw.close();
	    		    return true;
		    }catch(Exception e){
		    		System.out.println("No se pudo imprimir");
		    }
		   System.out.println("LA FUNCION writeInFileFila  no ejecuto bien");
			return false;
		}
	//public boolean existInFileFila(String archivo, Object linea){}
	//CREAR NUESTRO REGISTRO DE BASES DE DATOS ACTIVAS Y GUARDADAS
	
	public boolean Crear_directorio (String donde){
		File filen = new File(donde);
		return filen.mkdirs();
	}
	//devuelve un numero negativo si no encuentra el arvhico
	public int buscarTextoenArchivo(String archivo, String palabra){
		int numero = -1;
		File folder = new File(archivo);
		FileReader fw = null;
		BufferedReader bw = null;
		BufferedReaderIterable bri = null;
		try {
			fw = new FileReader(folder);
			bw = new BufferedReader( fw);
			bri = new BufferedReaderIterable( bw );
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	   try{
	    		for ( String leyendo_linea : bri ){
	    			if(leyendo_linea.toLowerCase().contains(palabra.toLowerCase())){
	    				System.out.println(leyendo_linea +" " +leyendo_linea + " "+ leyendo_linea+" ");
	    				numero = numero +2 ;
	    			};
	    		}
	    		bw.close();
    		    fw.close();
	    }catch(Exception e){
	    		System.out.println("No se pudo imprimir");
	    }
		return numero;
	}
	//Devuelve la carpeta root de db_registry.txt
	public String getcarpetaRootMYDBReg(){
		String separador = separatorOSDirectory();
		String envio = dir + separador +"MyDB"+ separador + "db_registry.txt" ;
		return envio;
	}
	public String getDireccionMYDB(){
		return dir + File.separator +"MyDB"+ File.separator + "";
	}

	public String getDir(){
		return dir;
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
	public void inicializar_RegistroDBcarpeta() throws Exception{
		File folder =null;
		try{
			folder = new File(getcarpetaRootMYDBReg());
		}catch(Exception h){ System.out.println("puchica nose creo");};
		folder.mkdirs();
		//folder.createNewFile();
	}

	public void inicializarRegistroDB() throws Exception{
		File folder =null;
		try{
			folder = new File(getcarpetaRootMYDBReg());
		}catch(Exception h){ System.out.println("puchica nose creo");};
		//folder.mkdirs();
		folder.createNewFile();
		writeInFileFila(getcarpetaRootMYDBReg()," ") ;
	}
	public boolean existeRegistroDB(){
		File folder = new File(getcarpetaRootMYDBReg());
		return folder.exists();
	}
	
	public boolean escribirenRegistroDB(Object Data_name){
		return 	Anadir_fila_fichero(getcarpetaRootMYDBReg(),(String)Data_name);
	}
	public boolean existe_Carpeta_registro_base_datos(String Data_base_name){
		String separator = separatorOSDirectory();
		String nueva_direccion = " ";
		boolean efectuo_cambio = false;
		nueva_direccion=dir + separator +"MyDB"+ separator + Data_base_name + separator+ Data_base_name+"reg.txt";
		File folder = new File(nueva_direccion);
		return folder.exists();
	}
	/**
	 *   crear  user/netbeans/mysql/proyecto/myDB/database_NAMEreg.txt
	 *   Y TAMBIEN
	 *   		user/netbeans/mysql/proyecto/myDB/
	 * */
	public boolean Crear_registro_tabla_y_base_datos(String Data_base_name){
		String separator = separatorOSDirectory();
		String nueva_direccion = " ";
		boolean efectuo_cambio = false;
		nueva_direccion = dir + separator +"MyDB"+ separator + Data_base_name + separator+ Data_base_name+"reg.txt";
		String nueva_direccion_folder =dir + separator +"MyDB"+ separator + Data_base_name + separator;
		File folder = new File(nueva_direccion);
		File folder_ante = new File(nueva_direccion_folder);
		try {
			folder_ante.mkdirs();
			folder.createNewFile();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			folder.mkdirs();
			e.printStackTrace();
		}
		return folder.exists();
	}
	/**
	 *   INGRESAR  
	 *   		user/netbeans/mysql/proyecto/myDB/database_NAMEreg.txt
	 *   Y TAMBIEN
	 *   		[TABLE_NAME, INT COLUMNS, PRIMARY KEY]
	 * */
	public boolean Ingresar_registro_base_datos_individual(String Data_base_name, String tabla_ingresada){
		String separator = separatorOSDirectory();
		String nueva_direccion = " ";
		boolean efectuo_cambio = false;
		nueva_direccion=dir + separator +"MyDB"+ separator + Data_base_name + separator+ Data_base_name+"reg.txt";
		File folder = new File(nueva_direccion);
		return Anadir_fila_fichero(nueva_direccion, tabla_ingresada );
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
		if (!folder.mkdirs()){ 		//crear nuevamente el directorio
			try {
				folder.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
	
	public boolean databaseIsInUse(){
		return true;
	}
	
	public void DropDatabasefromfileorarchive(String dababase){
		String separator = separatorOSDirectory();
		String nueva_direccion = " ";
		boolean efectuo_cambio = false;
		nueva_direccion=dir + separator +"MyDB"+ separator + dababase + separator;	
		File folder = new File(nueva_direccion);
		folder.delete();
	}
	
	public boolean isinDatabaseFichero(String direccion, String busqueda){
		int e = buscarTextoenArchivo(direccion,busqueda);
		return (e > 0) ? true : false;
	}
	public boolean eliminarFilaConParesDatabaseTable(String direccion, String busqueda,String busqueda2){
		FileWriter fw = null;
		FileReader fr = null;
		String[] vacio = null;
		File nuevo = null;
		File viejo = null;
		String tempo_direc = dir +File.separator+"MyDB"+File.separator+ "temp.txt";
		System.out.println(tempo_direc);
		boolean modificado = false;
		System.out.println(tempo_direc);
		try {
			nuevo = new File(tempo_direc);
			nuevo.createNewFile();
			viejo = new File(direccion);

			fw = new FileWriter(nuevo);
			fr = new FileReader(viejo);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	   BufferedWriter bw = new BufferedWriter(fw); //ESCRIBO
	   BufferedReader br = new BufferedReader(fr); //LEO EL VIEJO
	   BufferedReaderIterable padre_iterador = new BufferedReaderIterable(br) ;
	   try{
	    		for ( String fila : padre_iterador ){
	    			vacio = fila.split(";");
	    			if ( !(vacio[0].contains(busqueda)  && vacio[1].contains(busqueda2))){
	    				bw.write(fila+"\n"); 
	    			}
	    		}
	    		bw.close();
    		    fw.close();
    		    br.close();
    		    fr.close();
    		    Files.copy( nuevo.toPath()  , viejo.toPath() , REPLACE_EXISTING );
    		    return true;
	    }catch(Exception e){
	    		System.out.println("No se pudo imprimir");
	    }
		return false;
	}
	
	public boolean verificarFilaConParesDatabaseTable(String direccion, String busqueda,String busqueda2){
		FileWriter fw = null;
		FileReader fr = null;
		String[] vacio = null;
		File nuevo = null;
		File viejo = null;
		String tempo_direc = dir +File.separator+"MyDB"+File.separator+ "temp.txt";
		System.out.println(tempo_direc);
		boolean modificado = false;
		System.out.println(tempo_direc);
		try {
			nuevo = new File(tempo_direc);
			nuevo.createNewFile();
			viejo = new File(direccion);

			fw = new FileWriter(nuevo);
			fr = new FileReader(viejo);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	   BufferedWriter bw = new BufferedWriter(fw); //ESCRIBO
	   BufferedReader br = new BufferedReader(fr); //LEO EL VIEJO
	   BufferedReaderIterable padre_iterador = new BufferedReaderIterable(br) ;
	   try{
	    		for ( String fila : padre_iterador ){
	    			vacio = fila.split(";");
	    			if ( !(vacio[0].contains(busqueda)  && vacio[1].contains(busqueda2))){
	    				bw.write(fila+"\n"); 
	    				modificado = true;
	    				
	    			}
	    		}
	    		bw.close();
    		    fw.close();
    		    br.close();
    		    fr.close();
	    }catch(Exception e){
	    		System.out.println("No se pudo imprimir");
	    }
		return modificado;
	}

	
	public ArrayList listadodeDBenRegistro(String ubicacion){
		FileReader fw = null;
		ArrayList listadatabases = new ArrayList();
		BufferedReader bw = null;
		BufferedReaderIterable bri = null;
		try {
			System.out.println(ubicacion);
			fw = new FileReader(ubicacion);
			bw = new BufferedReader( fw);
			bri = new BufferedReaderIterable( bw );
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	   try{
	    		for ( String leyendo_linea : bri ){
	    			listadatabases.add(leyendo_linea);
	    		}
	    		bw.close();
    		    fw.close();
	    }catch(Exception e){
	    		System.out.println("No se pudo imprimir");
	    }
		return listadatabases;
	}
	/**
	 * @parameter
	 *     direccion: ubicacion de donde esta la ubicacion del texto a modificar
	 *     busqueda
	 * */
	public boolean Anadir_fila_fichero(String direccion, String anidar){
		
		String tempo_direc = dir +File.separator+"MyDB"+File.separator+ "temp.txt";
		File nuevo =  null;
		FileWriter fw = null;
		
		
		File viejo = new File(tempo_direc);
		FileReader fr = null;
		
		String[] vacio = null;
		
		boolean modificado = false;
		try {
			
			System.out.println(tempo_direc);
			nuevo = new File(tempo_direc); //escribir en temporal NUEVO
			viejo = new File(direccion);
			if(!nuevo.exists()){
				nuevo.createNewFile();
			}
			System.out.println(viejo.exists() + "" + nuevo.exists() + nuevo.getPath());
			fr = new FileReader(viejo);			
			fw = new FileWriter(nuevo);
			
		   BufferedWriter bw = new BufferedWriter(fw); //ESCRIBO
		   BufferedReader br = new BufferedReader(fr); //LEO EL VIEJO
		   BufferedReaderIterable padre_iterador = new BufferedReaderIterable(br) ;
		   
		   for ( String fila : padre_iterador ){
			   bw.append(fila+"\n");
   			}
		    bw.append(anidar);
	    		bw.close();
    		    fw.close();
    		    br.close();
    		    fr.close();
    		    Files.copy( nuevo.toPath()  , viejo.toPath() , REPLACE_EXISTING );
    		    return true;
	    }catch(Exception e){
	    		System.out.println("No se pudo imprimir ");
	    }
		return false;
	}

}

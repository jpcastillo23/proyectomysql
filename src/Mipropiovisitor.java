import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.RuleNode;

import java.io.File;
import java.net.InetAddress; //esto es por si quiero generar translado VIA IP (como extra... talvez despeus)
import java.util.*;
/**
 * proyecto por JOSE PABLO CASTILLO RODAS
 * UNIVERSIDAD DEL VALLE
 * 
 * SE UTILIZO ANTLR4 CONFIGURADO EN ECLIPSE
 * */
public class Mipropiovisitor extends MISQLGRAMMARBaseVisitor<Misqlobject> {
	 /**
	  * @variables :
	  * 
	  * */
	public int watch_dog_process_count=0;
	public List<String> using_Databases = new ArrayList<String>();
	public List<String> using_Tables = new ArrayList<String>();
	//   SINTAXIS: <STRING DATABASES, STRING TABLE>
	public ArrayList<Tipodato> usingTablesDatabases = new ArrayList<Tipodato>();
	public HashMap<String,Tipodato> asStatementsaliases = new HashMap<String,Tipodato>();
	
	/**
	 * @return Misqlobject as a list in case of much atention.
	 *  EN CASO ME VUELVA UN EXPERTO EN ANTLR PODRÉ CREAR MI PROPIO
	 *  VISITADOR Y SABER COMO QUIERO QUE RETORNEN MIS DATOS
	@Override
	public Misqlobject visitChildren(RuleNode ctx){
		ArrayList se_pudo = new ArrayList();
		for (int index = 0; index < ctx.getChildCount(); index++)  
			se_pudo.addAll( (List)visit(ctx.getChild(index)));  
		return Misqlobject;
		
	}
	@Override  
	 public List<E> visitTerminal(TerminalNode node) {  
	  if (node.getSymbol().getType() == CSVLexer.NUMBER) {  
	   int number = Integer.parseInt(node.getText());  
	   return Arrays.asList(number);  
	  } // */
	@Override
	public Misqlobject visitSql_stmt_list(@NotNull MISQLGRAMMARParser.Sql_stmt_listContext ctx) { 
		System.out.println("1 visitSql_stmt_list");
		int cuantos = ctx.getChildCount(); //estos incluyen la coma (imprimase para ver)
		Misqlobject iterable = null;
		boolean continuar = true;
		for(int i=0;i<cuantos/2;i++){
			/**
			 * 
			 * CONJUNTO DE IMPRESIONES PARA EVALUAR COMO SE DESGLOSA EL ARBOL
			 * 
			 * System.out.println(ctx.getChild(i*2).getText());
			 * System.out.println(visit(ctx.getChild(i*2)));
			 * 
			**/
			System.out.println(visit( ctx.getChild(i*2) )+" "+i);
			/**
			 *    pruebas de como se desglosan las intrucciones por ramificacion en 
			 *    ANTLR4
			System.out.println(ctx.getChildCount() +" " +ctx.getChild(0).getChildCount() +" \n");
			System.out.println("\t \n"+ctx.getText() +" popo " +ctx.getChild(0).getText() +" ");
			System.out.println(ctx.sql_stmt(i).getText());
			//  
			System.out.println("fucker " + cuantos);
			iterable =  new Misqlobject(visitChildren(ctx.sql_stmt(i)));
			System.out.println(iterable.retornoTipoObjeto());
			System.out.println("fucker3");
			continuar = iterable.asBoolean();
			System.out.println("fucker2");
			if (!continuar){
				return new Misqlobject(false);
			}*/
		}
		Misqlobject todoexitoso = new Misqlobject(new Boolean(true))   ;
		return  		todoexitoso;
	}
	
	@Override 
	public Misqlobject visitSql_stmt(@NotNull MISQLGRAMMARParser.Sql_stmtContext ctx) { 
		System.out.println("2 visitSql_stmt");
		//visitChildren(ctx);   OJO CON ESTO, DEBE DE SER OBJETO BOOLEANNO MULA
		Misqlobject luego = new Misqlobject( visitChildren(ctx) );
		System.out.println(luego);
		Misqlobject boleano = new Misqlobject(new Boolean( false));
		//System.out.println(boleano.toString()+"PRUEBA");
		return boleano;
	}	
	/**
	 * 	CREACION DE NUEVA TABLA
	 * su estructura es de la siguiente manera.
	 * 		K_CREATE ( K_TEMP | K_TEMPORARY )? K_TABLE ( K_IF K_NOT K_EXISTS )?
   			( database_name '.' )? table_name
   			( '(' column_def ( ',' column_def )* ( ',' table_constraint )* ')' ( K_WITHOUT IDENTIFIER )? | K_AS select_stmt  ) 
 	 *
 	 *	nota: REALMENTE  DATABASES no tiene que formar parte de la sintaxis. se asume que
 	 *	estas usando una.
	 */
	@Override 
	public Misqlobject visitCreate_table_stmt(@NotNull MISQLGRAMMARParser.Create_table_stmtContext ctx) { 
		System.out.println("3 visitCreate_table_stmt");
		//verificacion si ya hay cargada una base_de_datos
		if(using_Databases.isEmpty()){
			return new Misqlobject(false);
		}
		int how_many = ctx.getChildCount();
		boolean posee_ya_archivo = false;
		String data_Base_name = "";
		ArrayList<String> listado_db_creado = new ArrayList<String>();
		System.out.println(ctx.database_name().getText());
		if(ctx.database_name().getText()==null){
			System.out.println("si funciona");
		}
		//Misqlobject mi_objeto  = new Misqlobject( visit(ctx.database_name()));//objeto string
		Misqlobject mi_objeto2 = new Misqlobject( visit(ctx.table_name() ));//objeto string
		System.out.println(mi_objeto2 +" en linea 46, Crate_table");
		DLL_manager manejador = new DLL_manager();
		//NECESITO UNA FUNCION QUE BUSQUE LAS BASES DDE DATOS Y PUEDA BUSCAR QUE ESTA
		//EN USO SI EN TAL caso EL USUARIO NO COLOCA EL Database.table
		//LISTADO_CREADO DA EL NOMBRE DE TODAS LAS BASES DE DATOS Y BUSCA EN CADA UNA
		listado_db_creado = manejador.listadodeDBenRegistro( manejador.getcarpetaRootMYDBReg());
		//estoy buscando el la capteta de la DB la informaciond e sus tablas
		posee_ya_archivo = (manejador.buscarTextoenArchivo(manejador.getDireccionMYDB()+using_Databases.get(0)+"reg.txt"  , mi_objeto2.asString()) >0);
		if(posee_ya_archivo){
			return new Misqlobject(false);
		}
		
		/**for(String archivo : listado_db_creado){  
			//si busco en el texto ubicado en la carpeta de la DB fila por fila si posee la tabla que deseo crear
			try{//                                                                                       /        DATABASE 			reg.txt		, 	
				posee_ya_archivo = (manejador.buscarTextoenArchivo(manejador.getDireccionMYDB()+archivo.split(".")[0]+"reg.txt"  , mi_objeto2.asString()) >0);
			if( posee_ya_archivo &&  true ){
				//ya tenia la tabla en la database de datos, denegar!!!
				System.out.println("LA TABLA YA EXISTE EN SU BASE DE DATOS");
				return new Misqlobject(false);
				}
			}catch(Exception e){
				System.out.println("Murio por que no puso database");
			}	
		}*/
		manejador.Anadir_fila_fichero(manejador.getDireccionMYDB()+File.separator + using_Databases.get(0) +"reg.txt" , mi_objeto2.asString());
		//getDireccionMYDB()
		return new Misqlobject(true) ;
	}
	
	public void print(String valor){
		System.out.println(valor);
	}

	/**
	 * CREACION DE BASES DE DATOS
	 * 
	 * ESTRUCTURA   : K_CREATE K_DATABASE ( K_IF K_NOT K_EXISTS )?  database_name 
	 * @return: retorna String con la direccion
	 * */
	@Override
	public Misqlobject visitCreate_database_stmt(@NotNull MISQLGRAMMARParser.Create_database_stmtContext ctx) { 
		
		System.out.println("4 visitCreate_database_stmt");
		int cuantos_elementos_hay = ctx.getChildCount();
		DLL_manager manejador = new DLL_manager();
		boolean existe_en_archivo, existe_directorio, prueba_sobreescritura1;
		Misqlobject[] nuevo ;
		String nombre_data_base = visit(ctx.database_name()).asString() ;
		String archivodbname = nombre_data_base +".db";
		Misqlobject retorno = new Misqlobject(nombre_data_base);
		System.out.println(retorno);
		System.out.println("probando CREATE DATABASE");
		String error = "ERROR";
		
		//Si existe no creo ni la carpeta ni nuestro registro y escribo en ella la nueva 
		// base  de datos
		
		try{     //REGISTRO DE BASE DE DATOS
			System.out.println("acavpy " + retorno);
			boolean prueba_sobreescritura = (manejador.isinDatabaseFichero(manejador.getcarpetaRootMYDBReg(),nombre_data_base));
			if(prueba_sobreescritura){
				//si encuentra esto es por que esta sobreescribiendo
				System.out.println(error + " trato de sobre escritura");
				return new Misqlobject(false);
			};

		}catch(Exception e){
			print("Error de manejo de backend");
		};    
		
		try{
			if(cuantos_elementos_hay==3){
				manejador.CrearCarpeta_base_datos(nombre_data_base);
				manejador.Crear_registro_tabla_y_base_datos(nombre_data_base);
				manejador.Anadir_fila_fichero(manejador.getcarpetaRootMYDBReg(),nombre_data_base+".db");
				using_Databases.add(nombre_data_base);
				return new Misqlobject(true);
				}
		}catch (Exception e){ print("FUCK NO LOGRE ALMACENAR NADA");};
		return new Misqlobject(true); 
	}

	@Override 
	public Misqlobject visitCreate_virtual_table_stmt(@NotNull MISQLGRAMMARParser.Create_virtual_table_stmtContext ctx) { 
		System.out.println("5 visitCreate_virtual_table_stmt");
		return visitChildren(ctx);
	}
 
	@Override 
	public Misqlobject visitType_name(@NotNull MISQLGRAMMARParser.Type_nameContext ctx) {
		System.out.println("6 visitType_name");
		return visitChildren(ctx);
	}
 
	@Override 
	public Misqlobject visitSavepoint_stmt(@NotNull MISQLGRAMMARParser.Savepoint_stmtContext ctx) { 
		System.out.println("7 visitSavepoint_stmt");

		return visitChildren(ctx);
	}
 
	@Override 
	public Misqlobject visitDrop_index_stmt(@NotNull MISQLGRAMMARParser.Drop_index_stmtContext ctx) { 
		System.out.println("8 visitDrop_index_stmt");

		return visitChildren(ctx);
	}
 
	@Override 
	public Misqlobject visitTable_name(@NotNull MISQLGRAMMARParser.Table_nameContext ctx) { 
		System.out.println("9 visitTable_name");
		String Mi_database_name = new String(ctx.getText());
		System.out.println("9 "+ Mi_database_name );
		Misqlobject nuevo = new Misqlobject(Mi_database_name);
		return nuevo;
	}
 
	@Override 
	public Misqlobject visitJoin_constraint(@NotNull MISQLGRAMMARParser.Join_constraintContext ctx) { 
		System.out.println("10 visitJoin_constraint");
		return visitChildren(ctx);
	}
 
	@Override 
	public Misqlobject visitCte_table_name(@NotNull MISQLGRAMMARParser.Cte_table_nameContext ctx) { 
		System.out.println("11 visitCte_table_name");
		return visitChildren(ctx);
	}
 
	@Override 
	public Misqlobject visitIndexed_column(@NotNull MISQLGRAMMARParser.Indexed_columnContext ctx) { 
		System.out.println("12 visitIndexed_column");
		return visitChildren(ctx);
	}
	 
	public boolean isParsableintoInteger(String input){
	    boolean parsable = true;
	    try{
	        Integer.parseInt(input);
	    }catch(NumberFormatException e){
	        parsable = false;
	    }
	    return parsable;
	}
	public boolean isParsableintoFloat(String input){
	    boolean parsable = true;
	    try{
	        Float.parseFloat(input);
	    }catch(NumberFormatException e){
	        parsable = false;
	    }
	    return parsable;
	}
	
	public boolean isParsableintoCalendar(String input){
	    boolean parsable = true;
	    Misqlobject prueba = null;
	    Date nuevo;
	    Calendar chunche;
	    try{
	    		chunche = prueba.stringToCalendar(input);
	    }catch(Exception e){
	        parsable = false;
	    }
	    return parsable;
	}
	
 /* 	  ESTE ES MI DATO NUMERICO LITERAL CREADO POR EL SISTEMA 
  * 	Y LE DENOMINAMOS LITERAL. ESTA LITERAL PUEDE SER CASTEADA
  * 	AL TIPO :	
 | 		NUMERIC_LITERAL
 | 		STRING_LITERAL
 | 		BLOB_LITERAL
 | 		K_NULL
 | 		K_CURRENT_TIME
 | 		K_CURRENT_DATE
 | 		K_CURRENT_TIMESTAMP
  *
  * 	
  * 
	*/
	@Override 
	public Misqlobject visitLiteral_value(@NotNull MISQLGRAMMARParser.Literal_valueContext ctx) { 
		System.out.println("13 visitLiteral_value, cantidad de hijos: "+ ctx.getChildCount() );
		//+ "\nwatch dog count: "+ watch_dog_process_count);
		//visit(ctx);  //INSTRUCCION RECURSIVA QUE PUEDE GENERAR LOOPS
		System.out.println("esto deberia de guardar :"+ ctx.getChild(0));
		watch_dog_process_count = watch_dog_process_count + 1 ;
		Misqlobject definiendo_mi_string;
		// COMO PUEDE SER CUALQUIER COSA LO CONVIERTO A A MI VALOR ABSOLUTO
		if (ctx.getChild(0).getText().toUpperCase().compareTo("CURRENT_DATE")==0){
			System.out.println("\nCURRENT_DATE creando");
			definiendo_mi_string = new Misqlobject( new Date() );
		}else if( ctx.getChild(0).equals(null) ){
			System.out.println("\nNull creando");
			definiendo_mi_string = null;
		}else if( ctx.getChild(0).getText().toUpperCase().compareTo("STRING_LITERAL")==0 ){
			System.out.println("\nSTRING_LITERAL creando");
			definiendo_mi_string = new Misqlobject(  new String(ctx.getChild(0).getText())  );
		}else if( ctx.getChild(0).getText().toUpperCase().compareTo("NUMERIC_LITERAL")==0 ){
			System.out.println("\nNUMERIC_LITERAL creando");
			definiendo_mi_string = new Misqlobject(  new String(ctx.getChild(0).getText())  );
		}else if( isParsableintoInteger(ctx.getText()) ){
			System.out.println("\nINTEGER creando");
			definiendo_mi_string = new Misqlobject(  new Integer( ctx.getText()) );
		}else if( isParsableintoFloat(ctx.getText()) ){
			System.out.println("\nFLOAT creando");
			definiendo_mi_string = new Misqlobject(  new Float( ctx.getText() )  );
		}else if( isParsableintoCalendar(ctx.getText()) ){
			System.out.println("\nDATE creando");
			definiendo_mi_string = new Misqlobject(  Calendar.getInstance()  );
		}else{
			System.out.println(ctx.getText()+"popo");
			definiendo_mi_string = new Misqlobject(  new String( visit(ctx).asString() )  );
		};
		System.out.println("Retorno 13 visitLiteral_value \n Dato:" + definiendo_mi_string.toString() +" del tipo "+ definiendo_mi_string.retornoTipoObjeto());
		return definiendo_mi_string;//visitChildren(ctx);*/
	}
 
	@Override 
	public Misqlobject visitDelete_stmt_limited(@NotNull MISQLGRAMMARParser.Delete_stmt_limitedContext ctx) { 
		System.out.println("14 visitDelete_stmt_limited");
		return visitChildren(ctx);
	}
 
	@Override 
	public Misqlobject visitTransaction_name(@NotNull MISQLGRAMMARParser.Transaction_nameContext ctx) { 
		System.out.println("15 visitTransaction_name");
		return visitChildren(ctx);
	}
 
	@Override 
	public Misqlobject visitColumn_def(@NotNull MISQLGRAMMARParser.Column_defContext ctx) { 
		System.out.println("16 visitColumn_def");
		return visitChildren(ctx);
	}
 
	@Override 
	public Misqlobject visitNew_table_name(@NotNull MISQLGRAMMARParser.New_table_nameContext ctx) { 
		System.out.println("17 visitNew_table_name");
		return visitChildren(ctx);
	}
 
	@Override 
	public Misqlobject visitKeyword(@NotNull MISQLGRAMMARParser.KeywordContext ctx) { 
		System.out.println("18 visitKeyword");
		String mi_key_word = ctx.getChild(0).getText();
		Misqlobject re_envio_keywork = new Misqlobject(mi_key_word);
		return re_envio_keywork;
	}
 
	@Override 
	public Misqlobject visitError_message(@NotNull MISQLGRAMMARParser.Error_messageContext ctx) { 
		System.out.println("19 visitError_message");
		return visitChildren(ctx);
	}
 
	@Override 
	public Misqlobject visitCreate_index_stmt(@NotNull MISQLGRAMMARParser.Create_index_stmtContext ctx) { 
		System.out.println("20 visitCreate_index_stmt");
		return visitChildren(ctx);
	}
 
	@Override 
	public Misqlobject visitUnary_operator(@NotNull MISQLGRAMMARParser.Unary_operatorContext ctx) { 
		System.out.println("21 visitUnary_operator");
		return visitChildren(ctx);
	}
 
	@Override 
	public Misqlobject visitRollback_stmt(@NotNull MISQLGRAMMARParser.Rollback_stmtContext ctx) { 
		System.out.println("22 visitRollback_stmt");
		return visitChildren(ctx);
	}
	@Override 
	public Misqlobject visitJoin_operator(@NotNull MISQLGRAMMARParser.Join_operatorContext ctx) { 
		System.out.println("23 visitJoin_operator");
		return visitChildren(ctx);
	}
 
	@Override 
	public Misqlobject visitCollation_name(@NotNull MISQLGRAMMARParser.Collation_nameContext ctx) { 
		System.out.println("24 visitCollation_name");
		return visitChildren(ctx);
	}
 
	@Override 
	public Misqlobject visitPragma_stmt(@NotNull MISQLGRAMMARParser.Pragma_stmtContext ctx) { 
		System.out.println("25 visitPragma_stmt");

		return visitChildren(ctx);
	}
 
	@Override 
	public Misqlobject visitUpdate_stmt(@NotNull MISQLGRAMMARParser.Update_stmtContext ctx) { 
		System.out.println("26 visitUpdate_stmt");

		return visitChildren(ctx);
	}
 
	@Override 
	public Misqlobject visitPrimary_sintax(@NotNull MISQLGRAMMARParser.Primary_sintaxContext ctx) { 
		System.out.println("27 visitPrimary_sintax");
		DLL_manager manejador = new DLL_manager();
		//creacion de la carpeta de MYDB
		try{     //REGISTRO DE BASE DE DATOS
			System.out.println("Existe Registro? " + manejador.existeRegistroDB());
			if( !manejador.existeRegistroDB()){
				System.out.println("Creando Registro de DB" );
				//manejador.inicializar_RegistroDBcarpeta();
				manejador.inicializarRegistroDB();
			};
		}catch(Exception e){
			print("Error de manejo de backend");
		};    		
		return visitChildren(ctx);
	}
 
	@Override 
	public Misqlobject visitColumn_alias(@NotNull MISQLGRAMMARParser.Column_aliasContext ctx) { 
		System.out.println("28 visitColumn_alias");

		return visitChildren(ctx);
	}
 
	@Override 
	public Misqlobject visitAlter_table_stmt(@NotNull MISQLGRAMMARParser.Alter_table_stmtContext ctx) { 
		System.out.println("29 visitAlter_table_stmt");
		
		return visitChildren(ctx);
	}
 
	@Override 
	public Misqlobject visitAttach_stmt(@NotNull MISQLGRAMMARParser.Attach_stmtContext ctx) { 
		System.out.println("30 visitAttach_stmt");

		return visitChildren(ctx);
	}
 
	@Override 
	public Misqlobject visitName(@NotNull MISQLGRAMMARParser.NameContext ctx) { 
		System.out.println("31 visitName");
		
		return visitChildren(ctx);
	}
 
	@Override 
	public Misqlobject visitTable_or_index_name(@NotNull MISQLGRAMMARParser.Table_or_index_nameContext ctx) { 
		System.out.println("32 visitTable_or_index_name");

		return visitChildren(ctx);
	}
 
	@Override 
	public Misqlobject visitModule_name(@NotNull MISQLGRAMMARParser.Module_nameContext ctx) { 
		System.out.println("33 visitModule_name");

		return visitChildren(ctx);
	}
 
	@Override 
	public Misqlobject visitCompound_select_stmt(@NotNull MISQLGRAMMARParser.Compound_select_stmtContext ctx) { 
		System.out.println("34 visitCompound_select_stmt");

		return visitChildren(ctx);
	}
 
	@Override 
	public Misqlobject visitTable_alias(@NotNull MISQLGRAMMARParser.Table_aliasContext ctx) { 
		System.out.println("35 visitTable_alias");

		return visitChildren(ctx);
	}
 
	@Override 
	public Misqlobject visitDrop_trigger_stmt(@NotNull MISQLGRAMMARParser.Drop_trigger_stmtContext ctx) { 
		System.out.println("36 visitDrop_trigger_stmt");

		return visitChildren(ctx);
	}
 
	@Override 
	public Misqlobject visitModule_argument(@NotNull MISQLGRAMMARParser.Module_argumentContext ctx) { 
		System.out.println("37 visitModule_argument");

		return visitChildren(ctx);
	}
 
	@Override 
	public Misqlobject visitCreate_trigger_stmt(@NotNull MISQLGRAMMARParser.Create_trigger_stmtContext ctx) { 
		System.out.println("38 visitCreate_trigger_stmt");

		return visitChildren(ctx);
	}
 
	@Override 
	public Misqlobject visitSigned_number(@NotNull MISQLGRAMMARParser.Signed_numberContext ctx) { 
		System.out.println("39 visitSigned_number");
		Integer miinteger;
		Float	mifloat;
		try{
			miinteger = new Integer(ctx.getChild(0).getText()+ctx.getChild(1).getText());
			return new Misqlobject(miinteger);
		}catch(Exception e){
			try{
				mifloat = new Float( ctx.getChild(0).getText()+ctx.getChild(1).getText()  );
				return new Misqlobject(mifloat);
			}catch(Exception e1){
				System.out.println("No puro convertir el numero -");
			}
		}
		return visitChildren(ctx);
	}
 
	@Override 
	public Misqlobject visitOrdering_term(@NotNull MISQLGRAMMARParser.Ordering_termContext ctx) { 
		System.out.println("40 visitOrdering_term");

		return visitChildren(ctx);
	}
 
	@Override 
	public Misqlobject visitSimple_select_stmt(@NotNull MISQLGRAMMARParser.Simple_select_stmtContext ctx) {
		System.out.println("41 visitSimple_select_stmt");
		return visitChildren(ctx);
	}
 
	@Override 
	public Misqlobject visitTable_or_subquery(@NotNull MISQLGRAMMARParser.Table_or_subqueryContext ctx) { 
		System.out.println("42 visitTable_or_subquery");

		return visitChildren(ctx);
	}
 
	@Override 
	public Misqlobject visitError(@NotNull MISQLGRAMMARParser.ErrorContext ctx) { 
		System.out.println("43 visitError");		
		return visitChildren(ctx);
	}
 
	@Override 
	public Misqlobject visitCommit_stmt(@NotNull MISQLGRAMMARParser.Commit_stmtContext ctx) { 
		System.out.println("42 visitCommit_stmt");

		return visitChildren(ctx);
	}
 
	@Override 
	public Misqlobject visitSelect_or_values(@NotNull MISQLGRAMMARParser.Select_or_valuesContext ctx) { 
		System.out.println("43 visitSelect_or_values");
		return visitChildren(ctx);
	}
 
	@Override 
	public Misqlobject visitSelect_core(@NotNull MISQLGRAMMARParser.Select_coreContext ctx) { 
		System.out.println("44 visitSelect_core");
		return visitChildren(ctx);
	}
 
	@Override 
	public Misqlobject visitSavepoint_name(@NotNull MISQLGRAMMARParser.Savepoint_nameContext ctx) { 
		System.out.println("45 visitSavepoint_name");
		return visitChildren(ctx);
	}
 
	@Override 
	public Misqlobject visitFactored_select_stmt(@NotNull MISQLGRAMMARParser.Factored_select_stmtContext ctx) { 
		System.out.println("46 visitFactored_select_stmt");
		return visitChildren(ctx);
	}
 
	@Override 
	public Misqlobject visitConflict_clause(@NotNull MISQLGRAMMARParser.Conflict_clauseContext ctx) { 
		System.out.println("47 visitConflict_clause");
		return visitChildren(ctx);
	}
 
	@Override 
	public Misqlobject visitCompound_operator(@NotNull MISQLGRAMMARParser.Compound_operatorContext ctx) { 
		System.out.println("48 visitCompound_operator");
		return visitChildren(ctx);
	}
 
	@Override 
	public Misqlobject visitTrigger_name(@NotNull MISQLGRAMMARParser.Trigger_nameContext ctx) { 
		System.out.println("49 visitTrigger_name");
		return visitChildren(ctx);
	}
 
	@Override 
	public Misqlobject visitBegin_stmt(@NotNull MISQLGRAMMARParser.Begin_stmtContext ctx) { 
		System.out.println("50 visitBegin_stmt");
		return visitChildren(ctx);
	}
 
	@Override 
	public Misqlobject visitForeign_key_clause(@NotNull MISQLGRAMMARParser.Foreign_key_clauseContext ctx) { 
		System.out.println("51 visitForeign_key_clause");
		return visitChildren(ctx);
	}
 
	@Override 
	public Misqlobject visitReindex_stmt(@NotNull MISQLGRAMMARParser.Reindex_stmtContext ctx) { 
		System.out.println("52 visitReindex_stmt");
		return visitChildren(ctx);
	}
 
	@Override 
	public Misqlobject visitAny_name(@NotNull MISQLGRAMMARParser.Any_nameContext ctx) { 
		System.out.println("53 visitAny_name");
		int cuantos_elementos = ctx.getChildCount();
		if (cuantos_elementos==1){
			return new Misqlobject((String)ctx.getChild(0).getText());
		}else if (cuantos_elementos > 1){
			return visitChildren(ctx);
		}else{
			System.out.println("\t \t posible error de elementos visitante= "+cuantos_elementos);
			return new Misqlobject(ctx.getChild(0).getClass() );
		}
	}
 
	@Override 
	public Misqlobject visitInsert_stmt(@NotNull MISQLGRAMMARParser.Insert_stmtContext ctx) { 
		System.out.println("54 visitInsert_stmt");

		return visitChildren(ctx);
	}
 
	@Override 
	public Misqlobject visitCommon_table_expression(@NotNull MISQLGRAMMARParser.Common_table_expressionContext ctx) { 
		System.out.println("55 visitCommon_table_expression");
		return visitChildren(ctx);
	}
 
	@Override 
	public Misqlobject visitDrop_table_stmt(@NotNull MISQLGRAMMARParser.Drop_table_stmtContext ctx) { 
		System.out.println("56 visitDrop_table_stmt");
		String data_base_name = visit( ctx.database_name() ).asString() ;
		String table_base_name = visit( ctx.table_name() ).asString() ;

		int i = using_Databases.size(); 
		for ( Tipodato valor : usingTablesDatabases ){
			if( ((String) valor.getString()).contains(table_base_name) &&  ((Misqlobject)valor.getValue()).asString().contains(data_base_name) ){
				return new Misqlobject( false);
			}
			
		}
		print("TENGO PENDIENTE EL DROP");
		//eliminarFilaConDatoDeUnDoc()
		
		return visitChildren(ctx);
	}
 
	@Override 
	public Misqlobject visitRelease_stmt(@NotNull MISQLGRAMMARParser.Release_stmtContext ctx) { 
		System.out.println("57 visitRelease_stmt");
		return visitChildren(ctx);
	}
 
	@Override 
	public Misqlobject visitVacuum_stmt(@NotNull MISQLGRAMMARParser.Vacuum_stmtContext ctx) { 
		System.out.println("58 visitVacuum_stmt");
		return visitChildren(ctx);
	}
 
	//@Override 
	public Misqlobject visitExpr(@NotNull MISQLGRAMMARParser.ExprContext ctx) { 
		System.out.println("59 visitExpr");
		return visitChildren(ctx);
	}
 
	@Override 
	public Misqlobject visitDrop_view_stmt(@NotNull MISQLGRAMMARParser.Drop_view_stmtContext ctx) { 
		System.out.println("60 visitDrop_view_stmt");
		return visitChildren(ctx);
	}
 
	@Override 
	public Misqlobject visitIndex_name(@NotNull MISQLGRAMMARParser.Index_nameContext ctx) { 
		System.out.println("61 visitIndex_name");
		return visitChildren(ctx);
	}
 
	@Override 
	public Misqlobject visitView_name(@NotNull MISQLGRAMMARParser.View_nameContext ctx) { 
		System.out.println("62 visitView_name");
		return visitChildren(ctx);
	}
 
	@Override 
	public Misqlobject visitDetach_stmt(@NotNull MISQLGRAMMARParser.Detach_stmtContext ctx) { 
		System.out.println("63 visitDetach_stmt");
		return visitChildren(ctx);
	}
 
	@Override 
	public Misqlobject visitWith_clause(@NotNull MISQLGRAMMARParser.With_clauseContext ctx) { 
		System.out.println("64 visitWith_clause");
		return visitChildren(ctx);
	}
 
	@Override 
	public Misqlobject visitQualified_table_name(@NotNull MISQLGRAMMARParser.Qualified_table_nameContext ctx) { 
		System.out.println("65 visitQualified_table_name");
		return visitChildren(ctx);
	}
 
	@Override 
	public Misqlobject visitCreate_view_stmt(@NotNull MISQLGRAMMARParser.Create_view_stmtContext ctx) { 
		System.out.println("66 visitCreate_view_stmt");
		return visitChildren(ctx);
	}
 
	@Override 
	public Misqlobject visitColumn_constraint(@NotNull MISQLGRAMMARParser.Column_constraintContext ctx) { 
		System.out.println("67 visitColumn_constraint");
		return visitChildren(ctx);
	}
 
	@Override 
	public Misqlobject visitDatabase_name(@NotNull MISQLGRAMMARParser.Database_nameContext ctx) { 
		System.out.println("68 visitDatabase_name");
		String Mi_database_name = new String(ctx.getText());
		Misqlobject nuevo = new Misqlobject(Mi_database_name);

		System.out.println("68 " + Mi_database_name +" " +nuevo.retornoTipoObjeto());

		return nuevo;
	}
 
	@Override 
	public Misqlobject visitColumn_name(@NotNull MISQLGRAMMARParser.Column_nameContext ctx) { 
		System.out.println("69 visitColumn_name");
		String Mi_column_name = new String(ctx.getText());
		System.out.println("69 "+ Mi_column_name );
		Misqlobject nuevo = new Misqlobject(Mi_column_name);
		return nuevo;
	}
 
	@Override 
	public Misqlobject visitResult_column(@NotNull MISQLGRAMMARParser.Result_columnContext ctx) { 
		System.out.println("70 visitResult_column");
		return visitChildren(ctx);
	}
 
	@Override 
	public Misqlobject visitTable_constraint(@NotNull MISQLGRAMMARParser.Table_constraintContext ctx) { 
		System.out.println("71 visitTable_constraint");
		return visitChildren(ctx);
	}
 
	@Override 
	public Misqlobject visitPragma_name(@NotNull MISQLGRAMMARParser.Pragma_nameContext ctx) { 
		System.out.println("72 visitPragma_name");
		return visitChildren(ctx);
	}
 
	@Override 
	public Misqlobject visitAnalyze_stmt(@NotNull MISQLGRAMMARParser.Analyze_stmtContext ctx) { 
		System.out.println("73 visitAnalyze_stmt");
		return visitChildren(ctx);
	}
 
	@Override 
	public Misqlobject visitForeign_table(@NotNull MISQLGRAMMARParser.Foreign_tableContext ctx) { 
		System.out.println("74 visitForeign_table");
		return visitChildren(ctx);
	}
 
	@Override 
	public Misqlobject visitFunction_name(@NotNull MISQLGRAMMARParser.Function_nameContext ctx) { 
		System.out.println("75 visitFunction_name");
		return visitChildren(ctx);
	}
 
	@Override 
	public Misqlobject visitJoin_clause(@NotNull MISQLGRAMMARParser.Join_clauseContext ctx) { 
		System.out.println("76 visitFunction_name");
		return visitChildren(ctx);
	}
 
	@Override 
	public Misqlobject visitUpdate_stmt_limited(@NotNull MISQLGRAMMARParser.Update_stmt_limitedContext ctx) { 
		System.out.println("77 visitFunction_name");
		return visitChildren(ctx);
	}
 
	@Override 
	public Misqlobject visitRaise_function(@NotNull MISQLGRAMMARParser.Raise_functionContext ctx) { 
		System.out.println("78 visitFunction_name");
		return visitChildren(ctx);
	}
 
	@Override 
	public Misqlobject visitSelect_stmt(@NotNull MISQLGRAMMARParser.Select_stmtContext ctx) { 
		System.out.println("79 visitFunction_name");
		return visitChildren(ctx);
	}
 
	@Override 
	public Misqlobject visitDelete_stmt(@NotNull MISQLGRAMMARParser.Delete_stmtContext ctx) { 
		System.out.println("80 visitFunction_name");
		return visitChildren(ctx);
	}
 
	@Override 
	public Misqlobject visitPragma_value(@NotNull MISQLGRAMMARParser.Pragma_valueContext ctx) { 
		System.out.println("81 visitFunction_name");
		return visitChildren(ctx);
	}
/**
 * 
 * 
 * 
 * 
 * **/
	@Override 
	public Misqlobject visitAssustituteexpression(@NotNull MISQLGRAMMARParser.AssustituteexpressionContext ctx) { 
		System.out.println("82 visitAssustituteexpression");

		return visitChildren(ctx); 
	}
 
	@Override 
	public Misqlobject visitExpressionOR(@NotNull MISQLGRAMMARParser.ExpressionORContext ctx) { 
		return visitChildren(ctx); 
	}
   
	@Override 
	public Misqlobject visitCollationexpression(@NotNull MISQLGRAMMARParser.CollationexpressionContext ctx) { 
		return visitChildren(ctx); 
	}
   
	@Override 
	public Misqlobject visitCompnotbetweenexpression(@NotNull MISQLGRAMMARParser.CompnotbetweenexpressionContext ctx) { 
		return visitChildren(ctx); 
	}

	@Override 
	public Misqlobject visitAglomeraexpression(@NotNull MISQLGRAMMARParser.AglomeraexpressionContext ctx) { 
		return visitChildren(ctx); 
	}
  
	@Override 
	public Misqlobject visitIsornotexpression(@NotNull MISQLGRAMMARParser.IsornotexpressionContext ctx) { 
		return visitChildren(ctx); 
	}
 
	@Override 
	public Misqlobject visitEquexpression(@NotNull MISQLGRAMMARParser.EquexpressionContext ctx) { 
		return visitChildren(ctx); 
	}
   
	@Override 
	public Misqlobject visitNotinexpression(@NotNull MISQLGRAMMARParser.NotinexpressionContext ctx) { 
		return visitChildren(ctx); 
	}
 
	@Override 
	public Misqlobject visitBitManiexpression(@NotNull MISQLGRAMMARParser.BitManiexpressionContext ctx) { 
		return visitChildren(ctx); 
	}
    
	@Override 
	public Misqlobject visitUnitaryexpression(@NotNull MISQLGRAMMARParser.UnitaryexpressionContext ctx) { 
		return visitChildren(ctx); 
	}
   
	@Override 
	public Misqlobject visitAddRestexpression(@NotNull MISQLGRAMMARParser.AddRestexpressionContext ctx) { 
		return visitChildren(ctx); 
	}
	@Override 
	public Misqlobject visitJoinexpression(@NotNull MISQLGRAMMARParser.JoinexpressionContext ctx) { 
		return visitChildren(ctx); 
	}
   
	@Override 
	public Misqlobject visitNullcompexpression(@NotNull MISQLGRAMMARParser.NullcompexpressionContext ctx) { 
		return visitChildren(ctx); 
	}
	@Override 
	public Misqlobject visitNotexistexpression(@NotNull MISQLGRAMMARParser.NotexistexpressionContext ctx) { 
		return visitChildren(ctx); 
	}
	@Override 
	public Misqlobject visitMulDivexpression(@NotNull MISQLGRAMMARParser.MulDivexpressionContext ctx) { 
		return visitChildren(ctx); 
	}
    @Override 
	public Misqlobject visitExpressionNumeral(@NotNull MISQLGRAMMARParser.ExpressionNumeralContext ctx) { 
		return visitChildren(ctx); 
	}
	@Override 
	public Misqlobject visitExpressionDBTableCol(@NotNull MISQLGRAMMARParser.ExpressionDBTableColContext ctx) { 
		return visitChildren(ctx); 
	}

	@Override 
	public Misqlobject visitComparationExpression(@NotNull MISQLGRAMMARParser.ComparationExpressionContext ctx) { 
		return visitChildren(ctx); 
	}
	@Override 
	public Misqlobject visitParenexpression(@NotNull MISQLGRAMMARParser.ParenexpressionContext ctx) { 
		return visitChildren(ctx); 
	}
	@Override 
	public Misqlobject visitFuntionexpression(@NotNull MISQLGRAMMARParser.FuntionexpressionContext ctx) { 
		return visitChildren(ctx); 
	}
      
	@Override 
	public Misqlobject visitBindexpression(@NotNull MISQLGRAMMARParser.BindexpressionContext ctx) { 
		return visitChildren(ctx); 
	}
   
	@Override 
	public Misqlobject visitOrwhileexpression(@NotNull MISQLGRAMMARParser.OrwhileexpressionContext ctx) { 
		return visitChildren(ctx); 
	}
    @Override 
	public Misqlobject visitAndwhileexpression(@NotNull MISQLGRAMMARParser.AndwhileexpressionContext ctx) { 
		return visitChildren(ctx); 
	}
   
	@Override public Misqlobject visitUse_stmt(@NotNull MISQLGRAMMARParser.Use_stmtContext ctx) { 
		System.out.println("881 visitUse_stmt");
		Misqlobject nombredatabaseforusing = visit(ctx.database_name() );
		Misqlobject retorno;
		Misqlobject iterando;
		//SI QUIERE USAR UNA TABLA, ELIMINA LA ACTUAL Y LUEGO COLOCA LA OTRA, PERO DEBE
		//DE VER SI NO HAY TABLAS DE ESTA DATABASE EN FUNCIONAMIENTO.
		
		//VER SI NO ESTA EN USO LA QUE ESTOY UTLIZANDO
		for( Tipodato iterable : usingTablesDatabases){
			//string es table, el otro el Misqlobject y es 
			iterando = ( Misqlobject )iterable.getValue() ;
			if( nombredatabaseforusing.asString().compareTo(iterando.asString() )==0 ){
				return new Misqlobject(false);
				//no se pudo usar debido a que se encontro en los de uso
			}
		
		}
		//cada vez que es un nuevo set de instrucciones tiene que resetearse
		if (using_Databases.size() >1){
			using_Databases.clear();
			using_Databases.add(nombredatabaseforusing.asString());		
		}
		retorno = new Misqlobject( ctx.database_name() );
		return new Misqlobject(true); 
	}
	public void voyaUsarRecurso(String table, Object database){

		usingTablesDatabases.add(new Tipodato(table,new Misqlobject(database)));
	}
}

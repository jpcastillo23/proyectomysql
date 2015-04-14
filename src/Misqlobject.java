import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.io.*;
import java.util.*;



//   /Users/josecastillo/Netbeans/Mi_Sql_pequeño/src/PRUEBAS_ANTLR_MISQLGRAMMAR
public class Misqlobject implements Comparable<Misqlobject>, InterfazPila<Misqlobject>, java.io.Serializable{
    public static final Misqlobject NULL = new Misqlobject();
    public static final Misqlobject VOID = new Misqlobject();
    private Vector<Misqlobject> lista;
    private int index;
    private String temp;
    private Object value;

    private Misqlobject() {
        // private constructor: only used for NULL and VOID
        value = new Object();
    }

    public Misqlobject(Object v) {
        if(v == null) {
            //throw new RuntimeException("v == null");
        }
        value = v;
        // only accept boolean, list, number or string types
        if(!(isNull() || isBoolean() || isList() || isNumber() || isString() || isStack() || isDate() || isCalendar())) {
            //throw new RuntimeException("invalid data type: " + v + " (" + v.getClass() + ")");
        }
    }
    public String retornoTipoObjeto(){
    		if(isBoolean()){
    			return "boolean";
    		}else if(isList()){
    			return "list";
    		}else if(isInteger()){
    			return "integer";
    		}else if(isFloat()){
    			return "float";
    		}else if(isDate()){
    			return "date";
    		}else if(isCalendar()){
    			return "calendar";
    		}else if(isString()){
    			return "string";
    		}else if(isNull()){
    			return "null";
    		}else if(isStack()){
    			return "stack";
    		}else{
    			return "null";
    		}
    }
    public String calendarToString(Calendar mi_calendario){
        Date date =  mi_calendario.getTime();
	    	SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
	    	String datestr = sdf.format(date); 
	    	return datestr;
    }
    public Calendar stringToCalendar(String mi_calendario){
		Calendar calendar = new GregorianCalendar();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
		Date date=null;
		try {
			date = sdf.parse(mi_calendario);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    calendar.setTime(date);
	    	return calendar;
	}
    /*
     * Metodo abstracto implementado para ingresar
     * un objeto a la pila
     */
    public Misqlobject(boolean habilitar_stack){
    	
        lista = new Vector();
        index = 0;
    }
    
    public Boolean asBoolean() {
        return (Boolean)value;
    }

    public Double asDouble() {
        return ((Number)value).doubleValue();
    }

    public Long asLong() {
        return ((Number)value).longValue();
    }

    @SuppressWarnings("unchecked")
    public List<Misqlobject> asList() {
        return (List<Misqlobject>)value;
    }

    public String asString() {
        return (String)value;
    }
    
    public Float asFloat() {
        return (Float)value;
    }
    
    public Calendar asCalendar() {
        return (Calendar)value;
    }
    public Integer asInteger() {
        return (Integer)value;
    }
    public Date asDate() {
        return (Date)value;
    }

    @Override
    public int compareTo(Misqlobject that) {
        if(this.isNumber() && that.isNumber() || this.isFloat() && that.isFloat() || this.isInteger() && that.isInteger() ) {
            if(this.equals(that)) {
                return 0;
            }
            else {
                return this.asDouble().compareTo(that.asDouble());
            }
        }
        else if(this.isString() && that.isString()) {
            return this.asString().compareTo(that.asString());
        }
        else if(this.isDate() && that.isDate()) {
            return this.asDate().compareTo(that.asDate());
        }
        
        else {
            throw new RuntimeException("illegal expression: can't compare `" + this + "` to `" + that + "`");
        }
    }

    @Override
    public boolean equals(Object o) {
        if(this == VOID || o == VOID) {
            throw new RuntimeException("can't use VOID: " + this + " ==/!= " + o);
        }
        if(this == o) {
            return true;
        }
        if(o == null || this.getClass() != o.getClass()) {
            return false;
        }
        Misqlobject that = (Misqlobject)o;
        if(this.isNumber() && that.isNumber()) {
            double diff = Math.abs(this.asDouble() - that.asDouble());
            return diff < 0.00000000001;
        }else if(this.isCalendar() && that.isCalendar()){
        		return this.value.equals(that.value);
        }
        else {
            return this.value.equals(that.value);
        }
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
    
    public boolean isDate(){
    		return value instanceof Date;
    }
    
    public boolean isBoolean() {
        return value instanceof Boolean;
    }
    
    public boolean isStack(){
    	return value instanceof InterfazPila;
    }

    public boolean isNumber() {
        return value instanceof Number;
    }

    public boolean isList() {
        return value instanceof List<?>;
    }

    public boolean isNull() {
        return (this == NULL) ? true : false;
    }

    public boolean isVoid() {
        return this == VOID;
    }

    public boolean isString() {
        return value instanceof String;
    }
    
    public boolean isInteger() {
        return value instanceof Integer;
    }
    
    public boolean isFloat() {
        return value instanceof Float;
    }

    public boolean isCalendar() {
        return value instanceof Calendar;
    }

    @Override
    public String toString() {
        return isNull() ? "NULL" : isVoid() ? "VOID" : String.valueOf(value);
    }

	@Override
	public void push(Misqlobject item) {
		// TODO Auto-generated method stub
	    lista.add(item);
	    index += 1;
	}

	@Override
	public Misqlobject pop() {
		// TODO Auto-generated method stub
        if(this.isEmpty())
            return null;
        else{
        	Misqlobject temp = lista.get(index);
            lista.remove(index);
            index -= 1;
            return temp;
        }
	}

    /*
     * Encargado de Devolver el Valor de componentes
     * en la pila
     */

    @Override
    public int size(){
        return lista.size();
    }
    /*
     * Funcion designada para devolver un boolean indicanco
     * si la pila esta vacia o llena
     */

    @Override
    public boolean isEmpty(){
        return lista.isEmpty();
    }

	@Override
	public Misqlobject peek() {
        if(this.isEmpty())
            return null;
        else{
            return lista.get(index);
        }
	}
	
    /*
     * SERIALIZAR LOS OBJETOS
     */
	 public void saveObject(String direccion, String table_name){
		 try
	      {
	         FileOutputStream fileOut =
	         new FileOutputStream(direccion+table_name);
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject( this.value);
	         out.close();
	         fileOut.close();
	         System.out.printf("Serialized data is saved in "+ direccion + table_name );
	      }catch(IOException i)
	      {
	          i.printStackTrace();
	      }
	 }
	 public Misqlobject getObject(String direccion, String table_name){
		 Misqlobject obtencion_objeto = null;
		 ObjectInputStream in;
		 FileInputStream fileIn;
		 try
	      {
	         fileIn = new FileInputStream(direccion+table_name);
	         in = new ObjectInputStream(fileIn);
	         obtencion_objeto =(Misqlobject)in.readObject();
	         in.close();
	         fileIn.close();
	         System.out.printf("Serialized data is saved in "+ direccion + table_name );
	      }catch(IOException i)
	      {
	          i.printStackTrace();
	      }catch(ClassNotFoundException c)
	      {
	          System.out.println("Employee class not found");
	          c.printStackTrace();
	          return NULL;
	       }
		 return obtencion_objeto;
	 }

}

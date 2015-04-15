
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

public class Main_My_Sql {
    public static void main(String[] args) throws Exception {
        MISQLGRAMMARLexer lexer = new MISQLGRAMMARLexer(new ANTLRFileStream("src/instrucciones.ins"));
        System.out.println(  "\nDISCREPANCIA EN LEXER");
        MISQLGRAMMARParser parser = new MISQLGRAMMARParser(new CommonTokenStream(lexer));
        System.out.println("\nDISCREPANCIA EN PARSER");
        ParseTree tree = parser.primary_sintax();
        System.out.println("DISCREPANCIA EN treeParser\n");
        /**estamos indicando que las
        evaluaciones las haremos fuera del
    	parser**/
        Mipropiovisitor visitor = new Mipropiovisitor();  
        /*Luego le metemos nuestras nuevas restricciones de evaluación 
    	      a nuestro parser. 										*/
        visitor.visit(tree);
    }
}


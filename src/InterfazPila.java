/*
 * @author Jose Pablo Castillo
 * Carne 10007
 * Universidad del Valle de Guatemala
 * Programacion Orientada a objetos
 *
 *
 * Clase interfaz encargada de ser la pila
 * Esta ligeramente modificada para un lenguaje mas comodo
 * de entender
 */
/**
 *
 * @param <T>
 * @author jaypicastillo
 */
public interface InterfazPila<T> {
     /*
     * @Funcion encargada de introduir un objeto tipo <T>
     * @a la pila.
     *
     * @param item - item anything
     * @pre debera tener atribuido que tipo de dato recibir en la pila
     * @post   debera tener una localizacion mas, con un objeti tipo T
     */
    public void push(T item);
    /*
     * Funcion encargada de devolver el objeto que
     * estaba asta el tope (ultimo ingresado) de la pila
     *
     *@pre Tiene algo en sus localidades
     * @post tiene uno menos (no se puede si esta isEmpty
     * @return  Objeto almacenado
     */
    public T pop();

    /*
     * Encargado de Devolver el Valor de componentes
     * en la pila
     *@pre debe de tener alguna longitud
     * @post
     * @return
     */
    public int size();

    /*
     * Funcion designada para devolver un boolean indicanco
     * si la pila esta vacia o llena
     * @pre
     * @post
     * @return
     */
    public boolean isEmpty();
    /*
     * Encargado de retornar un objeto (no alias) con las
     * mismas caracteristicas de quien está en el tope
     *
     * @pre Tiene minimo un objeto almacenado
     * @post Debera seguir teniendolo
     * @return
     */
    public T peek();
    /*
     * retorna una descripcion de como esta pila se encuentra
     * @pre
     * @post
     * @return
     */
    public String toString();
}

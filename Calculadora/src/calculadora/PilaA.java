/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package calculadora;

/**
 * Clase PilaA
 * @author GERARDO OBEID GUZMÁN
 * @author JOSE PABLO ANTÚNEZ
 * @author DIEGO GARCÍA GAYOU
 * @author WILFREDO SALAZAR
 */

public class PilaA <T> implements PilaADT <T> {
    private T[]  pila;
    private int tope;
    private final int MAX = 100;
    
    /**
     * <ul>
     * <li>Constructor PilaA con un número máximo de elementos prestablecido (100)</li>
     * </ul>
     */
    public PilaA(){
        pila = (T[]) new Object[MAX];
        this.tope = -1;
    }
    
     /**
     * <ul>
     * <li>Constructor PilaA con un número máximo de elementos dado por el usuario</li><br>
     * </ul>
     */
    public PilaA(int max){
        pila = (T[]) new Object[max];
        this.tope = -1;
    }
    
    /**
     * <ul>
     * <li>Método expande() (privado)</li><br>
     * <li>Este método se llama solo en caso de que no haya espacio</li><br>
     * <li>Si se llama:</li><br>
     * <li>Primero, crea un nuevo arreglo del doble de tamaño</li><br>
     * <li>Segundo, copia toda la información al nuevo arreglo</li><br>
     * </ul>
     */
    private void expande(){
        T[] pilaExpanded = (T[]) new Object[pila.length * 2];
        for (int i = 0; i  <= tope; i++){
            pilaExpanded[i] = pila[i];
        }
        pila = pilaExpanded;
    }
    
    /**
     * <ul>
     * <li>Método push()</li><br>
     * <li>Agrega un elemento al final de la Pila</li><br>
     * <li>En caso de no haber espacio, se expande el arreglo de la pila</li>
     * </ul>
     * @param newData: un nuevo dato para almacenar
     */
    public void push(T newData){
        if (tope == pila.length-1)
            expande();
        tope ++;
        pila[tope]= newData;
        
    }
    
    /**
     * <ul>
     * <li>Método pop()</li><br>
     * <li>Primero se valida que la Pila tenga elmentos y despúes se elimina el último elemento</li><br>
     * </ul>
     * @return eliminado: Un elemeto genérico que corresponde al último elemento que fue eliminado
     */
    public T pop(){
        if (isEmpty())
            throw new EmtpyCollectionException("Pila sin elementos");
        T eliminado = pila[tope];
        pila[tope] = null;
        tope --;
        return eliminado;
    }
    
    /**
     * <ul>
     * <li>Método peek()</li><br>
     * <li>Regresa el elemento que se encuentra en el tope del arreglo utilizado en la pila</li><br>
     * </ul>
     * @return Elementos genérico que corresponde al último elemento de la pila
     */
    public T peek(){
        if (isEmpty())
            throw new EmtpyCollectionException("Pila sin elementos");
        return pila[tope];
    }
    
    /**
     * <ul>
     * <li>Método isEmpty()</li><br>
     * <li>Checa y regresa un booleano si el tope es igual a -1</li><br>
     * <li>Si el tope es igual a -1 regresa verdadero ya que PilaA esta vacía</li><br>
     * </ul>
     * @return false si la Pila tiene elementos / true si esta vacía
     */
    public boolean isEmpty(){
        return tope == -1;
    }
    
    /**
     * <ul>
     * <li>Método toString()</li><br>
     * <li>Itera sobre el arreglo sobre el cuál está contruido nuestra pila y lo concatena en una cadena</li>
     * </ul>
     * @return stb.toString(): Cadena con la inforación contenida de la Pila
     */
    public String toString() {
        StringBuilder stb = new StringBuilder();
        for(int i = 0; i <= tope; i++){
            stb.append(pila[i]).append(" ");
        }
        return stb.toString();
    }
    
}

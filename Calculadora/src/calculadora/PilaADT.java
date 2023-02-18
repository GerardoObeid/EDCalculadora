/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package calculadora;

/**
 * Interface PIlaADT
 * @author GERARDO OBEID GUZMÁN
 * @author JOSE PABLO ANTÚNEZ
 * @author DIEGO GARCÍA GAYOU
 * @author WILFREDO SALAZAR
 */

public interface PilaADT <T> {
    /**
     * <p>Agregar un elemento a la pila</p>
     */
    public void push(T o);
    
    /**
     * <p>Remover el último elemento de la pila</p>
     */
    public T pop();
    
    /**
     * <p>Ver cuál es el último elemento de la pila</p>
     */
    public T peek();
    
    /**
     * <p>Revisar si la pila esta vacía</p>
     */
    public boolean isEmpty();
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package calculadora;

/**
 *
 * @author obeid
 */
public class PilaA <T> implements PilaADT <T> {
    private T[]  pila;
    private int tope;
    private final int MAX = 100;
    
    public PilaA(){
        pila = (T[]) new Object[MAX];
        this.tope = -1;
    }
    
    public PilaA(int max){
        pila = (T[]) new Object[max];
        this.tope = -1;
    }
    
    private void expande(){
        T[] pilaExpanded = (T[]) new Object[pila.length * 2];
        for (int i = 0; i  <= tope; i++){
            pilaExpanded[i] = pila[i];
        }
        pila = pilaExpanded;
    }
    
    
    public void push(T newData){
        if (tope == pila.length-1)
            expande();
        tope ++;
        pila[tope]= newData;
        
    }
    public T pop(){
        if (isEmpty())
            throw new EmtpyCollectionException("Pila sin elementos");
        T eliminado = pila[tope];
        pila[tope] = null;
        tope --;
        return eliminado;
    }
    
    public T peek(){
        if (isEmpty())
            throw new EmtpyCollectionException("Pila sin elementos");
        return pila[tope];
    }
    public boolean isEmpty(){
        return tope == -1;
        
    }

    public String toString() {
        StringBuilder stb = new StringBuilder();
        for(int i = 0; i <= tope; i++){
            stb.append(pila[i]).append(" ");
        }
        return stb.toString();
    }
    
}

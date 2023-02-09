/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package calculadora;

/**
 *
 * @author obeid
 */
public interface PilaADT <T> {
    public void push(T o);
    public T pop();
    public T peek();
    public boolean isEmpty();
}

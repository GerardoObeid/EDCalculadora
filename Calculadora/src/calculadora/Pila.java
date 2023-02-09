/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package calculadora;

/**
 *
 * @author obeid
 */
public class Pila {

    public static void main(String[] args) {
        PilaADT <Integer> pila1  = new PilaA();
        PilaA <String> pila2 = new PilaA();
        
        pila1.push(1);
        pila1.push(2);
        pila1.push(3);
        pila1.push(4);
        pila1.push(5);
        
        System.out.println(pila1);
        System.out.println(pila1.pop());
        
    }
}

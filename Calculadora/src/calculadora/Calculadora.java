/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package calculadora;
import java.util.Scanner;


/**
 *
 * @author obeid    
 */
public class Calculadora {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce una expresión matemática infijo: ");
        String infijo = sc.nextLine();
        System.out.println(revisaCadena(infijo));
        System.out.println("La expresión matemática postfijo es: " + infijoAPostfijo(infijo));
    }
    public static boolean revisaCadena(String cadena){
        Character caracter, previous_token;
        boolean res = true;
        PilaADT <Character> pila1  = new PilaA();
        int i = 0;
        
        while (i < cadena.length() && res){
            caracter = cadena.charAt(i);
            if (caracter == '(' )
                pila1.push(caracter);
            else if (caracter == ')'){
                try{
                    previous_token = pila1.pop();
                    if (previous_token != '(')
                        res =  false;
                    }
                  catch (EmtpyCollectionException error){
                       res = false;
                  }
                   
               }
           i++; 
        }
        if (!pila1.isEmpty())
            res = !res;
        return res;
    }
     
    public static String infijoAPostfijo(String infijo) {
        String postfijo = "";
        PilaADT <Character> pila = new PilaA <Character>();
        boolean b;
        
        b = revisaCadena(infijo);
        System.out.println(b);
        if (!b)
            throw new InvalidEcuationException("Ecuación no balanceada");
        
        //Por cada elemento de la cadena ingresada
        for (int i = 0; i < infijo.length(); i++) {
            char c = infijo.charAt(i);
           
            //si es un número o letra lo agregas
            if (esOperando(c)) {
                postfijo += c;
            } 
            else if (c == '(') {
                pila.push(c);
                postfijo += "";
            } 
            else if (c == ')') {
                while (pila.peek() != '(') {
                    postfijo += pila.pop();
                }
                pila.pop();
            } 
            else {
                //vaciamos los operadores de la pila
                while (!pila.isEmpty() && prioridad(c) <= prioridad(pila.peek())) {
                    postfijo += pila.pop();
                }
                pila.push(c);
            }
        }
        while (!pila.isEmpty()) {
            postfijo += pila.pop();
        }
        return postfijo;
    }

    public static boolean esOperando(char c) {
        return (c >= '0' && c <= '9') || (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }
    
    public static double evaluate(String postfijo){
        double res = 0;
        for (int i = 0; i < postfijo.length(); i++) {
        }
        return res;
    }
    
    public static int prioridad(char c) {
        switch (c) {
            case '+':
                 return 1;
            case '-':
                return 1;
            case '*':
                return 2;
            case '/':
                return 2;
            case '^':
                return 3;
        }
        return -1;
    }
}
    


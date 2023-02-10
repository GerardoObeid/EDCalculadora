/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package calculadora;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


/**
 *
 * @author obeid    
 */
public class Calculadora {

    public static void main(String[] args) {

        String st = "-2.5^3";
         infijoAPostfijo(st);
        //System.out.println("La expresión matemática postfijo es: " + infijoAPostfijo(terms));
    }
    
    //Métodos de revisión
    public static boolean esOperando(String c) {
        return c.matches("-?\\d+.?\\d+");
    }
    

    public static boolean esOperador(char c){
        return (c == '+' || c == '-' || c == '*' || c == '/' || c == '^');
    }
    
    public static boolean revisaCadena(String cadena){
        Character caracter, previous_token;
        boolean res = true;
        PilaADT <Character> pila1  = new PilaA();
        int i = 0;
        
        //Para evitar seguir revisando si hay operador al final está mal
        if (esOperador(cadena.charAt(cadena.length()-1)))
            throw new InvalidEcuationException();
        
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
    
    //Preporcesamiento de cadena
    public static ArrayList <String> getGroupedCadena(String cadena){
        int i;
        String [] infijo;
        ArrayList <String> infijoDivided= new ArrayList<String>();
        boolean ignoreFirstPossibleNeg = true;
        
        infijo = cadena.split("(?<=[-+*^//()])|(?=[-+*^//()])");
        i = 0;
        if (ignoreFirstPossibleNeg){
             while(i < infijo.length){
                    infijoDivided.add(infijo[i]);
                    i++;
                  }
        }
        else{
            while(i < infijo.length){
                if (i == 0 && infijo[i].equals("-")){
                    infijoDivided.add( "-" + infijo[i+1]);
                    i++;
                  }
                else{
                    infijoDivided.add(infijo[i]);
                }
                 i++;
            }
        }
        infijo = null;
        return infijoDivided;
    }
    
     /**
      *     <ul>
      *    <li>Conversión de la cadena almacenda en un arraylist a postfijo</li>
      *     </ul>
      *    
      */
    public static ArrayList  <String> infijoAPostfijo(String cadena) {
        PilaADT <String> pila = new PilaA <String>();
        ArrayList  <String>p = new ArrayList  <String>();
        
         if(!revisaCadena(cadena))
            throw new InvalidEcuationException();
         p= getGroupedCadena(cadena);
        System.out.println(p);
        return p;    
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
    
    
    //Evaluación de postfijo
    public static double evaluate(String postfijo){
        double res = 0;
        for (int i = 0; i < postfijo.length(); i++) {
        }
        return res;
    }
   
}
    


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package calculadora;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


/**
 * @author GERARDO OBEID GUZMÁN
 * @author JOSE PABLO ANTÚNEZ
 * @author DIEGO GARCIA GAYOU
 * @author  
 */
public class Calculadora {

    public static void main(String[] args) {

        String st = "1+1";
        System.out.println("El resultado es: " + infijoAPostfijo(st));
    }
    
    //Métodos de revisión
    public static boolean esOperando(String c) {
        return c.matches("\\-?\\d+\\.?\\d*") ||c.matches("-?\\.\\d+"); 
    }
    
    public static boolean esOperador(char c){
        return (c == '+' || c == '-' || c == '*' || c == '/' || c == '^');
    }
    
    //Revisar
    public static boolean revisaCadena(String cadena){
        Character caracter, previous_token;
        boolean res = true;
        PilaADT <Character> pila1  = new PilaA();
        int i = 0;
        int prioridad1, prioridad2;
        
        //Para evitar seguir revisando si hay operador al final está mal
        if (esOperador(cadena.charAt(cadena.length()-1)))
            throw new InvalidEcuationException();
        if (esOperador(cadena.charAt(0)) && cadena.charAt(0) != '-')
            throw new InvalidEcuationException();
        while (i < cadena.length() && res){
            caracter = cadena.charAt(i);
            if (i < cadena.length()-1 && esOperador(caracter) && esOperador(cadena.charAt(i+1))){
                prioridad1 = getPrioridad(caracter);
                prioridad2 = getPrioridad(cadena.charAt(i+1));
                if (prioridad1 <= prioridad2)
                    throw new InvalidEcuationException();
            }
            if (caracter == '(' ){
                pila1.push(caracter);
                if (i < cadena.length()-1 && esOperador(cadena.charAt(i+1)) && cadena.charAt(i+1) != '-')
                    throw new InvalidEcuationException();
            }
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
      
        infijo = cadena.split("(?<=[-+*^//()])|(?=[-+*^//()])");
        i = 0;
        
        while(i < infijo.length){
            if (i == 0 && infijo[i].equals("-")){
                infijoDivided.add( "-" + infijo[i+1]);
                i++;
              }
            else{
                if (i != -1 && infijo[i].equals("-") && (esOperador(infijo[i-1].charAt(0)) || infijo[i-1].equals("("))){
                    infijoDivided.add( "-" + infijo[i+1]);
                    i++;
                }
                else
                    infijoDivided.add(infijo[i]);
            }
             i++;
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
    public static double infijoAPostfijo(String cadena) {
        PilaADT <String> operadores = new PilaA <>();
        ArrayList  <String> groupList;
        ArrayList  <Double> operandosList = new ArrayList  <>();
        
        if(!revisaCadena(cadena))
            throw new InvalidEcuationException();
        
        groupList = getGroupedCadena(cadena);
        
        for (int i = 0; i < groupList.size(); i++) {
            String c = groupList.get(i);
            if (esOperando(c))
                operandosList.add(Double.parseDouble(c));
            else if (esOperador(c.charAt(0))){
                while(!operadores.isEmpty() && getPrioridad(c.charAt(0)) <= getPrioridad(operadores.peek().charAt(0))){
                    
                }
                operadores.push(c);
             
            }
            else if (c.equals(("("))) {
                operadores.push(c);
            }             
            else if (c.equals(")")) {
                while (!operadores.isEmpty() && !operadores.peek().equals("(")) {
                    int size = operandosList.size();
                    double evaluado = evaluate(c,operandosList, size);
                    operandosList.set(size-2, evaluado);
                    operandosList.remove(size-1);
                }
                operadores.pop();
            } 
        }
        while(!operadores.isEmpty()){
            int size = operandosList.size();
            double evaluado = evaluate(operadores.pop(),operandosList, size);
            operandosList.set(size-2, evaluado);
            operandosList.remove(size-1);
        }
    
        System.out.println(operandosList.get(0));
        
        return operandosList.get(0);    
    }
    
 public static int getPrioridad(char c) {
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
    public static double evaluate(String operador, ArrayList<Double> operandos, int size){
        
        double num1 =  operandos.get(size-2);
        double num2 =  operandos.get(size-1);
        Double res;
        
        Double evaluado = 0.0;
        switch(operador){
            case("+"):
                res = num1 + num2;
                break;
            case("-"):
                res = num1 - num2;
                break;
            case("*"):
                res = num1 * num2;
                break;
            case("^"):
                res = Math.pow(num1, num2);
                break;
            case("/"):
                 res = num1 / num2;
                 if(res.isInfinite() || res.isNaN())
                     throw new RuntimeException("Error");
                break;
        }
        return evaluado;
    }
}
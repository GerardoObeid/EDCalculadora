/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package calculadora;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <pre>
 * Clase Calculadora<br>
 * Contiene los métodos necesarios para validar, convertir y evaluar una expresión matemática
 * </pre>
 * @author GERARDO OBEID GUZMÁN
 * @author JOSE PABLO ANTÚNEZ
 * @author DIEGO GARCÍA GAYOU
 * @author WILFREDO SALAZAR
 */
public abstract class Calculadora { 
    
 /**
     * <ul>
     * <li>Función esOperando()</li><br>
     * <li>Regex condición 1: </li><br>
     * <ul><li>Checa números positivos o negativos, con o sin punto decimal</li><br></ul>
     * <li>Regex condición 2: </li><br>
     * <ul><li>Checa números positivos o negativos, que empiecen con punto decimal</li><br></ul>
     * <li>Regex condición 3: </li><br>
     * <ul><li>Checa números positivos o negativos, con o sin punto decimal y con notación exponenecial</li></ul>
     * </ul>
     * @param c Cadena que contiene una parte de la expresión matemática
     * @return <ul>
        * <li>true si cumple una de las condiciones del regex (regular expressions)</li> 
        * <li>false si no cumple ninguna condición</li>
     * </ul>
 */
    public static boolean esOperando(String c) {
        return c.matches("\\-?\\d+\\.?\\d*") || c.matches("-?\\.\\d+") || c.matches("\\-?\\d+\\.?\\d*E{0,1}\\d+"); 
    }
    
  /**
     * <ul>
     * <li>Función esOperador()</li><br>
     * <li>Revisa si el caracter enviado es un operador</li><br>
     * </ul>
     * @param c un caracter de la cadena
     * @return true si es algún operador de (+,-,/,*,^) / false si no es ningún operador
 */
    public static boolean esOperador(char c){
        return (c == '+' || c == '-' || c == '*' || c == '/' || c == '^');
    }
    
/**
     * <ul>
     * <li>Función revisaCadena()</li><br>
     * <li>Primero, se valida que la expresión no termine con un operador</li><br>
     * <li>Segundo, se valida que no empiece con un operador a excepción de que sea un número negativo (ej. -30)</li><br>
     * <li>Tercero, como incorporamos los operandos π y e, se valida que estos no estén consecutivos sin un operador</li><br>
     * <li>Cuarto, se verifica balanceo de parentésis y que no haya operadores que no deberían ser consecutivos</li><br>
     * </ul>
     * @param cadena Contiene expresión matemática infijo
     * @return<ul> 
     * <li>true si es una expresión válida</li> 
     * <li>false si no es válida</li>
     * </ul> 
 */
    public static boolean revisaCadena(String cadena){
        Character caracter, previous_token;
        boolean res = true;
        PilaADT <Character> pila1  = new PilaA();
        int i = 0;
        int prioridad1, prioridad2;
        String regex; 
        Pattern pattern; 
        Matcher checker; 
        regex = "eπ|πe|\\dπ|\\de|π\\d|e\\d|\\)\\(";
        pattern = Pattern.compile(regex, Pattern.MULTILINE);
        checker = pattern.matcher(cadena);
        
        //REVISIONES INICIALES
        if (esOperador(cadena.charAt(cadena.length()-1)))
            throw new InvalidEcuationException("Ecuación Inválida");
        if (esOperador(cadena.charAt(0)) && cadena.charAt(0) != '-')
            throw new InvalidEcuationException("Ecuación Inválida");
        if (checker.find())
            throw new InvalidEcuationException("Ecuación Inválida");

        //EN ESTA SECCIÓN SE REVISA EL BALANCEO DE PARÉNTESIS 
        //SE VALIDA QUE NO HAYA OPERADORES CON LA MISMA PRIORIDAD O UNO DE MENOR PRIORIDAD PRECEDIDO POR UNO DE MAYOR
        //ej. -*3, +/5
        while (i < cadena.length() && res){
            caracter = cadena.charAt(i);
            if (i < cadena.length()-1 && esOperador(caracter) && esOperador(cadena.charAt(i+1))){
                prioridad1 = getPrioridad(caracter);
                prioridad2 = getPrioridad(cadena.charAt(i+1));
                if (prioridad1 <= prioridad2)
                    throw new InvalidEcuationException("Ecuación Inválida");
            }
            if (caracter == '(' ){
                pila1.push(caracter);
                if (i < cadena.length()-1 && esOperador(cadena.charAt(i+1)) && cadena.charAt(i+1) != '-')
                    throw new InvalidEcuationException("Ecuación Inválida");
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
    
 /**
     * <ul>
     * <li>Función getGroupedCadena()</li><br>
     * <li>Recibe una cadena y sustituye π y e si se encuentran en la cadena</li><br>
     * <li>utiliza regex para dividir la cadena por cada signo de los siguientes: (-,+,*,^,/,(,))</li><br>
     * <li>Devuelve un ArrayList de tipo String con los términos aun en infijo, pero divididos</li>
     * </ul>
     * @param cadena cadena infijo de una expresión matemática
     * @return infijoDivided: ArrayList de Strings con la cadena dividida
 */
    public static ArrayList <String> getGroupedCadena(String cadena){
        int i;
        String [] infijo;
        ArrayList <String> infijoDivided= new ArrayList<String>();
        if (cadena.contains("e"))
            cadena = cadena.replace("e", "" + Math.E);
        if (cadena.contains("π"))
            cadena = cadena.replace("π", "" + Math.PI);
        
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
     * <ul>
     * <li> Función infijoAPostfijo()</li><br>
     * <li>Conversión de la cadena almacenda en un arraylist a postfijo</li><br>
     * <li>Recibe una cadena y llama a la función revisaCadena y getGroupedCadena para validar y agrupar</li><br>
     * <li>Devuelve un ArrayList de tipo String con los términos en postfijo</li>
     * </ul>
     * @param cadena: cadena con una expresión infijo
     * @return PostfixExpression: ArrayList de Strings con la expresión en postfijo
 */
    public static ArrayList  <String> infijoAPostfijo(String cadena) {
        PilaADT <String> operadores = new PilaA <>();
        ArrayList  <String> groupList;
        ArrayList  <String> PostfixExpression = new ArrayList  <>();
        
        if(!revisaCadena(cadena))
            throw new InvalidEcuationException("Ecuación Inválida");
        
        groupList = getGroupedCadena(cadena);
        for (int i = 0; i < groupList.size(); i++) {
            String c = groupList.get(i);
            if (esOperando(c)) {
                PostfixExpression.add(c);
            } 
            else if (esOperador(c.charAt(0))){
                while(!operadores.isEmpty() && getPrioridad(c.charAt(0)) <= getPrioridad(operadores.peek().charAt(0))){
                    PostfixExpression.add(operadores.pop());
                }
                operadores.push(c);
            }
            else if (c.equals(("("))) {
                operadores.push(c);
            }             
            else if (c.equals(")")) {
                while (!operadores.isEmpty() && !operadores.peek().equals("(")) {
                    PostfixExpression.add(operadores.pop());
                }
                operadores.pop();
            } 
        }
        while(!operadores.isEmpty())
            PostfixExpression.add(operadores.pop());
        return PostfixExpression;    
    }
 
 /**
     * <ul>
     * <li>Función que devuelve la prioridad de un operador</li><br>
     * <li>Recibe un caracter y revisa su prioridad</li><br>
     * <li>Devuelve un entero con la prioridad</li>
     * </ul>
     * @param c: Recibe un caracter
     * @return Un número entre [1,3] correspondeinte a la prioridad de operadores
 */
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
    
    
 /**
     * <ul>
     * <li>Función evaluate()</li><br>
     * <li>Evalúa una expresión postfijo</li><br>
     * <li>Itera sobre el arreglo postfijo y si encuentra un operador saca dos operandos de una pila</li><br>
     * <li>Despúes hace la operación correspondiente y agrega esa respuesta a la pila</li><br>
     * <li>Si no es un operador agrega los números a la pila</li><br>
     * <li>Devuelve la evaluación de la expresión</li>
     * </ul>
  * @param postfijo Un ArrayList de Strings que almacena la expresión 
  * @return res: El resultado de la evalucación
  */
    public static double evaluate(ArrayList <String>postfijo){
        Double num1, num2, res;
        PilaADT<Double> operandos = new PilaA();
        String element;
        
        res = 0.0;
        for(int i = 0; i < postfijo.size(); i++){
            element = postfijo.get(i);
            if(element.length() == 1 && esOperador(element.charAt(0))) {
                num2 = operandos.pop();
                num1 = operandos.pop();
                switch(element){
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
                        if(res.isInfinite() || res.isNaN())
                             throw new UndefinedOperationException("Infinito");
                        break;
                    case("/"):
                         res = num1 / num2;
                         if(res.isInfinite() || res.isNaN())
                             throw new UndefinedOperationException("Error Matemático");
                        break;
                }
                operandos.push(res);
            } else {
                operandos.push(Double.valueOf(element));
            }
        }
        res = operandos.pop();
        return res;
    }  
}
    


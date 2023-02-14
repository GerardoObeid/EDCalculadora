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
 * @author GERARDO OBEID GUZMÁN
 * @author JOSE PABLO ANTÚNEZ
 * @author DIEGO GARCÍA GAYOU
 * @author WILFREDO SALAZAR
 */
public abstract class Calculadora {
    public static void main(String[] args) {

        String st = "5+πe+7";
                
        System.out.println(st.matches(".*[eπ]|[πe].*"));
        ArrayList <String> Postfix = infijoAPostfijo(st);
        System.out.println("La expresión Postfijo es: " + Postfix );
        System.out.println("El resultado es: " + evaluate(Postfix));
    }
    
    //Métodos de revisión
    public static boolean esOperando(String c) {
        return c.matches("\\-?\\d+\\.?\\d*") ||c.matches("-?\\.\\d+") || c.matches("\\-?\\d+\\.?\\d*E{0,1}\\d+"); 
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
        String regex; 
        Pattern pattern; 
        Matcher checker; 
        //Para evitar seguir revisando si hay operador al final está mal
        if (esOperador(cadena.charAt(cadena.length()-1)))
            throw new InvalidEcuationException("Ecuación Inválida");
        if (esOperador(cadena.charAt(0)) && cadena.charAt(0) != '-')
            throw new InvalidEcuationException("Ecuación Inválida");
        
        regex = "eπ|πe|\\dπ|\\de|π\\d|e\\d|\\)\\(";
        pattern = Pattern.compile(regex, Pattern.MULTILINE);
        checker = pattern.matcher(cadena);
        if (checker.find())
            throw new InvalidEcuationException("Ecuación Inválida");

        
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
    
    //Preporcesamiento de cadena
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
     * <pre>
     * <ul>
     * <li>Conversión de la cadena almacenda en un arraylist a postfijo</li>
     * <li>Recibe una cadena y llama a la función revisaCadena y getGroupedCadena para validar y agrupar</li>
     * <li>Devuelve un ArrayList de tipo String con los términos en postfijo</li>
     * </ul>
     * </pre>
     * @param cadena
     * @return PostfixExpression
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
     * <pre>
     * <ul>
     * <li>Función que devuelve la prioridad de un operador</li>
     * <li>Recibe un caracter y revisa su prioridad</li>
     * <li>Devuelve un entero con la prioridad
     * </pre>
     * @param c
     * @return [1,3]
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
  * 
  * @param postfijo 
  * @return res
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
                operandos.push(Double.parseDouble(element));
            }
        }
        res = operandos.pop();
        return res;
    }  
}
    


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package calculadora;

/**
 * Excepción para ecuaciones inváldias
 * @author GERARDO OBEID GUZMÁN
 * @author JOSE PABLO ANTÚNEZ
 * @author DIEGO GARCÍA GAYOU
 * @author WILFREDO SALAZAR
 */
public class InvalidEcuationException extends RuntimeException {
    public InvalidEcuationException(){}
    public InvalidEcuationException(String message){
        super(message);
    }
}

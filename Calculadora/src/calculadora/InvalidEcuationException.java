/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package calculadora;

/**
 *
 * @author obeid
 */
public class InvalidEcuationException extends RuntimeException {
    public InvalidEcuationException(){}
    public InvalidEcuationException(String message){
        super(message);
    }
}

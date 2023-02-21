/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package calculadora;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author obeid
 */
public class CalculadoraTest {
    
    public CalculadoraTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of esOperando method, of class Calculadora.
     */
    @Test
    public void testEsOperando() {
        System.out.println("esOperando");
        String c = "-25.333";
        boolean expResult = true;
        boolean result = Calculadora.esOperando(c);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of esOperador method, of class Calculadora.
     */
    @Test
    public void testEsOperador() {
        System.out.println("esOperador");
        char c = '+';
        boolean expResult = true;
        boolean result = Calculadora.esOperador(c);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of revisaCadena method, of class Calculadora.
     */
    @Test
    public void testRevisaCadena() {
        System.out.println("revisaCadena");
        String cadena = "25+3*(2+4)";
        boolean expResult = true;
        boolean result = Calculadora.revisaCadena(cadena);
        assertEquals(expResult, result);
    }

    /**
     * Test of getGroupedCadena method, of class Calculadora.
     */
    @Test
    public void testGetGroupedCadena() {
        System.out.println("getGroupedCadena");
        String cadena = "-25+3*(2+3)";
        ArrayList<String> expResult = new ArrayList<>();
        expResult.add("-25");
        expResult.add("+");
        expResult.add("3");
        expResult.add("*");
        expResult.add("(");
        expResult.add("2");
        expResult.add("+");
        expResult.add("3");
        expResult.add(")");
        
        ArrayList<String> result = Calculadora.getGroupedCadena(cadena);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of infijoAPostfijo method, of class Calculadora.
     */
    @Test
    public void testInfijoAPostfijo() {
        System.out.println("infijoAPostfijo");
        String cadena = "25+3";
        ArrayList<String> expResult = new ArrayList<>();
        expResult.add("25");
        expResult.add("3");
        expResult.add("+");
        ArrayList<String> result = Calculadora.infijoAPostfijo(cadena);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of getPrioridad method, of class Calculadora.
     */
    @Test
    public void testGetPrioridad() {
        System.out.println("getPrioridad");
        char c = '+';
        int expResult = 1;
        int result = Calculadora.getPrioridad(c);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of evaluate method, of class Calculadora.
     */
    @Test
    public void testEvaluate() {
        System.out.println("evaluate");
        ArrayList<String> postfijo = new ArrayList<>();
        postfijo.add("2.5");
        postfijo.add("-4");
        postfijo.add("*");
        double expResult = -10.0;
        double result = Calculadora.evaluate(postfijo);
        assertEquals(expResult, result, 0);
        // TODO review the generated test code and remove the default call to fail.
    }
    
}

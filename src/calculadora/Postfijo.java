/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculadora;

/**
 *
 * @author Rodrigo
 */
import java.util.Stack;

public class Postfijo {

    //Declaración de las pilas
    Stack< String> E = new Stack<>(); //Pila entrada
    Stack< String> P = new Stack<>(); //Pila temporal para operadores
    Stack< String> S = new Stack<>(); //Pila salida

    String expresion;
    String[] arrayInfijo;

    public String traducir(String expresion) {
        String postfix = "!";
        //Depurar la expresion algebraica
        String expr = limpiar(expresion);
        arrayInfijo = expr.split(" ");

        //Añadir la array a la Pila de entrada (E)
        for (int i = arrayInfijo.length - 1; i >= 0; i--) {
            E.push(arrayInfijo[i]);
        }
        try {
            //Algoritmo Infijo a Postfijo
            while (!E.isEmpty()) {
                switch (prefijo(E.peek())) {
                    case 1:
                        P.push(E.pop());
                        break;
                    case 3:
                    case 4:
                        while (prefijo(P.peek()) >= prefijo(E.peek())) {
                            S.push(P.pop());
                        }
                        P.push(E.pop());
                        break;
                    case 2:
                        while (!P.peek().equals("(")) {
                            S.push(P.pop());
                        }
                        P.pop();
                        E.pop();
                        break;
                    default:
                        S.push(E.pop());
                }
            }

            //Eliminacion de `impurezas´ en la expresiones algebraicas
            String infix = expr.replace(" ", "");
            postfix = S.toString().replaceAll("[\\]\\[,]", "");

            //Mostrar resultados:
            System.out.println("Expresion Infija: " + infix);
            System.out.println("Expresion Postfija: " + postfix);

        } catch (Exception ex) {
            System.out.println("Error en la expresión algebraica");
            System.err.println(ex);
        }

        return postfix.replace(" ", "");

    }

    //Limpiar expresión algebraica
    private static String limpiar(String expresion) {
        expresion = expresion.replaceAll("\\s+", ""); //Elimina espacios en blanco
        expresion = "(" + expresion + ")";
        String operadores = "+-*/()";
        String str = "";

        //Deja espacios entre operadores
        for (int i = 0; i < expresion.length(); i++) {
            if (operadores.contains("" + expresion.charAt(i))) {
                str += " " + expresion.charAt(i) + " ";
            } else {
                str += expresion.charAt(i);
            }
        }
        return str.replaceAll("\\s+", " ").trim();
    }

    //Jerarquia de los operadores
    private static int prefijo(String operador) {
        int prf = 99;
        if (operador.equals("^")) {
            prf = 5;
        }
        if (operador.equals("*") || operador.equals("/")) {
            prf = 4;
        }
        if (operador.equals("+") || operador.equals("-")) {
            prf = 3;
        }
        if (operador.equals(")")) {
            prf = 2;
        }
        if (operador.equals("(")) {
            prf = 1;
        }
        return prf;
    }

}

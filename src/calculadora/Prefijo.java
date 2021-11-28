/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculadora;

import java.util.Stack;

/**
 *
 * @author Rodrigo
 */
public class Prefijo {

    public static String traducir(String expresion) {
        //System.out.println("La cadena de lectura es: "+ input +" La longitud es: "+ len);
        char c, tempChar; // Estas dos son variables intermedias, c se utiliza para almacenar el carácter en la posición correspondiente en el bucle,
        Stack<Character> s1 = new Stack<Character>(); // Instancia una pila de símbolos
        Stack<Integer> s2 = new Stack<Integer>(); // Instancia una pila de números
        Stack<Object> expression = new Stack<Object>(); // Instanciar una pila de expresiones de prefijo
        // escanea la expresión de derecha a izquierda
        for (int i = expresion.length() - 1; i >= 0; --i) {
            c = expresion.charAt(i);
            // Determine si la lectura es un número, si lo es, inserte el número en la pila de operandos y la pila de expresiones
            if (Character.isDigit(c)) {
                String s = String.valueOf(c);
                // Convertir a tipo Int:
                int j = Integer.parseInt(s);
                s2.push(j);
                expression.push(j);
            } else if (esOperador(c)) {
                // Si el personaje actual es un operador (incluidos paréntesis)
                while (!s1.isEmpty()
                        && s1.peek() != ')'
                        && jerarquia(c, s1.peek()) < 0) {
                    // La pila del operador actual no está vacía y el operador en la parte superior de la pila del operador no es un corchete derecho y la prioridad del operador actual es menor que la prioridad del operador en la parte superior de la pila,
                    // Luego tome el elemento superior de la pila de operadores y realice una operación con los dos elementos superiores de la pila de operandos e inserte el resultado de la operación en la pila de operandos
                    expression.push(s1.peek());
                    s2.push(calcular(s2.pop(), s2.pop(), s1.pop()));
                }
                s1.push(c);
            } else if (c == ')') {
                // Debido a que escaneamos la expresión infija de derecha a izquierda, para un "()" primero debe leerse en el corchete derecho
                s1.push(c);
            } else if (c == '(') {
                // Si es un paréntesis izquierdo "(", entonces el operador en la parte superior de la pila S1 aparece en secuencia y se inserta en la pila de expresión hasta que se encuentra el paréntesis izquierdo, en cuyo punto se descarta el par de paréntesis;
                while ((tempChar = s1.pop()) != ')') {
                    expression.push(tempChar);
                    s2.push(calcular(s2.pop(), s2.pop(), tempChar));
                    if (s1.isEmpty()) {
                        throw new IllegalArgumentException(
                                "bracket dosen't match, missing right bracket ')'.");
                    }
                }
            } else if (c == ' ') {
                // Si la expresión contiene espacios, no maneje espacios
            } else {
                throw new IllegalArgumentException(
                        "wrong character '" + c + "'");
            }
        }
        while (!s1.isEmpty()) {
            tempChar = s1.pop();
            expression.push(tempChar);
            s2.push(calcular(s2.pop(), s2.pop(), tempChar));
        }
        String pila = "";
        while (!expression.isEmpty()) {
            pila+=expression.pop();
            System.out.print(pila);
            
        }
        int result = s2.pop();
        if (!s2.isEmpty()) {
            throw new IllegalArgumentException("input is a wrong expression.");
        }
        System.out.println();
        System.out.println("El resultado del cálculo es:" + result);
        return pila + "t" + result;
    }

    private static boolean esOperador(char c) {
        return (c == '+' || c == '-' || c == '*' || c == '/');
    }

    private static int jerarquia(char op1, char op2) {
        switch (op1) {
            case '+':
            case '-':
                return (op2 == '*' || op2 == '/' ? -1 : 0);
            case '*':
            case '/':
                return (op2 == '+' || op2 == '-' ? 1 : 0);
        }
        return 1;
    }

    private static int calcular(int num1, int num2, char op) {

        switch (op) {
            case '+':
                return num1 + num2;
            case '-':
                return num1 - num2;
            case '*':
                return num1 * num2;
            case '/':
                if (num2 == 0) {
                    throw new IllegalArgumentException("divisor can't be 0.");
                }
                return num1 / num2;
            default:
                return 0; // will never catch up here 
            }

    }

}

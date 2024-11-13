package org.example;

import java.util.Stack;

interface Calculator {

    static double calculateEquation(StringBuilder string) {
        String[] reversPolishNotation = convertToReversPolishNotation(string).split(String.valueOf(' '));
        return calculateReversPolishNotation(reversPolishNotation);
    }

    static boolean isMathOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '(' || c == ')';
    }

    static boolean isNumber(char c) {
        return c - '0' >= 0 && c - '0' <= 9;
    }

    static double calculateReversPolishNotation (String[] tokens) {
        Stack<Double> stack = new Stack<>();
        String operators = "+-*/";
        for (String token : tokens) {
            if (!operators.contains(token)) {
                stack.push(Double.valueOf(token));
                continue;
            }
            double a = stack.pop();
            double b = stack.pop();
            switch (token) {
                case "+" -> stack.push(b + a);
                case "-" -> stack.push(b - a);
                case "*" -> stack.push(b * a);
                case "/" -> stack.push(b / a);
            }
        }
        return stack.pop();
    }

    static String convertToReversPolishNotation(StringBuilder expression) {
        String resultString = "";
        int length = expression.length();
        Stack<Character> operator = new Stack<>();
        Stack<String> reversePolish = new Stack<>();
        operator.push('#');             // using "#' to avoid out of bounds exception
        for (int counter = 0; counter < length; ) { // don't using counter++ to copy digits correctly
            if (isNumber(expression.charAt(counter))) {         // if char is number
                StringBuilder number = new StringBuilder();
                while (counter < length && isNumber(expression.charAt(counter)))
                    number.append(expression.charAt(counter++));
                reversePolish.push(number.toString());
            } else if (isMathOperator(expression.charAt(counter))) {       // if char is operator
                char op = expression.charAt(counter);
                switch (op) {
                    case '(':
                        operator.push(op);
                        break;
                    case ')':
                        while (operator.peek() != '(')
                            reversePolish.push(Character.toString(operator.pop()));
                        operator.pop();
                        break;
                    case '+':
                    case '-':
                        if (operator.peek() == '(')
                            operator.push(op);
                        else {
                            while (operator.peek() != '#' && operator.peek() != '(')
                                reversePolish.push(Character.toString(operator.pop()));
                            operator.push(op);
                        }
                        break;
                    case '*':
                    case '/':
                        if (operator.peek() == '(')
                            operator.push(op);
                        else {
                            while (operator.peek() != '#' && operator.peek() != '+' &&
                                    operator.peek() != '-' && operator.peek() != '(')
                                reversePolish.push(Character.toString(operator.pop()));
                            operator.push(op);
                        }
                        break;
                }
                counter++;
            }
        }
        while (operator.peek() != '#')
            reversePolish.push(Character.toString(operator.pop()));
        while (!reversePolish.isEmpty())
            resultString = (resultString.isEmpty()) ? reversePolish.pop() + resultString : reversePolish.pop() + " " + resultString;
        return resultString;
    }
}

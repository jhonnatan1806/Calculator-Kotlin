package com.jhaner.calculadora.model

import java.util.Stack

class CalculatorModel {

    private val operatorStack = Stack<Char>()
    private val valueStack = Stack<Double>()

    fun evaluate(expression: String): Double {
        val tokens = expression.toCharArray()
        var i = 0
        while (i < tokens.size) {
            val token = tokens[i]

            when {
                // Ignore spaces
                token == ' ' -> {
                    i++
                    continue
                }
                // If the token is a number or a negative sign followed by a number
                token.isDigit() || (token == '-' && (i == 0 || tokens[i - 1] == '(')) -> {
                    val stringBuffer = StringBuffer()
                    if (token == '-') {
                        stringBuffer.append('-')
                        i++
                    }
                    // Collect all digits after the negative sign or number
                    while (i < tokens.size && (tokens[i].isDigit() || tokens[i] == '.')) {
                        stringBuffer.append(tokens[i++])
                    }
                    valueStack.push(stringBuffer.toString().toDouble())
                    i-- // Adjust the index after loop
                }
                // Handle '('
                token == '(' -> operatorStack.push(token)
                // Handle ')', resolve the expression inside parentheses
                token == ')' -> {
                    while (operatorStack.peek() != '(') {
                        valueStack.push(applyOperator(operatorStack.pop(), valueStack.pop(), valueStack.pop()))
                    }
                    operatorStack.pop() // Remove '('
                }
                // Handle operators
                isOperator(token) -> {
                    while (!operatorStack.isEmpty() && hasPrecedence(token, operatorStack.peek())) {
                        valueStack.push(applyOperator(operatorStack.pop(), valueStack.pop(), valueStack.pop()))
                    }
                    operatorStack.push(token)
                }
            }
            i++
        }

        // Process all the remaining operators
        while (!operatorStack.isEmpty()) {
            valueStack.push(applyOperator(operatorStack.pop(), valueStack.pop(), valueStack.pop()))
        }

        return valueStack.pop()
    }

    // Check if the character is an operator
    private fun isOperator(c: Char): Boolean {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '%'
    }

    // Apply the operator to the operands
    private fun applyOperator(op: Char, b: Double, a: Double): Double {
        return when (op) {
            '+' -> a + b
            '-' -> a - b
            '*' -> a * b
            '/' -> if (b != 0.0) a / b else throw ArithmeticException("Division by zero")
            '%' -> a % b
            else -> 0.0
        }
    }

    // Check if the first operator has precedence over the second operator
    private fun hasPrecedence(op1: Char, op2: Char): Boolean {
        if (op2 == '(' || op2 == ')') return false
        return (op1 != '*' && op1 != '/' && op1 != '%') || (op2 != '+' && op2 != '-')
    }

}
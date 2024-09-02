package com.jhaner.calculadora.model

import java.util.Stack

class CalculatorModel {

    private val operatorStack = Stack<Char>()
    private val valueStack = Stack<Double>()

    fun evaluate(expression: String): Double {
        val tokens = expression.replace(" ", "").toCharArray()
        var i = 0

        while (i < tokens.size) {
            val token = tokens[i]

            when {
                // Handle numbers including decimals
                token.isDigit() || (token == '.' &&
                            (   i == 0 ||
                                tokens[i - 1] == '(' ||
                                tokens[i - 1] == '+' ||
                                tokens[i - 1] == '-' ||
                                tokens[i - 1] == '*' ||
                                tokens[i - 1] == '/' )
                        ) -> {
                    val stringBuffer = StringBuffer()
                    // If it's a decimal point, handle leading zero if necessary
                    if (token == '.') {
                        if (i == 0 || !tokens[i - 1].isDigit()) {
                            stringBuffer.append('0')
                        }
                    }
                    // Collect the number including the decimal part
                    while (i < tokens.size && (tokens[i].isDigit() || tokens[i] == '.')) {
                        stringBuffer.append(tokens[i++])
                    }
                    valueStack.push(stringBuffer.toString().toDouble())
                    i-- // Adjust the index after loop
                }
                // Handle implicit multiplication
                token == '(' -> {
                    if (i > 0 && (tokens[i - 1].isDigit() || tokens[i - 1] == ')')) {
                        operatorStack.push('*') // Handle implicit multiplication
                    }
                    operatorStack.push(token)
                }
                // Handle ')', resolve the expression inside parentheses
                token == ')' -> {
                    while (operatorStack.isNotEmpty() && operatorStack.peek() != '(') {
                        processOperator()
                    }
                    if (operatorStack.isNotEmpty() && operatorStack.peek() == '(') {
                        operatorStack.pop() // Remove '('
                    }
                }
                // Handle operators
                isOperator(token) -> {
                    // Special handling for negative numbers
                    if (token == '-' && (i == 0 || tokens[i - 1] == '(' || isOperator(tokens[i - 1]))) {
                        // Handle negative number
                        val stringBuffer = StringBuffer()
                        stringBuffer.append('-')
                        i++
                        while (i < tokens.size && (tokens[i].isDigit() || tokens[i] == '.')) {
                            stringBuffer.append(tokens[i++])
                        }
                        valueStack.push(stringBuffer.toString().toDouble())
                        i-- // Adjust the index after loop
                    } else {
                        // Regular operator
                        while (operatorStack.isNotEmpty() && hasPrecedence(token, operatorStack.peek())) {
                            processOperator()
                        }
                        operatorStack.push(token)
                    }
                }
            }
            i++
        }

        // Process all the remaining operators
        while (operatorStack.isNotEmpty()) {
            processOperator()
        }

        if (valueStack.isEmpty()) {
            throw IllegalStateException("The value stack is empty after evaluation")
        }

        return valueStack.pop()
    }

    private fun processOperator() {
        if (valueStack.size < 2) {
            throw IllegalStateException("Not enough values to perform the operation")
        }
        val b = valueStack.pop()
        val a = valueStack.pop()
        val op = operatorStack.pop()
        valueStack.push(applyOperator(op, a, b))
    }

    // Check if the character is an operator
    private fun isOperator(c: Char): Boolean {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '%'
    }

    // Apply the operator to the operands
    private fun applyOperator(op: Char, a: Double, b: Double): Double {
        return when (op) {
            '+' -> a + b
            '-' -> a - b
            '*' -> a * b
            '/' -> if (b != 0.0) a / b else throw ArithmeticException("Division by zero")
            '%' -> a % b
            else -> throw IllegalArgumentException("Invalid operator: $op")
        }
    }

    // Check if the first operator has precedence over the second operator
    private fun hasPrecedence(op1: Char, op2: Char): Boolean {
        if (op2 == '(' || op2 == ')') return false
        return (op1 != '*' && op1 != '/' && op1 != '%') || (op2 != '+' && op2 != '-')
    }
}

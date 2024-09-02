package com.jhaner.calculadora.model

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class CalculatorModelTest {

    private lateinit var calculator: CalculatorModel

    @Before
    fun setUp() {
        calculator = CalculatorModel()
    }

    @Test
    fun testAddition() {
        val result = calculator.evaluate("3 + 2")
        assertEquals(5.0, result, 0.001)
    }

    @Test
    fun testSubtraction() {
        val result = calculator.evaluate("10 - 4")
        assertEquals(6.0, result, 0.001)
    }

    @Test
    fun testMultiplication() {
        val result = calculator.evaluate("6 * 3")
        assertEquals(18.0, result, 0.001)
    }

    @Test
    fun testDivision() {
        val result = calculator.evaluate("15 / 3")
        assertEquals(5.0, result, 0.001)
    }

    @Test
    fun testModulo() {
        val result = calculator.evaluate("10 % 3")
        assertEquals(1.0, result, 0.001)
    }

    @Test
    fun testComplexExpression() {
        val result = calculator.evaluate("10 + 2 * 6")
        assertEquals(22.0, result, 0.001)
    }

    @Test
    fun testParenthesesExpression() {
        val result = calculator.evaluate("(10 + 2) * 6")
        assertEquals(72.0, result, 0.001)
    }

    @Test
    fun testNestedParenthesesExpression() {
        val result = calculator.evaluate("((10 + 2) * 6) / 2")
        assertEquals(36.0, result, 0.001)
    }

    @Test(expected = ArithmeticException::class)
    fun testDivisionByZero() {
        calculator.evaluate("10 / 0")

    }

    @Test
    fun testWhiteSpaceHandling() {
        val result = calculator.evaluate("  3  +  2 ")
        assertEquals(5.0, result, 0.001)
    }

    @Test
    fun testNegativeNumbers() {
        val result = calculator.evaluate("-5 + 3")
        assertEquals(-2.0, result, 0.001)
    }

    @Test
    fun testMultipleOperators() {
        val result = calculator.evaluate("10 + 2 - 3 * 4 / 2")
        assertEquals(6.0, result, 0.001)
    }
}
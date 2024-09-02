package com.jhaner.calculadora.presenter

import com.jhaner.calculadora.model.CalculatorModel
import com.jhaner.calculadora.view.OperationView

class OperationPresenter(
    private val view: OperationView,
    private val calculatorModel: CalculatorModel,
    private val historyPresenter: HistoryPresenter
) {

    private var currentOperation: String = ""
    private var isResultDisplayed: Boolean = false

    fun onDigitPressed(digit: String) {
        if (isResultDisplayed) {
            currentOperation = ""
            isResultDisplayed = false
        }
        currentOperation += digit
        view.updateOperationDisplay(currentOperation)
    }

    fun onOperatorPressed(operator: String) {
        currentOperation += " $operator "
        isResultDisplayed = false
        view.updateOperationDisplay(currentOperation)
    }

    fun onEqualsPressed() {
        try {
            val result = calculatorModel.evaluate(currentOperation)
            val resultText = if (result % 1.0 == 0.0) {
                result.toInt().toString()
            } else {
                result.toString()
            }
            view.updateResult(resultText)
            historyPresenter.onAddHistory("$currentOperation = $resultText")
            currentOperation = resultText
            isResultDisplayed = true
        } catch (e: Exception) {
            view.updateResult("Error")
        }
    }

    fun onClearPressed() {
        currentOperation = ""
        isResultDisplayed = false
        view.updateOperationDisplay("")
        view.updateResult("")
    }

    fun onDeletePressed() {
        if (currentOperation.isNotEmpty()) {
            currentOperation = currentOperation.dropLast(1)
            view.updateOperationDisplay(currentOperation)
        }
    }

    fun onParenthesisPressed(parenthesis: String) {
        currentOperation += parenthesis
        isResultDisplayed = false
        view.updateOperationDisplay(currentOperation)
    }
}
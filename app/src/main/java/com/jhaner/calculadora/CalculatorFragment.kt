package com.jhaner.calculadora

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.jhaner.calculadora.model.CalculatorModel
import com.jhaner.calculadora.model.HistoryModel
import com.jhaner.calculadora.presenter.HistoryPresenter
import com.jhaner.calculadora.presenter.OperationPresenter
import com.jhaner.calculadora.view.HistoryView
import com.jhaner.calculadora.view.OperationView

class CalculatorFragment : Fragment(), OperationView, HistoryView {

    private lateinit var operationPresenter: OperationPresenter
    private lateinit var historyPresenter: HistoryPresenter

    private lateinit var btnDigit0: Button
    private lateinit var btnDigit1: Button
    private lateinit var btnDigit2: Button
    private lateinit var btnDigit3: Button
    private lateinit var btnDigit4: Button
    private lateinit var btnDigit5: Button
    private lateinit var btnDigit6: Button
    private lateinit var btnDigit7: Button
    private lateinit var btnDigit8: Button
    private lateinit var btnDigit9: Button
    private lateinit var btnAdd: Button
    private lateinit var btnSubtract: Button
    private lateinit var btnMultiply: Button
    private lateinit var btnDivide: Button
    private lateinit var btnDecimal: Button
    private lateinit var btnDelete: Button
    private lateinit var btnClear: Button
    private lateinit var btnEquals: Button
    private lateinit var btnParenthesis: Button

    private lateinit var txtOperation: TextView
    private lateinit var txtHistory: TextView

    private var isOpenParenthesis = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_calculator, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicialización de la Toolbar
        val toolbar: Toolbar = requireActivity().findViewById(R.id.toolbar)
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Calculator"

        // Inicialización de los botones
        btnDigit0 = view.findViewById(R.id.btnDigit0)
        btnDigit1 = view.findViewById(R.id.btnDigit1)
        btnDigit2 = view.findViewById(R.id.btnDigit2)
        btnDigit3 = view.findViewById(R.id.btnDigit3)
        btnDigit4 = view.findViewById(R.id.btnDigit4)
        btnDigit5 = view.findViewById(R.id.btnDigit5)
        btnDigit6 = view.findViewById(R.id.btnDigit6)
        btnDigit7 = view.findViewById(R.id.btnDigit7)
        btnDigit8 = view.findViewById(R.id.btnDigit8)
        btnDigit9 = view.findViewById(R.id.btnDigit9)
        btnAdd = view.findViewById(R.id.btnAdd)
        btnSubtract = view.findViewById(R.id.btnSubtract)
        btnMultiply = view.findViewById(R.id.btnMultiply)
        btnDivide = view.findViewById(R.id.btnDivide)
        btnDecimal = view.findViewById(R.id.btnDecimal)
        btnDelete = view.findViewById(R.id.btnDelete)
        btnClear = view.findViewById(R.id.btnClear)
        btnEquals = view.findViewById(R.id.btnEquals)
        btnParenthesis = view.findViewById(R.id.btnParenthesis)

        // Inicialización de los TextViews
        txtOperation = view.findViewById(R.id.txtOperation)
        txtHistory = view.findViewById(R.id.txtHistory)

        // Inicialización del modelo y presentadores
        val calculatorModel = CalculatorModel()
        val historyModel = HistoryModel()

        historyPresenter = HistoryPresenter(this, historyModel)
        operationPresenter = OperationPresenter(this, calculatorModel, historyPresenter)

        // Configurar listeners de botones
        btnDigit0.setOnClickListener { operationPresenter.onDigitPressed("0") }
        btnDigit1.setOnClickListener { operationPresenter.onDigitPressed("1") }
        btnDigit2.setOnClickListener { operationPresenter.onDigitPressed("2") }
        btnDigit3.setOnClickListener { operationPresenter.onDigitPressed("3") }
        btnDigit4.setOnClickListener { operationPresenter.onDigitPressed("4") }
        btnDigit5.setOnClickListener { operationPresenter.onDigitPressed("5") }
        btnDigit6.setOnClickListener { operationPresenter.onDigitPressed("6") }
        btnDigit7.setOnClickListener { operationPresenter.onDigitPressed("7") }
        btnDigit8.setOnClickListener { operationPresenter.onDigitPressed("8") }
        btnDigit9.setOnClickListener { operationPresenter.onDigitPressed("9") }

        btnAdd.setOnClickListener { operationPresenter.onOperatorPressed("+") }
        btnSubtract.setOnClickListener { operationPresenter.onOperatorPressed("-") }
        btnMultiply.setOnClickListener { operationPresenter.onOperatorPressed("*") }
        btnDivide.setOnClickListener { operationPresenter.onOperatorPressed("/") }

        btnDecimal.setOnClickListener { operationPresenter.onDigitPressed(".") }
        btnDelete.setOnClickListener { operationPresenter.onDeletePressed() }
        btnClear.setOnClickListener { operationPresenter.onClearPressed() }

        btnEquals.setOnClickListener {
            if (!isOpenParenthesis) {
                operationPresenter.onParenthesisPressed(")")
                isOpenParenthesis = true
                btnParenthesis.text = "("
            }
            operationPresenter.onEqualsPressed()
        }

        btnParenthesis.setOnClickListener {
            val parenthesis = if (isOpenParenthesis) "(" else ")"
            operationPresenter.onParenthesisPressed(parenthesis)
            isOpenParenthesis = !isOpenParenthesis
            btnParenthesis.text = if (isOpenParenthesis) "(" else ")"
        }
    }

    // Métodos de la interfaz OperationView
    override fun updateOperationDisplay(displayText: String) {
        txtOperation.text = displayText
    }

    override fun updateResult(resultText: String) {
        txtOperation.text = resultText
    }

    // Métodos de la interfaz HistoryView
    override fun updateHistoryDisplay(historyText: String) {
        txtHistory.text = historyText
    }
}
package com.jhaner.calculadora.presenter

import com.jhaner.calculadora.model.HistoryModel
import com.jhaner.calculadora.view.HistoryView

class HistoryPresenter(private val view: HistoryView, private val historyModel: HistoryModel) {

    fun onAddHistory(entry: String) {
        historyModel.addHistory(entry)
        view.updateHistoryDisplay(entry)
    }

    fun onClearHistoryPressed() {
        historyModel.clearHistory()
        view.updateHistoryDisplay("")
    }

    fun onShowLastHistory() {
        val lastEntry = historyModel.getLastEntry() ?: "No history"
        view.updateHistoryDisplay(lastEntry)
    }
}
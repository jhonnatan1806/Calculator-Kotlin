package com.jhaner.calculadora.model

class HistoryModel {

    private val historyStack: MutableList<String> = mutableListOf()

    // Method to add a new entry to the history
    fun addHistory(entry: String) {
        historyStack.add(entry)
    }

    // Method to retrieve the entire history as a list
    fun getHistory(): List<String> {
        return historyStack
    }

    // Method to clear the entire history
    fun clearHistory() {
        historyStack.clear()
    }

    // Optional: Method to retrieve the last entry (useful if you need it)
    fun getLastEntry(): String? {
        return if (historyStack.isNotEmpty()) historyStack.last() else null
    }

    // Optional: Method to check if the history is empty
    fun isHistoryEmpty(): Boolean {
        return historyStack.isEmpty()
    }
}


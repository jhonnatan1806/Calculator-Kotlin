package com.jhaner.calculadora.model

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class HistoryModelTest {

    private lateinit var historyModel: HistoryModel

    @Before
    fun setUp() {
        historyModel = HistoryModel()
    }

    @Test
    fun testAddHistory() {
        historyModel.addHistory("5 + 3 = 8")
        val history = historyModel.getHistory()
        assertEquals(1, history.size)
        assertEquals("5 + 3 = 8", history[0])
    }

    @Test
    fun testAddMultipleEntries() {
        historyModel.addHistory("2 + 2 = 4")
        historyModel.addHistory("10 - 4 = 6")
        val history = historyModel.getHistory()
        assertEquals(2, history.size)
        assertEquals("2 + 2 = 4", history[0])
        assertEquals("10 - 4 = 6", history[1])
    }

    @Test
    fun testClearHistory() {
        historyModel.addHistory("5 * 5 = 25")
        historyModel.addHistory("6 / 2 = 3")
        historyModel.clearHistory()
        val history = historyModel.getHistory()
        assertTrue(history.isEmpty())
    }

    @Test
    fun testGetLastEntry() {
        historyModel.addHistory("8 / 2 = 4")
        val lastEntry = historyModel.getLastEntry()
        assertEquals("8 / 2 = 4", lastEntry)
    }

    @Test
    fun testGetLastEntryWhenEmpty() {
        val lastEntry = historyModel.getLastEntry()
        assertNull(lastEntry)
    }

    @Test
    fun testIsHistoryEmptyInitially() {
        assertTrue(historyModel.isHistoryEmpty())
    }

    @Test
    fun testIsHistoryEmptyAfterAddingEntries() {
        historyModel.addHistory("9 + 1 = 10")
        assertFalse(historyModel.isHistoryEmpty())
    }
}
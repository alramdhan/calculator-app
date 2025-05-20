package org.logixphere.kalkulator.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.logixphere.kalkulator.data.Operator

class CalculatorViewModel : ViewModel() {
    private val _display = MutableStateFlow("")
    val display: StateFlow<String> = _display

    private val _result = MutableStateFlow("")
    val result: StateFlow<String> = _result

    private var operand1: String = ""
    private var operand2: String = ""
    private var operator: Operator? = null

    fun onNumberClick(number: String) {
        if(operator == null) {
            operand1 += number
        } else {
            operand2 += number
        }

        updateDisplay()
    }

    fun onOperatorClick(operator: Operator) {
        if(operand1.isNotEmpty()) {
            this.operator = operator
        }

        updateDisplay()
    }

    fun onEqualsClick() {
        if(operand1.isNotEmpty() && operand2.isNotEmpty() && operator != null) {
            val total = when(operator) {
                Operator.ADD -> operand1.toDouble() + operand2.toDouble()
                Operator.SUBTRACT -> operand1.toDouble() - operand2.toDouble()
                Operator.MULTIPLY -> operand1.toDouble() * operand2.toDouble()
                Operator.DIVIDE -> operand1.toDouble() / operand2.toDouble()
                Operator.MODULO -> operand1.toDouble() % operand2.toDouble()
                else -> 0.0
            }

            _result.value = if(total == total.toLong().toDouble()) {
                total.toLong().toString()
            } else {
                total.toString()
            }

            updateDisplay()
        }
    }

    fun onClearClick() {
        operand1 = ""
        operand2 = ""
        operator = null
        _result.value = ""
        updateDisplay()
    }

    fun onBackspaceClick() {
        when {
            operand2.isNotEmpty() -> {
                operand2 = operand2.dropLast(1)
            }
            operator != null -> {
                operator = null
            }
            operand1.isNotEmpty() -> {
                operand1 = operand1.dropLast(1)
            }
        }

        updateDisplay()
    }

    fun onToggleSignClick() {
        if(operator == null) {
            operand1 = toggleSign(operand1)
        } else {
            operand2 = toggleSign(operand2)
        }

        updateDisplay()
    }

    private fun toggleSign(value: String): String {
        return if(value.startsWith("-")) {
            value.substring(1)
        } else {
            "-$value"
        }
    }

    private fun updateDisplay() {
        _display.value = operand1 + (operator?.let { " ${it.symbol} " } ?: "") + operand2
    }
}
package org.logixphere.kalkulator.data

enum class Operator(val symbol: String) {
    ADD("+"),
    SUBTRACT("-"),
    MULTIPLY("*"),
    DIVIDE("/"),
    MODULO("%"),
    DELETE("DEL"),
    EQUALS("="),
    CHANGE("C"),
}
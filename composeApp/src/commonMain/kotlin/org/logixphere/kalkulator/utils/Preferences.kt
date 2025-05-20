package org.logixphere.kalkulator.utils

interface Preferences {
    fun setDarkThemeEnabled(isEnabled: Boolean)
    fun isDarkThemeEnabled(): Boolean
}
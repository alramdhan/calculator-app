package org.logixphere.kalkulator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.logixphere.kalkulator.utils.PreferencesFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        PreferencesFactory.setInstance(AndroidPreferences(this))

        setContent {
            CalculatorApp()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    CalculatorApp()
}
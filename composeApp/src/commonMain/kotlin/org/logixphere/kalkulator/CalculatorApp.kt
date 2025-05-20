package org.logixphere.kalkulator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kalkulator.composeapp.generated.resources.Res
import kalkulator.composeapp.generated.resources.ic_add
import kalkulator.composeapp.generated.resources.ic_arrow_delete
import kalkulator.composeapp.generated.resources.ic_change_positif_negatif
import kalkulator.composeapp.generated.resources.ic_divide
import kalkulator.composeapp.generated.resources.ic_equals
import kalkulator.composeapp.generated.resources.ic_modulo
import kalkulator.composeapp.generated.resources.ic_moon
import kalkulator.composeapp.generated.resources.ic_multiply
import kalkulator.composeapp.generated.resources.ic_subtract
import kalkulator.composeapp.generated.resources.ic_sun
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.logixphere.kalkulator.data.Operator
import org.logixphere.kalkulator.ui.CalculatorTheme
import org.logixphere.kalkulator.ui.colorDark
import org.logixphere.kalkulator.utils.PreferencesFactory
import org.logixphere.kalkulator.viewmodel.CalculatorViewModel

@Composable
@Preview
fun CalculatorApp(viewModel: CalculatorViewModel = viewModel { CalculatorViewModel() }) {
    val preferences = PreferencesFactory.getInstance()
    var isDarkTheme by remember { mutableStateOf(preferences.isDarkThemeEnabled()) }
    val display = viewModel.display.collectAsState()
    val result = viewModel.result.collectAsState()

    CalculatorTheme(
        darkTheme = isDarkTheme
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                content = {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = getPadding(), horizontal = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        ToggleDarkThemeSwitch(
                            isDarkTheme = isDarkTheme,
                            onToggle = {
                                isDarkTheme = it
                                preferences.setDarkThemeEnabled(isEnabled = it)
                            }
                        )

                        Text(
                            modifier = Modifier.weight(1f),
                            text = display.value,
                            style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.End,
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 28.sp
                        )
                        Text(
                            modifier = Modifier.weight(1f),
                            text = result.value,
                            style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.End,
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 40.sp,
                            lineHeight = 4.sp
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        NumberPad(viewModel = viewModel)
                    }
                }
            )
        }
    }
}

@Composable
fun NumberPad(modifier: Modifier = Modifier, viewModel: CalculatorViewModel) {
    val buttons = listOf(
        listOf("C", Operator.CHANGE, Operator.MODULO, Operator.DIVIDE),
        listOf("7", "8", "9", Operator.MULTIPLY),
        listOf("4", "5", "6", Operator.SUBTRACT),
        listOf("1", "2", "3", Operator.ADD),
        listOf(".", "0", Operator.DELETE, Operator.EQUALS),
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        buttons.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                row.forEach { item ->
                    when(item) {
                        is String -> {
                            NumberButton(
                                modifier = Modifier.weight(1f),
                                number = item,
                                onClick = {
                                    when(item) {
                                        "C" -> viewModel.onClearClick()
                                        else -> viewModel.onNumberClick(number = item)
                                    }
                                }
                            )
                        }
                        is Operator -> {
                            OperatorButton(
                                modifier = Modifier.weight(1f),
                                operator = item,
                                onClick = {
                                    when(item) {
                                        Operator.CHANGE -> viewModel.onToggleSignClick()
                                        Operator.EQUALS -> viewModel.onEqualsClick()
                                        Operator.DELETE -> viewModel.onBackspaceClick()
                                        else -> viewModel.onOperatorClick(operator = item)
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NumberButton(
    modifier: Modifier = Modifier,
    number: String,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier.height(72.dp),
        shape = RoundedCornerShape(24.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
        onClick = onClick
    ) {
        Text(
            text = number,
            fontFamily = FontFamily.Monospace,
            fontSize = 26.sp,
            maxLines = 1,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
fun OperatorButton(
    modifier: Modifier = Modifier,
    operator: Operator,
    onClick: () -> Unit
) {
    val icon = when(operator) {
        Operator.ADD -> painterResource(Res.drawable.ic_add)
        Operator.SUBTRACT -> painterResource(Res.drawable.ic_subtract)
        Operator.MULTIPLY -> painterResource(Res.drawable.ic_multiply)
        Operator.DIVIDE -> painterResource(Res.drawable.ic_divide)
        Operator.CHANGE -> painterResource(Res.drawable.ic_change_positif_negatif)
        Operator.MODULO -> painterResource(Res.drawable.ic_modulo)
        Operator.EQUALS -> painterResource(Res.drawable.ic_equals)
        Operator.DELETE -> painterResource(Res.drawable.ic_arrow_delete)
    }

    Button(
        modifier = modifier.height(72.dp),
        shape = RoundedCornerShape(24.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
        onClick = onClick
    ) {
        Icon(
            modifier = modifier.size(24.dp),
            painter = icon,
            contentDescription = operator.name
        )
    }
}

@Composable
fun ToggleDarkThemeSwitch(
    isDarkTheme: Boolean,
    onToggle: (Boolean) -> Unit
) {
    Switch(
        checked = isDarkTheme,
        onCheckedChange = { onToggle(it) },
        colors = SwitchDefaults.colors(
            checkedBorderColor = Color.Transparent,
            uncheckedBorderColor = Color.Transparent,
            checkedIconColor = MaterialTheme.colorScheme.primary,
            uncheckedIconColor = MaterialTheme.colorScheme.primary,
            checkedTrackColor = colorDark,
            uncheckedTrackColor = Color.White,
            checkedThumbColor = Color.Transparent,
            uncheckedThumbColor = Color.Transparent,
        ),
        thumbContent = {
            Icon(
                painter = if(isDarkTheme) painterResource(Res.drawable.ic_moon)
                    else painterResource(Res.drawable.ic_sun),
                contentDescription = ""
            )
        }
    )
}
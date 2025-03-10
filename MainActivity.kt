package com.example.practica1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.Period
import java.time.temporal.ChronoUnit

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { UIPrincipal() }
    }
}

@Composable
fun UIPrincipal() {
    var option by remember { mutableStateOf(0) }
    var num1 by remember { mutableStateOf("") }
    var num2 by remember { mutableStateOf("") }
    var num3 by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var cumple by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    Column(Modifier.fillMaxSize().padding(16.dp), Arrangement.Top,
        Alignment.CenterHorizontally) {
        Text("Menú", style = MaterialTheme.typography.headlineMedium)

        Button(onClick = { option = 1 }) { Text("Sumar tres números") }
        Button(onClick = { option = 2 }) { Text("Ingresar nombre completo") }
        Button(onClick = { option = 3 }) { Text("Calcular tiempo de vida") }

        Spacer(modifier = Modifier.height(20.dp))

        when (option) {
            1 -> {
                Text("Ingrese tres números:")
                TextField(value = num1, onValueChange = { num1 = it }, label = { Text("Número 1") })
                TextField(value = num2, onValueChange = { num2 = it }, label = { Text("Número 2") })
                TextField(value = num3, onValueChange = { num3 = it }, label = { Text("Número 3") })
                Button(onClick = {
                    result = sumThreeNumbers(num1.toIntOrNull() ?: 0, num2.toIntOrNull() ?: 0, num3.toIntOrNull() ?: 0)
                }) { Text("Sumar") }
            }
            2 -> {
                Text("Ingrese su nombre:")
                TextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre completo") })
                Button(onClick = { result = enterFullName(nombre) }) { Text("Mostrar Nombre") }
            }
            3 -> {
                Text("Ingrese su fecha de nacimiento (YYYY-MM-DD):")
                TextField(value = cumple, onValueChange = { cumple = it }, label = { Text("Fecha de nacimiento") })
                Button(onClick = { result = calculateAge(cumple) }) { Text("Calcular") }
            }
        }

        if (result.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(result, style = MaterialTheme.typography.bodyLarge)
        }
    }
}

fun sumThreeNumbers(a: Int, b: Int, c: Int): String {
    val sum = a + b + c
    return "La suma de $a + $b + $c es: $sum"
}

fun enterFullName(nombre: String): String {
    return "Nombre ingresado: $nombre"
}

fun calculateAge(cumple: String): String {
    return try {
        val nacimiento = LocalDate.parse(cumple)
        val hoy = LocalDate.now()
        val period = Period.between(nacimiento, hoy)
        val diasvividos = ChronoUnit.DAYS.between(nacimiento, hoy)
        val semanasvividas = diasvividos / 7
        val mesesvividos = ChronoUnit.MONTHS.between(nacimiento, hoy)
        val horasvividas = diasvividos * 24
        val minutosvividos = horasvividas * 60
        val segundosvividos = minutosvividos * 60

        """
            Has vivido aproximadamente:
            - $mesesvividos meses
            - $semanasvividas semanas
            - $diasvividos días
            - $horasvividas horas
            - $minutosvividos minutos
            - $segundosvividos segundos
        """.trimIndent()
    } catch (e: Exception) {
        "Formato de fecha inválido. Use YYYY-MM-DD."
    }
}

@Preview(showBackground = true)
@Composable
fun Previsualizacion() {
    UIPrincipal()
}

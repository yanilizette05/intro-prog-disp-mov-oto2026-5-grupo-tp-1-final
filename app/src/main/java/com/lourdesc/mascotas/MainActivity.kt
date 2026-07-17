package com.lourdesc.mascotas

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lourdesc.mascotas.ui.theme.MascotasTheme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MascotasTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        AplicacionDeMascotas()
                    }
                }
            }
        }
    }
}

@Composable
fun Formulario(
    nombre: String,
    especie: String,
    raza: String,
    edad: String,
    peso: String,
    onNombreChange: (String) -> Unit,
    onEspecieChange: (String) -> Unit,
    onRazaChange: (String) -> Unit,
    onEdadChange: (String) -> Unit,
    onPesoChange: (String) -> Unit,
    onAgregarMascota: () -> Unit
) {
    Column {
        Text(
            "Ingrese una Mascota",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = nombre,
            onValueChange = onNombreChange,
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = especie,
            onValueChange = onEspecieChange,
            label = { Text("Especie") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = raza,
            onValueChange = onRazaChange,
            label = { Text("Raza") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = edad,
                onValueChange = onEdadChange,
                label = { Text("Edad") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            OutlinedTextField(
                value = peso,
                onValueChange = onPesoChange,
                label = { Text("Peso en Kg") },
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onAgregarMascota,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Agregar Mascota")
        }
    }
}

@Composable
fun Tarjeta(
    mascota: Mascota,
    onEliminarMascota: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = mascota.nombre,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item { InfoContacto(titulo = "Especie", valor = mascota.especie) }
                item { InfoContacto(titulo = "Raza", valor = mascota.raza) }
                item { InfoContacto(titulo = "Edad", valor = mascota.edad) }
                item { InfoContacto(titulo = "Peso", valor = mascota.peso) }
            }

            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = onEliminarMascota,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Eliminar Mascota", color = MaterialTheme.colorScheme.onError)
            }
        }
    }
}

@Composable
fun InfoContacto(titulo: String, valor: String) {
    Box(
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.12f),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = "$titulo: $valor",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun AplicacionDeMascotas() {
    var mascotas by remember { mutableStateOf(listOf<Mascota>()) }
    var nombre by remember { mutableStateOf("") }
    var especie by remember { mutableStateOf("") }
    var raza by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }
    var peso by remember { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Formulario(
                nombre = nombre,
                especie = especie,
                raza = raza,
                edad = edad,
                peso = peso,
                onNombreChange = { nombre = it },
                onEspecieChange = { especie = it },
                onRazaChange = { raza = it },
                onEdadChange = { edad = it },
                onPesoChange = { peso = it },
                onAgregarMascota = {
                    if (nombre.isNotBlank() && especie.isNotBlank()) {
                        val nuevoMascota = Mascota(
                            nombre = nombre,
                            especie = especie,
                            raza = raza,
                            edad = edad,
                            peso = peso
                        )
                        mascotas = mascotas + nuevoMascota
                        nombre = ""; especie = ""; raza = ""; edad = ""; peso = ""
                    }
                }
            )
        }

        item {
            if (mascotas.isNotEmpty()) {
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Mascotas Registradas",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        items(mascotas, key = { it.id }) { mascota ->
            Tarjeta(
                mascota = mascota,
                onEliminarMascota = {
                    mascotas = mascotas.filter { it.id != mascota.id }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VistaPreviewAplicacionDeContactos() {
    MascotasTheme {
        AplicacionDeMascotas()
    }
}
data class Mascota(
    val id: Long = System.currentTimeMillis() + (0..1000).random(),
    val nombre: String,
    val especie: String,
    val raza: String,
    val edad: String,
    val peso: String
)
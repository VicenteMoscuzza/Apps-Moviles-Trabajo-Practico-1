package com.example.appsmovilestrabajopractico1

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun WelcomeScreen(navController: NavHostController) {
    var platform by remember { mutableStateOf("") }
    var programming by remember { mutableStateOf(false) }
    var networks by remember { mutableStateOf(false) }
    var security by remember { mutableStateOf(false) }
    var hardware by remember { mutableStateOf(false) }
    var other by remember { mutableStateOf(false) }
    var otherText by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        IconButton(onClick = { navController.navigate("login") }) {
            Icon(Icons.Filled.ArrowBack, contentDescription = "Volver", tint = Color.Black)
        }

        Text(text = "¡Bienvenido a la aplicación Juan Torres!", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = "Seleccioná tu plataforma:")

        Row {
            Button(onClick = { platform = "Android" }) {
                Text("Android")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { platform = "iOS" }) {
                Text("iOS")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (platform.isNotEmpty()) {
            val imageRes = if (platform == "Android") R.drawable.android_logo else R.drawable.ios_logo
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = "Logo de $platform",
                modifier = Modifier.size(100.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Preferencias:")

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = programming, onCheckedChange = { programming = it })
            Text("Programación")
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = networks, onCheckedChange = { networks = it })
            Text("Redes")
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = security, onCheckedChange = { security = it })
            Text("Seguridad")
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = hardware, onCheckedChange = { hardware = it })
            Text("Hardware")
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = other, onCheckedChange = { other = it })
            Text("Otra")
        }

        if (other) {
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = otherText,
                onValueChange = { otherText = it },
                label = { Text("Especificar otra preferencia") },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
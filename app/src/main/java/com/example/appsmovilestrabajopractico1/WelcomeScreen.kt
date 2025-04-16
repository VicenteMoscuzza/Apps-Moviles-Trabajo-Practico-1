// WelcomeScreen.kt
package com.example.appsmovilestrabajopractico1

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.material3.OutlinedTextFieldDefaults


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomeScreen(navController: NavHostController) {
    var platform by remember { mutableStateOf("") }
    var programming by remember { mutableStateOf(false) }
    var networks by remember { mutableStateOf(false) }
    var security by remember { mutableStateOf(false) }
    var hardware by remember { mutableStateOf(false) }
    var other by remember { mutableStateOf(false) }
    var otherText by remember { mutableStateOf("") }

    // Variables para validación y mensajes
    var showValidationMessage by remember { mutableStateOf(false) }
    var isFormValid by remember { mutableStateOf(false) }
    var validationMessage by remember { mutableStateOf("") }

    // Coroutine scope para animaciones
    val scope = rememberCoroutineScope()

    // ScrollState para hacer scroll
    val scrollState = rememberScrollState()

    // Función para validar el formulario
    fun validateForm() {
        val hasSelectedPlatform = platform.isNotEmpty()
        val hasSelectedPreference = programming || networks || security || hardware || (other && otherText.isNotEmpty())

        isFormValid = hasSelectedPlatform && hasSelectedPreference

        validationMessage = when {
            !hasSelectedPlatform && !hasSelectedPreference -> "Debes seleccionar una plataforma y al menos una preferencia"
            !hasSelectedPlatform -> "Debes seleccionar una plataforma"
            !hasSelectedPreference -> "Debes seleccionar al menos una preferencia"
            else -> "¡Formulario enviado con éxito!"
        }

        showValidationMessage = true

        // Ocultar el mensaje después de 3 segundos
        scope.launch {
            delay(3000)
            showValidationMessage = false
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = AppColors.background
                )
            )
    ) {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { navController.navigate("login") },
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(24.dp))
                            .background(Color.White.copy(alpha = 0.7f))
                    ) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = AppColors.primary
                        )
                    }
                    Text(
                        text = "Bienvenido",
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppColors.primary
                    )
                    // Espacio para mantener el título centrado
                    Spacer(modifier = Modifier.size(48.dp))
                }
            },
            bottomBar = {
                // Botón de envío siempre visible en la parte inferior
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White.copy(alpha = 0.9f))
                        .padding(16.dp)
                ) {
                    // Mensaje de validación animado
                    AnimatedVisibility(
                        visible = showValidationMessage,
                        enter = fadeIn(animationSpec = tween(300)) +
                                expandVertically(animationSpec = tween(300)),
                        exit = fadeOut(animationSpec = tween(300)) +
                                shrinkVertically(animationSpec = tween(300))
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isFormValid) Color(0xFFE3F2FD) else Color(0xFFFFEBEE)
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = if (isFormValid) Icons.Default.Check else Icons.Default.Send,
                                    contentDescription = null,
                                    tint = if (isFormValid) AppColors.success else AppColors.error,
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.width(16.dp))
                                Text(
                                    text = validationMessage,
                                    color = if (isFormValid) AppColors.success else AppColors.error
                                )
                            }
                        }
                    }

                    Button(
                        onClick = { validateForm() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(28.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AppColors.primary
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Send,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "Enviar Formulario",
                            fontSize = 18.sp
                        )
                    }
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 24.dp)
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.Start
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = AppColors.cardBackground
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 4.dp
                    )
                ) {
                    Text(
                        text = "¡Bienvenido a la aplicación Juan Torres!",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = AppColors.primary,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                Text(
                    text = "Seleccioná tu plataforma:",
                    modifier = Modifier.padding(vertical = 8.dp),
                    fontWeight = FontWeight.Medium,
                    color = AppColors.primaryVariant
                )

                Row(
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Button(
                        onClick = { platform = "Android" },
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        shape = RoundedCornerShape(25.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (platform == "Android") AppColors.success else AppColors.primary
                        )
                    ) {
                        Text(
                            "Android",
                            fontSize = 16.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Button(
                        onClick = { platform = "iOS" },
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        shape = RoundedCornerShape(25.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (platform == "iOS") AppColors.terciary else AppColors.primary
                        )
                    ) {
                        Text(
                            "iOS",
                            fontSize = 16.sp
                        )
                    }
                }

                if (platform.isNotEmpty()) {
                    val imageRes = if (platform == "Android") R.drawable.android_logo else R.drawable.ios_logo
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            ),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 4.dp
                            )
                        ) {
                            Image(
                                painter = painterResource(id = imageRes),
                                contentDescription = "Logo de $platform",
                                modifier = Modifier
                                    .size(120.dp)
                                    .padding(16.dp)
                            )
                        }
                    }
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = AppColors.cardBackground
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 4.dp
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Preferencias:",
                            fontWeight = FontWeight.Bold,
                            color = AppColors.primaryVariant,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        // Preferencias con checkboxes redondeados y coloridos
                        PreferenceCheckbox(
                            text = "Programación",
                            checked = programming,
                            onCheckedChange = { programming = it }
                        )

                        PreferenceCheckbox(
                            text = "Redes",
                            checked = networks,
                            onCheckedChange = { networks = it }
                        )

                        PreferenceCheckbox(
                            text = "Seguridad",
                            checked = security,
                            onCheckedChange = { security = it }
                        )

                        PreferenceCheckbox(
                            text = "Hardware",
                            checked = hardware,
                            onCheckedChange = { hardware = it }
                        )

                        PreferenceCheckbox(
                            text = "Otra",
                            checked = other,
                            onCheckedChange = { other = it }
                        )

                        if (other) {
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = otherText,
                                onValueChange = { otherText = it },
                                label = { Text("Especificar otra preferencia") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp)),
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = AppColors.primary,
                                    focusedLabelColor = AppColors.primary
                                )
                            )
                        }
                    }
                }

                // Espacio adicional al final para evitar que el contenido quede oculto por el botón
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }
}

@Composable
fun PreferenceCheckbox(
    text: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(if (checked) AppColors.primary.copy(alpha = 0.1f) else Color.Transparent)
            .border(
                width = if (checked) 1.dp else 0.dp,
                color = if (checked) AppColors.primary else Color.Transparent,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(8.dp)
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                checkedColor = AppColors.primary,
                checkmarkColor = Color.White
            )
        )
        Text(
            text = text,
            modifier = Modifier.padding(start = 8.dp),
            color = if (checked) AppColors.primary else Color.Black.copy(alpha = 0.7f),
            fontWeight = if (checked) FontWeight.Bold else FontWeight.Normal
        )
    }
}
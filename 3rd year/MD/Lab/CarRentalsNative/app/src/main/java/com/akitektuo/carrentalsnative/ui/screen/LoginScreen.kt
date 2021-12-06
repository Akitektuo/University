package com.akitektuo.carrentalsnative.ui.screen

import android.os.Parcelable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.parcelize.Parcelize

data class LoginUiState(
    var email: String = "",
    var password: String = ""
)

class LoginViewModel : ViewModel() {
    val uiState = mutableStateOf(LoginUiState())
}

@Composable
fun LoginScreen(viewModel: LoginViewModel = viewModel()) = LayoutContainer {
    val state = viewModel.uiState.value

    LoginLabel()
    OutlinedTextField(
        modifier = Modifier.padding(vertical = 16.dp),
        value = state.email,
        label = { Text("Email") },
        onValueChange = { viewModel.uiState.value.email = it })
    OutlinedTextField(
        modifier = Modifier.padding(vertical = 16.dp),
        value = state.password,
        label = { Text("Password") },
        onValueChange = { state.password = it })
    Row(Modifier.padding(vertical = 32.dp)) {
        Button(modifier = Modifier.padding(16.dp), onClick = { }) {
            Text(text = "Register")
        }
        Button(modifier = Modifier.padding(16.dp), onClick = { }) {
            Text(text = "Log in")
        }
    }
}

@Composable
fun LayoutContainer(content: @Composable ColumnScope.() -> Unit) = Column(
    modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 48.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    content = content
)

@Composable
fun LoginLabel() = Text(
    modifier = Modifier.padding(64.dp),
    text = "Log in",
    fontSize = 32.sp,
    fontWeight = FontWeight.Bold
)
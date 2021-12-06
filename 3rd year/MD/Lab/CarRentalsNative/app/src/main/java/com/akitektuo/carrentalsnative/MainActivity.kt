package com.akitektuo.carrentalsnative

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.akitektuo.carrentalsnative.ui.screen.LoginScreen
import com.akitektuo.carrentalsnative.ui.theme.CarRentalsNativeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CarRentalsNativeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    LoginScreen()
                }
            }
        }
    }
}

@Composable
fun MessageCard(vararg names: String) = Column {
    Text(text = "Hello ${names[0]}!")
    Text(text = "Hello ${names[1]}!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CarRentalsNativeTheme {
        MessageCard("Android")
    }
}
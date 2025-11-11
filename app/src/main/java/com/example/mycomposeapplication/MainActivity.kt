package com.example.mycomposeapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mycomposeapplication.ui.theme.MyComposeApplicationTheme


class MainActivity : ComponentActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { MyContent() }
    }
}

@Composable
fun MyContent() {
    MyComposeApplicationTheme {
        Scaffold( modifier = Modifier.fillMaxSize() ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                Practic27()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    MyContent()
}
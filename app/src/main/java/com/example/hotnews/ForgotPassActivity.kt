package com.example.hotnews

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hotnews.ui.theme.HotNewsTheme
import com.google.firebase.auth.FirebaseAuth

class ForgotPassActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HotNewsTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    ForgotPassScreen()
                }
            }
        }
    }
}

@Composable
fun ForgotPassScreen() {
    var emailState by remember {
        mutableStateOf("")
    }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Please enter your registered email below to receive password reset instruction",
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Email field
        OutlinedTextField(
            value = emailState,

            label = { Text("Email") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            onValueChange = { emailState = it },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10)
        )

        Spacer(modifier = Modifier.height(25.dp))

        // Reset Password Button
        Button(
            onClick = {
                if (emailState.isNotBlank()) {
                    sendPasswordResetEmail(emailState, context)
                } else {
                    Toast.makeText(context, "Please enter your email.", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .background(Color.Transparent, shape = RoundedCornerShape(8.dp))
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(
                text = "Reset PassWord",
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
            )
        }
    }
}

private fun sendPasswordResetEmail(email: String, context: Context) {
    FirebaseAuth.getInstance().sendPasswordResetEmail(email)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(context, "Password reset email sent.", Toast.LENGTH_SHORT).show()

                val intent = Intent(context, LoginActivity::class.java)
                context.startActivity(intent)
            } else {
                Toast.makeText(context, "Failed to send password reset email.", Toast.LENGTH_SHORT).show()
            }
        }
}

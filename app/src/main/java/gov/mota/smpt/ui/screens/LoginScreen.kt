package gov.mota.smpt.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import gov.mota.smpt.ui.components.*
import gov.mota.smpt.ui.theme.*

@Composable
fun LoginScreen(
    onLogin: (String, String) -> Boolean,
    onDemoLogin: () -> Unit
) {
    var mobileNumber by remember { mutableStateOf("") }
    var otp by remember { mutableStateOf("") }
    var otpSent by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceWhite)
            .verticalScroll(rememberScrollState())
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(GovNavy)
                .padding(24.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Government of India",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextOnDarkSecondary
                )
                Text(
                    text = "Ministry of Tribal Affairs",
                    style = MaterialTheme.typography.titleSmall,
                    color = TextOnDark
                )
                Spacer(Modifier.height(16.dp))
                Text(
                    text = "SMPT",
                    style = MaterialTheme.typography.headlineMedium,
                    color = TextOnDark,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Scholarship Management Portal for Tribes",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextOnDarkSecondary,
                    textAlign = TextAlign.Center
                )
            }
        }

        // Login form
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Text(
                text = "Login",
                style = MaterialTheme.typography.headlineSmall,
                color = TextPrimary
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "Enter your registered mobile number",
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary
            )

            Spacer(Modifier.height(24.dp))

            // Mobile number
            Text(
                text = "Mobile Number",
                style = MaterialTheme.typography.labelLarge,
                color = TextPrimary
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = mobileNumber,
                onValueChange = { if (it.length <= 10) mobileNumber = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Enter 10-digit mobile number") },
                prefix = { Text("+91  ", color = TextSecondary) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                shape = RoundedCornerShape(8.dp),
                singleLine = true
            )

            if (otpSent) {
                Spacer(Modifier.height(16.dp))
                Text(
                    text = "OTP",
                    style = MaterialTheme.typography.labelLarge,
                    color = TextPrimary
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = otp,
                    onValueChange = { if (it.length <= 6) otp = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Enter 6-digit OTP") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    shape = RoundedCornerShape(8.dp),
                    singleLine = true
                )
            }

            if (errorMessage != null) {
                Spacer(Modifier.height(8.dp))
                Text(
                    text = errorMessage!!,
                    style = MaterialTheme.typography.bodySmall,
                    color = StatusRed
                )
            }

            Spacer(Modifier.height(24.dp))

            if (!otpSent) {
                PrimaryButton(
                    text = "Send OTP",
                    onClick = {
                        if (mobileNumber.length == 10) {
                            otpSent = true
                            errorMessage = null
                        } else {
                            errorMessage = "Please enter a valid 10-digit mobile number"
                        }
                    }
                )
            } else {
                PrimaryButton(
                    text = "Continue",
                    onClick = {
                        val success = onLogin(mobileNumber, otp)
                        if (!success) {
                            errorMessage = "Invalid OTP. For demo, use 123456"
                        }
                    }
                )
            }

            Spacer(Modifier.height(16.dp))

            // Demo OTP hint
            InfoBanner(
                text = "Demo OTP: 123456",
                type = BannerType.PROTOTYPE
            )

            Spacer(Modifier.height(16.dp))

            // Student ID option
            OutlinedButton(
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Continue with registered student ID")
            }

            Spacer(Modifier.height(24.dp))

            // Demo login
            TextButton(
                onClick = onDemoLogin,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Demo Login (Skip to Home)",
                    color = GovBlue,
                    style = MaterialTheme.typography.labelLarge
                )
            }

            Spacer(Modifier.height(16.dp))

            // Prototype label
            Text(
                text = "Prototype Demo — No real authentication",
                style = MaterialTheme.typography.labelSmall,
                color = TextTertiary,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

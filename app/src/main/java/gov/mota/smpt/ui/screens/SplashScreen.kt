package gov.mota.smpt.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gov.mota.smpt.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onNavigateNext: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(2500)
        onNavigateNext()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(GovNavy),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(32.dp)
        ) {
            // Government identity
            Text(
                text = "भारत सरकार",
                style = MaterialTheme.typography.bodyMedium,
                color = TextOnDarkSecondary,
                textAlign = TextAlign.Center
            )
            Text(
                text = "Government of India",
                style = MaterialTheme.typography.bodyLarge,
                color = TextOnDark,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = "जनजातीय कार्य मंत्रालय",
                style = MaterialTheme.typography.bodyMedium,
                color = TextOnDarkSecondary,
                textAlign = TextAlign.Center
            )
            Text(
                text = "Ministry of Tribal Affairs",
                style = MaterialTheme.typography.titleMedium,
                color = TextOnDark,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(40.dp))

            // App name
            Text(
                text = "SMPT",
                fontSize = 42.sp,
                fontWeight = FontWeight.Bold,
                color = TextOnDark,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = "Scholarship Management\nPortal for Tribes",
                style = MaterialTheme.typography.titleMedium,
                color = TextOnDark,
                textAlign = TextAlign.Center,
                lineHeight = 24.sp
            )

            Spacer(Modifier.height(16.dp))

            Text(
                text = "Unified Scholarship & Fellowship Services",
                style = MaterialTheme.typography.bodySmall,
                color = TextOnDarkSecondary,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(48.dp))

            // Prototype label
            Text(
                text = "Prototype interface for demonstration purposes",
                style = MaterialTheme.typography.labelSmall,
                color = TextOnDarkSecondary.copy(alpha = 0.7f),
                textAlign = TextAlign.Center
            )
        }
    }
}

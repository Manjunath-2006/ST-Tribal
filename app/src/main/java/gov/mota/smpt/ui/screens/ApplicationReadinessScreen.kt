package gov.mota.smpt.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import gov.mota.smpt.ui.components.*
import gov.mota.smpt.ui.theme.*
import gov.mota.smpt.viewmodel.SmptViewModel

@Composable
fun ApplicationReadinessScreen(
    applicationId: String,
    viewModel: SmptViewModel,
    onBackClick: () -> Unit,
    onSubmit: () -> Unit
) {
    val checks = remember { viewModel.getReadinessChecks(applicationId) }
    val allComplete = checks.all { it.complete }
    val incompleteCount = checks.count { !it.complete }

    Scaffold(
        topBar = {
            GovernmentTopBar(title = "Application Readiness", onBackClick = onBackClick)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Text(
                text = "APPLICATION READINESS",
                style = MaterialTheme.typography.titleLarge,
                color = GovNavy,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(4.dp))
            PrototypeLabel()

            Spacer(Modifier.height(20.dp))

            // Result summary
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (allComplete) StatusGreenBg else StatusAmberBg
                )
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        if (allComplete) Icons.Filled.CheckCircle else Icons.Filled.Warning,
                        contentDescription = null,
                        tint = if (allComplete) StatusGreen else StatusAmber,
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(Modifier.width(14.dp))
                    Column {
                        Text(
                            text = if (allComplete) "Your application is ready to submit."
                            else "$incompleteCount actions required before submission.",
                            style = MaterialTheme.typography.titleSmall,
                            color = TextPrimary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            // Check list
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = CardBackground),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    checks.forEachIndexed { index, check ->
                        ReadinessCheckRow(check)
                        if (index < checks.size - 1) {
                            HorizontalDivider(color = SurfaceDivider.copy(alpha = 0.5f))
                        }
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            if (allComplete) {
                PrimaryButton(
                    text = "Submit Application",
                    onClick = onSubmit,
                    icon = Icons.Filled.Send
                )
            } else {
                InfoBanner(
                    text = "Please complete all required actions before submitting your application.",
                    type = BannerType.WARNING
                )
            }

            Spacer(Modifier.height(16.dp))
        }
    }
}

package gov.mota.smpt.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import gov.mota.smpt.ui.components.*
import gov.mota.smpt.ui.theme.*
import gov.mota.smpt.viewmodel.SmptViewModel

@Composable
fun ConflictCheckScreen(
    targetSchemeId: String,
    viewModel: SmptViewModel,
    onBackClick: () -> Unit,
    onProceedToReadiness: () -> Unit
) {
    val conflict = remember { viewModel.checkConflict(targetSchemeId) }
    var checkRun by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            GovernmentTopBar(title = "Existing Scholarship Check", onBackClick = onBackClick)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            PrototypeLabel()
            Spacer(Modifier.height(16.dp))

            // Current vs Selected
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceLight)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Current scholarship",
                        style = MaterialTheme.typography.labelMedium,
                        color = TextSecondary
                    )
                    Text(
                        text = conflict.currentSchemeName,
                        style = MaterialTheme.typography.titleMedium,
                        color = GovNavy,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(16.dp))
                    HorizontalDivider(color = SurfaceDivider)
                    Spacer(Modifier.height(16.dp))
                    Text(
                        text = "Selected scholarship",
                        style = MaterialTheme.typography.labelMedium,
                        color = TextSecondary
                    )
                    Text(
                        text = conflict.targetSchemeName,
                        style = MaterialTheme.typography.titleMedium,
                        color = GovNavy,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            // Warning
            InfoBanner(
                text = "Your current scholarship status may affect your new application. Let's check before you continue.",
                type = BannerType.WARNING
            )

            Spacer(Modifier.height(24.dp))

            if (!checkRun) {
                PrimaryButton(
                    text = "Run Conflict Check",
                    onClick = { checkRun = true },
                    icon = Icons.Filled.CompareArrows
                )
            } else {
                // Result
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = CardBackground),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Filled.Warning,
                                contentDescription = null,
                                tint = StatusAmber,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(Modifier.width(10.dp))
                            Text(
                                text = conflict.resultStatus,
                                style = MaterialTheme.typography.titleMedium,
                                color = StatusAmber,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(Modifier.height(12.dp))
                        Text(
                            text = conflict.explanation,
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextPrimary
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = conflict.recommendation,
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondary
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))

                // Exception-based verification flow
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceLight)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Verification Flow",
                            style = MaterialTheme.typography.titleSmall,
                            color = TextPrimary
                        )
                        Spacer(Modifier.height(8.dp))

                        val flowSteps = listOf(
                            "Government / Institutional Source" to Icons.Filled.AccountBalance,
                            "Automatic Matching" to Icons.Filled.Sync,
                            "Match Found?" to Icons.Filled.Help,
                            "Needs Review → Manual Review" to Icons.Filled.RateReview
                        )
                        flowSteps.forEachIndexed { index, (step, icon) ->
                            Row(
                                modifier = Modifier.padding(vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(icon, contentDescription = null, tint = GovBlue, modifier = Modifier.size(18.dp))
                                Spacer(Modifier.width(10.dp))
                                Text(text = step, style = MaterialTheme.typography.bodySmall, color = TextPrimary)
                            }
                            if (index < flowSteps.size - 1) {
                                Text("    ↓", color = TextTertiary, style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                }

                Spacer(Modifier.height(24.dp))

                SecondaryButton(
                    text = "Proceed with Application",
                    onClick = onProceedToReadiness
                )
            }

            Spacer(Modifier.height(16.dp))
        }
    }
}

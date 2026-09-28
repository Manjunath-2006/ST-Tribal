package gov.mota.smpt.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import gov.mota.smpt.data.model.StageStatus
import gov.mota.smpt.ui.components.*
import gov.mota.smpt.ui.theme.*
import gov.mota.smpt.viewmodel.SmptViewModel

@Composable
fun ApplicationDetailScreen(
    applicationId: String,
    viewModel: SmptViewModel,
    onBackClick: () -> Unit,
    onNavigateToDocuments: () -> Unit,
    onNavigateToJago: () -> Unit,
    onNavigateToReadiness: (String) -> Unit
) {
    val application = viewModel.getApplicationById(applicationId)

    Scaffold(
        topBar = {
            GovernmentTopBar(title = "Application Details", onBackClick = onBackClick)
        }
    ) { padding ->
        if (application == null) {
            ErrorState(title = "Application not found", onRetry = onBackClick)
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Header
            Text(
                text = application.schemeName,
                style = MaterialTheme.typography.headlineSmall,
                color = GovNavy
            )
            Spacer(Modifier.height(4.dp))
            InfoRow("Application ID", application.id)
            InfoRow("Submitted", application.applicationDate)
            Spacer(Modifier.height(4.dp))
            PrototypeLabel()

            Spacer(Modifier.height(16.dp))

            // Status summary card using STATUS → ACTION → EXPLANATION pattern
            val currentStage = application.stages.find { it.status == StageStatus.IN_PROGRESS }
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = StatusBlueBg.copy(alpha = 0.3f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // STATUS
                    Text(
                        text = "STATUS",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextTertiary
                    )
                    Text(
                        text = currentStage?.title ?: "Processing",
                        style = MaterialTheme.typography.titleMedium,
                        color = GovNavy,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(10.dp))

                    // ACTION
                    Text(
                        text = "ACTION",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextTertiary
                    )
                    Text(
                        text = application.nextAction,
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextPrimary
                    )
                    Spacer(Modifier.height(10.dp))

                    // EXPLANATION
                    Text(
                        text = "EXPLANATION",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextTertiary
                    )
                    Text(
                        text = currentStage?.description ?: "Your application is being processed.",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary
                    )
                }
            }

            Spacer(Modifier.height(20.dp))

            // Application Timeline
            SectionHeader(title = "Application Journey")
            Spacer(Modifier.height(12.dp))
            ApplicationTimeline(stages = application.stages)

            Spacer(Modifier.height(20.dp))

            // Deficiencies
            if (application.deficiencies.any { !it.resolved }) {
                SectionHeader(title = "Action Required")
                Spacer(Modifier.height(8.dp))
                application.deficiencies.filter { !it.resolved }.forEach { deficiency ->
                    ActionRequiredCard(
                        deficiency = deficiency,
                        onResolveClick = onNavigateToDocuments,
                        onAskJago = onNavigateToJago,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
                Spacer(Modifier.height(16.dp))
            }

            // Stage details
            SectionHeader(title = "Stage Details")
            Spacer(Modifier.height(8.dp))
            application.stages.forEach { stage ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceLight)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stage.title,
                                style = MaterialTheme.typography.titleSmall,
                                color = TextPrimary
                            )
                            StageStatusBadge(stage.status)
                        }
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = "What is happening? ${stage.description}",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondary
                        )
                        Text(
                            text = "Do I need to do anything? ${if (stage.actionRequired) "Yes" else "No action required"}",
                            style = MaterialTheme.typography.bodySmall,
                            color = if (stage.actionRequired) StatusAmber else TextSecondary
                        )
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            // Buttons
            SecondaryButton(
                text = "Application Readiness",
                onClick = { onNavigateToReadiness(applicationId) },
                icon = Icons.Filled.Checklist
            )

            Spacer(Modifier.height(16.dp))
        }
    }
}

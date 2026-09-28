package gov.mota.smpt.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import gov.mota.smpt.ui.components.*
import gov.mota.smpt.ui.theme.*
import gov.mota.smpt.viewmodel.SmptViewModel

@Composable
fun ScholarshipDetailScreen(
    schemeId: String,
    viewModel: SmptViewModel,
    onBackClick: () -> Unit,
    onCheckEligibility: (String) -> Unit,
    onConflictCheck: (String) -> Unit,
    onTrackApplication: (String) -> Unit
) {
    val scheme = viewModel.getSchemeById(schemeId)
    val existingApp = viewModel.applications.value.find { it.schemeId == schemeId }

    Scaffold(
        topBar = {
            GovernmentTopBar(title = scheme?.name ?: "Scholarship", onBackClick = onBackClick)
        }
    ) { padding ->
        if (scheme == null) {
            ErrorState(title = "Scheme not found", onRetry = onBackClick)
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Scheme title
            Text(
                text = scheme.name,
                style = MaterialTheme.typography.headlineSmall,
                color = GovNavy
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = scheme.intendedGroup,
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary
            )
            Spacer(Modifier.height(8.dp))
            PrototypeLabel()

            Spacer(Modifier.height(16.dp))

            // Overview
            DetailSection("Overview", scheme.overview)

            // Who can apply
            DetailSection("Who can apply?", scheme.intendedGroup)

            // Eligibility
            SectionHeader(title = "Eligibility")
            Spacer(Modifier.height(8.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceLight)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    scheme.eligibilityCriteria.forEach { criterion ->
                        Row(modifier = Modifier.padding(vertical = 4.dp)) {
                            Text("•  ", color = TextSecondary)
                            Text(
                                text = criterion,
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextPrimary
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // Documents Required
            SectionHeader(title = "Documents Required")
            Spacer(Modifier.height(8.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceLight)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    scheme.requiredDocuments.forEach { doc ->
                        Row(modifier = Modifier.padding(vertical = 3.dp)) {
                            Icon(
                                Icons.Filled.Description,
                                contentDescription = null,
                                tint = GovBlue,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(
                                text = doc,
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextPrimary
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // Application Process
            DetailSection("Application Process", scheme.applicationProcess)

            // Verification
            SectionHeader(title = "Verification")
            Spacer(Modifier.height(8.dp))
            scheme.verificationSteps.forEachIndexed { index, step ->
                Row(modifier = Modifier.padding(vertical = 4.dp)) {
                    Text(
                        text = "${index + 1}.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary,
                        modifier = Modifier.width(24.dp)
                    )
                    Text(
                        text = step,
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextPrimary
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            // Sanction
            DetailSection("Sanction", scheme.sanctionInfo)

            // Disbursement
            DetailSection("Disbursement", scheme.disbursementInfo)

            // Additional sections
            scheme.sections.forEach { section ->
                DetailSection(section.title, section.content)
            }

            Spacer(Modifier.height(24.dp))

            // Buttons
            if (existingApp != null) {
                PrimaryButton(
                    text = "Track Application",
                    onClick = { onTrackApplication(existingApp.id) },
                    icon = Icons.Filled.Timeline
                )
            } else {
                PrimaryButton(
                    text = "Check Eligibility",
                    onClick = { onCheckEligibility(schemeId) },
                    icon = Icons.Filled.CheckCircle
                )
                Spacer(Modifier.height(10.dp))
                SecondaryButton(
                    text = "Check Existing Scholarship",
                    onClick = { onConflictCheck(schemeId) },
                    icon = Icons.Filled.CompareArrows
                )
            }

            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
private fun DetailSection(title: String, content: String) {
    SectionHeader(title = title)
    Spacer(Modifier.height(6.dp))
    Text(
        text = content,
        style = MaterialTheme.typography.bodyMedium,
        color = TextPrimary
    )
    Spacer(Modifier.height(16.dp))
}

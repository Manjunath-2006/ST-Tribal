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
fun EligibilityCheckScreen(
    schemeId: String,
    viewModel: SmptViewModel,
    onBackClick: () -> Unit,
    onConflictCheck: (String) -> Unit,
    onApply: () -> Unit
) {
    val result = remember { viewModel.checkEligibility(schemeId) }
    var showResult by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            GovernmentTopBar(title = "Eligibility Check", onBackClick = onBackClick)
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
                text = result.schemeName,
                style = MaterialTheme.typography.headlineSmall,
                color = GovNavy
            )
            Spacer(Modifier.height(4.dp))
            PrototypeLabel()

            Spacer(Modifier.height(20.dp))

            if (!showResult) {
                // Pre-check state
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceLight)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            Icons.Filled.FactCheck,
                            contentDescription = null,
                            tint = GovBlue,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(Modifier.height(16.dp))
                        Text(
                            text = "Check your eligibility",
                            style = MaterialTheme.typography.titleMedium,
                            color = TextPrimary
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = "SMPT will check your profile information against the eligibility criteria for this scholarship.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextSecondary,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                }
                Spacer(Modifier.height(24.dp))
                PrimaryButton(
                    text = "Check Eligibility",
                    onClick = { showResult = true },
                    icon = Icons.Filled.CheckCircle
                )
            } else {
                // Result
                SectionHeader(title = "Why you may be eligible")
                Spacer(Modifier.height(12.dp))

                // Verified criteria
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = CardBackground),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        result.verifiedCriteria.forEach { criterion ->
                            EligibilityCriterionRow(criterion)
                        }
                    }
                }

                // Pending criteria
                if (result.pendingCriteria.isNotEmpty()) {
                    Spacer(Modifier.height(16.dp))
                    SectionHeader(title = "Still to verify")
                    Spacer(Modifier.height(8.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = CardBackground),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            result.pendingCriteria.forEach { criterion ->
                                EligibilityCriterionRow(criterion)
                            }
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))

                // Explanation
                InfoBanner(
                    text = result.explanation,
                    type = if (result.overallEligible) BannerType.SUCCESS else BannerType.INFO
                )

                Spacer(Modifier.height(8.dp))
                InfoBanner(
                    text = "Eligibility is subject to verification.",
                    type = BannerType.PROTOTYPE
                )

                Spacer(Modifier.height(24.dp))

                if (result.overallEligible) {
                    SecondaryButton(
                        text = "Check Existing Scholarship",
                        onClick = { onConflictCheck(schemeId) },
                        icon = Icons.Filled.CompareArrows
                    )
                    Spacer(Modifier.height(10.dp))
                    PrimaryButton(
                        text = "Proceed to Apply",
                        onClick = onApply,
                        icon = Icons.Filled.ArrowForward
                    )
                }
            }

            Spacer(Modifier.height(16.dp))
        }
    }
}

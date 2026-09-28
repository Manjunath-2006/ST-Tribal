package gov.mota.smpt.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import gov.mota.smpt.ui.components.*
import gov.mota.smpt.ui.theme.*
import gov.mota.smpt.viewmodel.SmptViewModel

@Composable
fun UnifiedVerificationScreen(
    viewModel: SmptViewModel,
    onBackClick: () -> Unit
) {
    val verificationItems by viewModel.verificationItems.collectAsState()

    Scaffold(
        topBar = {
            GovernmentTopBar(title = "Unified Verification", onBackClick = onBackClick)
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                Text(
                    text = "See the verification status of your scholarship information in one place.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )
                Spacer(Modifier.height(4.dp))
                InfoBanner(
                    text = "Prototype Verification — All source statuses are mock data.",
                    type = BannerType.PROTOTYPE
                )
            }

            items(verificationItems) { item ->
                VerificationCard(item = item)
            }

            // Exception-based verification explanation
            item {
                Spacer(Modifier.height(8.dp))
                SectionHeader(title = "How Verification Works")
                Spacer(Modifier.height(8.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceLight)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        val steps = listOf(
                            "Government / Institutional Source",
                            "↓  Automatic Matching",
                            "↓  Match Found?",
                            "  YES → Verified",
                            "  NO  → Needs Review → Manual Review"
                        )
                        steps.forEach { step ->
                            Text(
                                text = step,
                                style = MaterialTheme.typography.bodySmall,
                                color = TextPrimary,
                                modifier = Modifier.padding(vertical = 2.dp)
                            )
                        }
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = "Automatic verification does not always block the student. When a match cannot be found, the case is escalated for manual review.",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondary
                        )
                    }
                }
            }
        }
    }
}

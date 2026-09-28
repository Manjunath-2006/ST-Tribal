package gov.mota.smpt.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import gov.mota.smpt.data.model.StageStatus
import gov.mota.smpt.ui.components.*
import gov.mota.smpt.ui.theme.*
import gov.mota.smpt.viewmodel.SmptViewModel

@Composable
fun ApplicationsScreen(
    viewModel: SmptViewModel,
    onApplicationClick: (String) -> Unit,
    onExploreScholarships: () -> Unit,
    onBackClick: (() -> Unit)? = null
) {
    val applications by viewModel.applications.collectAsState()

    Scaffold(
        topBar = {
            GovernmentTopBar(title = "Applications", onBackClick = onBackClick)
        }
    ) { padding ->
        if (applications.isEmpty()) {
            EmptyState(
                icon = Icons.Filled.Assignment,
                title = "No active applications",
                description = "You haven't submitted a scholarship application yet.",
                buttonText = "Explore Scholarships",
                onButtonClick = onExploreScholarships
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item { PrototypeLabel() }

                items(applications) { app ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onApplicationClick(app.id) },
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = CardBackground),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = app.schemeName,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = GovNavy,
                                    modifier = Modifier.weight(1f)
                                )
                                val currentStatus = app.stages.find { it.status == StageStatus.IN_PROGRESS }?.status
                                    ?: StageStatus.PENDING
                                StageStatusBadge(currentStatus)
                            }
                            Spacer(Modifier.height(8.dp))
                            InfoRow("Application ID", app.id)
                            InfoRow("Submitted", app.applicationDate)
                            InfoRow("Current Stage", app.stages.find { it.status == StageStatus.IN_PROGRESS }?.title ?: "Submitted")
                            InfoRow("Next Action", app.nextAction)

                            if (app.deficiencies.any { !it.resolved }) {
                                Spacer(Modifier.height(8.dp))
                                Surface(
                                    shape = RoundedCornerShape(4.dp),
                                    color = StatusAmberBg
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            Icons.Filled.ErrorOutline,
                                            contentDescription = null,
                                            tint = StatusAmber,
                                            modifier = Modifier.size(14.dp)
                                        )
                                        Spacer(Modifier.width(4.dp))
                                        Text(
                                            text = "${app.deficiencies.count { !it.resolved }} deficiency",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = StatusAmber,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    }
                                }
                            }

                            Spacer(Modifier.height(8.dp))
                            Text(
                                text = "Track Application →",
                                style = MaterialTheme.typography.labelLarge,
                                color = GovBlue
                            )
                        }
                    }
                }
            }
        }
    }
}

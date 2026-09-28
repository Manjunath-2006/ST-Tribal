package gov.mota.smpt.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import gov.mota.smpt.ui.components.*
import gov.mota.smpt.ui.theme.*
import gov.mota.smpt.viewmodel.SmptViewModel
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: SmptViewModel,
    onNavigateToScholarships: () -> Unit,
    onNavigateToApplicationDetail: (String) -> Unit,
    onNavigateToNotifications: () -> Unit,
    onNavigateToJago: () -> Unit,
    onNavigateToDocuments: () -> Unit,
    onNavigateToUnifiedVerification: () -> Unit,
    onNavigateToScholarshipAwareness: () -> Unit
) {
    val student by viewModel.student.collectAsState()
    val applications by viewModel.applications.collectAsState()
    val isOffline by viewModel.isOffline.collectAsState()
    val deficiencies = viewModel.getDeficiencies()
    val proactiveAlerts by viewModel.proactiveAlerts.collectAsState()

    val greeting = when (Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) {
        in 5..11 -> "Good morning"
        in 12..16 -> "Good afternoon"
        else -> "Good evening"
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "SMPT",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onNavigateToNotifications) {
                        BadgedBox(
                            badge = {
                                Badge(containerColor = StatusRed) {
                                    Text("3", color = TextOnDark, style = MaterialTheme.typography.labelSmall)
                                }
                            }
                        ) {
                            Icon(Icons.Filled.Notifications, contentDescription = "Notifications")
                        }
                    }
                    IconButton(onClick = onNavigateToJago) {
                        Icon(Icons.Filled.Assistant, contentDescription = "JAGO")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = GovNavy,
                    titleContentColor = TextOnDark,
                    actionIconContentColor = TextOnDark
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(SurfaceLight)
                .verticalScroll(rememberScrollState())
        ) {
            // Offline banner
            if (isOffline) {
                InfoBanner(
                    text = "You're offline. Showing your last available scholarship information.\nLast updated: 28 Sep 2026, 5:30 PM",
                    type = BannerType.WARNING,
                    modifier = Modifier.padding(16.dp, 12.dp, 16.dp, 0.dp)
                )
            }

            // Greeting
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "$greeting, ${student.name}",
                    style = MaterialTheme.typography.headlineSmall,
                    color = TextPrimary
                )
                Text(
                    text = "Your Scholarship Overview",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )
            }

            // Summary stats
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                SummaryStatCard(
                    label = "Active\nScholarship",
                    value = "1",
                    icon = Icons.Filled.School,
                    modifier = Modifier.weight(1f)
                )
                SummaryStatCard(
                    label = "Applications",
                    value = "${applications.size}",
                    icon = Icons.Filled.Assignment,
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                SummaryStatCard(
                    label = "Action\nRequired",
                    value = "${deficiencies.size}",
                    icon = Icons.Filled.ErrorOutline,
                    modifier = Modifier.weight(1f),
                    valueColor = if (deficiencies.isNotEmpty()) StatusAmber else StatusGreen
                )
                SummaryStatCard(
                    label = "Payments\nReceived",
                    value = "₹${"%,d".format(viewModel.totalReceived)}",
                    icon = Icons.Filled.AccountBalance,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(Modifier.height(20.dp))

            // Current Scholarship
            val activeApp = viewModel.getActiveApplication()
            if (activeApp != null) {
                SectionHeader(
                    title = "Current Scholarship",
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(Modifier.height(8.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .clickable { onNavigateToApplicationDetail(activeApp.id) },
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
                                text = activeApp.schemeName,
                                style = MaterialTheme.typography.titleMedium,
                                color = GovNavy,
                                modifier = Modifier.weight(1f)
                            )
                            StageStatusBadge(
                                activeApp.stages.find { it.status == gov.mota.smpt.data.model.StageStatus.IN_PROGRESS }?.status
                                    ?: gov.mota.smpt.data.model.StageStatus.IN_PROGRESS
                            )
                        }
                        Spacer(Modifier.height(10.dp))
                        HorizontalDivider(color = SurfaceDivider)
                        Spacer(Modifier.height(10.dp))

                        InfoRow("Status", "Verification in Progress")
                        InfoRow(
                            "Current Stage",
                            activeApp.stages.find { it.status == gov.mota.smpt.data.model.StageStatus.IN_PROGRESS }?.title
                                ?: "Processing"
                        )
                        InfoRow("Next Action", activeApp.nextAction)

                        Spacer(Modifier.height(12.dp))
                        Text(
                            text = "Track Application →",
                            style = MaterialTheme.typography.labelLarge,
                            color = GovBlue
                        )
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            // Action Required
            if (deficiencies.isNotEmpty()) {
                SectionHeader(
                    title = "Action Required",
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(Modifier.height(8.dp))
                deficiencies.forEach { deficiency ->
                    ActionRequiredCard(
                        deficiency = deficiency,
                        onResolveClick = { onNavigateToDocuments() },
                        onAskJago = { onNavigateToJago() },
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                    )
                }
            } else {
                SectionHeader(
                    title = "Action Required",
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(Modifier.height(8.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = StatusGreenBg)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Filled.CheckCircle, contentDescription = null, tint = StatusGreen)
                        Spacer(Modifier.width(12.dp))
                        Column {
                            Text("No action required", style = MaterialTheme.typography.titleSmall, color = StatusGreen)
                            Text("Your applications are progressing normally.", style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                        }
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            // JAGO Proactive Alerts
            if (proactiveAlerts.isNotEmpty()) {
                SectionHeader(
                    title = "JAGO Updates",
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(Modifier.height(8.dp))
                proactiveAlerts.take(2).forEach { alert ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                            .clickable { onNavigateToJago() },
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = StatusBlueBg.copy(alpha = 0.5f))
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Filled.Assistant,
                                contentDescription = null,
                                tint = GovBlue,
                                modifier = Modifier.size(22.dp)
                            )
                            Spacer(Modifier.width(12.dp))
                            Text(
                                text = alert.message,
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextPrimary,
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = alert.actionLabel + " →",
                                style = MaterialTheme.typography.labelSmall,
                                color = GovBlue
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            // Quick Actions
            SectionHeader(
                title = "Quick Actions",
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                QuickActionCard("Find\nScholarship", Icons.Filled.Search, onNavigateToScholarships, Modifier.weight(1f))
                QuickActionCard("My\nDocuments", Icons.Filled.Description, onNavigateToDocuments, Modifier.weight(1f))
                QuickActionCard("Verification\nStatus", Icons.Filled.VerifiedUser, onNavigateToUnifiedVerification, Modifier.weight(1f))
                QuickActionCard("Ask\nJAGO", Icons.Filled.Assistant, onNavigateToJago, Modifier.weight(1f))
            }

            Spacer(Modifier.height(20.dp))

            // Scholarship Awareness banner
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clickable { onNavigateToScholarshipAwareness() },
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = AccentSaffronLight)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Filled.Lightbulb,
                        contentDescription = null,
                        tint = AccentSaffron,
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "You may be eligible for more",
                            style = MaterialTheme.typography.titleSmall,
                            color = TextPrimary
                        )
                        Text(
                            text = "Check if additional scholarship schemes are available for you.",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondary
                        )
                    }
                    Text("→", color = AccentSaffron, style = MaterialTheme.typography.titleLarge)
                }
            }

            Spacer(Modifier.height(16.dp))

            // Prototype label
            PrototypeLabel(modifier = Modifier.padding(horizontal = 16.dp))

            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun QuickActionCard(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(icon, contentDescription = null, tint = GovBlue, modifier = Modifier.size(26.dp))
            Spacer(Modifier.height(6.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = TextPrimary,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}

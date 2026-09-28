package gov.mota.smpt.ui.screens

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import gov.mota.smpt.ui.components.*
import gov.mota.smpt.ui.theme.*
import gov.mota.smpt.viewmodel.SmptViewModel

@Composable
fun MinistryInsightsScreen(
    viewModel: SmptViewModel,
    onBackClick: () -> Unit
) {
    val insights by viewModel.ministryInsights.collectAsState()

    Scaffold(
        topBar = {
            GovernmentTopBar(title = "Ministry Insights", onBackClick = onBackClick)
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
                InfoBanner(
                    text = "Prototype / Mock Data — These values are fictional and for demonstration purposes only. Do NOT present as official Government statistics.",
                    type = BannerType.PROTOTYPE
                )
            }

            item {
                Text(
                    text = "Ministry of Tribal Affairs",
                    style = MaterialTheme.typography.titleMedium,
                    color = GovNavy
                )
                Text(
                    text = "Scholarship Overview (Prototype)",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )
            }

            items(insights.chunked(2)) { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    row.forEach { insight ->
                        Card(
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = CardBackground),
                            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(14.dp),
                                horizontalAlignment = Alignment.Start
                            ) {
                                Text(
                                    text = insight.value,
                                    style = MaterialTheme.typography.headlineSmall,
                                    color = GovNavy,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(Modifier.height(4.dp))
                                Text(
                                    text = insight.label,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = TextSecondary
                                )
                            }
                        }
                    }
                    if (row.size == 1) Spacer(Modifier.weight(1f))
                }
            }

            item {
                Spacer(Modifier.height(8.dp))
                PrototypeLabel()
            }
        }
    }
}

@Composable
fun ScholarshipAwarenessScreen(
    viewModel: SmptViewModel,
    onBackClick: () -> Unit,
    onCheckScholarships: () -> Unit
) {
    val awareness = viewModel.awarenessInfo

    Scaffold(
        topBar = {
            GovernmentTopBar(title = "Scholarship Awareness", onBackClick = onBackClick)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            PrototypeLabel()

            Spacer(Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = AccentSaffronLight)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        Icons.Filled.Lightbulb,
                        contentDescription = null,
                        tint = AccentSaffron,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(Modifier.height(12.dp))
                    Text(
                        text = awareness.message,
                        style = MaterialTheme.typography.titleMedium,
                        color = TextPrimary,
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = awareness.detail,
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(Modifier.height(20.dp))

            SectionHeader(title = "Matching Sources")
            Spacer(Modifier.height(8.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceLight)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Conceptual matching of:",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary
                    )
                    Spacer(Modifier.height(8.dp))
                    awareness.matchingSources.forEach { source ->
                        Row(
                            modifier = Modifier.padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Filled.CheckCircle,
                                contentDescription = null,
                                tint = StatusGreen,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(Modifier.width(10.dp))
                            Text(
                                text = source,
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextPrimary
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            PrimaryButton(
                text = "Check Scholarships",
                onClick = onCheckScholarships,
                icon = Icons.Filled.Search
            )

            Spacer(Modifier.height(12.dp))

            InfoBanner(
                text = "This demonstrates proactive outreach to potentially unreached beneficiaries using data from UDISE+, APAAR, OTR and scholarship registration systems.",
                type = BannerType.INFO
            )
        }
    }
}

@Composable
fun ExistingSystemsScreen(
    viewModel: SmptViewModel,
    onBackClick: () -> Unit
) {
    val systems by viewModel.existingSystems.collectAsState()

    Scaffold(
        topBar = {
            GovernmentTopBar(title = "Existing Scholarship Systems", onBackClick = onBackClick)
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
                    text = "SMPT conceptually integrates with these existing government scholarship systems.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )
                Spacer(Modifier.height(4.dp))
                PrototypeLabel()
            }

            items(systems) { system ->
                ExistingSystemCard(system = system)
            }

            item {
                InfoBanner(
                    text = "This frontend uses mock integration data. No real live APIs are connected.",
                    type = BannerType.PROTOTYPE
                )
            }
        }
    }
}

@Composable
fun VerificationSourcesScreen(
    viewModel: SmptViewModel,
    onBackClick: () -> Unit
) {
    val sources by viewModel.govDataSources.collectAsState()

    Scaffold(
        topBar = {
            GovernmentTopBar(title = "Verification Sources", onBackClick = onBackClick)
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
                    text = "Government & Institutional Verification Sources",
                    style = MaterialTheme.typography.titleMedium,
                    color = GovNavy
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "These are the government and institutional data sources used for verification.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )
                Spacer(Modifier.height(4.dp))
                PrototypeLabel()
            }

            items(sources) { source ->
                GovernmentSourceCard(source = source)
            }

            item {
                InfoBanner(
                    text = "All verification data shown is mock/prototype data. No real government APIs are connected.",
                    type = BannerType.PROTOTYPE
                )
            }
        }
    }
}

@Composable
fun HelpScreen(onBackClick: () -> Unit) {
    Scaffold(
        topBar = {
            GovernmentTopBar(title = "Help & Support", onBackClick = onBackClick)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
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
                        Icons.Filled.Help,
                        contentDescription = null,
                        tint = GovBlue,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(Modifier.height(12.dp))
                    Text(
                        text = "Need Help?",
                        style = MaterialTheme.typography.titleLarge,
                        color = TextPrimary
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = "SMPT is a prototype application for the Smart India Hackathon 2026.\n\nFor actual scholarship queries, please visit the National Scholarship Portal (NSP) or contact the Ministry of Tribal Affairs.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            listOf(
                "How do I apply for a scholarship?" to "Go to Scholarships → Select a scheme → Check Eligibility → Apply",
                "How do I track my application?" to "Go to Applications → Select your application → View timeline",
                "How do I update my documents?" to "Go to Profile → My Documents → Upload or update",
                "How do I contact JAGO?" to "Tap the JAGO icon on the home screen or navigate to JAGO from the menu"
            ).forEach { (q, a) ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(containerColor = CardBackground)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(q, style = MaterialTheme.typography.titleSmall, color = TextPrimary)
                        Spacer(Modifier.height(4.dp))
                        Text(a, style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                    }
                }
            }

            Spacer(Modifier.height(16.dp))
            PrototypeLabel()
        }
    }
}

package gov.mota.smpt.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import gov.mota.smpt.data.model.VerificationStatus
import gov.mota.smpt.ui.components.*
import gov.mota.smpt.ui.theme.*
import gov.mota.smpt.viewmodel.SmptViewModel

@Composable
fun DocumentsScreen(
    viewModel: SmptViewModel,
    onBackClick: () -> Unit
) {
    val documents by viewModel.documents.collectAsState()
    val categories = documents.map { it.category }.distinct()
    var selectedCategory by remember { mutableStateOf<String?>(null) }
    var showDigiLocker by remember { mutableStateOf(false) }

    val filteredDocs = if (selectedCategory == null) documents
    else documents.filter { it.category == selectedCategory }

    Scaffold(
        topBar = {
            GovernmentTopBar(title = "My Documents", onBackClick = onBackClick)
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // DigiLocker Connect
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = StatusBlueBg.copy(alpha = 0.3f)),
                    onClick = { showDigiLocker = !showDigiLocker }
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Filled.CloudDone, contentDescription = null, tint = GovBlue)
                            Spacer(Modifier.width(10.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "DigiLocker Connected",
                                    style = MaterialTheme.typography.titleSmall,
                                    color = GovNavy
                                )
                                Text(
                                    text = "Documents imported and verified",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = TextSecondary
                                )
                            }
                            StatusBadge(VerificationStatus.VERIFIED)
                        }

                        if (showDigiLocker) {
                            Spacer(Modifier.height(12.dp))
                            HorizontalDivider(color = SurfaceDivider)
                            Spacer(Modifier.height(12.dp))

                            Text(
                                text = "Documents Available from DigiLocker",
                                style = MaterialTheme.typography.labelMedium,
                                color = TextSecondary
                            )
                            Spacer(Modifier.height(8.dp))
                            listOf(
                                "ST Certificate" to "✓ Verified",
                                "Academic Certificate" to "✓ Available",
                                "Income Certificate" to "✓ Available"
                            ).forEach { (doc, status) ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(doc, style = MaterialTheme.typography.bodyMedium, color = TextPrimary)
                                    Text(status, style = MaterialTheme.typography.bodySmall, color = StatusGreen)
                                }
                            }

                            Spacer(Modifier.height(8.dp))
                            InfoBanner(
                                text = "Prototype / Mock Integration",
                                type = BannerType.PROTOTYPE
                            )
                        }
                    }
                }
            }

            // Category filter
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilterChip(
                        selected = selectedCategory == null,
                        onClick = { selectedCategory = null },
                        label = { Text("All") }
                    )
                    categories.take(4).forEach { cat ->
                        FilterChip(
                            selected = selectedCategory == cat,
                            onClick = { selectedCategory = if (selectedCategory == cat) null else cat },
                            label = { Text(cat) }
                        )
                    }
                }
            }

            item {
                PrototypeLabel()
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "Reuse verified documents across scholarship applications",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )
            }

            // Document cards
            items(filteredDocs) { document ->
                DocumentCard(
                    document = document,
                    onClick = {}
                )
            }

            item {
                Spacer(Modifier.height(8.dp))
                InfoBanner(
                    text = "Verified documents can be reused across multiple scholarship applications.",
                    type = BannerType.INFO
                )
            }
        }
    }
}

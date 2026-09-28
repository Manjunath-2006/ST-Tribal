package gov.mota.smpt.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import gov.mota.smpt.ui.components.*
import gov.mota.smpt.ui.theme.*
import gov.mota.smpt.viewmodel.SmptViewModel

@Composable
fun ScholarshipsScreen(
    viewModel: SmptViewModel,
    onSchemeClick: (String) -> Unit,
    onBackClick: (() -> Unit)? = null
) {
    val schemes by viewModel.schemes.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    val filteredSchemes = if (searchQuery.isBlank()) schemes
    else schemes.filter {
        it.name.contains(searchQuery, ignoreCase = true) ||
        it.shortDescription.contains(searchQuery, ignoreCase = true)
    }

    Scaffold(
        topBar = {
            GovernmentTopBar(title = "Find a Scholarship", onBackClick = onBackClick)
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Search
            item {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Search scholarship") },
                    leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = GovBlue,
                        unfocusedBorderColor = SurfaceDivider
                    )
                )
            }

            item {
                PrototypeLabel()
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "Ministry of Tribal Affairs — All Scholarship Schemes",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )
            }

            // Scheme cards
            items(filteredSchemes) { scheme ->
                ScholarshipCard(
                    scheme = scheme,
                    onClick = { onSchemeClick(scheme.id) }
                )
            }

            item {
                Spacer(Modifier.height(8.dp))
                InfoBanner(
                    text = "Showing all five MoTA scholarship/fellowship schemes. Eligibility is subject to verification.",
                    type = BannerType.INFO
                )
            }
        }
    }
}

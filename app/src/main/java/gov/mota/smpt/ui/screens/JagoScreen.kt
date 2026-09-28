package gov.mota.smpt.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
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
import kotlinx.coroutines.launch

@Composable
fun JagoScreen(
    viewModel: SmptViewModel,
    onBackClick: () -> Unit,
    onNavigate: (String) -> Unit
) {
    val messages by viewModel.chatMessages.collectAsState()
    val proactiveAlerts by viewModel.proactiveAlerts.collectAsState()
    var inputText by remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            GovernmentTopBar(title = "JAGO", onBackClick = onBackClick) {
                Text(
                    text = "Your Scholarship Assistant",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextOnDarkSecondary
                )
            }
        },
        bottomBar = {
            // Input
            Surface(
                shadowElevation = 4.dp,
                color = SurfaceWhite
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = inputText,
                        onValueChange = { inputText = it },
                        modifier = Modifier.weight(1f),
                        placeholder = { Text("Ask JAGO...") },
                        shape = RoundedCornerShape(24.dp),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = GovBlue,
                            unfocusedBorderColor = SurfaceDivider
                        )
                    )
                    Spacer(Modifier.width(8.dp))
                    IconButton(
                        onClick = {
                            if (inputText.isNotBlank()) {
                                viewModel.sendJagoMessage(inputText)
                                inputText = ""
                                scope.launch {
                                    listState.animateScrollToItem(messages.size + 1)
                                }
                            }
                        }
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.Send,
                            contentDescription = "Send",
                            tint = GovNavy
                        )
                    }
                }
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            state = listState,
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Welcome
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceLight)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            Icons.Filled.Assistant,
                            contentDescription = null,
                            tint = GovNavy,
                            modifier = Modifier.size(40.dp)
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = "JAGO",
                            style = MaterialTheme.typography.titleLarge,
                            color = GovNavy,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Your Scholarship Assistant",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondary
                        )
                    }
                }
            }

            // Proactive alerts
            if (proactiveAlerts.isNotEmpty()) {
                item {
                    Text(
                        text = "Updates for you",
                        style = MaterialTheme.typography.labelMedium,
                        color = TextSecondary
                    )
                }
                items(proactiveAlerts) { alert ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = AccentSaffronLight),
                        onClick = { onNavigate(alert.actionRoute) }
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Filled.Lightbulb,
                                contentDescription = null,
                                tint = AccentSaffron,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(Modifier.width(10.dp))
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

            // Suggested questions
            item {
                Text(
                    text = "Try asking",
                    style = MaterialTheme.typography.labelMedium,
                    color = TextSecondary
                )
                Spacer(Modifier.height(4.dp))
            }

            items(viewModel.jagoSuggestions) { suggestion ->
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    color = SurfaceWhite,
                    shadowElevation = 0.5.dp,
                    onClick = {
                        viewModel.sendJagoMessage(suggestion.question)
                        scope.launch {
                            listState.animateScrollToItem(messages.size + viewModel.jagoSuggestions.size + 5)
                        }
                    }
                ) {
                    Text(
                        text = "\"${suggestion.question}\"",
                        style = MaterialTheme.typography.bodyMedium,
                        color = GovBlue,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }

            // Chat messages
            items(messages) { message ->
                JagoMessageBubble(
                    message = message,
                    onActionClick = { route -> onNavigate(route) }
                )
            }
        }
    }
}

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
import gov.mota.smpt.viewmodel.SmptViewModel

@Composable
fun NotificationsScreen(
    viewModel: SmptViewModel,
    onBackClick: () -> Unit,
    onNotificationClick: (String?) -> Unit
) {
    val notifications by viewModel.notifications.collectAsState()

    Scaffold(
        topBar = {
            GovernmentTopBar(title = "Notifications", onBackClick = onBackClick)
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item { PrototypeLabel() }

            items(notifications) { notification ->
                NotificationCard(
                    notification = notification,
                    onClick = { onNotificationClick(notification.targetRoute) }
                )
            }
        }
    }
}

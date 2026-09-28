package gov.mota.smpt.ui.screens

import androidx.compose.foundation.background
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
import gov.mota.smpt.ui.components.*
import gov.mota.smpt.ui.theme.*
import gov.mota.smpt.viewmodel.SmptViewModel

@Composable
fun PaymentsScreen(
    viewModel: SmptViewModel,
    onPaymentClick: (String) -> Unit,
    onBackClick: (() -> Unit)? = null
) {
    val payments by viewModel.payments.collectAsState()

    Scaffold(
        topBar = {
            GovernmentTopBar(title = "Scholarship Payments", onBackClick = onBackClick)
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Summary
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Card(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = StatusGreenBg)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Total Received", style = MaterialTheme.typography.labelMedium, color = TextSecondary)
                            Spacer(Modifier.height(4.dp))
                            Text(
                                "₹${"%,d".format(viewModel.totalReceived)}",
                                style = MaterialTheme.typography.headlineSmall,
                                color = StatusGreen,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Card(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = StatusBlueBg)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Pending", style = MaterialTheme.typography.labelMedium, color = TextSecondary)
                            Spacer(Modifier.height(4.dp))
                            Text(
                                "₹${"%,d".format(viewModel.totalPending)}",
                                style = MaterialTheme.typography.headlineSmall,
                                color = StatusBlue,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            item {
                PrototypeLabel()
            }

            // Payment list
            item {
                SectionHeader(title = "Payment History")
            }

            items(payments) { payment ->
                PaymentCard(
                    payment = payment,
                    onClick = { onPaymentClick(payment.id) }
                )
            }

            // DBT explanation
            item {
                Spacer(Modifier.height(8.dp))
                InfoBanner(
                    text = "Scholarship payments are transferred through Direct Benefit Transfer (DBT) to the registered bank account.",
                    type = BannerType.INFO
                )
            }
        }
    }
}

@Composable
fun PaymentDetailScreen(
    paymentId: String,
    viewModel: SmptViewModel,
    onBackClick: () -> Unit
) {
    val payment = viewModel.getPaymentById(paymentId)

    Scaffold(
        topBar = {
            GovernmentTopBar(title = "Payment Details", onBackClick = onBackClick)
        }
    ) { padding ->
        if (payment == null) {
            ErrorState(title = "Payment not found", onRetry = onBackClick)
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = CardBackground),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "₹${"%,d".format(payment.amount)}",
                            style = MaterialTheme.typography.headlineMedium,
                            color = GovNavy,
                            fontWeight = FontWeight.Bold
                        )
                        PaymentStatusBadge(payment.status)
                    }

                    Spacer(Modifier.height(16.dp))
                    HorizontalDivider(color = SurfaceDivider)
                    Spacer(Modifier.height(16.dp))

                    DetailRow("Scheme", payment.schemeName)
                    DetailRow("Amount", "₹${"%,d".format(payment.amount)}")
                    DetailRow("Date", payment.date)
                    DetailRow("Academic Year", payment.academicYear)
                    DetailRow("Transaction Reference", "****${payment.transactionRefLast4}")
                    DetailRow("Payment Status", payment.status.name)
                }
            }

            Spacer(Modifier.height(16.dp))

            // What does this status mean
            var expanded by androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf(false) }
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceLight),
                onClick = { expanded = !expanded }
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "What does this status mean?",
                            style = MaterialTheme.typography.titleSmall,
                            color = GovBlue
                        )
                        Icon(
                            if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                            contentDescription = null,
                            tint = GovBlue
                        )
                    }
                    if (expanded) {
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = when (payment.status) {
                                gov.mota.smpt.data.model.PaymentStatus.CREDITED -> "This payment has been successfully transferred to your registered bank account through Direct Benefit Transfer (DBT)."
                                gov.mota.smpt.data.model.PaymentStatus.PROCESSING -> "This payment is currently being processed by the disbursement system. It should be credited to your account within a few working days."
                                gov.mota.smpt.data.model.PaymentStatus.PENDING -> "This payment is pending and awaiting processing. It will be initiated after all necessary approvals."
                                gov.mota.smpt.data.model.PaymentStatus.FAILED -> "This payment could not be processed. Please verify your bank account details and contact support."
                            },
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondary
                        )
                    }
                }
            }

            Spacer(Modifier.height(12.dp))
            PrototypeLabel()
        }
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondary
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = TextPrimary,
            fontWeight = FontWeight.Medium
        )
    }
}

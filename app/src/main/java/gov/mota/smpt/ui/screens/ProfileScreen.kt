package gov.mota.smpt.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import gov.mota.smpt.data.model.VerificationStatus
import gov.mota.smpt.ui.components.*
import gov.mota.smpt.ui.theme.*
import gov.mota.smpt.viewmodel.SmptViewModel

@Composable
fun ProfileScreen(
    viewModel: SmptViewModel,
    onNavigateToDocuments: () -> Unit,
    onNavigateToVerification: () -> Unit,
    onNavigateToExistingSystems: () -> Unit,
    onNavigateToVerificationSources: () -> Unit,
    onNavigateToMinistryInsights: () -> Unit,
    onNavigateToHelp: () -> Unit,
    onBackClick: (() -> Unit)? = null
) {
    val student by viewModel.student.collectAsState()

    Scaffold(
        topBar = {
            GovernmentTopBar(title = "Profile", onBackClick = onBackClick)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Profile header
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = CardBackground),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape)
                                .background(GovNavy),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = student.name.take(2).uppercase(),
                                style = MaterialTheme.typography.titleLarge,
                                color = TextOnDark,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(Modifier.width(16.dp))
                        Column {
                            Text(
                                text = student.name,
                                style = MaterialTheme.typography.titleLarge,
                                color = TextPrimary
                            )
                            Text(
                                text = "Student ID: ${student.id}",
                                style = MaterialTheme.typography.bodySmall,
                                color = TextSecondary
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                StatusBadge(VerificationStatus.VERIFIED)
                                Spacer(Modifier.width(6.dp))
                                Text(
                                    text = "ST Verified",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = StatusGreen
                                )
                            }
                        }
                    }

                    Spacer(Modifier.height(16.dp))
                    HorizontalDivider(color = SurfaceDivider)
                    Spacer(Modifier.height(12.dp))

                    ProfileInfoRow("Date of Birth", student.dateOfBirth)
                    ProfileInfoRow("State", student.state)
                    ProfileInfoRow("District", student.district)
                    ProfileInfoRow("Education", student.educationLevel)
                    ProfileInfoRow("Institution", student.institutionName)
                    ProfileInfoRow("Annual Family Income", "₹${"%,d".format(student.annualFamilyIncome)}")
                    ProfileInfoRow("Aadhaar", "XXXX XXXX ${student.aadhaarLast4}")
                    ProfileInfoRow("Bank Account", "XXXXXX${student.bankAccountLast4}")
                    ProfileInfoRow("OTR ID", student.otrId)
                }
            }

            Spacer(Modifier.height(20.dp))

            // Menu items
            SectionHeader(title = "Scholarship Services")
            Spacer(Modifier.height(8.dp))

            ProfileMenuItem(
                icon = Icons.Filled.Description,
                title = "My Documents",
                subtitle = "View and manage your documents",
                onClick = onNavigateToDocuments
            )
            ProfileMenuItem(
                icon = Icons.Filled.VerifiedUser,
                title = "Unified Verification",
                subtitle = "Check all verification statuses",
                onClick = onNavigateToVerification
            )
            ProfileMenuItem(
                icon = Icons.Filled.Storage,
                title = "Verification Sources",
                subtitle = "Government & institutional data sources",
                onClick = onNavigateToVerificationSources
            )
            ProfileMenuItem(
                icon = Icons.Filled.Lan,
                title = "Existing Scholarship Systems",
                subtitle = "NSP, SFMP, NOS Portal",
                onClick = onNavigateToExistingSystems
            )

            Spacer(Modifier.height(16.dp))
            SectionHeader(title = "More")
            Spacer(Modifier.height(8.dp))

            ProfileMenuItem(
                icon = Icons.Filled.Analytics,
                title = "Ministry Insights",
                subtitle = "Prototype analytics dashboard",
                onClick = onNavigateToMinistryInsights
            )
            ProfileMenuItem(
                icon = Icons.Filled.Help,
                title = "Help & Support",
                subtitle = "Get help with SMPT",
                onClick = onNavigateToHelp
            )
            ProfileMenuItem(
                icon = Icons.Filled.Language,
                title = "Language",
                subtitle = "English, हिन्दी, தமிழ், + more",
                onClick = {}
            )

            Spacer(Modifier.height(20.dp))
            PrototypeLabel()
            Spacer(Modifier.height(4.dp))
            Text(
                text = "Prototype interface for demonstration purposes",
                style = MaterialTheme.typography.labelSmall,
                color = TextTertiary
            )
            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
private fun ProfileInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, style = MaterialTheme.typography.bodySmall, color = TextSecondary, modifier = Modifier.weight(0.4f))
        Text(value, style = MaterialTheme.typography.bodySmall, color = TextPrimary, fontWeight = FontWeight.Medium, modifier = Modifier.weight(0.6f))
    }
}

@Composable
private fun ProfileMenuItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.5.dp)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = null, tint = GovBlue, modifier = Modifier.size(24.dp))
            Spacer(Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(title, style = MaterialTheme.typography.titleSmall, color = TextPrimary)
                Text(subtitle, style = MaterialTheme.typography.bodySmall, color = TextSecondary)
            }
            Icon(Icons.Filled.ChevronRight, contentDescription = null, tint = TextTertiary)
        }
    }
}

package gov.mota.smpt.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gov.mota.smpt.data.model.*
import gov.mota.smpt.ui.theme.*

// ═══════════════════════════════════════════════════════════════
// Government Top Bar
// ═══════════════════════════════════════════════════════════════

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GovernmentTopBar(
    title: String,
    onBackClick: (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {}
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            if (onBackClick != null) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        },
        actions = actions,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = GovNavy,
            titleContentColor = TextOnDark,
            navigationIconContentColor = TextOnDark,
            actionIconContentColor = TextOnDark
        )
    )
}

// ═══════════════════════════════════════════════════════════════
// Status Badge
// ═══════════════════════════════════════════════════════════════

@Composable
fun StatusBadge(status: VerificationStatus) {
    val (bgColor, textColor, label) = when (status) {
        VerificationStatus.VERIFIED -> Triple(StatusGreenBg, StatusGreen, "Verified")
        VerificationStatus.PENDING -> Triple(StatusAmberBg, StatusAmber, "Pending")
        VerificationStatus.NEEDS_REVIEW -> Triple(StatusAmberBg, StatusAmber, "Needs Review")
        VerificationStatus.NOT_AVAILABLE -> Triple(StatusGrayBg, StatusGray, "Not Available")
    }
    Surface(
        shape = RoundedCornerShape(4.dp),
        color = bgColor
    ) {
        Text(
            text = label,
            color = textColor,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Composable
fun PaymentStatusBadge(status: PaymentStatus) {
    val (bgColor, textColor, label) = when (status) {
        PaymentStatus.CREDITED -> Triple(StatusGreenBg, StatusGreen, "Credited")
        PaymentStatus.PROCESSING -> Triple(StatusBlueBg, StatusBlue, "Processing")
        PaymentStatus.PENDING -> Triple(StatusAmberBg, StatusAmber, "Pending")
        PaymentStatus.FAILED -> Triple(StatusRedBg, StatusRed, "Failed")
    }
    Surface(
        shape = RoundedCornerShape(4.dp),
        color = bgColor
    ) {
        Text(
            text = label,
            color = textColor,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Composable
fun StageStatusBadge(status: StageStatus) {
    val (bgColor, textColor, label) = when (status) {
        StageStatus.COMPLETED -> Triple(StatusGreenBg, StatusGreen, "Completed")
        StageStatus.IN_PROGRESS -> Triple(StatusBlueBg, StatusBlue, "In Progress")
        StageStatus.PENDING -> Triple(StatusGrayBg, StatusGray, "Pending")
    }
    Surface(
        shape = RoundedCornerShape(4.dp),
        color = bgColor
    ) {
        Text(
            text = label,
            color = textColor,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

// ═══════════════════════════════════════════════════════════════
// Section Header
// ═══════════════════════════════════════════════════════════════

@Composable
fun SectionHeader(
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    action: String? = null,
    onActionClick: () -> Unit = {}
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = TextPrimary,
                modifier = Modifier.weight(1f)
            )
            if (action != null) {
                TextButton(onClick = onActionClick) {
                    Text(
                        text = action,
                        style = MaterialTheme.typography.labelLarge,
                        color = GovBlue
                    )
                }
            }
        }
        if (subtitle != null) {
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary,
                modifier = Modifier.padding(top = 2.dp)
            )
        }
    }
}

// ═══════════════════════════════════════════════════════════════
// Primary & Secondary Buttons
// ═══════════════════════════════════════════════════════════════

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 48.dp),
        enabled = enabled,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = GovNavy,
            contentColor = TextOnDark
        )
    ) {
        if (icon != null) {
            Icon(icon, contentDescription = null, modifier = Modifier.size(20.dp))
            Spacer(Modifier.width(8.dp))
        }
        Text(text, style = MaterialTheme.typography.labelLarge)
    }
}

@Composable
fun SecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 48.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = GovNavy
        ),
        border = ButtonDefaults.outlinedButtonBorder(enabled = true)
    ) {
        if (icon != null) {
            Icon(icon, contentDescription = null, modifier = Modifier.size(20.dp))
            Spacer(Modifier.width(8.dp))
        }
        Text(text, style = MaterialTheme.typography.labelLarge)
    }
}

// ═══════════════════════════════════════════════════════════════
// Info Banner
// ═══════════════════════════════════════════════════════════════

@Composable
fun InfoBanner(
    text: String,
    modifier: Modifier = Modifier,
    type: BannerType = BannerType.INFO
) {
    val (bgColor, iconColor, icon) = when (type) {
        BannerType.INFO -> Triple(StatusBlueBg, StatusBlue, Icons.Filled.Info)
        BannerType.WARNING -> Triple(StatusAmberBg, StatusAmber, Icons.Filled.Warning)
        BannerType.SUCCESS -> Triple(StatusGreenBg, StatusGreen, Icons.Filled.CheckCircle)
        BannerType.PROTOTYPE -> Triple(AccentSaffronLight, AccentSaffron, Icons.Filled.Science)
    }
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        color = bgColor
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.Top
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(20.dp)
            )
            Spacer(Modifier.width(10.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.bodySmall,
                color = TextPrimary
            )
        }
    }
}

enum class BannerType { INFO, WARNING, SUCCESS, PROTOTYPE }

// ═══════════════════════════════════════════════════════════════
// Prototype Label
// ═══════════════════════════════════════════════════════════════

@Composable
fun PrototypeLabel(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(4.dp),
        color = AccentSaffronLight
    ) {
        Text(
            text = "Prototype / Mock Data",
            style = MaterialTheme.typography.labelSmall,
            color = AccentSaffron,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
        )
    }
}

// ═══════════════════════════════════════════════════════════════
// Empty State
// ═══════════════════════════════════════════════════════════════

@Composable
fun EmptyState(
    icon: ImageVector,
    title: String,
    description: String,
    buttonText: String? = null,
    onButtonClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            icon,
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = TextTertiary
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = TextPrimary,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondary,
            textAlign = TextAlign.Center
        )
        if (buttonText != null) {
            Spacer(Modifier.height(20.dp))
            PrimaryButton(text = buttonText, onClick = onButtonClick)
        }
    }
}

// ═══════════════════════════════════════════════════════════════
// Error State
// ═══════════════════════════════════════════════════════════════

@Composable
fun ErrorState(
    title: String = "Unable to load scholarship information.",
    description: String = "Please check your internet connection and try again.",
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            Icons.Filled.CloudOff,
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = TextTertiary
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = TextPrimary,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondary,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(20.dp))
        PrimaryButton(text = "Retry", onClick = onRetry, icon = Icons.Filled.Refresh)
    }
}

// ═══════════════════════════════════════════════════════════════
// Scholarship Card
// ═══════════════════════════════════════════════════════════════

@Composable
fun ScholarshipCard(
    scheme: ScholarshipScheme,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = scheme.name,
                style = MaterialTheme.typography.titleMedium,
                color = GovNavy
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = scheme.intendedGroup,
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = scheme.shortDescription,
                style = MaterialTheme.typography.bodyMedium,
                color = TextPrimary,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = StatusGreenBg
                ) {
                    Text(
                        text = "Open",
                        color = StatusGreen,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
                Text(
                    text = "Check Eligibility →",
                    style = MaterialTheme.typography.labelLarge,
                    color = GovBlue
                )
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════
// Verification Card
// ═══════════════════════════════════════════════════════════════

@Composable
fun VerificationCard(
    item: VerificationItem,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
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
                    text = item.informationType,
                    style = MaterialTheme.typography.titleSmall,
                    color = TextPrimary,
                    modifier = Modifier.weight(1f)
                )
                StatusBadge(item.status)
            }
            Spacer(Modifier.height(8.dp))
            InfoRow("Source", item.source)
            InfoRow("Last checked", item.lastChecked)
            if (item.detail.isNotEmpty()) {
                Spacer(Modifier.height(4.dp))
                Text(
                    text = item.detail,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )
            }
            Spacer(Modifier.height(8.dp))
            Text(
                text = "View Details →",
                style = MaterialTheme.typography.labelMedium,
                color = GovBlue
            )
        }
    }
}

// ═══════════════════════════════════════════════════════════════
// Payment Card
// ═══════════════════════════════════════════════════════════════

@Composable
fun PaymentCard(
    payment: Payment,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(
                        when (payment.status) {
                            PaymentStatus.CREDITED -> StatusGreenBg
                            PaymentStatus.PROCESSING -> StatusBlueBg
                            else -> StatusAmberBg
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    when (payment.status) {
                        PaymentStatus.CREDITED -> Icons.Filled.CheckCircle
                        PaymentStatus.PROCESSING -> Icons.Filled.Schedule
                        else -> Icons.Filled.Pending
                    },
                    contentDescription = null,
                    tint = when (payment.status) {
                        PaymentStatus.CREDITED -> StatusGreen
                        PaymentStatus.PROCESSING -> StatusBlue
                        else -> StatusAmber
                    },
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = payment.schemeName,
                    style = MaterialTheme.typography.titleSmall,
                    color = TextPrimary
                )
                Text(
                    text = payment.date,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "₹${"%,d".format(payment.amount)}",
                    style = MaterialTheme.typography.titleSmall,
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(4.dp))
                PaymentStatusBadge(payment.status)
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════
// Document Card
// ═══════════════════════════════════════════════════════════════

@Composable
fun DocumentCard(
    document: Document,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(SurfaceLight),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Filled.Description,
                    contentDescription = null,
                    tint = GovBlue,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = document.name,
                    style = MaterialTheme.typography.titleSmall,
                    color = TextPrimary
                )
                Text(
                    text = "Source: ${document.source}",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )
            }
            StatusBadge(document.status)
        }
    }
}

// ═══════════════════════════════════════════════════════════════
// Action Required Card
// ═══════════════════════════════════════════════════════════════

@Composable
fun ActionRequiredCard(
    deficiency: Deficiency,
    onResolveClick: () -> Unit,
    onAskJago: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        border = CardDefaults.outlinedCardBorder().let {
            androidx.compose.foundation.BorderStroke(1.dp, StatusAmber)
        }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Filled.ErrorOutline,
                    contentDescription = null,
                    tint = StatusAmber,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = "ACTION REQUIRED",
                    style = MaterialTheme.typography.labelLarge,
                    color = StatusAmber,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(Modifier.height(12.dp))
            Text(
                text = deficiency.documentName,
                style = MaterialTheme.typography.titleSmall,
                color = TextPrimary
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "Reason: ${deficiency.reason}",
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "What you can do: ${deficiency.actionDescription}",
                style = MaterialTheme.typography.bodySmall,
                color = TextPrimary
            )
            Spacer(Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onResolveClick,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = GovNavy)
                ) {
                    Icon(Icons.Filled.Upload, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(Modifier.width(4.dp))
                    Text("Upload", style = MaterialTheme.typography.labelMedium)
                }
                OutlinedButton(
                    onClick = onAskJago,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Ask JAGO", style = MaterialTheme.typography.labelMedium)
                }
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════
// Eligibility Card
// ═══════════════════════════════════════════════════════════════

@Composable
fun EligibilityCriterionRow(
    criterion: EligibilityCriterion,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            if (criterion.met) Icons.Filled.CheckCircle else Icons.Filled.RadioButtonUnchecked,
            contentDescription = null,
            tint = if (criterion.met) StatusGreen else TextTertiary,
            modifier = Modifier.size(20.dp)
        )
        Spacer(Modifier.width(12.dp))
        Column {
            Text(
                text = criterion.name,
                style = MaterialTheme.typography.bodyMedium,
                color = TextPrimary,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = criterion.description,
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary
            )
        }
    }
}

// ═══════════════════════════════════════════════════════════════
// Application Timeline
// ═══════════════════════════════════════════════════════════════

@Composable
fun ApplicationTimeline(
    stages: List<ApplicationStageInfo>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        stages.forEachIndexed { index, stage ->
            Row(modifier = Modifier.fillMaxWidth()) {
                // Timeline indicator
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.width(32.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(
                                when (stage.status) {
                                    StageStatus.COMPLETED -> StatusGreen
                                    StageStatus.IN_PROGRESS -> GovBlue
                                    StageStatus.PENDING -> SurfaceDivider
                                }
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        when (stage.status) {
                            StageStatus.COMPLETED -> Icon(
                                Icons.Filled.Check, contentDescription = null,
                                tint = Color.White, modifier = Modifier.size(16.dp)
                            )
                            StageStatus.IN_PROGRESS -> Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .clip(CircleShape)
                                    .background(Color.White)
                            )
                            StageStatus.PENDING -> Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .clip(CircleShape)
                                    .background(TextTertiary)
                            )
                        }
                    }
                    if (index < stages.size - 1) {
                        Box(
                            modifier = Modifier
                                .width(2.dp)
                                .height(48.dp)
                                .background(
                                    if (stage.status == StageStatus.COMPLETED) StatusGreen
                                    else SurfaceDivider
                                )
                        )
                    }
                }

                Spacer(Modifier.width(12.dp))

                // Stage content
                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stage.title,
                            style = MaterialTheme.typography.titleSmall,
                            color = when (stage.status) {
                                StageStatus.PENDING -> TextTertiary
                                else -> TextPrimary
                            },
                            modifier = Modifier.weight(1f)
                        )
                        StageStatusBadge(stage.status)
                    }
                    if (stage.date != null) {
                        Text(
                            text = stage.date,
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondary
                        )
                    }
                    if (stage.description.isNotEmpty()) {
                        Text(
                            text = stage.description,
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondary,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                    Spacer(Modifier.height(16.dp))
                }
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════
// Readiness Check Row
// ═══════════════════════════════════════════════════════════════

@Composable
fun ReadinessCheckRow(check: ReadinessCheck) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            if (check.complete) Icons.Filled.CheckCircle else Icons.Filled.Cancel,
            contentDescription = null,
            tint = if (check.complete) StatusGreen else StatusAmber,
            modifier = Modifier.size(22.dp)
        )
        Spacer(Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = check.label,
                style = MaterialTheme.typography.bodyMedium,
                color = TextPrimary,
                fontWeight = FontWeight.Medium
            )
            if (check.detail.isNotEmpty()) {
                Text(
                    text = check.detail,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )
            }
        }
        Text(
            text = if (check.complete) "Complete" else "Required",
            style = MaterialTheme.typography.labelSmall,
            color = if (check.complete) StatusGreen else StatusAmber,
            fontWeight = FontWeight.SemiBold
        )
    }
}

// ═══════════════════════════════════════════════════════════════
// Government Source Card
// ═══════════════════════════════════════════════════════════════

@Composable
fun GovernmentSourceCard(
    source: GovernmentDataSource,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
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
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = source.name,
                        style = MaterialTheme.typography.titleSmall,
                        color = GovNavy,
                        fontWeight = FontWeight.Bold
                    )
                    if (source.name != source.fullName) {
                        Text(
                            text = source.fullName,
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondary
                        )
                    }
                }
                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = StatusGreenBg
                ) {
                    Text(
                        text = source.status,
                        color = StatusGreen,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
            }
            Spacer(Modifier.height(6.dp))
            Text(
                text = source.purpose,
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary
            )
            if (source.verificationItems.isNotEmpty()) {
                Spacer(Modifier.height(10.dp))
                HorizontalDivider(color = SurfaceDivider)
                Spacer(Modifier.height(10.dp))
                source.verificationItems.forEach { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 3.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            if (item.verified) Icons.Filled.CheckCircle else Icons.Filled.ErrorOutline,
                            contentDescription = null,
                            tint = if (item.verified) StatusGreen else StatusAmber,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = item.label,
                            style = MaterialTheme.typography.bodySmall,
                            color = TextPrimary,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = item.value,
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondary
                        )
                    }
                }
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════
// JAGO Message Bubble
// ═══════════════════════════════════════════════════════════════

@Composable
fun JagoMessageBubble(
    message: ChatMessage,
    onActionClick: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val alignment = if (message.isFromUser) Alignment.CenterEnd else Alignment.CenterStart
    val bgColor = if (message.isFromUser) GovNavy else SurfaceLight
    val textColor = if (message.isFromUser) TextOnDark else TextPrimary

    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = alignment
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(0.85f)
        ) {
            Surface(
                shape = RoundedCornerShape(
                    topStart = 12.dp,
                    topEnd = 12.dp,
                    bottomStart = if (message.isFromUser) 12.dp else 2.dp,
                    bottomEnd = if (message.isFromUser) 2.dp else 12.dp
                ),
                color = bgColor
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = message.content,
                        style = MaterialTheme.typography.bodyMedium,
                        color = textColor
                    )
                    if (message.actionLabel != null && !message.isFromUser) {
                        Spacer(Modifier.height(8.dp))
                        TextButton(
                            onClick = { message.actionRoute?.let(onActionClick) },
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text(
                                text = message.actionLabel,
                                style = MaterialTheme.typography.labelMedium,
                                color = GovBlue
                            )
                            Spacer(Modifier.width(4.dp))
                            Icon(
                                Icons.Filled.ArrowForward,
                                contentDescription = null,
                                tint = GovBlue,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }
            Text(
                text = message.timestamp,
                style = MaterialTheme.typography.labelSmall,
                color = TextTertiary,
                modifier = Modifier.padding(top = 4.dp, start = 4.dp)
            )
        }
    }
}

// ═══════════════════════════════════════════════════════════════
// Notification Card
// ═══════════════════════════════════════════════════════════════

@Composable
fun NotificationCard(
    notification: Notification,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (notification.read) CardBackground else StatusBlueBg.copy(alpha = 0.3f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = if (notification.read) 0.dp else 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            val icon = when (notification.category) {
                NotificationCategory.APPLICATION -> Icons.Filled.Assignment
                NotificationCategory.PAYMENT -> Icons.Filled.Payment
                NotificationCategory.DOCUMENT -> Icons.Filled.Description
                NotificationCategory.DEADLINE -> Icons.Filled.Schedule
                NotificationCategory.INFORMATION -> Icons.Filled.Info
            }
            val iconTint = when (notification.category) {
                NotificationCategory.APPLICATION -> GovBlue
                NotificationCategory.PAYMENT -> StatusGreen
                NotificationCategory.DOCUMENT -> StatusAmber
                NotificationCategory.DEADLINE -> StatusRed
                NotificationCategory.INFORMATION -> GovBlue
            }
            Icon(icon, contentDescription = null, tint = iconTint, modifier = Modifier.size(22.dp))
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = notification.title,
                    style = MaterialTheme.typography.titleSmall,
                    color = TextPrimary
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text = notification.message,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = notification.timestamp,
                    style = MaterialTheme.typography.labelSmall,
                    color = TextTertiary
                )
            }
            if (!notification.read) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(GovBlue)
                )
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════
// Existing System Card
// ═══════════════════════════════════════════════════════════════

@Composable
fun ExistingSystemCard(
    system: ExistingSystem,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = system.name,
                style = MaterialTheme.typography.titleSmall,
                color = GovNavy
            )
            Spacer(Modifier.height(4.dp))
            InfoRow("Purpose", system.purpose)
            InfoRow("Integration Status", system.integrationStatus)
        }
    }
}

// ═══════════════════════════════════════════════════════════════
// Summary Stat Card
// ═══════════════════════════════════════════════════════════════

@Composable
fun SummaryStatCard(
    label: String,
    value: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    valueColor: Color = GovNavy
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = GovBlue,
                modifier = Modifier.size(22.dp)
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.headlineSmall,
                color = valueColor,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(2.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary
            )
        }
    }
}

// ═══════════════════════════════════════════════════════════════
// Info Row helper
// ═══════════════════════════════════════════════════════════════

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
    ) {
        Text(
            text = "$label: ",
            style = MaterialTheme.typography.bodySmall,
            color = TextSecondary
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall,
            color = TextPrimary,
            fontWeight = FontWeight.Medium
        )
    }
}

// ═══════════════════════════════════════════════════════════════
// Loading Skeleton
// ═══════════════════════════════════════════════════════════════

@Composable
fun LoadingSkeleton(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        repeat(3) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(SurfaceMedium)
            )
            Spacer(Modifier.height(12.dp))
        }
    }
}

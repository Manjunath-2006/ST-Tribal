package gov.mota.smpt.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import gov.mota.smpt.ui.components.*
import gov.mota.smpt.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileSetupScreen(onComplete: () -> Unit) {
    var currentStep by remember { mutableIntStateOf(0) }
    val steps = listOf("Basic Details", "Education", "Eligibility Details", "Documents")

    Scaffold(
        topBar = {
            GovernmentTopBar(title = "Profile Setup")
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Step indicator
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                steps.forEachIndexed { index, step ->
                    Column(horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally) {
                        Surface(
                            shape = RoundedCornerShape(50),
                            color = if (index <= currentStep) GovNavy else SurfaceDivider,
                            modifier = Modifier.size(32.dp)
                        ) {
                            Box(contentAlignment = androidx.compose.ui.Alignment.Center) {
                                Text(
                                    "${index + 1}",
                                    color = if (index <= currentStep) TextOnDark else TextTertiary,
                                    style = MaterialTheme.typography.labelMedium
                                )
                            }
                        }
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = step,
                            style = MaterialTheme.typography.labelSmall,
                            color = if (index <= currentStep) TextPrimary else TextTertiary
                        )
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            Text(
                text = "Step ${currentStep + 1}: ${steps[currentStep]}",
                style = MaterialTheme.typography.titleMedium,
                color = TextPrimary
            )

            Spacer(Modifier.height(16.dp))

            when (currentStep) {
                0 -> BasicDetailsStep()
                1 -> EducationStep()
                2 -> EligibilityStep()
                3 -> DocumentsStep()
            }

            Spacer(Modifier.height(8.dp))

            // Why do we need this
            Row(modifier = Modifier.padding(vertical = 8.dp)) {
                Icon(
                    Icons.Filled.Info,
                    contentDescription = null,
                    tint = GovBlue,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = "Why do we need this? This information helps us determine your scholarship eligibility and reduce repeated data entry.",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )
            }

            Spacer(Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (currentStep > 0) {
                    SecondaryButton(
                        text = "Back",
                        onClick = { currentStep-- },
                        modifier = Modifier.weight(1f)
                    )
                }
                PrimaryButton(
                    text = if (currentStep == steps.size - 1) "Complete" else "Next",
                    onClick = {
                        if (currentStep == steps.size - 1) {
                            onComplete()
                        } else {
                            currentStep++
                        }
                    },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun BasicDetailsStep() {
    var name by remember { mutableStateOf("Arjun Kumar") }
    var dob by remember { mutableStateOf("15/03/2004") }
    var state by remember { mutableStateOf("Tamil Nadu") }
    var district by remember { mutableStateOf("Coimbatore") }

    ProfileField("Full Name", name) { name = it }
    ProfileField("Date of Birth", dob) { dob = it }
    ProfileField("State", state) { state = it }
    ProfileField("District", district) { district = it }
}

@Composable
private fun EducationStep() {
    var level by remember { mutableStateOf("Undergraduate (B.Tech)") }
    var institution by remember { mutableStateOf("ABC Engineering College, Coimbatore") }

    ProfileField("Education Level", level) { level = it }
    ProfileField("Institution Name", institution) { institution = it }
}

@Composable
private fun EligibilityStep() {
    var stStatus by remember { mutableStateOf("Yes — Verified") }
    var income by remember { mutableStateOf("₹1,80,000") }
    var disability by remember { mutableStateOf("No") }
    var pvtg by remember { mutableStateOf("No") }

    ProfileField("ST Status", stStatus) { stStatus = it }
    ProfileField("Annual Family Income", income) { income = it }
    ProfileField("Disability Status", disability) { disability = it }
    ProfileField("PVTG Status", pvtg) { pvtg = it }
}

@Composable
private fun DocumentsStep() {
    Column {
        Text(
            text = "Your documents will be fetched from DigiLocker and other verified sources.",
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondary
        )
        Spacer(Modifier.height(16.dp))
        listOf(
            "Aadhaar Card" to "UIDAI — Verified",
            "ST Certificate" to "DigiLocker — Verified",
            "Income Certificate" to "e-District — Available",
            "Academic Certificate" to "DigiLocker — Available"
        ).forEach { (doc, status) ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceLight)
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                ) {
                    Text(
                        text = doc,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.weight(1f),
                        color = TextPrimary
                    )
                    Text(
                        text = status,
                        style = MaterialTheme.typography.bodySmall,
                        color = StatusGreen
                    )
                }
            }
        }
    }
}

@Composable
private fun ProfileField(label: String, value: String, onValueChange: (String) -> Unit) {
    Column(modifier = Modifier.padding(vertical = 6.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = TextPrimary
        )
        Spacer(Modifier.height(4.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            singleLine = true
        )
    }
}

package gov.mota.smpt.data.model

/**
 * Core data models for the SMPT application.
 * All models represent the scholarship management domain for
 * Ministry of Tribal Affairs (MoTA) schemes.
 */

data class Student(
    val id: String,
    val name: String,
    val dateOfBirth: String,
    val mobileNumber: String,
    val state: String,
    val district: String,
    val stStatus: VerificationStatus,
    val educationLevel: String,
    val institutionName: String,
    val annualFamilyIncome: Long,
    val disabilityStatus: Boolean = false,
    val pvtgStatus: Boolean = false,
    val aadhaarLast4: String,
    val bankAccountLast4: String,
    val otrId: String,
    val profileComplete: Boolean = true
)

data class ScholarshipScheme(
    val id: String,
    val name: String,
    val shortDescription: String,
    val intendedGroup: String,
    val overview: String,
    val eligibilityCriteria: List<String>,
    val requiredDocuments: List<String>,
    val applicationProcess: String,
    val verificationSteps: List<String>,
    val sanctionInfo: String,
    val disbursementInfo: String,
    val currentStatus: SchemeStatus = SchemeStatus.OPEN,
    val sections: List<SchemeSection> = emptyList()
)

data class SchemeSection(
    val title: String,
    val content: String
)

enum class SchemeStatus {
    OPEN, CLOSED, UPCOMING
}

data class ScholarshipApplication(
    val id: String,
    val schemeId: String,
    val schemeName: String,
    val studentId: String,
    val applicationDate: String,
    val currentStage: ApplicationStage,
    val stages: List<ApplicationStageInfo>,
    val nextAction: String,
    val deficiencies: List<Deficiency> = emptyList(),
    val readinessChecks: List<ReadinessCheck> = emptyList()
)

data class ApplicationStageInfo(
    val stage: ApplicationStage,
    val title: String,
    val date: String? = null,
    val status: StageStatus,
    val description: String = "",
    val actionRequired: Boolean = false
)

enum class ApplicationStage {
    SUBMITTED, INSTITUTION_VERIFICATION, DOCUMENT_VERIFICATION,
    STATE_MOTA_VERIFICATION, SANCTION, DISBURSEMENT
}

enum class StageStatus {
    COMPLETED, IN_PROGRESS, PENDING
}

data class EligibilityResult(
    val schemeId: String,
    val schemeName: String,
    val overallEligible: Boolean,
    val verifiedCriteria: List<EligibilityCriterion>,
    val pendingCriteria: List<EligibilityCriterion>,
    val explanation: String
)

data class EligibilityCriterion(
    val name: String,
    val description: String,
    val met: Boolean
)

data class ScholarshipConflict(
    val currentSchemeId: String,
    val currentSchemeName: String,
    val targetSchemeId: String,
    val targetSchemeName: String,
    val conflictFound: Boolean,
    val resultStatus: String,
    val explanation: String,
    val recommendation: String
)

data class ReadinessCheck(
    val category: String,
    val label: String,
    val complete: Boolean,
    val detail: String = ""
)

data class Deficiency(
    val id: String,
    val applicationId: String,
    val documentName: String,
    val reason: String,
    val actionDescription: String,
    val resolved: Boolean = false
)

data class VerificationItem(
    val id: String,
    val informationType: String,
    val source: String,
    val sourceFullName: String,
    val status: VerificationStatus,
    val lastChecked: String,
    val detail: String = ""
)

enum class VerificationStatus {
    VERIFIED, PENDING, NEEDS_REVIEW, NOT_AVAILABLE
}

data class GovernmentDataSource(
    val id: String,
    val name: String,
    val fullName: String,
    val purpose: String,
    val status: String,
    val lastChecked: String,
    val verificationItems: List<DataSourceVerification> = emptyList()
)

data class DataSourceVerification(
    val label: String,
    val value: String,
    val verified: Boolean
)

data class ExistingSystem(
    val name: String,
    val purpose: String,
    val integrationStatus: String
)

data class Document(
    val id: String,
    val name: String,
    val category: String,
    val status: VerificationStatus,
    val source: String,
    val uploadDate: String,
    val canReuse: Boolean = true
)

data class Payment(
    val id: String,
    val schemeId: String,
    val schemeName: String,
    val amount: Long,
    val date: String,
    val status: PaymentStatus,
    val academicYear: String,
    val transactionRefLast4: String
)

enum class PaymentStatus {
    CREDITED, PROCESSING, PENDING, FAILED
}

data class Notification(
    val id: String,
    val title: String,
    val message: String,
    val category: NotificationCategory,
    val timestamp: String,
    val read: Boolean = false,
    val targetRoute: String? = null
)

enum class NotificationCategory {
    APPLICATION, PAYMENT, DOCUMENT, DEADLINE, INFORMATION
}

data class ChatMessage(
    val id: String,
    val content: String,
    val isFromUser: Boolean,
    val timestamp: String,
    val actionLabel: String? = null,
    val actionRoute: String? = null
)

data class JagoSuggestion(
    val question: String,
    val category: String
)

data class ProactiveAlert(
    val id: String,
    val message: String,
    val actionLabel: String,
    val actionRoute: String
)

data class MinistryInsight(
    val label: String,
    val value: String,
    val description: String = ""
)

data class AwarenessInfo(
    val message: String,
    val detail: String,
    val matchingSources: List<String>
)

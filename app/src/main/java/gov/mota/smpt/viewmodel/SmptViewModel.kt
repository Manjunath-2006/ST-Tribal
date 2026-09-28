package gov.mota.smpt.viewmodel

import androidx.lifecycle.ViewModel
import gov.mota.smpt.data.model.*
import gov.mota.smpt.data.repository.MockSmptRepository
import gov.mota.smpt.data.repository.SmptRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Primary ViewModel for the SMPT application.
 * Drives all UI screens from the repository layer.
 * Uses StateFlow for reactive, lifecycle-aware state management.
 */
class SmptViewModel : ViewModel() {

    private val repository: SmptRepository = MockSmptRepository()

    // ── Auth State ───────────────────────────────────────────
    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    private val _hasCompletedOnboarding = MutableStateFlow(false)
    val hasCompletedOnboarding: StateFlow<Boolean> = _hasCompletedOnboarding.asStateFlow()

    private val _hasCompletedProfileSetup = MutableStateFlow(false)
    val hasCompletedProfileSetup: StateFlow<Boolean> = _hasCompletedProfileSetup.asStateFlow()

    // ── Student ──────────────────────────────────────────────
    private val _student = MutableStateFlow(repository.getStudent())
    val student: StateFlow<Student> = _student.asStateFlow()

    // ── Schemes ──────────────────────────────────────────────
    private val _schemes = MutableStateFlow(repository.getAllSchemes())
    val schemes: StateFlow<List<ScholarshipScheme>> = _schemes.asStateFlow()

    // ── Applications ─────────────────────────────────────────
    private val _applications = MutableStateFlow(repository.getAllApplications())
    val applications: StateFlow<List<ScholarshipApplication>> = _applications.asStateFlow()

    // ── Verification ─────────────────────────────────────────
    private val _verificationItems = MutableStateFlow(repository.getVerificationItems())
    val verificationItems: StateFlow<List<VerificationItem>> = _verificationItems.asStateFlow()

    // ── Government Data Sources ──────────────────────────────
    private val _govDataSources = MutableStateFlow(repository.getGovernmentDataSources())
    val govDataSources: StateFlow<List<GovernmentDataSource>> = _govDataSources.asStateFlow()

    // ── Existing Systems ─────────────────────────────────────
    private val _existingSystems = MutableStateFlow(repository.getExistingSystems())
    val existingSystems: StateFlow<List<ExistingSystem>> = _existingSystems.asStateFlow()

    // ── Documents ────────────────────────────────────────────
    private val _documents = MutableStateFlow(repository.getAllDocuments())
    val documents: StateFlow<List<Document>> = _documents.asStateFlow()

    // ── Payments ─────────────────────────────────────────────
    private val _payments = MutableStateFlow(repository.getAllPayments())
    val payments: StateFlow<List<Payment>> = _payments.asStateFlow()

    val totalReceived: Long get() = repository.getTotalReceived()
    val totalPending: Long get() = repository.getTotalPending()

    // ── Notifications ────────────────────────────────────────
    private val _notifications = MutableStateFlow(repository.getAllNotifications())
    val notifications: StateFlow<List<Notification>> = _notifications.asStateFlow()

    // ── JAGO ─────────────────────────────────────────────────
    private val _chatMessages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val chatMessages: StateFlow<List<ChatMessage>> = _chatMessages.asStateFlow()

    val jagoSuggestions: List<JagoSuggestion> get() = repository.getJagoSuggestions()

    private val _proactiveAlerts = MutableStateFlow(repository.getProactiveAlerts())
    val proactiveAlerts: StateFlow<List<ProactiveAlert>> = _proactiveAlerts.asStateFlow()

    // ── Ministry Insights ────────────────────────────────────
    private val _ministryInsights = MutableStateFlow(repository.getMinistryInsights())
    val ministryInsights: StateFlow<List<MinistryInsight>> = _ministryInsights.asStateFlow()

    // ── Awareness ────────────────────────────────────────────
    val awarenessInfo: AwarenessInfo get() = repository.getAwarenessInfo()

    // ── Connectivity ─────────────────────────────────────────
    private val _isOffline = MutableStateFlow(false)
    val isOffline: StateFlow<Boolean> = _isOffline.asStateFlow()

    // ═════════════════════════════════════════════════════════
    // Actions
    // ═════════════════════════════════════════════════════════

    fun completeOnboarding() {
        _hasCompletedOnboarding.value = true
    }

    fun login(mobile: String, otp: String): Boolean {
        // Demo login: accept OTP 123456
        return if (otp == "123456") {
            _isLoggedIn.value = true
            true
        } else {
            false
        }
    }

    fun demoLogin() {
        _isLoggedIn.value = true
        _hasCompletedOnboarding.value = true
        _hasCompletedProfileSetup.value = true
    }

    fun completeProfileSetup() {
        _hasCompletedProfileSetup.value = true
    }

    fun getSchemeById(id: String): ScholarshipScheme? {
        return repository.getSchemeById(id)
    }

    fun getApplicationById(id: String): ScholarshipApplication? {
        return repository.getApplicationById(id)
    }

    fun getPaymentById(id: String): Payment? {
        return repository.getPaymentById(id)
    }

    fun checkEligibility(schemeId: String): EligibilityResult {
        return repository.checkEligibility(schemeId)
    }

    fun checkConflict(targetSchemeId: String): ScholarshipConflict {
        // Use the active application's scheme as current
        val activeSchemeId = _applications.value.firstOrNull()?.schemeId ?: "POST_MATRIC"
        return repository.checkConflict(activeSchemeId, targetSchemeId)
    }

    fun getActiveApplication(): ScholarshipApplication? {
        return _applications.value.firstOrNull {
            it.currentStage != ApplicationStage.DISBURSEMENT
        }
    }

    fun getDeficiencies(): List<Deficiency> {
        return _applications.value.flatMap { it.deficiencies }.filter { !it.resolved }
    }

    fun sendJagoMessage(message: String) {
        val userMsg = ChatMessage(
            id = "u_${System.currentTimeMillis()}",
            content = message,
            isFromUser = true,
            timestamp = "Now"
        )
        val response = repository.getJagoResponse(message)
        _chatMessages.value = _chatMessages.value + userMsg + response
    }

    fun getReadinessChecks(applicationId: String): List<ReadinessCheck> {
        return repository.getAllApplications()
            .find { it.id == applicationId }
            ?.readinessChecks ?: emptyList()
    }

    fun toggleOffline() {
        _isOffline.value = !_isOffline.value
    }
}

package gov.mota.smpt.data.repository

import gov.mota.smpt.data.model.*

/**
 * Repository interface for SMPT.
 * Defines the contract for data access that can be backed by
 * mock data (prototype) or real API integration (production).
 */
interface SmptRepository {
    // Student
    fun getStudent(): Student

    // Schemes
    fun getAllSchemes(): List<ScholarshipScheme>
    fun getSchemeById(id: String): ScholarshipScheme?

    // Applications
    fun getAllApplications(): List<ScholarshipApplication>
    fun getApplicationById(id: String): ScholarshipApplication?

    // Eligibility
    fun checkEligibility(schemeId: String): EligibilityResult

    // Conflict
    fun checkConflict(currentSchemeId: String, targetSchemeId: String): ScholarshipConflict

    // Verification
    fun getVerificationItems(): List<VerificationItem>

    // Government Data Sources
    fun getGovernmentDataSources(): List<GovernmentDataSource>

    // Existing Systems
    fun getExistingSystems(): List<ExistingSystem>

    // Documents
    fun getAllDocuments(): List<Document>
    fun getDocumentsByCategory(category: String): List<Document>

    // Payments
    fun getAllPayments(): List<Payment>
    fun getPaymentById(id: String): Payment?
    fun getTotalReceived(): Long
    fun getTotalPending(): Long

    // Notifications
    fun getAllNotifications(): List<Notification>

    // JAGO
    fun getJagoSuggestions(): List<JagoSuggestion>
    fun getJagoResponse(query: String): ChatMessage
    fun getProactiveAlerts(): List<ProactiveAlert>

    // Ministry Insights
    fun getMinistryInsights(): List<MinistryInsight>

    // Awareness
    fun getAwarenessInfo(): AwarenessInfo
}

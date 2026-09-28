package gov.mota.smpt.data.repository

import gov.mota.smpt.data.mock.MockDataProvider
import gov.mota.smpt.data.model.*

/**
 * Mock implementation of SmptRepository.
 * Uses MockDataProvider to supply realistic prototype data.
 * Can be replaced with a real API-backed implementation.
 */
class MockSmptRepository : SmptRepository {

    override fun getStudent(): Student = MockDataProvider.student

    override fun getAllSchemes(): List<ScholarshipScheme> = MockDataProvider.schemes

    override fun getSchemeById(id: String): ScholarshipScheme? =
        MockDataProvider.schemes.find { it.id == id }

    override fun getAllApplications(): List<ScholarshipApplication> =
        MockDataProvider.applications

    override fun getApplicationById(id: String): ScholarshipApplication? =
        MockDataProvider.applications.find { it.id == id }

    override fun checkEligibility(schemeId: String): EligibilityResult =
        MockDataProvider.getEligibilityResult(schemeId)

    override fun checkConflict(currentSchemeId: String, targetSchemeId: String): ScholarshipConflict =
        MockDataProvider.scholarshipConflict

    override fun getVerificationItems(): List<VerificationItem> =
        MockDataProvider.verificationItems

    override fun getGovernmentDataSources(): List<GovernmentDataSource> =
        MockDataProvider.governmentDataSources

    override fun getExistingSystems(): List<ExistingSystem> =
        MockDataProvider.existingSystems

    override fun getAllDocuments(): List<Document> =
        MockDataProvider.documents

    override fun getDocumentsByCategory(category: String): List<Document> =
        MockDataProvider.documents.filter { it.category == category }

    override fun getAllPayments(): List<Payment> =
        MockDataProvider.payments

    override fun getPaymentById(id: String): Payment? =
        MockDataProvider.payments.find { it.id == id }

    override fun getTotalReceived(): Long = MockDataProvider.totalReceived

    override fun getTotalPending(): Long = MockDataProvider.totalPending

    override fun getAllNotifications(): List<Notification> =
        MockDataProvider.notifications

    override fun getJagoSuggestions(): List<JagoSuggestion> =
        MockDataProvider.jagoSuggestions

    override fun getJagoResponse(query: String): ChatMessage =
        MockDataProvider.getJagoResponse(query)

    override fun getProactiveAlerts(): List<ProactiveAlert> =
        MockDataProvider.proactiveAlerts

    override fun getMinistryInsights(): List<MinistryInsight> =
        MockDataProvider.ministryInsights

    override fun getAwarenessInfo(): AwarenessInfo =
        MockDataProvider.awarenessInfo
}

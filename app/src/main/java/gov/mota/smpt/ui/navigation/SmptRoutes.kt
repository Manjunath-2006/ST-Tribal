package gov.mota.smpt.ui.navigation

/**
 * Navigation route definitions for the SMPT application.
 * Centralized route management for Navigation Compose.
 */
object SmptRoutes {
    // Pre-auth
    const val SPLASH = "splash"
    const val ONBOARDING = "onboarding"
    const val LOGIN = "login"
    const val PROFILE_SETUP = "profile_setup"

    // Main
    const val HOME = "home"
    const val SCHOLARSHIPS = "scholarships"
    const val SCHOLARSHIP_DETAIL = "scholarship_detail/{schemeId}"
    const val ELIGIBILITY_CHECK = "eligibility_check/{schemeId}"
    const val CONFLICT_CHECK = "conflict_check/{schemeId}"
    const val APPLICATION_READINESS = "application_readiness/{applicationId}"
    const val APPLICATIONS = "applications"
    const val APPLICATION_DETAIL = "application_detail/{applicationId}"
    const val PAYMENTS = "payments"
    const val PAYMENT_DETAIL = "payment_detail/{paymentId}"
    const val PROFILE = "profile"

    // Secondary
    const val DOCUMENTS = "documents"
    const val UNIFIED_VERIFICATION = "unified_verification"
    const val NOTIFICATIONS = "notifications"
    const val JAGO = "jago"
    const val HELP = "help"
    const val MINISTRY_INSIGHTS = "ministry_insights"
    const val SCHOLARSHIP_AWARENESS = "scholarship_awareness"
    const val EXISTING_SYSTEMS = "existing_systems"
    const val VERIFICATION_SOURCES = "verification_sources"

    // Helpers
    fun scholarshipDetail(schemeId: String) = "scholarship_detail/$schemeId"
    fun eligibilityCheck(schemeId: String) = "eligibility_check/$schemeId"
    fun conflictCheck(schemeId: String) = "conflict_check/$schemeId"
    fun applicationReadiness(applicationId: String) = "application_readiness/$applicationId"
    fun applicationDetail(applicationId: String) = "application_detail/$applicationId"
    fun paymentDetail(paymentId: String) = "payment_detail/$paymentId"
}

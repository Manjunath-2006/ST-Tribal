package gov.mota.smpt.data.mock

import gov.mota.smpt.data.model.*

/**
 * Mock data provider for the SMPT prototype.
 * All data is fictional and for demonstration purposes only.
 *
 * Contains realistic but mock data for:
 * - Student profile (Arjun Kumar)
 * - All five MoTA scholarship schemes
 * - Applications, eligibility, conflicts
 * - Verification statuses, documents, payments
 * - Notifications, JAGO responses
 * - Government data source statuses
 */
object MockDataProvider {

    // ── Student ──────────────────────────────────────────────
    val student = Student(
        id = "STU-2026-00421",
        name = "Arjun Kumar",
        dateOfBirth = "15 Mar 2004",
        mobileNumber = "9876543210",
        state = "Tamil Nadu",
        district = "Coimbatore",
        stStatus = VerificationStatus.VERIFIED,
        educationLevel = "B.Tech Artificial Intelligence and Machine Learning",
        institutionName = "ABC Engineering College, Coimbatore",
        annualFamilyIncome = 180000,
        disabilityStatus = false,
        pvtgStatus = false,
        aadhaarLast4 = "4821",
        bankAccountLast4 = "4821",
        otrId = "OTR-XXXX-4821"
    )

    // ── Scholarship Schemes ──────────────────────────────────
    val schemes = listOf(
        ScholarshipScheme(
            id = "PRE_MATRIC",
            name = "Pre-Matric Scholarship",
            shortDescription = "For ST students studying in Class 9 and 10",
            intendedGroup = "ST students in Class 9–10",
            overview = "The Pre-Matric Scholarship scheme provides financial assistance to Scheduled Tribe students studying in Class 9 and Class 10 to reduce dropout rates and support continuation of education.",
            eligibilityCriteria = listOf(
                "Student must belong to Scheduled Tribe",
                "Studying in Class 9 or 10 in a recognized school",
                "Annual family income within prescribed limit",
                "Not receiving any other scholarship"
            ),
            requiredDocuments = listOf(
                "ST Certificate", "Income Certificate", "School Enrollment",
                "Previous Year Marksheet", "Bank Account Details", "Aadhaar"
            ),
            applicationProcess = "Apply through the National Scholarship Portal (NSP) during the open window.",
            verificationSteps = listOf(
                "Institution Verification", "Document Verification",
                "State Verification", "MoTA Verification"
            ),
            sanctionInfo = "Scholarship is sanctioned after successful verification at all levels.",
            disbursementInfo = "Amount is disbursed directly to the student's bank account through DBT."
        ),
        ScholarshipScheme(
            id = "POST_MATRIC",
            name = "Post-Matric Scholarship",
            shortDescription = "For ST students studying beyond Class 10",
            intendedGroup = "ST students in Class 11 and above",
            overview = "The Post-Matric Scholarship provides financial support to Scheduled Tribe students pursuing education beyond Class 10, including undergraduate and postgraduate courses.",
            eligibilityCriteria = listOf(
                "Student must belong to Scheduled Tribe",
                "Studying in Class 11 or above in a recognized institution",
                "Annual family income within prescribed limit",
                "Not receiving any other scholarship"
            ),
            requiredDocuments = listOf(
                "ST Certificate", "Income Certificate", "Institution Enrollment",
                "Previous Year Marksheet", "Fee Receipt", "Bank Account Details",
                "Aadhaar", "Domicile Certificate"
            ),
            applicationProcess = "Apply through the National Scholarship Portal (NSP) during the open window.",
            verificationSteps = listOf(
                "Institution Verification", "Document Verification",
                "State Verification", "MoTA Verification"
            ),
            sanctionInfo = "Scholarship is sanctioned after successful verification by institution, state, and MoTA.",
            disbursementInfo = "Maintenance allowance and fees are disbursed through DBT to the student's bank account.",
            sections = listOf(
                SchemeSection("Education Details", "Course, year of study, and enrollment information."),
                SchemeSection("Institution", "Name, AISHE code, and accreditation details of the institution."),
                SchemeSection("Income", "Annual family income declaration and verification.")
            )
        ),
        ScholarshipScheme(
            id = "TOP_CLASS",
            name = "Top Class Scholarship",
            shortDescription = "For ST students in top institutions",
            intendedGroup = "ST students in notified premier institutions",
            overview = "The Top Class Scholarship supports Scheduled Tribe students admitted to notified premier institutions across the country to pursue higher education.",
            eligibilityCriteria = listOf(
                "Student must belong to Scheduled Tribe",
                "Admitted to a notified top-class institution",
                "Annual family income within prescribed limit",
                "Not receiving any other scholarship"
            ),
            requiredDocuments = listOf(
                "ST Certificate", "Income Certificate", "Admission Letter",
                "Institution Fee Receipt", "Previous Academic Records",
                "Bank Account Details", "Aadhaar"
            ),
            applicationProcess = "Apply through the National Scholarship Portal (NSP) for the relevant academic year.",
            verificationSteps = listOf(
                "Institution Verification", "Document Verification",
                "State Verification", "MoTA Verification"
            ),
            sanctionInfo = "Scholarship is sanctioned after verification by the institution and MoTA.",
            disbursementInfo = "Full tuition fees and maintenance allowance are disbursed through DBT.",
            sections = listOf(
                SchemeSection("Institution", "Must be a notified premier institution."),
                SchemeSection("Academic Details", "Course, branch, and year of study."),
                SchemeSection("Income", "Annual family income verification.")
            )
        ),
        ScholarshipScheme(
            id = "NFST",
            name = "National Fellowship for ST Students (NFST)",
            shortDescription = "Fellowship for ST students pursuing M.Phil/Ph.D.",
            intendedGroup = "ST students pursuing M.Phil/Ph.D. research",
            overview = "The National Fellowship for Scheduled Tribe Students provides financial assistance to ST students to pursue higher studies leading to M.Phil and Ph.D. degrees.",
            eligibilityCriteria = listOf(
                "Student must belong to Scheduled Tribe",
                "Qualified NET/JRF or equivalent examination",
                "Admitted for M.Phil/Ph.D. in a recognized university",
                "Not receiving any other fellowship"
            ),
            requiredDocuments = listOf(
                "ST Certificate", "NET/JRF Qualification Certificate",
                "University Admission Letter", "Research Proposal",
                "Academic Certificates", "Bank Account Details", "Aadhaar"
            ),
            applicationProcess = "Apply through the Scholarship Fellowship Management Portal (SFMP) operated by Canara Bank.",
            verificationSteps = listOf(
                "University Verification", "Document Verification",
                "UGC-NTA Qualification Verification", "MoTA Verification"
            ),
            sanctionInfo = "Fellowship is sanctioned for the prescribed duration upon successful verification.",
            disbursementInfo = "Monthly fellowship amount, HRA, and contingency are disbursed through SFMP."
        ),
        ScholarshipScheme(
            id = "NOS",
            name = "National Overseas Scholarship (NOS)",
            shortDescription = "For ST students pursuing studies abroad",
            intendedGroup = "ST students for Masters/Ph.D. abroad",
            overview = "The National Overseas Scholarship provides financial support to selected ST students to pursue Masters and Ph.D. level courses at accredited universities/institutions abroad.",
            eligibilityCriteria = listOf(
                "Student must belong to Scheduled Tribe",
                "Secured admission in an accredited foreign university",
                "Annual family income within prescribed limit",
                "Age within prescribed limit",
                "Not availing any other scholarship for overseas study"
            ),
            requiredDocuments = listOf(
                "ST Certificate", "Income Certificate", "Admission Letter from Foreign University",
                "Academic Certificates", "Passport", "IELTS/TOEFL Score",
                "Bank Account Details", "Aadhaar"
            ),
            applicationProcess = "Apply through the National Overseas Scholarship (NOS) Portal.",
            verificationSteps = listOf(
                "Document Verification", "Foreign University Verification",
                "Selection Committee Review", "MoTA Approval"
            ),
            sanctionInfo = "Scholarship is sanctioned after selection by the committee and MoTA approval.",
            disbursementInfo = "Tuition fees, maintenance, travel, and other allowances are provided."
        )
    )

    // ── Applications ─────────────────────────────────────────
    val applications = listOf(
        ScholarshipApplication(
            id = "APP-2026-0842",
            schemeId = "POST_MATRIC",
            schemeName = "Post-Matric Scholarship",
            studentId = student.id,
            applicationDate = "12 Aug 2026",
            currentStage = ApplicationStage.STATE_MOTA_VERIFICATION,
            nextAction = "No action required",
            stages = listOf(
                ApplicationStageInfo(
                    ApplicationStage.SUBMITTED, "Application Submitted",
                    "12 Aug 2026", StageStatus.COMPLETED,
                    "Your application was successfully submitted."
                ),
                ApplicationStageInfo(
                    ApplicationStage.INSTITUTION_VERIFICATION, "Institution Verification",
                    "14 Aug 2026", StageStatus.COMPLETED,
                    "Your institution has verified your enrollment and academic details."
                ),
                ApplicationStageInfo(
                    ApplicationStage.DOCUMENT_VERIFICATION, "Document Verification",
                    "17 Aug 2026", StageStatus.COMPLETED,
                    "All submitted documents have been verified."
                ),
                ApplicationStageInfo(
                    ApplicationStage.STATE_MOTA_VERIFICATION, "State / MoTA Verification",
                    null, StageStatus.IN_PROGRESS,
                    "Your application is currently being verified by the relevant authority."
                ),
                ApplicationStageInfo(
                    ApplicationStage.SANCTION, "Sanction",
                    null, StageStatus.PENDING,
                    "Scholarship sanction will be processed after verification."
                ),
                ApplicationStageInfo(
                    ApplicationStage.DISBURSEMENT, "Disbursement",
                    null, StageStatus.PENDING,
                    "Payment will be disbursed to your bank account after sanction."
                )
            ),
            deficiencies = listOf(
                Deficiency(
                    id = "DEF-001",
                    applicationId = "APP-2026-0842",
                    documentName = "Income Certificate",
                    reason = "The submitted certificate could not be automatically verified.",
                    actionDescription = "Upload a valid income certificate.",
                    resolved = false
                )
            ),
            readinessChecks = listOf(
                ReadinessCheck("Identity", "Identity", true, "UIDAI verified"),
                ReadinessCheck("ST Status", "ST Status", true, "DigiLocker verified"),
                ReadinessCheck("Eligibility", "Eligibility", true, "Criteria matched"),
                ReadinessCheck("Academic Record", "Academic Record", true, "APAAR verified"),
                ReadinessCheck("Institution", "Institution", true, "AISHE verified"),
                ReadinessCheck("Income", "Income", true, "e-District verified"),
                ReadinessCheck("Existing Scholarship", "Existing Scholarship", true, "No conflict"),
                ReadinessCheck("Documents", "Documents", true, "All documents available")
            )
        ),
        ScholarshipApplication(
            id = "APP-2026-1156",
            schemeId = "TOP_CLASS",
            schemeName = "Top Class Scholarship",
            studentId = student.id,
            applicationDate = "25 Sep 2026",
            currentStage = ApplicationStage.SUBMITTED,
            nextAction = "Resolve income certificate deficiency",
            stages = listOf(
                ApplicationStageInfo(
                    ApplicationStage.SUBMITTED, "Application Submitted",
                    "25 Sep 2026", StageStatus.COMPLETED,
                    "Your application was successfully submitted."
                ),
                ApplicationStageInfo(
                    ApplicationStage.INSTITUTION_VERIFICATION, "Institution Verification",
                    null, StageStatus.PENDING,
                    "Awaiting institution verification."
                ),
                ApplicationStageInfo(
                    ApplicationStage.DOCUMENT_VERIFICATION, "Document Verification",
                    null, StageStatus.PENDING,
                    "Documents will be verified after institution step."
                ),
                ApplicationStageInfo(
                    ApplicationStage.STATE_MOTA_VERIFICATION, "State / MoTA Verification",
                    null, StageStatus.PENDING,
                    "Pending"
                ),
                ApplicationStageInfo(
                    ApplicationStage.SANCTION, "Sanction",
                    null, StageStatus.PENDING,
                    "Pending"
                ),
                ApplicationStageInfo(
                    ApplicationStage.DISBURSEMENT, "Disbursement",
                    null, StageStatus.PENDING,
                    "Pending"
                )
            ),
            deficiencies = emptyList(),
            readinessChecks = listOf(
                ReadinessCheck("Identity", "Identity", true),
                ReadinessCheck("ST Status", "ST Status", true),
                ReadinessCheck("Eligibility", "Eligibility", true),
                ReadinessCheck("Academic Record", "Academic Record", true),
                ReadinessCheck("Institution", "Institution", true),
                ReadinessCheck("Income", "Income", false, "Income certificate needs update"),
                ReadinessCheck("Existing Scholarship", "Existing Scholarship", true, "Checked"),
                ReadinessCheck("Documents", "Documents", false, "1 document pending")
            )
        )
    )

    // ── Eligibility Results ──────────────────────────────────
    fun getEligibilityResult(schemeId: String): EligibilityResult {
        return when (schemeId) {
            "TOP_CLASS" -> EligibilityResult(
                schemeId = "TOP_CLASS",
                schemeName = "Top Class Scholarship",
                overallEligible = true,
                verifiedCriteria = listOf(
                    EligibilityCriterion("ST Status", "ST status verified via DigiLocker", true),
                    EligibilityCriterion("Education Level", "Currently enrolled in higher education", true),
                    EligibilityCriterion("Institution", "Institution information available via AISHE", true),
                    EligibilityCriterion("Income", "Income information available via e-District", true)
                ),
                pendingCriteria = listOf(
                    EligibilityCriterion("Academic Record", "Academic record verification pending", false),
                    EligibilityCriterion("Existing Scholarship", "Existing scholarship status to be checked", false)
                ),
                explanation = "Based on available information, you may be eligible for this scholarship. Eligibility is subject to verification."
            )
            "PRE_MATRIC" -> EligibilityResult(
                schemeId = "PRE_MATRIC",
                schemeName = "Pre-Matric Scholarship",
                overallEligible = false,
                verifiedCriteria = listOf(
                    EligibilityCriterion("ST Status", "ST status verified", true)
                ),
                pendingCriteria = listOf(
                    EligibilityCriterion("Education Level", "This scheme is for Class 9–10 students", false)
                ),
                explanation = "Based on your current education level, this scheme may not be applicable. This scholarship is intended for students in Class 9 and 10."
            )
            "NFST" -> EligibilityResult(
                schemeId = "NFST",
                schemeName = "National Fellowship for ST Students (NFST)",
                overallEligible = false,
                verifiedCriteria = listOf(
                    EligibilityCriterion("ST Status", "ST status verified", true),
                    EligibilityCriterion("Income", "Income information available", true)
                ),
                pendingCriteria = listOf(
                    EligibilityCriterion("NET/JRF Qualification", "NET/JRF qualification not found", false),
                    EligibilityCriterion("M.Phil/Ph.D. Admission", "Research program admission not found", false)
                ),
                explanation = "This fellowship is for students pursuing M.Phil/Ph.D. with NET/JRF qualification. Your current profile does not indicate these qualifications."
            )
            "NOS" -> EligibilityResult(
                schemeId = "NOS",
                schemeName = "National Overseas Scholarship (NOS)",
                overallEligible = false,
                verifiedCriteria = listOf(
                    EligibilityCriterion("ST Status", "ST status verified", true),
                    EligibilityCriterion("Income", "Income information available", true)
                ),
                pendingCriteria = listOf(
                    EligibilityCriterion("Foreign Admission", "Admission to foreign university not found", false),
                    EligibilityCriterion("Academic Level", "Masters/Ph.D. level qualification required", false)
                ),
                explanation = "This scholarship supports overseas study at Masters/Ph.D. level. Admission to an accredited foreign university is required."
            )
            else -> EligibilityResult(
                schemeId = schemeId,
                schemeName = schemes.find { it.id == schemeId }?.name ?: "Scholarship",
                overallEligible = true,
                verifiedCriteria = listOf(
                    EligibilityCriterion("ST Status", "ST status verified", true),
                    EligibilityCriterion("Education Level", "Matched", true),
                    EligibilityCriterion("Institution", "Available", true),
                    EligibilityCriterion("Income", "Available", true)
                ),
                pendingCriteria = emptyList(),
                explanation = "Based on available information, you may be eligible."
            )
        }
    }

    // ── Scholarship Conflict ─────────────────────────────────
    val scholarshipConflict = ScholarshipConflict(
        currentSchemeId = "POST_MATRIC",
        currentSchemeName = "Post-Matric Scholarship",
        targetSchemeId = "TOP_CLASS",
        targetSchemeName = "Top Class Scholarship",
        conflictFound = true,
        resultStatus = "Review Required",
        explanation = "SMPT has identified an existing scholarship. The new application should be checked against applicable scheme rules before submission.",
        recommendation = "Your existing Post-Matric Scholarship is currently active. Before applying for the Top Class Scholarship, the system will verify whether both can coexist as per scheme guidelines."
    )

    // ── Verification Items ───────────────────────────────────
    val verificationItems = listOf(
        VerificationItem("v1", "Identity", "UIDAI", "Unique Identification Authority of India", VerificationStatus.VERIFIED, "28 Sep 2026", "Aadhaar: XXXX XXXX 4821"),
        VerificationItem("v2", "ST Certificate", "DigiLocker / State e-District", "DigiLocker & State e-District Systems", VerificationStatus.VERIFIED, "28 Sep 2026", "Community certificate verified"),
        VerificationItem("v3", "Academic Identity", "APAAR", "Automated Permanent Academic Account Registry", VerificationStatus.VERIFIED, "28 Sep 2026", "Academic identity matched"),
        VerificationItem("v4", "School Record", "UDISE+", "Unified District Information System for Education Plus", VerificationStatus.VERIFIED, "28 Sep 2026", "Enrollment record matched"),
        VerificationItem("v5", "Higher Education", "AISHE", "All India Survey on Higher Education", VerificationStatus.VERIFIED, "28 Sep 2026", "Institution: ABC Engineering College — AISHE Match: Verified"),
        VerificationItem("v6", "Income Certificate", "State e-District", "State e-District Systems", VerificationStatus.NEEDS_REVIEW, "28 Sep 2026", "Income information could not be automatically matched. Manual verification may be required."),
        VerificationItem("v7", "NET/JRF", "UGC-NTA", "University Grants Commission - National Testing Agency", VerificationStatus.NOT_AVAILABLE, "28 Sep 2026", "Not applicable for current education level"),
        VerificationItem("v8", "Scholarship Registration", "OTR", "One Time Registration", VerificationStatus.VERIFIED, "28 Sep 2026", "OTR ID: OTR-XXXX-4821 — Status: Matched")
    )

    // ── Government Data Sources ──────────────────────────────
    val governmentDataSources = listOf(
        GovernmentDataSource(
            "ds1", "DigiLocker", "DigiLocker",
            "Digital document access and verification",
            "Connected", "28 Sep 2026",
            listOf(
                DataSourceVerification("ST Certificate", "Verified", true),
                DataSourceVerification("Academic Certificate", "Available", true)
            )
        ),
        GovernmentDataSource(
            "ds2", "AISHE", "All India Survey on Higher Education",
            "Higher education and institution information",
            "Connected", "28 Sep 2026",
            listOf(
                DataSourceVerification("Institution", "ABC Engineering College", true),
                DataSourceVerification("AISHE Match", "Verified", true)
            )
        ),
        GovernmentDataSource(
            "ds3", "UDISE+", "Unified District Information System for Education Plus",
            "School education and enrollment information",
            "Connected", "28 Sep 2026",
            listOf(DataSourceVerification("Enrollment Record", "Matched", true))
        ),
        GovernmentDataSource(
            "ds4", "APAAR", "Automated Permanent Academic Account Registry",
            "Academic identity and academic records",
            "Connected", "28 Sep 2026",
            listOf(DataSourceVerification("Academic Identity", "Matched", true))
        ),
        GovernmentDataSource(
            "ds5", "UIDAI", "Unique Identification Authority of India",
            "Identity verification",
            "Connected", "28 Sep 2026",
            listOf(
                DataSourceVerification("Aadhaar", "XXXX XXXX 4821", true),
                DataSourceVerification("Identity Verification", "Verified", true)
            )
        ),
        GovernmentDataSource(
            "ds6", "State e-District Systems", "State e-District Systems",
            "Verification of state-issued certificates and records",
            "Connected", "28 Sep 2026",
            listOf(
                DataSourceVerification("Income Certificate", "Needs Review", false),
                DataSourceVerification("Domicile Certificate", "Verified", true),
                DataSourceVerification("ST/Community Certificate", "Verified", true)
            )
        ),
        GovernmentDataSource(
            "ds7", "UGC-NTA", "University Grants Commission - National Testing Agency",
            "Relevant examination/qualification verification",
            "Connected", "28 Sep 2026",
            listOf(DataSourceVerification("NET/JRF Qualification", "Not Applicable", false))
        ),
        GovernmentDataSource(
            "ds8", "OTR", "One Time Registration (OTR)",
            "Scholarship registration / matching",
            "Connected", "28 Sep 2026",
            listOf(
                DataSourceVerification("OTR ID", "OTR-XXXX-4821", true),
                DataSourceVerification("Status", "Matched", true)
            )
        )
    )

    // ── Existing Systems ─────────────────────────────────────
    val existingSystems = listOf(
        ExistingSystem(
            "National Scholarship Portal (NSP)",
            "Scholarship application and processing",
            "Demo Integration"
        ),
        ExistingSystem(
            "Scholarship Fellowship Management Portal (SFMP)\nCanara Bank",
            "Fellowship management and disbursement for NFST",
            "Demo Integration"
        ),
        ExistingSystem(
            "National Overseas Scholarship (NOS) Portal",
            "Overseas scholarship application and management",
            "Demo Integration"
        )
    )

    // ── Documents ────────────────────────────────────────────
    val documents = listOf(
        Document("d1", "Aadhaar Card", "Identity", VerificationStatus.VERIFIED, "UIDAI", "10 Jul 2026"),
        Document("d2", "ST Certificate", "ST Certificate", VerificationStatus.VERIFIED, "DigiLocker", "12 Jul 2026"),
        Document("d3", "Income Certificate", "Income", VerificationStatus.NEEDS_REVIEW, "State e-District", "15 Jul 2026"),
        Document("d4", "Class 12 Marksheet", "Academic", VerificationStatus.VERIFIED, "DigiLocker", "12 Jul 2026"),
        Document("d5", "Institution Enrollment", "Institution", VerificationStatus.VERIFIED, "AISHE", "20 Jul 2026"),
        Document("d6", "Domicile Certificate", "Domicile", VerificationStatus.VERIFIED, "State e-District", "15 Jul 2026"),
        Document("d7", "Bank Passbook", "Other", VerificationStatus.VERIFIED, "Manual Upload", "10 Jul 2026"),
        Document("d8", "Fee Receipt", "Institution", VerificationStatus.PENDING, "Manual Upload", "25 Sep 2026", canReuse = false)
    )

    // ── Payments ─────────────────────────────────────────────
    val payments = listOf(
        Payment("p1", "POST_MATRIC", "Post-Matric Scholarship", 12000, "15 Aug 2026", PaymentStatus.CREDITED, "2026-27", "4821"),
        Payment("p2", "POST_MATRIC", "Post-Matric Scholarship", 12000, "15 Sep 2026", PaymentStatus.CREDITED, "2026-27", "4821"),
        Payment("p3", "POST_MATRIC", "Post-Matric Scholarship", 12000, "15 Oct 2026", PaymentStatus.PROCESSING, "2026-27", "4821")
    )

    val totalReceived: Long = 24000
    val totalPending: Long = 12000

    // ── Notifications ────────────────────────────────────────
    val notifications = listOf(
        Notification("n1", "Verification Update", "Your Post-Matric Scholarship application has moved to State / MoTA Verification.", NotificationCategory.APPLICATION, "28 Sep 2026, 10:30 AM", false, "application_detail/APP-2026-0842"),
        Notification("n2", "Document Action", "Your income certificate requires an update.", NotificationCategory.DOCUMENT, "27 Sep 2026, 2:15 PM", false, "documents"),
        Notification("n3", "Payment Processed", "Your scholarship payment of ₹12,000 has been processed.", NotificationCategory.PAYMENT, "15 Sep 2026, 11:00 AM", true, "payment_detail/p2"),
        Notification("n4", "Application Submitted", "Your Top Class Scholarship application has been submitted.", NotificationCategory.APPLICATION, "25 Sep 2026, 9:00 AM", true, "application_detail/APP-2026-1156"),
        Notification("n5", "Payment Credited", "₹12,000 has been credited to your bank account.", NotificationCategory.PAYMENT, "15 Aug 2026, 10:00 AM", true, "payment_detail/p1"),
        Notification("n6", "Deadline Reminder", "Post-Matric Scholarship renewal window closes on 31 Oct 2026.", NotificationCategory.DEADLINE, "26 Sep 2026, 8:00 AM", false, "scholarships"),
        Notification("n7", "Scholarship Awareness", "You may be eligible for additional scholarship schemes.", NotificationCategory.INFORMATION, "20 Sep 2026, 9:00 AM", true, "scholarship_awareness")
    )

    // ── JAGO ─────────────────────────────────────────────────
    val jagoSuggestions = listOf(
        JagoSuggestion("Am I eligible for a scholarship?", "Eligibility"),
        JagoSuggestion("What documents do I need?", "Documents"),
        JagoSuggestion("Where is my application?", "Application"),
        JagoSuggestion("Why is verification pending?", "Verification"),
        JagoSuggestion("Can I apply for another scholarship?", "Conflict"),
        JagoSuggestion("When will my scholarship be paid?", "Payment")
    )

    val proactiveAlerts = listOf(
        ProactiveAlert("pa1", "You have one pending scholarship action.", "View Action", "action_center"),
        ProactiveAlert("pa2", "Your latest scholarship payment is currently being processed.", "View Payment", "payment_detail/p3"),
        ProactiveAlert("pa3", "Your application has moved to the next verification stage.", "Track Application", "application_detail/APP-2026-0842")
    )

    fun getJagoResponse(query: String): ChatMessage {
        val lowerQuery = query.lowercase()
        return when {
            lowerQuery.contains("where") && (lowerQuery.contains("scholarship") || lowerQuery.contains("application")) ->
                ChatMessage("j_resp", "Your Post-Matric Scholarship application is currently under State / MoTA verification. No action is required from you at this time.", false, "Now", "View Application", "application_detail/APP-2026-0842")

            lowerQuery.contains("eligible") ->
                ChatMessage("j_resp", "Based on your profile, you are currently receiving the Post-Matric Scholarship. You may also be eligible for the Top Class Scholarship. Would you like to check your eligibility?", false, "Now", "Check Eligibility", "eligibility_check/TOP_CLASS")

            lowerQuery.contains("document") ->
                ChatMessage("j_resp", "Most of your documents are verified. However, your Income Certificate currently needs review. You can upload an updated certificate from the Documents section.", false, "Now", "View Documents", "documents")

            lowerQuery.contains("verification") || lowerQuery.contains("pending") ->
                ChatMessage("j_resp", "Your application is in the State / MoTA Verification stage. This is a routine verification step. Your income certificate requires manual review as it could not be automatically matched.", false, "Now", "View Verification", "unified_verification")

            lowerQuery.contains("another") || lowerQuery.contains("conflict") || lowerQuery.contains("apply") ->
                ChatMessage("j_resp", "You currently have an active Post-Matric Scholarship. Before applying for another scheme, SMPT will check whether your existing scholarship may affect the new application.", false, "Now", "Run Conflict Check", "conflict_check/TOP_CLASS")

            lowerQuery.contains("pay") || lowerQuery.contains("money") || lowerQuery.contains("paid") ->
                ChatMessage("j_resp", "You have received ₹24,000 so far under the Post-Matric Scholarship. Your latest payment of ₹12,000 is currently being processed and should be credited to your registered bank account soon.", false, "Now", "View Payments", "payments")

            else ->
                ChatMessage("j_resp", "I can help you with your scholarship journey. You can ask me about your application status, eligibility, documents, payments, or verification status.", false, "Now")
        }
    }

    // ── Ministry Insights ────────────────────────────────────
    val ministryInsights = listOf(
        MinistryInsight("Registered ST Students", "1,24,580", "Total students registered across all schemes"),
        MinistryInsight("Active Beneficiaries", "98,420", "Students currently receiving scholarship benefits"),
        MinistryInsight("Applications Under Verification", "8,420", "Applications in various verification stages"),
        MinistryInsight("Applications With Deficiencies", "3,218", "Applications requiring student action"),
        MinistryInsight("Potentially Unreached Students", "17,942", "Students who may be eligible but not yet enrolled"),
        MinistryInsight("Total Disbursement", "₹842 Crore", "Total amount disbursed in current academic year")
    )

    // ── Scholarship Awareness ────────────────────────────────
    val awarenessInfo = AwarenessInfo(
        message = "You may be eligible for scholarship support.",
        detail = "Your education information indicates that you may qualify for one or more Ministry of Tribal Affairs scholarship schemes.",
        matchingSources = listOf("UDISE+", "APAAR", "OTR", "Scholarship Registration")
    )
}

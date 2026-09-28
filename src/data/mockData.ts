import {
  Student,
  ScholarshipScheme,
  ScholarshipApplication,
  VerificationItem,
  GovernmentDataSource,
  ExistingSystem,
  Payment,
  DocumentItem,
  NotificationItem,
  ChatMessage,
  ProactiveAlert
} from './types';

export const MOCK_STUDENT: Student = {
  id: 'STU-2026-8841',
  name: 'Arjun Kumar',
  category: 'Scheduled Tribe (ST)',
  gender: 'Male',
  dob: '14 August 2004',
  state: 'Tamil Nadu',
  district: 'Nilgiris',
  institution: 'Government Arts & Science College, Ooty (AISHE: C-24891)',
  course: 'Bachelor of Science (Computer Science)',
  academicYear: '2025-2026 (2nd Year)',
  familyIncome: 180000, // ₹1,80,000 / annum
  mobile: '+91 98765 43210',
  email: 'arjun.kumar.st@example.gov.in',
  apaarId: 'APAAR-9908-1123-4567',
  isSTVerified: true,
  isIncomeVerified: true,
  isInstitutionVerified: true,
  isBankDBTLinked: true
};

export const MOCK_SCHEMES: ScholarshipScheme[] = [
  {
    id: 'MOTA-SCHEME-01',
    name: 'Pre-Matric Scholarship for ST Students',
    shortCode: 'Pre-Matric ST',
    level: 'School (Class 9 & 10)',
    minIncomeLimit: 250000,
    stCategoryOnly: true,
    minAttendance: 75,
    benefitDescription: 'Financial support for tuition fees, books, and monthly stipend for ST students studying in classes IX & X.',
    amountPerYear: 7500,
    deadline: '31 October 2026',
    applicationMode: 'State e-District / Integrated SMPT Portal',
    verifyingAuthorities: ['School Headmaster', 'District Tribal Welfare Officer', 'State Nodal Department']
  },
  {
    id: 'MOTA-SCHEME-02',
    name: 'Post-Matric Scholarship for ST Students',
    shortCode: 'Post-Matric ST',
    level: 'Post-Secondary (Higher Education)',
    minIncomeLimit: 250000,
    stCategoryOnly: true,
    minAttendance: 75,
    benefitDescription: 'Comprehensive funding covering full non-refundable course fees, maintenance allowance, and book grants for post-secondary ST students.',
    amountPerYear: 36000,
    deadline: '15 November 2026',
    applicationMode: 'SMPT Federated Direct Portal',
    verifyingAuthorities: ['Institution Nodal Officer (AISHE)', 'District Tribal Development Officer', 'State Tribal Welfare Dept']
  },
  {
    id: 'MOTA-SCHEME-03',
    name: 'National Fellowship for Higher Education of ST Students',
    shortCode: 'NFST',
    level: 'M.Phil / Ph.D Fellowship',
    minIncomeLimit: 600000,
    stCategoryOnly: true,
    minAttendance: 80,
    benefitDescription: 'Financial assistance to ST students for pursuing M.Phil and Ph.D. degrees in Indian Universities & Research Institutes.',
    amountPerYear: 370000,
    deadline: '30 November 2026',
    applicationMode: 'SMPT Fellowship Module',
    verifyingAuthorities: ['University Registrar', 'UGC/NTA Verification Engine', 'Ministry of Tribal Affairs Central Cell']
  },
  {
    id: 'MOTA-SCHEME-04',
    name: 'National Overseas Scholarship for ST Students',
    shortCode: 'NOS',
    level: 'Master / Ph.D (Foreign Universities)',
    minIncomeLimit: 600000,
    stCategoryOnly: true,
    minAttendance: 85,
    benefitDescription: 'Financial assistance to selected ST candidates for pursuing Master level courses and Ph.D. abroad in specified fields of study.',
    amountPerYear: 1800000,
    deadline: '31 December 2026',
    applicationMode: 'NOS International Wing Interface',
    verifyingAuthorities: ['Selection Committee MoTA', 'Indian Embassy Nodal Desk', 'Central MoTA PMU']
  },
  {
    id: 'MOTA-SCHEME-05',
    name: 'Top Class Education Scheme for ST Students',
    shortCode: 'Top Class ST',
    level: 'Premier Institutes (IITs, NITs, IIMs, AIIMS, NLUs)',
    minIncomeLimit: 600000,
    stCategoryOnly: true,
    minAttendance: 80,
    benefitDescription: 'Full tuition fee waiver, living expenses, computer allowance, and books grant for ST students admitted to top notification institutes.',
    amountPerYear: 240000,
    deadline: '20 November 2026',
    applicationMode: 'SMPT Top Class Premier Portal',
    verifyingAuthorities: ['Dean of Student Affairs', 'State Nodal Officer', 'MoTA Central Nodal Agency']
  }
];

export const MOCK_APPLICATIONS: ScholarshipApplication[] = [
  {
    id: 'APP-2026-1156',
    schemeId: 'MOTA-SCHEME-02',
    schemeName: 'Post-Matric Scholarship for ST Students',
    applicationDate: '12 August 2026',
    academicYear: '2025-2026',
    status: 'DEFICIENCY_RAISED',
    nextAction: 'Re-verify Income Certificate on DigiLocker or Upload Fresh Document',
    estimatedDisbursement: '₹36,000 (Batch 2 Expected Nov 2026)',
    stages: [
      { stageId: 1, title: 'Application Submission', status: 'COMPLETED', dateCompleted: '12 Aug 2026', remarks: 'Submitted via SMPT Portal' },
      { stageId: 2, title: 'Student Identity & ST Caste Verification', status: 'COMPLETED', dateCompleted: '14 Aug 2026', remarks: 'Auto-verified via DigiLocker / State e-District API' },
      { stageId: 3, title: 'Institution Verification (AISHE)', status: 'COMPLETED', dateCompleted: '20 Aug 2026', remarks: 'Verified by Principal, Govt Arts & Science College, Ooty' },
      { stageId: 4, title: 'District Tribal Welfare Verification', status: 'IN_PROGRESS', dateCompleted: undefined, remarks: 'Deficiency flag raised regarding income certificate expiry' },
      { stageId: 5, title: 'State Nodal Approval', status: 'PENDING' },
      { stageId: 6, title: 'PFMS / DBT Payment Disbursement', status: 'PENDING' }
    ],
    deficiencies: [
      {
        id: 'DEF-2026-091',
        title: 'Income Certificate Validity Expiry Flag',
        description: 'The Income Certificate fetched from State e-District shows valid up to 31 March 2026. District officer requested renewal for AY 2026-27.',
        sourceAuthority: 'State e-District (Tamil Nadu e-Sevai)',
        stage: 'District Verification',
        severity: 'WARNING',
        resolved: false,
        resolutionAction: 'Update Income Certificate reference via DigiLocker or upload latest signed PDF.'
      }
    ]
  },
  {
    id: 'APP-2025-8812',
    schemeId: 'MOTA-SCHEME-02',
    schemeName: 'Post-Matric Scholarship for ST Students',
    applicationDate: '10 July 2025',
    academicYear: '2024-2025',
    status: 'DISBURSED',
    nextAction: 'Completed',
    estimatedDisbursement: 'Disbursed ₹24,000 on 15 Jan 2026',
    stages: [
      { stageId: 1, title: 'Application Submission', status: 'COMPLETED', dateCompleted: '10 Jul 2025' },
      { stageId: 2, title: 'Student Identity Verification', status: 'COMPLETED', dateCompleted: '12 Jul 2025' },
      { stageId: 3, title: 'Institution Verification', status: 'COMPLETED', dateCompleted: '28 Jul 2025' },
      { stageId: 4, title: 'District Verification', status: 'COMPLETED', dateCompleted: '15 Aug 2025' },
      { stageId: 5, title: 'State Nodal Approval', status: 'COMPLETED', dateCompleted: '10 Sep 2025' },
      { stageId: 6, title: 'DBT Payment Disbursement', status: 'COMPLETED', dateCompleted: '15 Jan 2026' }
    ],
    deficiencies: []
  }
];

export const MOCK_VERIFICATION_ITEMS: VerificationItem[] = [
  { id: 'V1', title: 'Aadhaar Identity & E-KYC', authority: 'UIDAI', status: 'VERIFIED', lastChecked: '28 Sep 2026', referenceNumber: 'XXXX-XXXX-4821', details: 'Name & DOB match Aadhaar 100%' },
  { id: 'V2', title: 'ST Community / Caste Certificate', authority: 'DigiLocker / TN e-Sevai', status: 'VERIFIED', lastChecked: '28 Sep 2026', referenceNumber: 'TN-ST-2022-77182', details: 'Kodaikanal/Nilgiris ST Tribe Record Validated' },
  { id: 'V3', title: 'Annual Family Income Certificate', authority: 'State Revenue Dept', status: 'MISMATCH', lastChecked: '26 Sep 2026', referenceNumber: 'INC-2024-99120', details: 'Certificate date valid till Mar 2026. Renewal required.' },
  { id: 'V4', title: 'Higher Education Enrolment', authority: 'AISHE (Ministry of Education)', status: 'VERIFIED', lastChecked: '25 Sep 2026', referenceNumber: 'AISHE C-24891', details: 'Govt Arts & Science College, Ooty (Active Roll #24CS09)' },
  { id: 'V5', title: 'Automated Permanent Academic Account Registry', authority: 'APAAR / Academic Bank of Credits', status: 'VERIFIED', lastChecked: '28 Sep 2026', referenceNumber: 'APAAR-9908-1123-4567', details: 'Academic credits synced for 1st Year B.Sc' },
  { id: 'V6', title: 'Bank Account & Aadhaar Seeding', authority: 'NPCI / PFMS DBT Gateway', status: 'VERIFIED', lastChecked: '28 Sep 2026', referenceNumber: 'DBT-MAP-9921', details: 'Canara Bank (A/c ****9812) - Active DBT Seeded' },
  { id: 'V7', title: 'School Level Educational Record', authority: 'UDISE+ Portal', status: 'VERIFIED', lastChecked: '20 Sep 2026', referenceNumber: 'UDISE-33090100412', details: '10th & 12th Board Records Authenticated' },
  { id: 'V8', title: 'Single Scheme Conflict Verification', authority: 'SMPT Federated Central Engine', status: 'VERIFIED', lastChecked: '28 Sep 2026', referenceNumber: 'CLEARED-NO-DUPLICATE', details: 'No active duplicate benefit from NSP or State Portals' }
];

export const MOCK_GOVERNMENT_SOURCES: GovernmentDataSource[] = [
  { id: 'S1', name: 'DigiLocker National Wallet', shortName: 'DigiLocker', description: 'Real-time fetching and electronic verification of ST Caste Certificates and Marksheets.', integratedData: ['ST Certificate', 'Class X Marksheet', 'Class XII Marksheet'], status: 'OPERATIONAL' },
  { id: 'S2', name: 'All India Survey on Higher Education', shortName: 'AISHE', description: 'Institution authentication and course code validation engine.', integratedData: ['College AISHE Code', 'Course Duration', 'Affiliation Status'], status: 'OPERATIONAL' },
  { id: 'S3', name: 'Unified District Information System for Education Plus', shortName: 'UDISE+', description: 'School level student enrolment and pre-matric history track.', integratedData: ['School Code', 'Pre-Matric Completion Status'], status: 'OPERATIONAL' },
  { id: 'S4', name: 'Automated Permanent Academic Account Registry', shortName: 'APAAR', description: 'One Nation One Student ID system storing lifelong academic credentials.', integratedData: ['Credit Ledger', 'Student Lifecycle ID'], status: 'SYNCED' },
  { id: 'S5', name: 'Unique Identification Authority of India', shortName: 'UIDAI', description: 'Consent-based demographic e-KYC and biometric verification.', integratedData: ['Aadhaar Masked ID', 'Name & DOB Matching'], status: 'OPERATIONAL' },
  { id: 'S6', name: 'State Revenue e-District Portals', shortName: 'e-District', description: 'Direct API validation of ST Community certificates and Income declarations.', integratedData: ['Income Certificate', 'Native Certificate'], status: 'OPERATIONAL' },
  { id: 'S7', name: 'UGC - National Testing Agency Engine', shortName: 'UGC / NTA', description: 'Fellowship eligibility and PhD registration verification for NFST/NOS.', integratedData: ['NET Score', 'PhD Enrolment Code'], status: 'SYNCED' },
  { id: 'S8', name: 'One-Time Registration Engine', shortName: 'OTR', description: 'Unified single-sign-on identity system eliminating multi-portal logins.', integratedData: ['Single Student ID', 'Cross-System Auth'], status: 'OPERATIONAL' }
];

export const MOCK_EXISTING_SYSTEMS: ExistingSystem[] = [
  { id: 'E1', name: 'National Scholarship Portal', shortName: 'NSP 2.0', purpose: 'Centralized government scholarship application portal', integrationStatus: 'FEDERATED', roleInSMPT: 'Cross-checks duplicate scheme claims to prevent double dipping.' },
  { id: 'E2', name: 'State Tribal Welfare Portals', shortName: 'SFMP (State Systems)', purpose: 'State-managed tribal welfare disbursement systems', integrationStatus: 'FEDERATED', roleInSMPT: 'Syncs state-share funding (60:40 ratio) seamlessly into SMPT.' },
  { id: 'E3', name: 'NOS International Portal', shortName: 'NOS MoTA Cell', purpose: 'Legacy portal for National Overseas Scholarship for ST students', integrationStatus: 'MIGRATED', roleInSMPT: 'Fully unified into SMPT single window for international studies.' }
];

export const MOCK_PAYMENTS: Payment[] = [
  {
    id: 'PAY-2026-9901',
    applicationId: 'APP-2026-1156',
    schemeName: 'Post-Matric Scholarship for ST Students',
    amount: 12000,
    date: 'Expected 15 Nov 2026',
    academicYear: '2025-2026 (Instalment 1)',
    status: 'PROCESSING',
    transactionRefLast4: 'PENDING',
    bankAccountMasked: 'Canara Bank (****9812)',
    dbtStatus: 'PFMS Batch Generated #44891'
  },
  {
    id: 'PAY-2025-4412',
    applicationId: 'APP-2025-8812',
    schemeName: 'Post-Matric Scholarship for ST Students',
    amount: 24000,
    date: '15 Jan 2026',
    academicYear: '2024-2025 (Final Batch)',
    status: 'CREDITED',
    transactionRefLast4: '7781',
    bankAccountMasked: 'Canara Bank (****9812)',
    dbtStatus: 'Successfully Credited via Aadhaar Direct Benefit Transfer'
  }
];

export const MOCK_DOCUMENTS: DocumentItem[] = [
  { id: 'DOC-1', title: 'ST Tribe Community Certificate', type: 'Caste Certificate', issuer: 'Sub-Collector, Nilgiris, TN', status: 'VERIFIED', issueDate: '14 May 2022', source: 'DIGILOCKER', documentNumberMasked: 'TN-ST-XXXX-7718' },
  { id: 'DOC-2', title: 'Annual Family Income Certificate', type: 'Income Certificate', issuer: 'Tahsildar, Ooty', status: 'PENDING', issueDate: '10 Apr 2024', source: 'STATE_PORTAL', documentNumberMasked: 'INC-2024-XXXX-9912' },
  { id: 'DOC-3', title: 'HSC (Class XII) Statement of Marks', type: 'Academic Marksheet', issuer: 'Tamil Nadu State Board', status: 'VERIFIED', issueDate: '20 Jun 2022', source: 'DIGILOCKER', documentNumberMasked: 'TN-HSC-XXXX-4491' },
  { id: 'DOC-4', title: 'College Enrolment Fee Receipt', type: 'Fee Receipt', issuer: 'Govt Arts & Science College, Ooty', status: 'VERIFIED', issueDate: '05 Jul 2025', source: 'MANUAL_UPLOAD', documentNumberMasked: 'FEE-2025-XXXX-1102' }
];

export const MOCK_NOTIFICATIONS: NotificationItem[] = [
  { id: 'N1', title: 'Action Required: Income Certificate Flag', message: 'District Officer requested updated Income Certificate for application APP-2026-1156.', timestamp: '2 hours ago', category: 'ACTION', read: false, actionRoute: 'Documents' },
  { id: 'N2', title: 'JAGO Assistant Notice', message: 'Your college verified your attendance (88%). No attendance deficiency.', timestamp: '1 day ago', category: 'STATUS', read: false },
  { id: 'N3', title: 'Scholarship Application Update', message: 'Application APP-2026-1156 moved to District Tribal Welfare Verification.', timestamp: '3 days ago', category: 'STATUS', read: true },
  { id: 'N4', title: 'DBT Payment Disbursed', message: '₹24,000 successfully credited to your Canara Bank account (****9812).', timestamp: '15 Jan 2026', category: 'PAYMENT', read: true, actionRoute: 'Payments' }
];

export const MOCK_CHAT_MESSAGES: ChatMessage[] = [
  {
    id: 'M1',
    sender: 'JAGO',
    text: 'Namaste Arjun! I am JAGO, your Ministry of Tribal Affairs Scholarship Assistant. How can I help you today?',
    timestamp: '10:00 AM',
    suggestedActions: [
      { label: 'Check My Application Status', route: 'Applications' },
      { label: 'Resolve Income Deficiency', route: 'Documents' },
      { label: 'Am I Eligible for NFST / NOS?', route: 'Scholarships' }
    ]
  }
];

export const MOCK_PROACTIVE_ALERTS: ProactiveAlert[] = [
  {
    id: 'ALT-1',
    title: 'Income Certificate Expiry Alert',
    message: 'Your Income Certificate from State e-District needs renewal for 2026-27.',
    severity: 'HIGH',
    actionRoute: 'Documents',
    actionLabel: 'Update Document'
  },
  {
    id: 'ALT-2',
    title: 'Top Class Education Scheme Eligibility',
    message: 'Did you know ST students in premier institutes get full tuition waiver?',
    severity: 'INFO',
    actionRoute: 'Scholarships',
    actionLabel: 'View Schemes'
  }
];

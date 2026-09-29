export interface Student {
  id: string;
  name: string;
  category: string;
  gender: string;
  dob: string;
  state: string;
  district: string;
  institution: string;
  course: string;
  academicYear: string;
  familyIncome: number;
  mobile: string;
  email: string;
  apaarId: string;
  isSTVerified: boolean;
  isIncomeVerified: boolean;
  isInstitutionVerified: boolean;
  isBankDBTLinked: boolean;
}

export interface ScholarshipScheme {
  id: string;
  name: string;
  shortCode: string;
  level: string; // Pre-Matric, Post-Matric, Higher Ed, Overseas, Top Class
  minIncomeLimit: number; // Max annual family income
  stCategoryOnly: boolean;
  minAttendance: number; // percentage
  benefitDescription: string;
  amountPerYear: number;
  deadline: string;
  applicationMode: string;
  verifyingAuthorities: string[];
}

export interface ApplicationStage {
  stageId: number;
  title: string;
  status: 'COMPLETED' | 'IN_PROGRESS' | 'PENDING' | 'ACTION_REQUIRED';
  dateCompleted?: string;
  remarks?: string;
}

export interface Deficiency {
  id: string;
  title: string;
  description: string;
  sourceAuthority: string; // e.g. "DigiLocker", "State e-District", "Institution"
  stage: string;
  severity: 'CRITICAL' | 'WARNING' | 'INFO';
  resolved: boolean;
  resolutionAction: string;
}

export interface ScholarshipApplication {
  id: string;
  schemeId: string;
  schemeName: string;
  applicationDate: string;
  academicYear: string;
  status: 'SUBMITTED' | 'INSTITUTION_VERIFIED' | 'DISTRICT_VERIFIED' | 'STATE_VERIFIED' | 'APPROVED' | 'DISBURSED' | 'DEFICIENCY_RAISED';
  stages: ApplicationStage[];
  deficiencies: Deficiency[];
  nextAction: string;
  estimatedDisbursement: string;
}

export interface VerificationItem {
  id: string;
  title: string;
  authority: string; // DigiLocker, AISHE, UDISE+, APAAR, UIDAI, etc.
  status: 'VERIFIED' | 'PENDING' | 'MISMATCH' | 'NOT_LINKED';
  lastChecked: string;
  referenceNumber: string;
  details: string;
}

export interface GovernmentDataSource {
  id: string;
  name: string;
  shortName: string;
  description: string;
  integratedData: string[];
  status: 'OPERATIONAL' | 'SYNCED' | 'MAINTENANCE';
}

export interface ExistingSystem {
  id: string;
  name: string;
  shortName: string;
  purpose: string;
  integrationStatus: 'MIGRATED' | 'FEDERATED' | 'DEPRECATED_SWAPPED';
  roleInEklavyaOne: string;
}

export interface Payment {
  id: string;
  applicationId: string;
  schemeName: string;
  amount: number;
  date: string;
  academicYear: string;
  status: 'CREDITED' | 'PROCESSING' | 'PENDING' | 'FAILED';
  transactionRefLast4: string;
  bankAccountMasked: string;
  dbtStatus: string;
}

export interface DocumentItem {
  id: string;
  title: string;
  type: string;
  issuer: string;
  status: 'VERIFIED' | 'PENDING' | 'REJECTED';
  issueDate: string;
  source: 'DIGILOCKER' | 'MANUAL_UPLOAD' | 'STATE_PORTAL';
  documentNumberMasked: string;
}

export interface NotificationItem {
  id: string;
  title: string;
  message: string;
  timestamp: string;
  category: 'ACTION' | 'STATUS' | 'PAYMENT' | 'ANNOUNCEMENT';
  read: boolean;
  actionRoute?: string;
}

export interface ChatMessage {
  id: string;
  sender: 'USER' | 'JAGO';
  text: string;
  timestamp: string;
  suggestedActions?: { label: string; route: string }[];
}

export interface ProactiveAlert {
  id: string;
  title: string;
  message: string;
  severity: 'HIGH' | 'MEDIUM' | 'INFO';
  actionRoute: string;
  actionLabel: string;
}

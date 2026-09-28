import React, { createContext, useContext, useState } from 'react';
import {
  Student,
  ScholarshipScheme,
  ScholarshipApplication,
  VerificationItem,
  Payment,
  DocumentItem,
  NotificationItem,
  ChatMessage,
  ProactiveAlert
} from '../data/types';
import {
  MOCK_STUDENT,
  MOCK_SCHEMES,
  MOCK_APPLICATIONS,
  MOCK_VERIFICATION_ITEMS,
  MOCK_GOVERNMENT_SOURCES,
  MOCK_EXISTING_SYSTEMS,
  MOCK_PAYMENTS,
  MOCK_DOCUMENTS,
  MOCK_NOTIFICATIONS,
  MOCK_CHAT_MESSAGES,
  MOCK_PROACTIVE_ALERTS
} from '../data/mockData';

interface AppContextType {
  isLoggedIn: boolean;
  hasCompletedOnboarding: boolean;
  hasCompletedProfileSetup: boolean;
  isOffline: boolean;
  student: Student;
  schemes: ScholarshipScheme[];
  applications: ScholarshipApplication[];
  verifications: VerificationItem[];
  payments: Payment[];
  documents: DocumentItem[];
  notifications: NotificationItem[];
  chatMessages: ChatMessage[];
  proactiveAlerts: ProactiveAlert[];
  
  // Actions
  login: (mobile: string, otp: string) => boolean;
  demoLogin: () => void;
  logout: () => void;
  completeOnboarding: () => void;
  completeProfileSetup: () => void;
  toggleOffline: () => void;
  sendJagoMessage: (text: string) => void;
  resolveDeficiency: (deficiencyId: string) => void;
  uploadDocument: (title: string, type: string) => void;
  markNotificationRead: (notificationId: string) => void;
}

const AppContext = createContext<AppContextType | undefined>(undefined);

export const AppProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [isLoggedIn, setIsLoggedIn] = useState(true); // Default to logged in for easy prototype testing
  const [hasCompletedOnboarding, setHasCompletedOnboarding] = useState(true);
  const [hasCompletedProfileSetup, setHasCompletedProfileSetup] = useState(true);
  const [isOffline, setIsOffline] = useState(false);

  const [student, setStudent] = useState<Student>(MOCK_STUDENT);
  const [schemes] = useState<ScholarshipScheme[]>(MOCK_SCHEMES);
  const [applications, setApplications] = useState<ScholarshipApplication[]>(MOCK_APPLICATIONS);
  const [verifications] = useState<VerificationItem[]>(MOCK_VERIFICATION_ITEMS);
  const [payments] = useState<Payment[]>(MOCK_PAYMENTS);
  const [documents, setDocuments] = useState<DocumentItem[]>(MOCK_DOCUMENTS);
  const [notifications, setNotifications] = useState<NotificationItem[]>(MOCK_NOTIFICATIONS);
  const [chatMessages, setChatMessages] = useState<ChatMessage[]>(MOCK_CHAT_MESSAGES);
  const [proactiveAlerts] = useState<ProactiveAlert[]>(MOCK_PROACTIVE_ALERTS);

  const login = (mobile: string, otp: string) => {
    if (otp === '123456' || otp.length === 6) {
      setIsLoggedIn(true);
      return true;
    }
    return false;
  };

  const demoLogin = () => {
    setIsLoggedIn(true);
    setHasCompletedOnboarding(true);
    setHasCompletedProfileSetup(true);
  };

  const logout = () => {
    setIsLoggedIn(false);
  };

  const completeOnboarding = () => {
    setHasCompletedOnboarding(true);
  };

  const completeProfileSetup = () => {
    setHasCompletedProfileSetup(true);
  };

  const toggleOffline = () => {
    setIsOffline(prev => !prev);
  };

  const sendJagoMessage = (text: string) => {
    const userMsg: ChatMessage = {
      id: `MSG-${Date.now()}`,
      sender: 'USER',
      text: text,
      timestamp: new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })
    };

    setChatMessages(prev => [...prev, userMsg]);

    // Generate intelligent AI JAGO reply
    setTimeout(() => {
      let replyText = 'I am JAGO, your MoTA Scholarship Assistant. All 5 MoTA ST schemes (Pre-Matric, Post-Matric, NFST, NOS, Top Class) are active on SMPT.';
      const lower = text.toLowerCase();
      
      if (lower.includes('income') || lower.includes('deficiency') || lower.includes('renew')) {
        replyText = 'Regarding your Income Certificate: District Welfare Officer flagged expiry. You can update your income details via DigiLocker or upload a signed certificate under My Documents.';
      } else if (lower.includes('payment') || lower.includes('dbt') || lower.includes('money')) {
        replyText = 'Your DBT Payment of ₹24,000 was credited on 15 Jan 2026. The next instalment of ₹12,000 for AY 2025-26 is currently in PFMS Batch processing.';
      } else if (lower.includes('status') || lower.includes('application')) {
        replyText = 'Your active application APP-2026-1156 is currently at Stage 4 (District Tribal Welfare Verification). Once the income document is re-verified, it proceeds to State Nodal Approval.';
      } else if (lower.includes('nfst') || lower.includes('phd') || lower.includes('overseas') || lower.includes('nos')) {
        replyText = 'ST students pursuing M.Phil/Ph.D or studying abroad are eligible for NFST (₹3.7 Lakh/yr) or NOS (₹18 Lakh/yr). Single Scheme rules apply - you cannot hold two central ST scholarships concurrently.';
      }

      const jagoMsg: ChatMessage = {
        id: `MSG-${Date.now() + 1}`,
        sender: 'JAGO',
        text: replyText,
        timestamp: new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })
      };
      setChatMessages(prev => [...prev, jagoMsg]);
    }, 600);
  };

  const resolveDeficiency = (deficiencyId: string) => {
    setApplications(prev =>
      prev.map(app => ({
        ...app,
        deficiencies: app.deficiencies.map(def =>
          def.id === deficiencyId ? { ...def, resolved: true } : def
        )
      }))
    );
  };

  const uploadDocument = (title: string, type: string) => {
    const newDoc: DocumentItem = {
      id: `DOC-${Date.now()}`,
      title,
      type,
      issuer: 'State e-District / DigiLocker',
      status: 'VERIFIED',
      issueDate: '28 Sep 2026',
      source: 'DIGILOCKER',
      documentNumberMasked: 'DOC-2026-XXXX-9901'
    };
    setDocuments(prev => [newDoc, ...prev]);
  };

  const markNotificationRead = (notificationId: string) => {
    setNotifications(prev =>
      prev.map(n => (n.id === notificationId ? { ...n, read: true } : n))
    );
  };

  return (
    <AppContext.Provider
      value={{
        isLoggedIn,
        hasCompletedOnboarding,
        hasCompletedProfileSetup,
        isOffline,
        student,
        schemes,
        applications,
        verifications,
        payments,
        documents,
        notifications,
        chatMessages,
        proactiveAlerts,
        login,
        demoLogin,
        logout,
        completeOnboarding,
        completeProfileSetup,
        toggleOffline,
        sendJagoMessage,
        resolveDeficiency,
        uploadDocument,
        markNotificationRead
      }}
    >
      {children}
    </AppContext.Provider>
  );
};

export const useApp = () => {
  const context = useContext(AppContext);
  if (!context) {
    throw new Error('useApp must be used within an AppProvider');
  }
  return context;
};

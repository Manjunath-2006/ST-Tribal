import React, { useState } from 'react';
import { View, Text, StyleSheet, ScrollView, TouchableOpacity, Alert } from 'react-native';
import { useApp } from '../context/AppContext';
import { Colors } from '../theme/colors';
import {
  GovernmentHeader,
  PrototypeTag,
  StatusBadge,
  InfoRow,
  PrimaryButton,
  SecondaryButton,
  InfoBanner
} from '../components/Components';
import { Ionicons } from '@expo/vector-icons';
import { MOCK_GOVERNMENT_SOURCES, MOCK_EXISTING_SYSTEMS } from '../data/mockData';

// ─── SCHOLARSHIP DETAIL ──────────────────────────────────────
export const ScholarshipDetailScreen: React.FC<{ route: any; navigation: any }> = ({
  route,
  navigation
}) => {
  const { schemeId } = route.params || {};
  const { schemes, applications } = useApp();
  const scheme = schemes.find(s => s.id === schemeId) || schemes[0];
  const hasApplied = applications.some(a => a.schemeId === scheme.id);

  return (
    <View style={styles.container}>
      <GovernmentHeader title={scheme.shortCode} showBack onBack={() => navigation.goBack()} />

      <ScrollView style={styles.content} contentContainerStyle={{ paddingBottom: 30 }}>
        <Text style={styles.schemeTitle}>{scheme.name}</Text>
        <Text style={styles.amountText}>₹{scheme.amountPerYear.toLocaleString('en-IN')} / year</Text>

        <PrototypeTag />

        <View style={styles.sectionCard}>
          <Text style={styles.cardSectionTitle}>Scheme Summary</Text>
          <Text style={styles.descText}>{scheme.benefitDescription}</Text>
          <InfoRow label="Academic Level" value={scheme.level} />
          <InfoRow label="Annual Family Income Ceiling" value={`₹${scheme.minIncomeLimit.toLocaleString('en-IN')}`} />
          <InfoRow label="Category Requirement" value={scheme.stCategoryOnly ? 'Scheduled Tribe (ST) Only' : 'All'} />
          <InfoRow label="Min. Attendance Required" value={`${scheme.minAttendance}%`} />
          <InfoRow label="Application Deadline" value={scheme.deadline} />
          <InfoRow label="Application Mode" value={scheme.applicationMode} />
        </View>

        <View style={styles.sectionCard}>
          <Text style={styles.cardSectionTitle}>Verifying Authorities</Text>
          {scheme.verifyingAuthorities.map((auth, idx) => (
            <View key={idx} style={styles.bulletRow}>
              <Ionicons name="checkmark-circle" size={16} color={Colors.govBlue} />
              <Text style={styles.bulletText}>{auth}</Text>
            </View>
          ))}
        </View>

        <PrimaryButton
          title="Check Eligibility & Apply"
          onPress={() => navigation.navigate('EligibilityCheck', { schemeId: scheme.id })}
          icon="checkmark-done-circle-outline"
          style={{ marginVertical: 8 }}
        />

        <SecondaryButton
          title="Check Single Scheme Conflicts"
          onPress={() => navigation.navigate('ConflictCheck', { targetSchemeId: scheme.id })}
          icon="shield-outline"
        />
      </ScrollView>
    </View>
  );
};

// ─── ELIGIBILITY CHECK ──────────────────────────────────────
export const EligibilityCheckScreen: React.FC<{ route: any; navigation: any }> = ({
  route,
  navigation
}) => {
  const { schemeId } = route.params || {};
  const { schemes, student } = useApp();
  const scheme = schemes.find(s => s.id === schemeId) || schemes[0];

  const isIncomeEligible = student.familyIncome <= scheme.minIncomeLimit;

  return (
    <View style={styles.container}>
      <GovernmentHeader title="Eligibility Evaluation" showBack onBack={() => navigation.goBack()} />

      <ScrollView style={styles.content} contentContainerStyle={{ paddingBottom: 30 }}>
        <Text style={styles.schemeTitle}>{scheme.name}</Text>

        <View style={styles.evalBanner}>
          <Ionicons name="sparkles" size={24} color={Colors.statusGreen} />
          <View style={{ marginLeft: 10, flex: 1 }}>
            <Text style={styles.evalTitle}>You Are Likely Eligible!</Text>
            <Text style={styles.evalDesc}>
              Automated match against DigiLocker ST record & AISHE enrolment.
            </Text>
          </View>
        </View>

        <PrototypeTag />

        <Text style={styles.cardSectionTitle}>Eligibility Criteria Breakdown</Text>

        <View style={styles.criterionCard}>
          <Ionicons name="checkmark-circle" size={22} color={Colors.statusGreen} />
          <View style={{ flex: 1, marginLeft: 10 }}>
            <Text style={styles.critTitle}>ST Community Certificate</Text>
            <Text style={styles.critDesc}>Verified via DigiLocker / TN e-Sevai (Nilgiris ST Record)</Text>
          </View>
        </View>

        <View style={styles.criterionCard}>
          <Ionicons
            name={isIncomeEligible ? 'checkmark-circle' : 'warning'}
            size={22}
            color={isIncomeEligible ? Colors.statusGreen : Colors.statusAmber}
          />
          <View style={{ flex: 1, marginLeft: 10 }}>
            <Text style={styles.critTitle}>Annual Family Income Limit</Text>
            <Text style={styles.critDesc}>
              Your Income: ₹{student.familyIncome.toLocaleString('en-IN')} (Limit: ₹{scheme.minIncomeLimit.toLocaleString('en-IN')})
            </Text>
          </View>
        </View>

        <View style={styles.criterionCard}>
          <Ionicons name="checkmark-circle" size={22} color={Colors.statusGreen} />
          <View style={{ flex: 1, marginLeft: 10 }}>
            <Text style={styles.critTitle}>Higher Education Enrolment</Text>
            <Text style={styles.critDesc}>Verified via AISHE (Govt Arts & Science College, Ooty)</Text>
          </View>
        </View>

        <PrimaryButton
          title="Proceed to Single Scheme Conflict Check"
          onPress={() => navigation.navigate('ConflictCheck', { targetSchemeId: scheme.id })}
          icon="arrow-forward"
          style={{ marginTop: 20 }}
        />
      </ScrollView>
    </View>
  );
};

// ─── CONFLICT CHECK ──────────────────────────────────────────
export const ConflictCheckScreen: React.FC<{ route: any; navigation: any }> = ({
  route,
  navigation
}) => {
  const { targetSchemeId } = route.params || {};
  const { schemes, applications } = useApp();
  const targetScheme = schemes.find(s => s.id === targetSchemeId) || schemes[0];
  const activeApp = applications.find(a => a.status !== 'DISBURSED');

  return (
    <View style={styles.container}>
      <GovernmentHeader title="Single Scheme Enforcement" showBack onBack={() => navigation.goBack()} />

      <ScrollView style={styles.content} contentContainerStyle={{ paddingBottom: 30 }}>
        <Text style={styles.schemeTitle}>Conflict Check with NSP & State Portals</Text>

        <PrototypeTag />

        <InfoBanner
          type="WARNING"
          text="Rule: A student can hold ONLY ONE active central/state ST scholarship concurrently to prevent duplicate benefit claims."
        />

        {activeApp ? (
          <View style={styles.conflictCard}>
            <View style={{ flexDirection: 'row', alignItems: 'center', marginBottom: 8 }}>
              <Ionicons name="warning" size={22} color={Colors.statusAmber} />
              <Text style={styles.conflictCardTitle}>Active Scholarship Detected</Text>
            </View>
            <Text style={styles.conflictText}>
              You have an active application under <Text style={{ fontWeight: '700' }}>{activeApp.schemeName}</Text>.
            </Text>
            <Text style={styles.conflictSubtext}>
              Applying for <Text style={{ fontWeight: '700' }}>{targetScheme.name}</Text> will require withdrawing or surrendering the current scheme once approved.
            </Text>
          </View>
        ) : (
          <View style={styles.noConflictCard}>
            <Ionicons name="checkmark-circle" size={26} color={Colors.statusGreen} />
            <Text style={styles.noConflictTitle}>No Conflict Detected</Text>
            <Text style={styles.noConflictText}>
              You have no active duplicate scholarship claims across NSP, SFMP, or MoTA systems.
            </Text>
          </View>
        )}

        <PrimaryButton
          title="Proceed to Application Readiness Checklist"
          onPress={() =>
            navigation.navigate('ApplicationReadiness', {
              applicationId: activeApp?.id || 'APP-2026-1156'
            })
          }
          icon="shield-checkmark-outline"
          style={{ marginTop: 16 }}
        />
      </ScrollView>
    </View>
  );
};

// ─── APPLICATION READINESS ──────────────────────────────────
export const ApplicationReadinessScreen: React.FC<{ route: any; navigation: any }> = ({
  route,
  navigation
}) => {
  const { applicationId } = route.params || {};

  return (
    <View style={styles.container}>
      <GovernmentHeader title="8-Point Application Readiness" showBack onBack={() => navigation.goBack()} />

      <ScrollView style={styles.content} contentContainerStyle={{ paddingBottom: 30 }}>
        <Text style={styles.subtitle}>Pre-Submission Audit Checklist</Text>

        <PrototypeTag />

        <View style={styles.checklist}>
          <CheckItem title="Aadhaar Identity e-KYC" status="PASS" desc="UIDAI Name & DOB 100% Match" />
          <CheckItem title="ST Community Certificate" status="PASS" desc="DigiLocker Record Validated" />
          <CheckItem title="Income Certificate Expiry" status="WARNING" desc="Valid till Mar 2026 (Needs Renewal)" />
          <CheckItem title="College AISHE Enrolment" status="PASS" desc="Code C-24891 Authenticated" />
          <CheckItem title="APAAR Academic Credit ID" status="PASS" desc="APAAR-9908-1123-4567 Synced" />
          <CheckItem title="Bank Aadhaar Seeding (DBT)" status="PASS" desc="Canara Bank ****9812 Active" />
          <CheckItem title="UDISE+ School Record" status="PASS" desc="Class X & XII Board Validated" />
          <CheckItem title="Single Scheme Enforcement" status="PASS" desc="No Duplicate Claim Flagged" />
        </View>

        <PrimaryButton
          title="Submit / Update Application"
          onPress={() => {
            Alert.alert('Application Updated', 'Your application details have been resubmitted to the District Nodal Officer.');
            navigation.navigate('Home');
          }}
          icon="send-outline"
          style={{ marginTop: 20 }}
        />
      </ScrollView>
    </View>
  );
};

const CheckItem: React.FC<{ title: string; status: 'PASS' | 'WARNING'; desc: string }> = ({
  title,
  status,
  desc
}) => (
  <View style={styles.checkRow}>
    <Ionicons
      name={status === 'PASS' ? 'checkmark-circle' : 'alert-circle'}
      size={22}
      color={status === 'PASS' ? Colors.statusGreen : Colors.statusAmber}
    />
    <View style={{ flex: 1, marginLeft: 10 }}>
      <Text style={styles.checkTitle}>{title}</Text>
      <Text style={styles.checkDesc}>{desc}</Text>
    </View>
  </View>
);

// ─── APPLICATION DETAIL ─────────────────────────────────────
export const ApplicationDetailScreen: React.FC<{ route: any; navigation: any }> = ({
  route,
  navigation
}) => {
  const { applicationId } = route.params || {};
  const { applications } = useApp();
  const app = applications.find(a => a.id === applicationId) || applications[0];

  return (
    <View style={styles.container}>
      <GovernmentHeader title="Application Tracking" showBack onBack={() => navigation.goBack()} />

      <ScrollView style={styles.content} contentContainerStyle={{ paddingBottom: 30 }}>
        <View style={styles.detailHeader}>
          <Text style={styles.schemeTitle}>{app.schemeName}</Text>
          <StatusBadge status={app.status} />
        </View>

        <PrototypeTag />

        <InfoRow label="Application ID" value={app.id} />
        <InfoRow label="Date Submitted" value={app.applicationDate} />
        <InfoRow label="Academic Year" value={app.academicYear} />
        <InfoRow label="Next Required Step" value={app.nextAction} valueColor={Colors.govBlue} />

        <Text style={styles.cardSectionTitle}>Stages Timeline</Text>
        {app.stages.map(stage => (
          <View key={stage.stageId} style={styles.stageRow}>
            <Ionicons
              name={
                stage.status === 'COMPLETED'
                  ? 'checkmark-circle'
                  : stage.status === 'IN_PROGRESS'
                  ? 'time'
                  : 'ellipse-outline'
              }
              size={22}
              color={
                stage.status === 'COMPLETED'
                  ? Colors.statusGreen
                  : stage.status === 'IN_PROGRESS'
                  ? Colors.statusBlue
                  : Colors.textMuted
              }
            />
            <View style={{ flex: 1, marginLeft: 10 }}>
              <Text style={styles.stageTitle}>{stage.title}</Text>
              {stage.dateCompleted && <Text style={styles.stageDate}>Completed: {stage.dateCompleted}</Text>}
              {stage.remarks && <Text style={styles.stageRemarks}>{stage.remarks}</Text>}
            </View>
          </View>
        ))}
      </ScrollView>
    </View>
  );
};

// ─── PAYMENT DETAIL ─────────────────────────────────────────
export const PaymentDetailScreen: React.FC<{ route: any; navigation: any }> = ({
  route,
  navigation
}) => {
  const { paymentId } = route.params || {};
  const { payments } = useApp();
  const pay = payments.find(p => p.id === paymentId) || payments[0];

  return (
    <View style={styles.container}>
      <GovernmentHeader title="Payment Transaction Detail" showBack onBack={() => navigation.goBack()} />

      <ScrollView style={styles.content} contentContainerStyle={{ paddingBottom: 30 }}>
        <View style={styles.payBox}>
          <Text style={styles.payAmount}>₹{pay.amount.toLocaleString('en-IN')}</Text>
          <StatusBadge status={pay.status} />
        </View>

        <PrototypeTag />

        <InfoRow label="Scheme Name" value={pay.schemeName} />
        <InfoRow label="Disbursement Date" value={pay.date} />
        <InfoRow label="Academic Year" value={pay.academicYear} />
        <InfoRow label="Bank Account" value={pay.bankAccountMasked} />
        <InfoRow label="Transaction Ref (Last 4)" value={pay.transactionRefLast4} />
        <InfoRow label="DBT Status" value={pay.dbtStatus} valueColor={Colors.govBlue} />
      </ScrollView>
    </View>
  );
};

// ─── SECONDARY INFORMATIONAL SCREENS ────────────────────────
export const MinistryInsightsScreen: React.FC<{ navigation: any }> = ({ navigation }) => (
  <View style={styles.container}>
    <GovernmentHeader title="Ministry of Tribal Affairs Insights" showBack onBack={() => navigation.goBack()} />
    <ScrollView style={styles.content} contentContainerStyle={{ paddingBottom: 30 }}>
      <Text style={styles.sectionTitle}>MoTA Dashboard Statistics 2025-26</Text>
      <PrototypeTag />
      <InfoRow label="Total ST Beneficiaries Covered" value="35.4 Lakh Students" />
      <InfoRow label="Total DBT Funds Disbursed" value="₹2,480 Crores" />
      <InfoRow label="Average Application Clearance Time" value="14 Days (via EKLAVYAONE)" />
      <InfoRow label="DigiLocker Verification Accuracy" value="99.4%" />
    </ScrollView>
  </View>
);

export const ScholarshipAwarenessScreen: React.FC<{ navigation: any }> = ({ navigation }) => (
  <View style={styles.container}>
    <GovernmentHeader title="ST Scholarship Awareness" showBack onBack={() => navigation.goBack()} />
    <ScrollView style={styles.content} contentContainerStyle={{ paddingBottom: 30 }}>
      <Text style={styles.sectionTitle}>Know Your Scholarship Rights</Text>
      <PrototypeTag />
      <Text style={styles.descText}>
        Under the Ministry of Tribal Affairs, Scheduled Tribe students across India have access to 5 key schemes supporting school, college, doctoral, and international studies.
      </Text>
    </ScrollView>
  </View>
);

export const ExistingSystemsScreen: React.FC<{ navigation: any }> = ({ navigation }) => (
  <View style={styles.container}>
    <GovernmentHeader title="Existing Portals Integration" showBack onBack={() => navigation.goBack()} />
    <ScrollView style={styles.content} contentContainerStyle={{ paddingBottom: 30 }}>
      <Text style={styles.sectionTitle}>NSP, SFMP & NOS Portals Unified</Text>
      <PrototypeTag />
      {MOCK_EXISTING_SYSTEMS.map(sys => (
        <View key={sys.id} style={styles.sectionCard}>
          <Text style={styles.cardSectionTitle}>{sys.name} ({sys.shortName})</Text>
          <Text style={styles.descText}>{sys.purpose}</Text>
          <InfoRow label="Integration Status" value={sys.integrationStatus} />
          <InfoRow label="Role in EKLAVYAONE" value={sys.roleInEklavyaOne} />
        </View>
      ))}
    </ScrollView>
  </View>
);

export const HelpScreen: React.FC<{ navigation: any }> = ({ navigation }) => (
  <View style={styles.container}>
    <GovernmentHeader title="Help & Support Hotline" showBack onBack={() => navigation.goBack()} />
    <ScrollView style={styles.content} contentContainerStyle={{ paddingBottom: 30 }}>
      <Text style={styles.sectionTitle}>MoTA Toll-Free Helpline</Text>
      <PrototypeTag />
      <InfoRow label="National Toll-Free" value="1800-11-7788" />
      <InfoRow label="MoTA Support Email" value="support-eklavyaone@mota.gov.in" />
      <InfoRow label="Working Hours" value="9:30 AM - 6:00 PM (Mon-Fri)" />
    </ScrollView>
  </View>
);

const styles = StyleSheet.create({
  container: { flex: 1, backgroundColor: Colors.surfaceLight },
  content: { padding: 16 },
  subtitle: { fontSize: 13, color: Colors.textSecondary, marginBottom: 8 },
  schemeTitle: { fontSize: 18, fontWeight: '800', color: Colors.govNavy, marginBottom: 4 },
  amountText: { fontSize: 16, fontWeight: '800', color: Colors.statusGreen, marginBottom: 10 },
  sectionCard: {
    backgroundColor: Colors.cardBackground,
    borderRadius: 8,
    padding: 14,
    marginVertical: 6,
    borderWidth: 1,
    borderColor: Colors.surfaceBorder
  },
  cardSectionTitle: { fontSize: 15, fontWeight: '700', color: Colors.govNavy, marginBottom: 8 },
  descText: { fontSize: 13, color: Colors.textSecondary, lineHeight: 18, marginBottom: 10 },
  bulletRow: { flexDirection: 'row', alignItems: 'center', marginVertical: 3 },
  bulletText: { fontSize: 13, color: Colors.textPrimary, marginLeft: 8 },
  evalBanner: {
    backgroundColor: Colors.statusGreenBg,
    borderRadius: 8,
    padding: 14,
    flexDirection: 'row',
    alignItems: 'center',
    marginVertical: 10
  },
  evalTitle: { fontSize: 15, fontWeight: '800', color: Colors.statusGreen },
  evalDesc: { fontSize: 12, color: Colors.textSecondary, marginTop: 2 },
  criterionCard: {
    backgroundColor: Colors.cardBackground,
    borderRadius: 8,
    padding: 12,
    flexDirection: 'row',
    alignItems: 'center',
    marginVertical: 4,
    borderWidth: 1,
    borderColor: Colors.surfaceBorder
  },
  critTitle: { fontSize: 14, fontWeight: '700', color: Colors.govNavy },
  critDesc: { fontSize: 12, color: Colors.textSecondary, marginTop: 2 },
  conflictCard: {
    backgroundColor: Colors.statusAmberBg,
    borderRadius: 8,
    padding: 14,
    marginVertical: 10,
    borderLeftWidth: 4,
    borderLeftColor: Colors.statusAmber
  },
  conflictCardTitle: { fontSize: 15, fontWeight: '700', color: Colors.statusAmber, marginLeft: 6 },
  conflictText: { fontSize: 13, color: Colors.textPrimary, lineHeight: 18 },
  conflictSubtext: { fontSize: 12, color: Colors.textSecondary, marginTop: 6 },
  noConflictCard: {
    backgroundColor: Colors.statusGreenBg,
    borderRadius: 8,
    padding: 16,
    alignItems: 'center',
    marginVertical: 10
  },
  noConflictTitle: { fontSize: 16, fontWeight: '800', color: Colors.statusGreen, marginTop: 6 },
  noConflictText: { fontSize: 12, color: Colors.textSecondary, textAlign: 'center', marginTop: 4 },
  checklist: { marginVertical: 10 },
  checkRow: {
    backgroundColor: Colors.cardBackground,
    borderRadius: 8,
    padding: 12,
    flexDirection: 'row',
    alignItems: 'center',
    marginVertical: 4,
    borderWidth: 1,
    borderColor: Colors.surfaceBorder
  },
  checkTitle: { fontSize: 14, fontWeight: '700', color: Colors.govNavy },
  checkDesc: { fontSize: 12, color: Colors.textSecondary, marginTop: 2 },
  detailHeader: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginBottom: 8
  },
  stageRow: {
    backgroundColor: Colors.cardBackground,
    borderRadius: 8,
    padding: 12,
    flexDirection: 'row',
    alignItems: 'flex-start',
    marginVertical: 4,
    borderWidth: 1,
    borderColor: Colors.surfaceBorder
  },
  stageTitle: { fontSize: 14, fontWeight: '700', color: Colors.govNavy },
  stageDate: { fontSize: 11, color: Colors.textMuted, marginTop: 2 },
  stageRemarks: { fontSize: 12, color: Colors.govBlue, marginTop: 4 },
  payBox: {
    backgroundColor: Colors.cardBackground,
    borderRadius: 10,
    padding: 20,
    alignItems: 'center',
    marginBottom: 10,
    borderWidth: 1,
    borderColor: Colors.surfaceBorder
  },
  payAmount: { fontSize: 28, fontWeight: '800', color: Colors.statusGreen, marginBottom: 8 },
  sectionTitle: { fontSize: 16, fontWeight: '700', color: Colors.govNavy, marginBottom: 8 }
});

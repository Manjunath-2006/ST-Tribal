import React from 'react';
import { View, Text, StyleSheet, ScrollView, TouchableOpacity } from 'react-native';
import { useApp } from '../context/AppContext';
import { Colors } from '../theme/colors';
import { Ionicons } from '@expo/vector-icons';
import {
  GovernmentHeader,
  PrototypeTag,
  SummaryStatCard,
  ActionRequiredCard,
  StatusBadge,
  InfoRow,
  InfoBanner
} from '../components/Components';

export const HomeScreen: React.FC<{ navigation: any }> = ({ navigation }) => {
  const { student, applications, isOffline, proactiveAlerts } = useApp();
  const activeApp = applications.find(a => a.status !== 'DISBURSED') || applications[0];
  const activeDeficiency = activeApp?.deficiencies.find(d => !d.resolved);

  return (
    <View style={styles.container}>
      <GovernmentHeader
        title="EKLAVYAONE - Portal for Tribes"
        rightAction={
          <TouchableOpacity onPress={() => navigation.navigate('Jago')}>
            <Ionicons name="sparkles" size={24} color={Colors.accentSaffron} />
          </TouchableOpacity>
        }
      />

      <ScrollView style={styles.content} contentContainerStyle={{ paddingBottom: 30 }}>
        {isOffline && (
          <InfoBanner
            type="WARNING"
            text="Offline Mode Active. Displaying cached scholarship records (Last synced: 28 Sep 2026)."
          />
        )}

        <View style={styles.welcomeSection}>
          <Text style={styles.welcomeSubtitle}>Ministry of Tribal Affairs (MoTA) • Govt. of India</Text>
          <Text style={styles.welcomeTitle}>Welcome, {student.name}</Text>
          <Text style={styles.welcomeMeta}>
            {student.category} • {student.institution}
          </Text>
        </View>

        <PrototypeTag />

        {/* Stats Row */}
        <View style={styles.statsRow}>
          <SummaryStatCard
            label="Active Schemes"
            value="1"
            icon="school-outline"
            onPress={() => navigation.navigate('Scholarships')}
          />
          <SummaryStatCard
            label="Applications"
            value={applications.length.toString()}
            icon="document-text-outline"
            onPress={() => navigation.navigate('Applications')}
          />
        </View>

        <View style={styles.statsRow}>
          <SummaryStatCard
            label="Action Required"
            value={activeDeficiency ? '1' : '0'}
            icon="alert-circle-outline"
            valueColor={activeDeficiency ? Colors.statusAmber : Colors.statusGreen}
            onPress={() => navigation.navigate('Documents')}
          />
          <SummaryStatCard
            label="Disbursed DBT"
            value="₹24,000"
            icon="wallet-outline"
            valueColor={Colors.statusGreen}
            onPress={() => navigation.navigate('Payments')}
          />
        </View>

        {/* Active Application Card */}
        {activeApp && (
          <View style={styles.sectionContainer}>
            <View style={styles.sectionHeader}>
              <Text style={styles.sectionTitle}>Active Scholarship Status</Text>
              <TouchableOpacity onPress={() => navigation.navigate('Applications')}>
                <Text style={styles.sectionLink}>View Timeline →</Text>
              </TouchableOpacity>
            </View>

            <View style={styles.activeAppCard}>
              <View style={styles.cardHeaderRow}>
                <Text style={styles.schemeName}>{activeApp.schemeName}</Text>
                <StatusBadge status={activeApp.status} />
              </View>

              <InfoRow label="Application ID" value={activeApp.id} />
              <InfoRow label="Academic Year" value={activeApp.academicYear} />
              <InfoRow label="Next Action" value={activeApp.nextAction} valueColor={Colors.govBlue} />
              <InfoRow label="Est. Disbursement" value={activeApp.estimatedDisbursement} valueColor={Colors.statusGreen} />
            </View>
          </View>
        )}

        {/* Action Required Banner */}
        {activeDeficiency && (
          <View style={styles.sectionContainer}>
            <Text style={styles.sectionTitle}>Action Required</Text>
            <ActionRequiredCard
              deficiency={activeDeficiency}
              onResolve={() => navigation.navigate('Documents')}
              onAskJago={() => navigation.navigate('Jago')}
            />
          </View>
        )}

        {/* JAGO Proactive Update Banner */}
        {proactiveAlerts.length > 0 && (
          <View style={styles.jagoBanner}>
            <View style={styles.jagoHeader}>
              <Ionicons name="sparkles" size={20} color={Colors.govBlue} />
              <Text style={styles.jagoTitle}>JAGO AI Contextual Alert</Text>
            </View>
            <Text style={styles.jagoText}>{proactiveAlerts[0].message}</Text>
            <TouchableOpacity
              style={styles.jagoBtn}
              onPress={() => navigation.navigate(proactiveAlerts[0].actionRoute)}
            >
              <Text style={styles.jagoBtnText}>{proactiveAlerts[0].actionLabel} →</Text>
            </TouchableOpacity>
          </View>
        )}

        {/* Quick Access Menu */}
        <Text style={styles.sectionTitle}>Portal Quick Actions</Text>
        <View style={styles.quickGrid}>
          <TouchableOpacity style={styles.quickCard} onPress={() => navigation.navigate('Scholarships')}>
            <Ionicons name="search-outline" size={24} color={Colors.govBlue} />
            <Text style={styles.quickText}>Find Schemes</Text>
          </TouchableOpacity>

          <TouchableOpacity style={styles.quickCard} onPress={() => navigation.navigate('UnifiedVerification')}>
            <Ionicons name="shield-checkmark-outline" size={24} color={Colors.govNavy} />
            <Text style={styles.quickText}>Unified Verifications</Text>
          </TouchableOpacity>

          <TouchableOpacity style={styles.quickCard} onPress={() => navigation.navigate('Documents')}>
            <Ionicons name="folder-open-outline" size={24} color={Colors.statusAmber} />
            <Text style={styles.quickText}>DigiLocker Wallet</Text>
          </TouchableOpacity>

          <TouchableOpacity style={styles.quickCard} onPress={() => navigation.navigate('Jago')}>
            <Ionicons name="chatbubbles-outline" size={24} color={Colors.accentSaffron} />
            <Text style={styles.quickText}>Ask JAGO AI</Text>
          </TouchableOpacity>
        </View>

        {/* Scheme Awareness Banner */}
        <TouchableOpacity
          style={styles.awarenessCard}
          onPress={() => navigation.navigate('ScholarshipAwareness')}
        >
          <Ionicons name="bulb-outline" size={26} color={Colors.accentSaffron} />
          <View style={{ flex: 1, marginLeft: 12 }}>
            <Text style={styles.awarenessTitle}>Explore ST Higher Education Schemes</Text>
            <Text style={styles.awarenessDesc}>
              Learn about NFST (M.Phil/Ph.D), NOS (Overseas), and Top Class Premier Institute waivers.
            </Text>
          </View>
          <Ionicons name="chevron-forward" size={20} color={Colors.textMuted} />
        </TouchableOpacity>
      </ScrollView>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: Colors.surfaceLight
  },
  content: {
    padding: 16
  },
  welcomeSection: {
    marginBottom: 10
  },
  welcomeSubtitle: {
    fontSize: 12,
    color: Colors.textSecondary,
    fontWeight: '600'
  },
  welcomeTitle: {
    fontSize: 22,
    fontWeight: '800',
    color: Colors.govNavy,
    marginTop: 2
  },
  welcomeMeta: {
    fontSize: 13,
    color: Colors.textSecondary,
    marginTop: 2
  },
  statsRow: {
    flexDirection: 'row',
    gap: 10,
    marginVertical: 4
  },
  sectionContainer: {
    marginTop: 16
  },
  sectionHeader: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginBottom: 8
  },
  sectionTitle: {
    fontSize: 16,
    fontWeight: '700',
    color: Colors.govNavy,
    marginTop: 14,
    marginBottom: 8
  },
  sectionLink: {
    fontSize: 13,
    color: Colors.govBlue,
    fontWeight: '600'
  },
  activeAppCard: {
    backgroundColor: Colors.cardBackground,
    borderRadius: 10,
    padding: 16,
    borderWidth: 1,
    borderColor: Colors.surfaceBorder,
    elevation: 1
  },
  cardHeaderRow: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginBottom: 12,
    paddingBottom: 10,
    borderBottomWidth: 1,
    borderBottomColor: Colors.surfaceDivider
  },
  schemeName: {
    fontSize: 15,
    fontWeight: '700',
    color: Colors.govNavy,
    flex: 1,
    marginRight: 8
  },
  jagoBanner: {
    backgroundColor: Colors.govBlueLight,
    borderRadius: 10,
    padding: 14,
    marginVertical: 12,
    borderLeftWidth: 4,
    borderLeftColor: Colors.govBlue
  },
  jagoHeader: {
    flexDirection: 'row',
    alignItems: 'center',
    marginBottom: 6
  },
  jagoTitle: {
    fontSize: 14,
    fontWeight: '700',
    color: Colors.govBlue,
    marginLeft: 6
  },
  jagoText: {
    fontSize: 13,
    color: Colors.textPrimary,
    lineHeight: 18
  },
  jagoBtn: {
    marginTop: 8,
    alignSelf: 'flex-start'
  },
  jagoBtnText: {
    fontSize: 12,
    fontWeight: '700',
    color: Colors.govBlue
  },
  quickGrid: {
    flexDirection: 'row',
    flexWrap: 'wrap',
    gap: 10
  },
  quickCard: {
    backgroundColor: Colors.cardBackground,
    width: '48%',
    padding: 16,
    borderRadius: 8,
    alignItems: 'center',
    borderWidth: 1,
    borderColor: Colors.surfaceBorder
  },
  quickText: {
    fontSize: 12,
    fontWeight: '600',
    color: Colors.textPrimary,
    marginTop: 8
  },
  awarenessCard: {
    backgroundColor: Colors.accentSaffronLight,
    borderRadius: 10,
    padding: 14,
    flexDirection: 'row',
    alignItems: 'center',
    marginTop: 16,
    borderWidth: 1,
    borderColor: Colors.accentSaffron
  },
  awarenessTitle: {
    fontSize: 14,
    fontWeight: '700',
    color: Colors.textPrimary
  },
  awarenessDesc: {
    fontSize: 12,
    color: Colors.textSecondary,
    marginTop: 2
  }
});

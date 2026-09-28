import React from 'react';
import { View, Text, StyleSheet, ScrollView, TouchableOpacity, Switch } from 'react-native';
import { useApp } from '../context/AppContext';
import { Colors } from '../theme/colors';
import { GovernmentHeader, PrototypeTag, InfoRow, StatusBadge } from '../components/Components';
import { Ionicons } from '@expo/vector-icons';

export const ProfileScreen: React.FC<{ navigation: any }> = ({ navigation }) => {
  const { student, isOffline, toggleOffline } = useApp();

  return (
    <View style={styles.container}>
      <GovernmentHeader title="Student Profile & Settings" />

      <ScrollView style={styles.content} contentContainerStyle={{ paddingBottom: 30 }}>
        {/* Profile Card */}
        <View style={styles.profileCard}>
          <View style={styles.avatarCircle}>
            <Text style={styles.avatarText}>{student.name.charAt(0)}</Text>
          </View>
          <Text style={styles.name}>{student.name}</Text>
          <Text style={styles.category}>{student.category}</Text>

          <View style={styles.verificationPill}>
            <Ionicons name="checkmark-seal" size={16} color={Colors.statusGreen} />
            <Text style={styles.verificationText}>ST Tribe Verified • DigiLocker Linked</Text>
          </View>
        </View>

        <PrototypeTag />

        {/* Offline Mode Toggle */}
        <View style={styles.offlineBox}>
          <View style={{ flex: 1 }}>
            <Text style={styles.offlineTitle}>Offline Mode Simulation</Text>
            <Text style={styles.offlineDesc}>Simulate zero-connectivity mode for remote tribal regions.</Text>
          </View>
          <Switch value={isOffline} onValueChange={toggleOffline} trackColor={{ false: '#CBD5E1', true: Colors.govBlue }} />
        </View>

        {/* Academic Profile Details */}
        <Text style={styles.sectionTitle}>Academic & Identity Profile</Text>
        <View style={styles.detailsCard}>
          <InfoRow label="Student ID" value={student.id} />
          <InfoRow label="APAAR ID" value={student.apaarId} />
          <InfoRow label="Institution" value={student.institution} />
          <InfoRow label="Course" value={student.course} />
          <InfoRow label="Academic Year" value={student.academicYear} />
          <InfoRow label="State & District" value={`${student.state}, ${student.district}`} />
          <InfoRow label="Family Annual Income" value={`₹${student.familyIncome.toLocaleString('en-IN')}`} />
        </View>

        {/* System & Verification Links */}
        <Text style={styles.sectionTitle}>Government System Links</Text>

        <TouchableOpacity style={styles.menuItem} onPress={() => navigation.navigate('UnifiedVerification')}>
          <Ionicons name="shield-checkmark-outline" size={22} color={Colors.govNavy} />
          <Text style={styles.menuText}>Unified 8-Source Verification Status</Text>
          <Ionicons name="chevron-forward" size={18} color={Colors.textMuted} />
        </TouchableOpacity>

        <TouchableOpacity style={styles.menuItem} onPress={() => navigation.navigate('Documents')}>
          <Ionicons name="folder-outline" size={22} color={Colors.govBlue} />
          <Text style={styles.menuText}>DigiLocker Document Wallet</Text>
          <Ionicons name="chevron-forward" size={18} color={Colors.textMuted} />
        </TouchableOpacity>

        <TouchableOpacity style={styles.menuItem} onPress={() => navigation.navigate('ExistingSystems')}>
          <Ionicons name="layers-outline" size={22} color={Colors.statusAmber} />
          <Text style={styles.menuText}>Existing Systems (NSP, SFMP, NOS Portal)</Text>
          <Ionicons name="chevron-forward" size={18} color={Colors.textMuted} />
        </TouchableOpacity>

        <TouchableOpacity style={styles.menuItem} onPress={() => navigation.navigate('MinistryInsights')}>
          <Ionicons name="analytics-outline" size={22} color={Colors.statusGreen} />
          <Text style={styles.menuText}>Ministry of Tribal Affairs Insights</Text>
          <Ionicons name="chevron-forward" size={18} color={Colors.textMuted} />
        </TouchableOpacity>

        <TouchableOpacity style={styles.menuItem} onPress={() => navigation.navigate('Help')}>
          <Ionicons name="help-circle-outline" size={22} color={Colors.textSecondary} />
          <Text style={styles.menuText}>Help & Support Hotline</Text>
          <Ionicons name="chevron-forward" size={18} color={Colors.textMuted} />
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
  profileCard: {
    backgroundColor: Colors.govNavy,
    borderRadius: 12,
    padding: 20,
    alignItems: 'center',
    marginBottom: 10
  },
  avatarCircle: {
    width: 60,
    height: 60,
    borderRadius: 30,
    backgroundColor: Colors.accentSaffron,
    alignItems: 'center',
    justifyContent: 'center',
    marginBottom: 10
  },
  avatarText: {
    fontSize: 26,
    fontWeight: '800',
    color: Colors.textOnDark
  },
  name: {
    fontSize: 20,
    fontWeight: '800',
    color: Colors.textOnDark
  },
  category: {
    fontSize: 13,
    color: Colors.accentSaffron,
    fontWeight: '600',
    marginTop: 2
  },
  verificationPill: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: 'rgba(255, 255, 255, 0.15)',
    paddingHorizontal: 12,
    paddingVertical: 5,
    borderRadius: 16,
    marginTop: 10
  },
  verificationText: {
    color: Colors.textOnDark,
    fontSize: 12,
    fontWeight: '600',
    marginLeft: 6
  },
  offlineBox: {
    backgroundColor: Colors.cardBackground,
    borderRadius: 8,
    padding: 14,
    flexDirection: 'row',
    alignItems: 'center',
    borderWidth: 1,
    borderColor: Colors.surfaceBorder,
    marginVertical: 10
  },
  offlineTitle: {
    fontSize: 14,
    fontWeight: '700',
    color: Colors.govNavy
  },
  offlineDesc: {
    fontSize: 12,
    color: Colors.textSecondary,
    marginTop: 2
  },
  sectionTitle: {
    fontSize: 15,
    fontWeight: '700',
    color: Colors.govNavy,
    marginTop: 14,
    marginBottom: 8
  },
  detailsCard: {
    backgroundColor: Colors.cardBackground,
    borderRadius: 8,
    padding: 14,
    borderWidth: 1,
    borderColor: Colors.surfaceBorder
  },
  menuItem: {
    backgroundColor: Colors.cardBackground,
    borderRadius: 8,
    padding: 14,
    flexDirection: 'row',
    alignItems: 'center',
    marginVertical: 4,
    borderWidth: 1,
    borderColor: Colors.surfaceBorder
  },
  menuText: {
    flex: 1,
    fontSize: 14,
    fontWeight: '600',
    color: Colors.textPrimary,
    marginLeft: 12
  }
});

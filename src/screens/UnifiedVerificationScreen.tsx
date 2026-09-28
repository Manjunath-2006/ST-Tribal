import React from 'react';
import { View, Text, StyleSheet, ScrollView } from 'react-native';
import { useApp } from '../context/AppContext';
import { Colors } from '../theme/colors';
import { GovernmentHeader, PrototypeTag, StatusBadge, InfoRow } from '../components/Components';
import { Ionicons } from '@expo/vector-icons';

export const UnifiedVerificationScreen: React.FC<{ navigation: any }> = ({ navigation }) => {
  const { verifications } = useApp();

  return (
    <View style={styles.container}>
      <GovernmentHeader title="Unified Verification System" showBack onBack={() => navigation.goBack()} />

      <ScrollView style={styles.content} contentContainerStyle={{ paddingBottom: 30 }}>
        <Text style={styles.subtitle}>8-Source Exception-based Verification Matrix</Text>
        <PrototypeTag />

        <View style={styles.summaryBanner}>
          <Ionicons name="checkmark-done-circle" size={24} color={Colors.statusGreen} />
          <View style={{ flex: 1, marginLeft: 10 }}>
            <Text style={styles.summaryTitle}>7 / 8 Data Points Verified</Text>
            <Text style={styles.summaryDesc}>
              Automated API checks run across DigiLocker, AISHE, APAAR, UIDAI & PFMS.
            </Text>
          </View>
        </View>

        {verifications.map(item => (
          <View key={item.id} style={styles.card}>
            <View style={styles.cardHeader}>
              <View style={{ flex: 1, marginRight: 8 }}>
                <Text style={styles.title}>{item.title}</Text>
                <Text style={styles.authority}>Authority: {item.authority}</Text>
              </View>
              <StatusBadge status={item.status} />
            </View>

            <Text style={styles.details}>{item.details}</Text>
            <InfoRow label="Reference ID" value={item.referenceNumber} />
            <InfoRow label="Last Checked" value={item.lastChecked} />
          </View>
        ))}
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
  subtitle: {
    fontSize: 13,
    color: Colors.textSecondary,
    marginBottom: 6
  },
  summaryBanner: {
    backgroundColor: Colors.statusGreenBg,
    borderRadius: 8,
    padding: 14,
    flexDirection: 'row',
    alignItems: 'center',
    marginVertical: 8,
    borderWidth: 1,
    borderColor: Colors.statusGreen
  },
  summaryTitle: {
    fontSize: 14,
    fontWeight: '700',
    color: Colors.statusGreen
  },
  summaryDesc: {
    fontSize: 12,
    color: Colors.textSecondary,
    marginTop: 2
  },
  card: {
    backgroundColor: Colors.cardBackground,
    borderRadius: 10,
    padding: 16,
    marginVertical: 6,
    borderWidth: 1,
    borderColor: Colors.surfaceBorder
  },
  cardHeader: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'flex-start',
    marginBottom: 8,
    paddingBottom: 8,
    borderBottomWidth: 1,
    borderBottomColor: Colors.surfaceDivider
  },
  title: {
    fontSize: 15,
    fontWeight: '700',
    color: Colors.govNavy
  },
  authority: {
    fontSize: 12,
    color: Colors.textSecondary,
    marginTop: 2
  },
  details: {
    fontSize: 13,
    color: Colors.textPrimary,
    marginBottom: 8
  }
});

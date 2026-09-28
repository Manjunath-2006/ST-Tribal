import React from 'react';
import { View, Text, StyleSheet, ScrollView, TouchableOpacity } from 'react-native';
import { useApp } from '../context/AppContext';
import { Colors } from '../theme/colors';
import { GovernmentHeader, PrototypeTag, StatusBadge, InfoRow, InfoBanner } from '../components/Components';

export const PaymentsScreen: React.FC<{ navigation: any }> = ({ navigation }) => {
  const { payments } = useApp();

  return (
    <View style={styles.container}>
      <GovernmentHeader title="Direct Benefit Transfer (DBT) Payments" />

      <ScrollView style={styles.content} contentContainerStyle={{ paddingBottom: 30 }}>
        <Text style={styles.subtitle}>PFMS Direct Aadhaar Seeding Disbursement Ledger</Text>
        <PrototypeTag />

        <InfoBanner
          type="INFO"
          text="All MoTA scholarship amounts are disbursed directly to your Aadhaar-seeded bank account via PFMS DBT."
        />

        {payments.map(pay => (
          <TouchableOpacity
            key={pay.id}
            style={styles.card}
            onPress={() => navigation.navigate('PaymentDetail', { paymentId: pay.id })}
            activeOpacity={0.9}
          >
            <View style={styles.cardHeader}>
              <View>
                <Text style={styles.amountText}>₹{pay.amount.toLocaleString('en-IN')}</Text>
                <Text style={styles.schemeText}>{pay.schemeName}</Text>
              </View>
              <StatusBadge status={pay.status} />
            </View>

            <InfoRow label="Academic Year" value={pay.academicYear} />
            <InfoRow label="Disbursement Date" value={pay.date} />
            <InfoRow label="Bank Account" value={pay.bankAccountMasked} />
            <InfoRow label="DBT Status" value={pay.dbtStatus} valueColor={Colors.govBlue} />
          </TouchableOpacity>
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
  card: {
    backgroundColor: Colors.cardBackground,
    borderRadius: 10,
    padding: 16,
    marginVertical: 8,
    borderWidth: 1,
    borderColor: Colors.surfaceBorder,
    elevation: 1
  },
  cardHeader: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'flex-start',
    marginBottom: 10,
    paddingBottom: 8,
    borderBottomWidth: 1,
    borderBottomColor: Colors.surfaceDivider
  },
  amountText: {
    fontSize: 20,
    fontWeight: '800',
    color: Colors.statusGreen
  },
  schemeText: {
    fontSize: 13,
    color: Colors.govNavy,
    fontWeight: '600',
    marginTop: 2
  }
});

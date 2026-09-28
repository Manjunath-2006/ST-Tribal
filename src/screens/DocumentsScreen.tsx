import React from 'react';
import { View, Text, StyleSheet, ScrollView, TouchableOpacity, Alert } from 'react-native';
import { useApp } from '../context/AppContext';
import { Colors } from '../theme/colors';
import { GovernmentHeader, PrototypeTag, StatusBadge, InfoRow, PrimaryButton } from '../components/Components';
import { Ionicons } from '@expo/vector-icons';

export const DocumentsScreen: React.FC<{ navigation: any }> = ({ navigation }) => {
  const { documents, resolveDeficiency, uploadDocument } = useApp();

  const handleSyncDigiLocker = () => {
    uploadDocument('Renewed Income Certificate 2026-27', 'Income Certificate');
    resolveDeficiency('DEF-2026-091');
    Alert.alert('DigiLocker Synced', 'Successfully fetched updated signed Income Certificate (2026-27). Deficiency cleared!');
  };

  return (
    <View style={styles.container}>
      <GovernmentHeader title="DigiLocker Document Wallet" showBack onBack={() => navigation.goBack()} />

      <ScrollView style={styles.content} contentContainerStyle={{ paddingBottom: 30 }}>
        <Text style={styles.subtitle}>Direct integration with DigiLocker & State e-District Portals</Text>
        <PrototypeTag />

        <View style={styles.digiBanner}>
          <Ionicons name="shield-checkmark" size={24} color={Colors.govBlue} />
          <View style={{ flex: 1, marginLeft: 10 }}>
            <Text style={styles.digiTitle}>DigiLocker Government Verified</Text>
            <Text style={styles.digiDesc}>No physical paper submission required. Instant API verification.</Text>
          </View>
        </View>

        <PrimaryButton
          title="Sync Latest Documents from DigiLocker"
          onPress={handleSyncDigiLocker}
          icon="refresh-outline"
          style={{ marginVertical: 10 }}
        />

        <Text style={styles.sectionTitle}>Issued Documents Ledger</Text>

        {documents.map(doc => (
          <View key={doc.id} style={styles.card}>
            <View style={styles.cardHeader}>
              <View style={{ flex: 1, marginRight: 8 }}>
                <Text style={styles.docTitle}>{doc.title}</Text>
                <Text style={styles.docIssuer}>Issuer: {doc.issuer}</Text>
              </View>
              <StatusBadge status={doc.status} />
            </View>

            <InfoRow label="Document Type" value={doc.type} />
            <InfoRow label="Source" value={doc.source} />
            <InfoRow label="Issue Date" value={doc.issueDate} />
            <InfoRow label="Ref Number" value={doc.documentNumberMasked} />
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
  digiBanner: {
    backgroundColor: Colors.govBlueLight,
    borderRadius: 8,
    padding: 14,
    flexDirection: 'row',
    alignItems: 'center',
    marginVertical: 8,
    borderWidth: 1,
    borderColor: Colors.govBlue
  },
  digiTitle: {
    fontSize: 14,
    fontWeight: '700',
    color: Colors.govBlue
  },
  digiDesc: {
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
    marginBottom: 10,
    paddingBottom: 8,
    borderBottomWidth: 1,
    borderBottomColor: Colors.surfaceDivider
  },
  docTitle: {
    fontSize: 15,
    fontWeight: '700',
    color: Colors.govNavy
  },
  docIssuer: {
    fontSize: 12,
    color: Colors.textSecondary,
    marginTop: 2
  }
});

import React from 'react';
import { View, Text, StyleSheet, ScrollView, TouchableOpacity } from 'react-native';
import { useApp } from '../context/AppContext';
import { Colors } from '../theme/colors';
import { GovernmentHeader, PrototypeTag, StatusBadge, InfoRow } from '../components/Components';

export const ApplicationsScreen: React.FC<{ navigation: any }> = ({ navigation }) => {
  const { applications } = useApp();

  return (
    <View style={styles.container}>
      <GovernmentHeader title="My Applications" />

      <ScrollView style={styles.content} contentContainerStyle={{ paddingBottom: 30 }}>
        <Text style={styles.subtitle}>Track your live applications and stage progression</Text>
        <PrototypeTag />

        {applications.map(app => (
          <TouchableOpacity
            key={app.id}
            style={styles.card}
            onPress={() => navigation.navigate('ApplicationDetail', { applicationId: app.id })}
            activeOpacity={0.9}
          >
            <View style={styles.cardHeader}>
              <Text style={styles.schemeTitle}>{app.schemeName}</Text>
              <StatusBadge status={app.status} />
            </View>

            <InfoRow label="Application ID" value={app.id} />
            <InfoRow label="Date Submitted" value={app.applicationDate} />
            <InfoRow label="Academic Year" value={app.academicYear} />
            <InfoRow label="Next Action" value={app.nextAction} valueColor={Colors.govBlue} />

            <View style={styles.cardFooter}>
              <Text style={styles.footerAction}>View Stage Timeline & Deficiencies →</Text>
            </View>
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
    alignItems: 'center',
    marginBottom: 10,
    paddingBottom: 8,
    borderBottomWidth: 1,
    borderBottomColor: Colors.surfaceDivider
  },
  schemeTitle: {
    fontSize: 15,
    fontWeight: '700',
    color: Colors.govNavy,
    flex: 1,
    marginRight: 8
  },
  cardFooter: {
    marginTop: 10,
    paddingTop: 8,
    borderTopWidth: 1,
    borderTopColor: Colors.surfaceDivider
  },
  footerAction: {
    fontSize: 13,
    fontWeight: '700',
    color: Colors.govBlue,
    textAlign: 'right'
  }
});

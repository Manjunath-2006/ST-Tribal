import React, { useState } from 'react';
import { View, Text, StyleSheet, ScrollView, TextInput } from 'react-native';
import { useApp } from '../context/AppContext';
import { Colors } from '../theme/colors';
import { GovernmentHeader, PrototypeTag, ScholarshipCard } from '../components/Components';
import { Ionicons } from '@expo/vector-icons';

export const ScholarshipsScreen: React.FC<{ navigation: any }> = ({ navigation }) => {
  const { schemes } = useApp();
  const [search, setSearch] = useState('');

  const filteredSchemes = schemes.filter(
    s =>
      s.name.toLowerCase().includes(search.toLowerCase()) ||
      s.shortCode.toLowerCase().includes(search.toLowerCase()) ||
      s.level.toLowerCase().includes(search.toLowerCase())
  );

  return (
    <View style={styles.container}>
      <GovernmentHeader title="MoTA ST Scholarship Schemes" />

      <ScrollView style={styles.content} contentContainerStyle={{ paddingBottom: 30 }}>
        <Text style={styles.headerSubtitle}>
          Official Ministry of Tribal Affairs Schemes Unified under EKLAVYAONE Single Window
        </Text>

        <PrototypeTag />

        <View style={styles.searchBox}>
          <Ionicons name="search" size={20} color={Colors.textMuted} style={{ marginRight: 8 }} />
          <TextInput
            style={styles.searchInput}
            placeholder="Search scheme name, level, code..."
            placeholderTextColor={Colors.textMuted}
            value={search}
            onChangeText={setSearch}
          />
        </View>

        {filteredSchemes.map(scheme => (
          <ScholarshipCard
            key={scheme.id}
            scheme={scheme}
            onPress={() => navigation.navigate('ScholarshipDetail', { schemeId: scheme.id })}
          />
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
  headerSubtitle: {
    fontSize: 13,
    color: Colors.textSecondary,
    marginBottom: 6
  },
  searchBox: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: Colors.cardBackground,
    borderRadius: 8,
    paddingHorizontal: 12,
    paddingVertical: 10,
    borderWidth: 1,
    borderColor: Colors.surfaceBorder,
    marginVertical: 10
  },
  searchInput: {
    flex: 1,
    fontSize: 14,
    color: Colors.textPrimary
  }
});

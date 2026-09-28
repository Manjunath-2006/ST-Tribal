import React from 'react';
import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import { Colors } from '../theme/colors';
import { Ionicons } from '@expo/vector-icons';

import { HomeScreen } from '../screens/HomeScreen';
import { ScholarshipsScreen } from '../screens/ScholarshipsScreen';
import { ApplicationsScreen } from '../screens/ApplicationsScreen';
import { PaymentsScreen } from '../screens/PaymentsScreen';
import { ProfileScreen } from '../screens/ProfileScreen';
import { JagoScreen } from '../screens/JagoScreen';
import { DocumentsScreen } from '../screens/DocumentsScreen';
import { UnifiedVerificationScreen } from '../screens/UnifiedVerificationScreen';
import {
  ScholarshipDetailScreen,
  EligibilityCheckScreen,
  ConflictCheckScreen,
  ApplicationReadinessScreen,
  ApplicationDetailScreen,
  PaymentDetailScreen,
  MinistryInsightsScreen,
  ScholarshipAwarenessScreen,
  ExistingSystemsScreen,
  HelpScreen
} from '../screens/SecondaryScreens';

const Stack = createNativeStackNavigator();
const Tab = createBottomTabNavigator();

function MainTabNavigator() {
  return (
    <Tab.Navigator
      screenOptions={{
        headerShown: false,
        tabBarActiveTintColor: Colors.govNavy,
        tabBarInactiveTintColor: Colors.textMuted,
        tabBarStyle: {
          backgroundColor: Colors.cardBackground,
          borderTopColor: Colors.surfaceBorder,
          height: 60,
          paddingBottom: 8,
          paddingTop: 8
        },
        tabBarLabelStyle: {
          fontSize: 11,
          fontWeight: '700'
        }
      }}
    >
      <Tab.Screen
        name="Home"
        component={HomeScreen}
        options={{
          tabBarLabel: 'Home',
          tabBarIcon: ({ color, size }) => <Ionicons name="home" size={size} color={color} />
        }}
      />
      <Tab.Screen
        name="Scholarships"
        component={ScholarshipsScreen}
        options={{
          tabBarLabel: 'Scholarships',
          tabBarIcon: ({ color, size }) => <Ionicons name="school" size={size} color={color} />
        }}
      />
      <Tab.Screen
        name="Applications"
        component={ApplicationsScreen}
        options={{
          tabBarLabel: 'Applications',
          tabBarIcon: ({ color, size }) => <Ionicons name="document-text" size={size} color={color} />
        }}
      />
      <Tab.Screen
        name="Payments"
        component={PaymentsScreen}
        options={{
          tabBarLabel: 'Payments',
          tabBarIcon: ({ color, size }) => <Ionicons name="wallet" size={size} color={color} />
        }}
      />
      <Tab.Screen
        name="Profile"
        component={ProfileScreen}
        options={{
          tabBarLabel: 'Profile',
          tabBarIcon: ({ color, size }) => <Ionicons name="person" size={size} color={color} />
        }}
      />
    </Tab.Navigator>
  );
}

export function AppNavigator() {
  return (
    <NavigationContainer>
      <Stack.Navigator screenOptions={{ headerShown: false }}>
        <Stack.Screen name="MainTabs" component={MainTabNavigator} />
        <Stack.Screen name="ScholarshipDetail" component={ScholarshipDetailScreen} />
        <Stack.Screen name="EligibilityCheck" component={EligibilityCheckScreen} />
        <Stack.Screen name="ConflictCheck" component={ConflictCheckScreen} />
        <Stack.Screen name="ApplicationReadiness" component={ApplicationReadinessScreen} />
        <Stack.Screen name="ApplicationDetail" component={ApplicationDetailScreen} />
        <Stack.Screen name="PaymentDetail" component={PaymentDetailScreen} />
        <Stack.Screen name="Documents" component={DocumentsScreen} />
        <Stack.Screen name="UnifiedVerification" component={UnifiedVerificationScreen} />
        <Stack.Screen name="Jago" component={JagoScreen} />
        <Stack.Screen name="MinistryInsights" component={MinistryInsightsScreen} />
        <Stack.Screen name="ScholarshipAwareness" component={ScholarshipAwarenessScreen} />
        <Stack.Screen name="ExistingSystems" component={ExistingSystemsScreen} />
        <Stack.Screen name="Help" component={HelpScreen} />
      </Stack.Navigator>
    </NavigationContainer>
  );
}

import React from 'react';
import { View, Text, StyleSheet, TouchableOpacity } from 'react-native';
import { Colors } from '../theme/colors';
import { Ionicons } from '@expo/vector-icons';
import { ScholarshipScheme, Deficiency, VerificationItem, Payment } from '../data/types';

export const GovernmentHeader: React.FC<{
  title: string;
  showBack?: boolean;
  onBack?: () => void;
  rightAction?: React.ReactNode;
}> = ({ title, showBack, onBack, rightAction }) => {
  return (
    <View style={styles.headerContainer}>
      <View style={styles.headerTop}>
        {showBack && (
          <TouchableOpacity style={styles.backBtn} onPress={onBack}>
            <Ionicons name="arrow-back" size={24} color={Colors.textOnDark} />
          </TouchableOpacity>
        )}
        <View style={styles.headerTitleCol}>
          <Text style={styles.headerGovText}>Ministry of Tribal Affairs • Govt. of India</Text>
          <Text style={styles.headerTitle}>{title}</Text>
        </View>
        {rightAction && <View style={styles.headerRight}>{rightAction}</View>}
      </View>
    </View>
  );
};

export const PrototypeTag: React.FC<{ style?: object }> = ({ style }) => (
  <View style={[styles.prototypeBadge, style]}>
    <Ionicons name="information-circle-outline" size={14} color={Colors.statusAmber} />
    <Text style={styles.prototypeText}>EKLAVYAONE Prototype / Mock Data • SIH 2026 MoTA</Text>
  </View>
);

export const PrimaryButton: React.FC<{
  title: string;
  onPress: () => void;
  icon?: keyof typeof Ionicons.glyphMap;
  disabled?: boolean;
  style?: object;
}> = ({ title, onPress, icon, disabled, style }) => (
  <TouchableOpacity
    style={[styles.primaryBtn, disabled && styles.disabledBtn, style]}
    onPress={onPress}
    disabled={disabled}
    activeOpacity={0.8}
  >
    {icon && <Ionicons name={icon} size={20} color={Colors.textOnDark} style={{ marginRight: 8 }} />}
    <Text style={styles.primaryBtnText}>{title}</Text>
  </TouchableOpacity>
);

export const SecondaryButton: React.FC<{
  title: string;
  onPress: () => void;
  icon?: keyof typeof Ionicons.glyphMap;
  style?: object;
}> = ({ title, onPress, icon, style }) => (
  <TouchableOpacity style={[styles.secondaryBtn, style]} onPress={onPress} activeOpacity={0.8}>
    {icon && <Ionicons name={icon} size={20} color={Colors.govBlue} style={{ marginRight: 8 }} />}
    <Text style={styles.secondaryBtnText}>{title}</Text>
  </TouchableOpacity>
);

export const InfoBanner: React.FC<{
  text: string;
  type?: 'INFO' | 'WARNING' | 'SUCCESS' | 'ERROR';
  style?: object;
}> = ({ text, type = 'INFO', style }) => {
  let bgColor = Colors.statusBlueBg;
  let textColor = Colors.statusBlue;
  let iconName: keyof typeof Ionicons.glyphMap = 'information-circle';

  if (type === 'WARNING') {
    bgColor = Colors.statusAmberBg;
    textColor = Colors.statusAmber;
    iconName = 'warning';
  } else if (type === 'SUCCESS') {
    bgColor = Colors.statusGreenBg;
    textColor = Colors.statusGreen;
    iconName = 'checkmark-circle';
  } else if (type === 'ERROR') {
    bgColor = Colors.statusRedBg;
    textColor = Colors.statusRed;
    iconName = 'alert-circle';
  }

  return (
    <View style={[styles.infoBanner, { backgroundColor: bgColor }, style]}>
      <Ionicons name={iconName} size={20} color={textColor} style={{ marginRight: 10 }} />
      <Text style={[styles.infoBannerText, { color: textColor }]}>{text}</Text>
    </View>
  );
};

export const SummaryStatCard: React.FC<{
  label: string;
  value: string;
  icon: keyof typeof Ionicons.glyphMap;
  valueColor?: string;
  onPress?: () => void;
}> = ({ label, value, icon, valueColor, onPress }) => (
  <TouchableOpacity style={styles.statCard} onPress={onPress} disabled={!onPress} activeOpacity={0.8}>
    <View style={styles.statHeader}>
      <Ionicons name={icon} size={22} color={Colors.govBlue} />
      <Text style={[styles.statValue, { color: valueColor || Colors.govNavy }]}>{value}</Text>
    </View>
    <Text style={styles.statLabel}>{label}</Text>
  </TouchableOpacity>
);

export const StatusBadge: React.FC<{
  status: string;
  type?: 'STAGE' | 'VERIFICATION' | 'PAYMENT';
}> = ({ status, type = 'STAGE' }) => {
  let bg = Colors.statusGrayBg;
  let text = Colors.statusGray;

  if (['COMPLETED', 'VERIFIED', 'CREDITED', 'APPROVED', 'DISBURSED'].includes(status)) {
    bg = Colors.statusGreenBg;
    text = Colors.statusGreen;
  } else if (['IN_PROGRESS', 'PROCESSING', 'SYNCED'].includes(status)) {
    bg = Colors.statusBlueBg;
    text = Colors.statusBlue;
  } else if (['PENDING', 'MISMATCH', 'ACTION_REQUIRED', 'DEFICIENCY_RAISED', 'WARNING'].includes(status)) {
    bg = Colors.statusAmberBg;
    text = Colors.statusAmber;
  } else if (['CRITICAL', 'REJECTED', 'FAILED'].includes(status)) {
    bg = Colors.statusRedBg;
    text = Colors.statusRed;
  }

  return (
    <View style={[styles.badgeContainer, { backgroundColor: bg }]}>
      <Text style={[styles.badgeText, { color: text }]}>{status.replace('_', ' ')}</Text>
    </View>
  );
};

export const ScholarshipCard: React.FC<{
  scheme: ScholarshipScheme;
  onPress: () => void;
}> = ({ scheme, onPress }) => (
  <TouchableOpacity style={styles.card} onPress={onPress} activeOpacity={0.9}>
    <View style={styles.cardHeader}>
      <View style={styles.schemeTag}>
        <Text style={styles.schemeTagText}>{scheme.shortCode}</Text>
      </View>
      <Text style={styles.schemeAmount}>₹{scheme.amountPerYear.toLocaleString('en-IN')}/yr</Text>
    </View>
    <Text style={styles.cardTitle}>{scheme.name}</Text>
    <Text style={styles.cardDesc} numberOfLines={2}>
      {scheme.benefitDescription}
    </Text>
    <View style={styles.cardFooter}>
      <Text style={styles.cardFooterText}>Level: {scheme.level}</Text>
      <Text style={styles.cardActionText}>Details & Apply →</Text>
    </View>
  </TouchableOpacity>
);

export const ActionRequiredCard: React.FC<{
  deficiency: Deficiency;
  onResolve: () => void;
  onAskJago: () => void;
}> = ({ deficiency, onResolve, onAskJago }) => (
  <View style={styles.actionCard}>
    <View style={styles.actionCardHeader}>
      <Ionicons name="alert-circle" size={20} color={Colors.statusAmber} />
      <Text style={styles.actionCardTitle}>{deficiency.title}</Text>
    </View>
    <Text style={styles.actionCardDesc}>{deficiency.description}</Text>
    <Text style={styles.actionCardSource}>Source: {deficiency.sourceAuthority}</Text>
    <View style={styles.actionCardRow}>
      <TouchableOpacity style={styles.smallActionBtn} onPress={onResolve}>
        <Text style={styles.smallActionText}>Resolve Document</Text>
      </TouchableOpacity>
      <TouchableOpacity style={styles.jagoLinkBtn} onPress={onAskJago}>
        <Ionicons name="sparkles-outline" size={14} color={Colors.govBlue} />
        <Text style={styles.jagoLinkText}>Ask JAGO</Text>
      </TouchableOpacity>
    </View>
  </View>
);

export const InfoRow: React.FC<{ label: string; value: string; valueColor?: string }> = ({
  label,
  value,
  valueColor
}) => (
  <View style={styles.infoRow}>
    <Text style={styles.infoLabel}>{label}</Text>
    <Text style={[styles.infoValue, valueColor ? { color: valueColor } : undefined]}>{value}</Text>
  </View>
);

const styles = StyleSheet.create({
  headerContainer: {
    backgroundColor: Colors.govNavy,
    paddingTop: 44,
    paddingBottom: 16,
    paddingHorizontal: 16,
    borderBottomWidth: 3,
    borderBottomColor: Colors.accentSaffron
  },
  headerTop: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between'
  },
  backBtn: {
    marginRight: 12
  },
  headerTitleCol: {
    flex: 1
  },
  headerGovText: {
    color: Colors.accentSaffron,
    fontSize: 11,
    fontWeight: '700',
    letterSpacing: 0.5,
    textTransform: 'uppercase'
  },
  headerTitle: {
    color: Colors.textOnDark,
    fontSize: 18,
    fontWeight: '700',
    marginTop: 2
  },
  headerRight: {
    marginLeft: 12
  },
  prototypeBadge: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: Colors.statusAmberBg,
    paddingVertical: 6,
    paddingHorizontal: 12,
    borderRadius: 6,
    borderWidth: 1,
    borderColor: Colors.statusAmber,
    marginVertical: 8
  },
  prototypeText: {
    fontSize: 12,
    color: Colors.statusAmber,
    fontWeight: '600',
    marginLeft: 6
  },
  primaryBtn: {
    backgroundColor: Colors.govNavy,
    paddingVertical: 14,
    paddingHorizontal: 20,
    borderRadius: 8,
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'center',
    elevation: 2
  },
  disabledBtn: {
    backgroundColor: Colors.textMuted
  },
  primaryBtnText: {
    color: Colors.textOnDark,
    fontSize: 15,
    fontWeight: '700'
  },
  secondaryBtn: {
    backgroundColor: Colors.cardBackground,
    borderWidth: 1.5,
    borderColor: Colors.govBlue,
    paddingVertical: 12,
    paddingHorizontal: 18,
    borderRadius: 8,
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'center'
  },
  secondaryBtnText: {
    color: Colors.govBlue,
    fontSize: 14,
    fontWeight: '700'
  },
  infoBanner: {
    flexDirection: 'row',
    alignItems: 'center',
    padding: 12,
    borderRadius: 8,
    marginVertical: 6
  },
  infoBannerText: {
    flex: 1,
    fontSize: 13,
    fontWeight: '500',
    lineHeight: 18
  },
  statCard: {
    backgroundColor: Colors.cardBackground,
    padding: 14,
    borderRadius: 10,
    borderWidth: 1,
    borderColor: Colors.surfaceBorder,
    flex: 1
  },
  statHeader: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between'
  },
  statValue: {
    fontSize: 18,
    fontWeight: '800'
  },
  statLabel: {
    fontSize: 12,
    color: Colors.textSecondary,
    marginTop: 6,
    fontWeight: '500'
  },
  badgeContainer: {
    paddingHorizontal: 8,
    paddingVertical: 4,
    borderRadius: 4
  },
  badgeText: {
    fontSize: 11,
    fontWeight: '700',
    textTransform: 'uppercase'
  },
  card: {
    backgroundColor: Colors.cardBackground,
    borderRadius: 10,
    padding: 16,
    marginVertical: 6,
    borderWidth: 1,
    borderColor: Colors.surfaceBorder,
    elevation: 1
  },
  cardHeader: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginBottom: 8
  },
  schemeTag: {
    backgroundColor: Colors.govBlueLight,
    paddingHorizontal: 8,
    paddingVertical: 3,
    borderRadius: 4
  },
  schemeTagText: {
    color: Colors.govBlue,
    fontSize: 12,
    fontWeight: '700'
  },
  schemeAmount: {
    color: Colors.statusGreen,
    fontSize: 14,
    fontWeight: '700'
  },
  cardTitle: {
    fontSize: 16,
    fontWeight: '700',
    color: Colors.govNavy,
    marginBottom: 6
  },
  cardDesc: {
    fontSize: 13,
    color: Colors.textSecondary,
    lineHeight: 18,
    marginBottom: 12
  },
  cardFooter: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    borderTopWidth: 1,
    borderTopColor: Colors.surfaceDivider,
    paddingTop: 10
  },
  cardFooterText: {
    fontSize: 12,
    color: Colors.textMuted
  },
  cardActionText: {
    fontSize: 13,
    color: Colors.govBlue,
    fontWeight: '700'
  },
  actionCard: {
    backgroundColor: Colors.statusAmberBg,
    borderRadius: 8,
    padding: 14,
    marginVertical: 6,
    borderLeftWidth: 4,
    borderLeftColor: Colors.statusAmber
  },
  actionCardHeader: {
    flexDirection: 'row',
    alignItems: 'center',
    marginBottom: 6
  },
  actionCardTitle: {
    fontSize: 14,
    fontWeight: '700',
    color: Colors.textPrimary,
    marginLeft: 6
  },
  actionCardDesc: {
    fontSize: 13,
    color: Colors.textSecondary,
    lineHeight: 18
  },
  actionCardSource: {
    fontSize: 11,
    color: Colors.textMuted,
    marginTop: 4
  },
  actionCardRow: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginTop: 10
  },
  smallActionBtn: {
    backgroundColor: Colors.statusAmber,
    paddingVertical: 6,
    paddingHorizontal: 12,
    borderRadius: 4
  },
  smallActionText: {
    color: Colors.textOnDark,
    fontSize: 12,
    fontWeight: '700'
  },
  jagoLinkBtn: {
    flexDirection: 'row',
    alignItems: 'center'
  },
  jagoLinkText: {
    color: Colors.govBlue,
    fontSize: 12,
    fontWeight: '700',
    marginLeft: 4
  },
  infoRow: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    paddingVertical: 6,
    borderBottomWidth: 1,
    borderBottomColor: Colors.surfaceDivider
  },
  infoLabel: {
    fontSize: 13,
    color: Colors.textSecondary
  },
  infoValue: {
    fontSize: 13,
    fontWeight: '600',
    color: Colors.textPrimary,
    maxWidth: '60%',
    textAlign: 'right'
  }
});

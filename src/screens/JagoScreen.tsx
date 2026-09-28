import React, { useState } from 'react';
import { View, Text, StyleSheet, ScrollView, TextInput, TouchableOpacity, KeyboardAvoidingView, Platform } from 'react-native';
import { useApp } from '../context/AppContext';
import { Colors } from '../theme/colors';
import { GovernmentHeader, PrototypeTag } from '../components/Components';
import { Ionicons } from '@expo/vector-icons';

export const JagoScreen: React.FC<{ navigation: any }> = ({ navigation }) => {
  const { chatMessages, sendJagoMessage } = useApp();
  const [input, setInput] = useState('');

  const handleSend = () => {
    if (input.trim()) {
      sendJagoMessage(input.trim());
      setInput('');
    }
  };

  const handleQuickQuestion = (q: string) => {
    sendJagoMessage(q);
  };

  return (
    <KeyboardAvoidingView
      style={styles.container}
      behavior={Platform.OS === 'ios' ? 'padding' : undefined}
    >
      <GovernmentHeader
        title="JAGO Contextual AI Assistant"
        showBack
        onBack={() => navigation.goBack()}
      />

      <ScrollView style={styles.chatList} contentContainerStyle={{ padding: 16 }}>
        <View style={styles.aiHeaderCard}>
          <Ionicons name="sparkles" size={28} color={Colors.accentSaffron} />
          <View style={{ marginLeft: 12, flex: 1 }}>
            <Text style={styles.aiTitle}>JAGO - MoTA Scholarship Assistant</Text>
            <Text style={styles.aiSubtitle}>
              Proactive alerts, scheme eligibility explanation & deficiency resolution guide.
            </Text>
          </View>
        </View>

        <PrototypeTag />

        {/* Suggested Questions */}
        <Text style={styles.suggestedTitle}>Suggested Questions:</Text>
        <ScrollView horizontal showsHorizontalScrollIndicator={false} style={styles.suggestedScroll}>
          <TouchableOpacity
            style={styles.suggestedChip}
            onPress={() => handleQuickQuestion('How do I resolve my Income Certificate deficiency?')}
          >
            <Text style={styles.suggestedChipText}>Resolve Income Deficiency</Text>
          </TouchableOpacity>

          <TouchableOpacity
            style={styles.suggestedChip}
            onPress={() => handleQuickQuestion('When will my ₹12,000 instalment be credited?')}
          >
            <Text style={styles.suggestedChipText}>Payment Disbursement Date</Text>
          </TouchableOpacity>

          <TouchableOpacity
            style={styles.suggestedChip}
            onPress={() => handleQuickQuestion('What is the difference between Post-Matric and NFST?')}
          >
            <Text style={styles.suggestedChipText}>NFST / NOS Eligibility</Text>
          </TouchableOpacity>
        </ScrollView>

        {/* Chat Messages */}
        {chatMessages.map(msg => (
          <View
            key={msg.id}
            style={[
              styles.messageBubble,
              msg.sender === 'USER' ? styles.userBubble : styles.jagoBubble
            ]}
          >
            <View style={styles.msgHeader}>
              <Text
                style={[
                  styles.msgSender,
                  { color: msg.sender === 'USER' ? Colors.textOnDark : Colors.govNavy }
                ]}
              >
                {msg.sender === 'USER' ? 'You' : 'JAGO Assistant'}
              </Text>
              <Text
                style={[
                  styles.msgTime,
                  { color: msg.sender === 'USER' ? 'rgba(255,255,255,0.7)' : Colors.textMuted }
                ]}
              >
                {msg.timestamp}
              </Text>
            </View>
            <Text
              style={[
                styles.msgText,
                { color: msg.sender === 'USER' ? Colors.textOnDark : Colors.textPrimary }
              ]}
            >
              {msg.text}
            </Text>

            {msg.suggestedActions?.map((action, i) => (
              <TouchableOpacity
                key={i}
                style={styles.actionBtn}
                onPress={() => navigation.navigate(action.route)}
              >
                <Text style={styles.actionBtnText}>{action.label} →</Text>
              </TouchableOpacity>
            ))}
          </View>
        ))}
      </ScrollView>

      {/* Input Bar */}
      <View style={styles.inputContainer}>
        <TextInput
          style={styles.input}
          placeholder="Ask JAGO anything about MoTA scholarships..."
          placeholderTextColor={Colors.textMuted}
          value={input}
          onChangeText={setInput}
          onSubmitEditing={handleSend}
        />
        <TouchableOpacity style={styles.sendBtn} onPress={handleSend}>
          <Ionicons name="send" size={20} color={Colors.textOnDark} />
        </TouchableOpacity>
      </View>
    </KeyboardAvoidingView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: Colors.surfaceLight
  },
  chatList: {
    flex: 1
  },
  aiHeaderCard: {
    backgroundColor: Colors.govNavy,
    borderRadius: 10,
    padding: 16,
    flexDirection: 'row',
    alignItems: 'center',
    marginBottom: 8
  },
  aiTitle: {
    fontSize: 16,
    fontWeight: '800',
    color: Colors.textOnDark
  },
  aiSubtitle: {
    fontSize: 12,
    color: 'rgba(255,255,255,0.8)',
    marginTop: 2
  },
  suggestedTitle: {
    fontSize: 13,
    fontWeight: '700',
    color: Colors.govNavy,
    marginTop: 10,
    marginBottom: 6
  },
  suggestedScroll: {
    flexDirection: 'row',
    marginBottom: 16
  },
  suggestedChip: {
    backgroundColor: Colors.govBlueLight,
    borderWidth: 1,
    borderColor: Colors.govBlue,
    paddingHorizontal: 12,
    paddingVertical: 6,
    borderRadius: 16,
    marginRight: 8
  },
  suggestedChipText: {
    fontSize: 12,
    color: Colors.govBlue,
    fontWeight: '600'
  },
  messageBubble: {
    borderRadius: 10,
    padding: 14,
    marginVertical: 6,
    maxWidth: '85%'
  },
  userBubble: {
    backgroundColor: Colors.govNavy,
    alignSelf: 'flex-end'
  },
  jagoBubble: {
    backgroundColor: Colors.cardBackground,
    alignSelf: 'flex-start',
    borderWidth: 1,
    borderColor: Colors.surfaceBorder
  },
  msgHeader: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginBottom: 4
  },
  msgSender: {
    fontSize: 12,
    fontWeight: '700'
  },
  msgTime: {
    fontSize: 10,
    marginLeft: 8
  },
  msgText: {
    fontSize: 14,
    lineHeight: 20
  },
  actionBtn: {
    backgroundColor: Colors.govBlueLight,
    paddingVertical: 6,
    paddingHorizontal: 10,
    borderRadius: 4,
    marginTop: 8,
    alignSelf: 'flex-start'
  },
  actionBtnText: {
    fontSize: 12,
    color: Colors.govBlue,
    fontWeight: '700'
  },
  inputContainer: {
    flexDirection: 'row',
    padding: 12,
    backgroundColor: Colors.cardBackground,
    borderTopWidth: 1,
    borderTopColor: Colors.surfaceBorder,
    alignItems: 'center'
  },
  input: {
    flex: 1,
    backgroundColor: Colors.surfaceLight,
    borderRadius: 20,
    paddingHorizontal: 16,
    paddingVertical: 10,
    fontSize: 14,
    color: Colors.textPrimary
  },
  sendBtn: {
    backgroundColor: Colors.govNavy,
    width: 42,
    height: 42,
    borderRadius: 21,
    alignItems: 'center',
    justifyContent: 'center',
    marginLeft: 8
  }
});

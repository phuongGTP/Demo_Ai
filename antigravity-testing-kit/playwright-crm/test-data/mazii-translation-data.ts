export const MAZII_TEST_DATA = {
  // Test data cho các test case
  translations: {
    // M1: Text Input tests
    simpleGreeting: {
      ja: 'こんにちは',
      vi: 'Xin chào',
      en: 'Hello',
    },
    multilineText: {
      vi: `Xin chào
Tôi tên là Minh
Tôi đến từ Hà Nội
Rất vui được gặp bạn`,
    },
    japanese: {
      ja: '日本語を勉強しています',
      furigana: 'にほんごをべんきょうしています',
    },
    furiganaTest: {
      ja: '日本語',
      furigana: 'にほんご',
    },
    thankYou: {
      ja: 'ありがとうございます',
      vi: 'Cảm ơn bạn rất nhiều',
    },
    goodbye: {
      ja: 'さようなら',
      vi: 'Tạm biệt',
    },
    goodMorning: {
      ja: 'おはようございます',
    },
    thanksShort: {
      ja: 'ありがとう',
      vi: 'Cảm ơn',
    },
    complexSentence: {
      ja: '私は毎日日本語を勉強します',
      furigana: 'わたしはまいにちにほんごをべんきょうします',
    },
    tokyoKanji: {
      ja: '東京は大きい都市です',
    },
    helloWorld: {
      en: 'hello world',
    },
    // M7: Translation History tests
    historyTests: [
      { ja: 'xin chào' },
      { ja: 'cảm ơn' },
      { ja: 'tạm biệt' },
    ],
  },

  languages: {
    source: {
      vietnamese: 'Tiếng Việt',
      english: 'English',
      japanese: '日本語',
    },
    target: {
      japanese: '日本語',
      korean: '한국어',
      vietnamese: 'Tiếng Việt',
    },
  },

  models: {
    free: 'Mazii Translator',
    aiBase: 'Mazii Base Translator',
    aiNmt: 'Mazii NMT',
  },

  uiElements: {
    // Textarea
    textareaInputSource: 'textarea[placeholder*="Nhập"]',
    clearIcon: 'button[aria-label*="xoá"], button[aria-label*="clear"]',

    // Language dropdowns
    sourceLangDropdown: '[data-testid="source-language"]',
    targetLangDropdown: '[data-testid="target-language"]',

    // Buttons
    translateButton: 'button:has-text("Dịch")',
    swapButton: '[data-testid="swap"], button[aria-label*="swap"]',

    // Toolbar icons
    voiceButton: 'button[aria-label*="voice"], button[aria-label*="ghi âm"]',
    handwritingButton: 'button[aria-label*="handwriting"], button[aria-label*="vẽ tay"]',
    radicalButton: 'button[aria-label*="radical"], button[aria-label*="bộ thủ"]',

    // Result card
    copyButton: 'button[aria-label*="copy"], button[aria-label*="sao chép"]',
    pronunciationButton: 'button[aria-label*="pronunciation"], button[aria-label*="phát âm"]',
    analyzeButton: 'button:has-text("Phân tích")',
    grammarButton: 'button:has-text("Kiểm tra ngữ pháp")',
    contrastButton: 'button:has-text("Tương phản")',
    modelDropdown: 'button:has-text("Mazii Translator")',

    // Modals
    voiceModal: '[data-testid="voice-modal"]',
    handwritingModal: '[data-testid="handwriting-modal"]',
    radicalModal: '[data-testid="radical-modal"]',
    modelDrawer: '[data-testid="model-drawer"]',

    // History
    historySection: '[data-testid="translation-history"]',
    historyItem: '[data-testid*="history-item"]',
    clearHistoryButton: 'button:has-text("Xoá toàn bộ")',

    // Empty state
    emptyState: '[data-testid="empty-state"]',
  },

  timeouts: {
    default: 10000,
    api: 30000,
    skeleton: 3000,
  },
};

export const LANGUAGES = {
  VIETNAMESE: 'Vietnamese',
  ENGLISH: 'English',
  JAPANESE: 'Japanese',
  KOREAN: 'Korean',
};

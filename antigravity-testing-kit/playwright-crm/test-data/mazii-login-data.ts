/**
 * Mazii Login — Test Data
 * Tất cả dữ liệu cụ thể để sử dụng trong login test cases
 */

export const MAZII_LOGIN_DATA = {
  // Valid credentials (từ env)
  validEmail: process.env.MAZII_EMAIL ?? 'phuonggt@eupgroup.net',
  validPassword: process.env.MAZII_PASSWORD ?? 'TestPass@123456',

  // Invalid credentials
  invalidEmail: 'wrong_user_404@gmail.com',
  invalidPassword: 'WrongPass@9999',

  // Email validation test cases
  invalidFormatNoAt: 'invalidemailgmail.com',
  invalidFormatNoDomain: 'user@.com',
  emailWithSpaces: '  phuonggt@eupgroup.net  ',
  emailSpecialChars: 'user.name+test@gmail.com',
  longEmail: 'verylongemailaddresswithcharacters1234567890@verylongdomainname.com',
  emailCaseSensitive: 'PHUONGGT@EUPGROUP.NET',

  // Password test cases
  shortPassword: 'Ab1!',
  emptyPassword: '',
  emptyEmail: '',

  // Error messages (expected)
  errors: {
    emptyEmail: 'Bạn chưa nhập địa chỉ email',
    emptyPassword: 'Bạn chưa nhập mật khẩu',
    invalidEmailFormat: 'Không đúng định dạng email',
    invalidCredentials: 'Tài khoản hoặc mật khẩu không đúng',
    recaptchaRequired: 'Vui lòng hoàn thành xác thực reCAPTCHA',
  },

  // URLs
  loginUrl: '/vi-VN/user/login',
  forgotPasswordUrl: '/vi-VN/user/forgot-password',
  registerUrl: '/vi-VN/user/register',
  homeUrl: '/vi-VN',

  // OAuth test data (symbolic, không thực authenticate)
  googleEmail: 'testuser_mazii@gmail.com',
  appleEmail: 'testuser.mazii@icloud.com',
};

// Export dạng constant cho convenience
export const VALID_LOGIN = {
  email: MAZII_LOGIN_DATA.validEmail,
  password: MAZII_LOGIN_DATA.validPassword,
};

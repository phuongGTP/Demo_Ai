import { chromium } from '@playwright/test';

(async () => {
  const browser = await chromium.launch({ headless: true });
  const page = await browser.newPage();
  
  try {
    await page.goto('https://beta.mazii.net/vi-VN/user/login', { waitUntil: 'domcontentloaded' });
    await page.waitForTimeout(2000);
    
    // Try to trigger error by submitting empty form
    console.log('Clicking login button to trigger validation...');
    const loginBtn = page.locator('button:has-text("Đăng nhập")');
    await loginBtn.click();
    await page.waitForTimeout(1500);
    
    console.log('\n=== ERROR MESSAGES AFTER VALIDATION ===');
    
    // Check for error div
    const errorDiv = await page.locator('div[class*="error"]').all();
    console.log(`Found ${errorDiv.length} error divs`);
    for (let i = 0; i < errorDiv.length; i++) {
      const text = await errorDiv[i].innerText();
      const className = await errorDiv[i].getAttribute('class');
      console.log(`Div ${i}: class="${className}", text="${text.substring(0, 100)}"`);
    }
    
    // Check for spans/divs containing text about email or password
    const emailErrors = await page.locator('text=/email|email address/i').all();
    console.log(`\nFound ${emailErrors.length} elements mentioning "email"`);
    for (let i = 0; i < Math.min(emailErrors.length, 3); i++) {
      const text = await emailErrors[i].innerText();
      console.log(`  - "${text.substring(0, 80)}"`);
    }
    
    const passwordErrors = await page.locator('text=/password|mật khẩu/i').all();
    console.log(`Found ${passwordErrors.length} elements mentioning "password"`);
    for (let i = 0; i < Math.min(passwordErrors.length, 3); i++) {
      const text = await passwordErrors[i].innerText();
      console.log(`  - "${text.substring(0, 80)}"`);
    }
    
    // Check form structure
    console.log('\n=== FORM STRUCTURE ===');
    const form = await page.locator('form').all();
    console.log(`Found ${form.length} forms`);
    
  } catch (e: any) {
    console.error('Error:', e.message);
  }
  
  await browser.close();
})();

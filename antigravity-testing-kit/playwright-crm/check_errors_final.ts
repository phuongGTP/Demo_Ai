import { chromium } from '@playwright/test';

(async () => {
  const browser = await chromium.launch({ headless: true });
  const page = await browser.newPage();
  
  try {
    await page.goto('https://beta.mazii.net/vi-VN/user/login', { waitUntil: 'domcontentloaded' });
    await page.waitForTimeout(2000);
    
    // Click login to trigger validation
    const loginBtn = page.locator('button[type="submit"].btn-login');
    await loginBtn.click();
    await page.waitForTimeout(1500);
    
    // Get the form that contains the email input
    const emailInput = page.locator('#email');
    const form = emailInput.locator('xpath=ancestor::form[1]');
    const html = await form.innerHTML();
    
    console.log('=== LOGIN FORM HTML AFTER VALIDATION ===');
    console.log(html.substring(0, 1500));
    
    // Check for invalid-feedback elements
    console.log('\n=== ERROR MESSAGES ===');
    const errors = await page.locator('.invalid-feedback').all();
    console.log(`Found ${errors.length} error messages`);
    for (const err of errors) {
      const text = await err.innerText();
      console.log(`"${text}"`);
    }
    
  } catch (e: any) {
    console.error('Error:', e.message);
  }
  
  await browser.close();
})();

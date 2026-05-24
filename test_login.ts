import { chromium } from '@playwright/test';

(async () => {
  const browser = await chromium.launch({ headless: true });
  const page = await browser.newPage();
  
  try {
    await page.goto('https://beta.mazii.net/vi-VN/user/login', { waitUntil: 'domcontentloaded' });
    await page.waitForTimeout(2000);
    
    // Try to login
    console.log('=== ATTEMPTING LOGIN ===');
    await page.locator('#email').fill('phuonggt@eupgroup.net');
    await page.locator('#password').fill('123456');
    
    console.log('Email filled');
    console.log('Password filled');
    
    const loginBtn = page.locator('button[type="submit"].btn-login');
    console.log(`Login button visible: ${await loginBtn.isVisible()}`);
    
    await loginBtn.click();
    console.log('Login button clicked');
    
    // Wait for response
    await page.waitForTimeout(3000);
    
    console.log(`\nCurrent URL: ${page.url()}`);
    
    // Check for error messages
    const errors = await page.locator('.invalid-feedback').all();
    console.log(`Error messages: ${errors.length}`);
    for (const err of errors) {
      const text = await err.innerText();
      console.log(`  - ${text.trim()}`);
    }
    
    // Check for loading indicator or modal
    const modals = await page.locator('.modal, [role="dialog"]').all();
    console.log(`Modals/Dialogs: ${modals.length}`);
    
    // Check page content
    const content = await page.content();
    if (content.includes('reCAPTCHA') || content.includes('captcha')) {
      console.log('\nWARNING: reCAPTCHA detected on page!');
    }
    
  } catch (e: any) {
    console.error('Error:', e.message);
  }
  
  await browser.close();
})();

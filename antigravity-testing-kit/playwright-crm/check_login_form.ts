import { chromium } from '@playwright/test';

(async () => {
  const browser = await chromium.launch({ headless: true });
  const page = await browser.newPage();
  
  try {
    await page.goto('https://beta.mazii.net/vi-VN/user/login', { waitUntil: 'domcontentloaded' });
    await page.waitForTimeout(2000);
    
    // Get the form that contains the email input
    const emailInput = page.locator('#email');
    const form = emailInput.locator('xpath=ancestor::form[1]');
    const html = await form.innerHTML();
    
    console.log('=== LOGIN FORM HTML ===');
    console.log(html);
    
  } catch (e: any) {
    console.error('Error:', e.message);
  }
  
  await browser.close();
})();

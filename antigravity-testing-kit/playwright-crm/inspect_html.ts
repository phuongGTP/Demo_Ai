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
    await page.waitForTimeout(1000);
    
    // Get the full form HTML
    const form = page.locator('form');
    const html = await form.innerHTML();
    
    // Extract just the relevant parts
    const emailPart = html.substring(html.indexOf('email'), html.indexOf('email') + 600);
    const passwordPart = html.substring(html.indexOf('password'), html.indexOf('password') + 600);
    
    console.log('=== EMAIL FIELD STRUCTURE ===');
    console.log(emailPart.substring(0, 400));
    
    console.log('\n=== PASSWORD FIELD STRUCTURE ===');
    console.log(passwordPart.substring(0, 400));
    
  } catch (e: any) {
    console.error('Error:', e.message);
  }
  
  await browser.close();
})();

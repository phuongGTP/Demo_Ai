import { chromium } from '@playwright/test';

(async () => {
  const browser = await chromium.launch({ headless: true });
  const page = await browser.newPage();
  
  try {
    await page.goto('https://beta.mazii.net/vi-VN/user/login', { waitUntil: 'domcontentloaded', timeout: 15000 });
    await page.waitForTimeout(3000);
    
    // Buttons with text
    console.log('\n=== ALL BUTTONS WITH TEXT ===');
    const buttons = await page.locator('button').all();
    for (const btn of buttons) {
      const text = (await btn.innerText()).trim();
      if (text) console.log(`"${text}"`);
    }
    
    // Links with text
    console.log('\n=== ALL LINKS ===');
    const links = await page.locator('a').all();
    for (const link of links) {
      const text = (await link.innerText()).trim();
      const href = await link.getAttribute('href');
      if (text || href) console.log(`text="${text}", href="${href}"`);
    }
    
    // Error message containers
    console.log('\n=== ELEMENTS WITH "ERROR" OR "ALERT" ===');
    const alerts = await page.locator('[role="alert"]').all();
    console.log(`Found ${alerts.length} alert elements`);
    
    const errorDivs = await page.locator('div[class*="error"]').all();
    console.log(`Found ${errorDivs.length} divs with error class`);
    
    const errorMessages = await page.locator('span:has-text("Email"), span:has-text("password")').all();
    console.log(`Found ${errorMessages.length} error message spans`);
    
    // Check specific locators
    console.log('\n=== CHECKING SPECIFIC LOCATORS ===');
    const loginBtn = await page.locator('button:has-text("Sign in")').all();
    console.log(`Login button (Sign in): ${loginBtn.length}`);
    
    const signupBtn = await page.locator('a:has-text("Sign up")').all();
    console.log(`Signup link: ${signupBtn.length}`);
    
  } catch (e: any) {
    console.error('Error:', e.message);
  }
  
  await browser.close();
})();

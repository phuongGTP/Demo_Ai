import { chromium } from '@playwright/test';

(async () => {
  const browser = await chromium.launch({ headless: true });
  const page = await browser.newPage();
  
  try {
    await page.goto('https://beta.mazii.net/vi-VN/user/login', { waitUntil: 'domcontentloaded' });
    await page.waitForTimeout(2000);
    
    // Use more specific selector for login button
    console.log('Clicking login button to trigger validation...');
    const loginBtn = page.locator('button[type="submit"].btn-login');
    await loginBtn.click();
    await page.waitForTimeout(1500);
    
    console.log('\n=== PAGE CONTENT AFTER VALIDATION ===');
    const content = await page.content();
    
    // Look for error-related patterns
    if (content.includes('error')) {
      console.log('Page contains "error" keyword');
      const idx = content.indexOf('error');
      console.log(content.substring(Math.max(0, idx - 200), idx + 200));
    }
    
    if (content.includes('required')) {
      console.log('\nPage contains "required" keyword');
    }
    
    // Check visible elements
    console.log('\n=== VISIBLE ERROR ELEMENTS ===');
    const allDivs = await page.locator('div').all();
    for (const div of allDivs) {
      try {
        const isVisible = await div.isVisible();
        const text = await div.innerText().catch(() => '');
        const className = await div.getAttribute('class');
        
        if ((text.includes('email') || text.includes('password') || text.includes('mật') || text.includes('required')) && isVisible) {
          console.log(`class="${className}", text="${text.substring(0, 100)}"`);
        }
      } catch (e) {}
    }
    
  } catch (e: any) {
    console.error('Error:', e.message);
  }
  
  await browser.close();
})();

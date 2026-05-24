import { chromium } from '@playwright/test';

(async () => {
  const browser = await chromium.launch({ headless: true });
  const page = await browser.newPage();
  
  try {
    await page.goto('https://beta.mazii.net/vi-VN/user/login', { waitUntil: 'domcontentloaded', timeout: 15000 });
    await page.waitForTimeout(2000);
    
    console.log('\n=== INPUTS ===');
    const inputs = await page.locator('input').all();
    for (let i = 0; i < inputs.length; i++) {
      const type = await inputs[i].getAttribute('type');
      const id = await inputs[i].getAttribute('id');
      const name = await inputs[i].getAttribute('name');
      const placeholder = await inputs[i].getAttribute('placeholder');
      console.log(`${i}: type=${type}, id=${id}, name=${name}, placeholder=${placeholder}`);
    }
    
    console.log('\n=== BUTTONS ===');
    const buttons = await page.locator('button').all();
    for (let i = 0; i < Math.min(buttons.length, 10); i++) {
      const text = (await buttons[i].innerText()).substring(0, 40);
      console.log(`${i}: "${text}"`);
    }
    
  } catch (e: any) {
    console.error('Error:', e.message);
  }
  
  await browser.close();
})();

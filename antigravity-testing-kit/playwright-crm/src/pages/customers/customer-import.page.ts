import { Locator, expect } from '@playwright/test';
import { BasePage } from '../base.page';
import * as fs from 'fs';
import * as path from 'path';

export class CustomerImportPage extends BasePage {
  readonly fileInput: Locator = this.page.locator('input#file_csv');
  readonly importBtn: Locator = this.page.locator('button.btn-import-submit.import');
  readonly simulateImportBtn: Locator = this.page.locator('button.btn-import-submit.simulate');
  readonly downloadSampleBtn: Locator = this.page.getByRole('button', { name: /download sample/i });
  readonly defaultPasswordInput: Locator = this.page.locator('#default_pass_all');
  readonly resultMessage: Locator = this.page.locator('.alert, [class*="import-result"], .import-message').first();

  async goto(): Promise<void> {
    await this.navigate('/admin/clients/import');
    await this.waitForPageLoad();
  }

  async uploadCsv(csvContent: string): Promise<void> {
    const tmpPath = path.resolve(process.cwd(), 'test-data/_tmp_import.csv');
    fs.writeFileSync(tmpPath, csvContent, 'utf-8');
    await this.fileInput.setInputFiles(tmpPath);
  }

  async uploadCsvFile(filePath: string): Promise<void> {
    await this.fileInput.setInputFiles(filePath);
  }

  async clickSimulateImport(): Promise<void> {
    await this.simulateImportBtn.click();
    await this.waitForPageLoad();
  }

  async clickImport(): Promise<void> {
    await this.importBtn.click();
    await this.waitForPageLoad();
  }

  async getResultText(): Promise<string> {
    await expect(this.resultMessage).toBeVisible({ timeout: 15000 });
    return this.resultMessage.innerText();
  }
}

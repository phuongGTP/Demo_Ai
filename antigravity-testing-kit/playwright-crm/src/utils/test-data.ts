import * as path from 'path';

function timestamp(): string {
  return new Date().toISOString().replace(/[-:.TZ]/g, '').slice(0, 12);
}

function rand(len = 4): string {
  return Math.random().toString(36).substring(2, 2 + len).toUpperCase();
}

export function generateCompanyName(prefix = 'AUTO'): string {
  return `${prefix}_Co_${timestamp()}_${rand()}`;
}

export function generateEmail(prefix = 'auto'): string {
  return `${prefix}_${timestamp()}_${rand()}@test.com`;
}

export function generatePhone(): string {
  return `09${Math.floor(10000000 + Math.random() * 89999999)}`;
}

export const testDataPaths = {
  validImport: path.resolve(process.cwd(), 'test-data/import-valid.csv'),
  missingEmail: path.resolve(process.cwd(), 'test-data/import-missing-email.csv'),
  duplicateEmail: path.resolve(process.cwd(), 'test-data/import-duplicate.csv'),
};

export function buildValidImportCsv(email: string, company: string): string {
  return `Firstname,Lastname,Email,Company\nImport,Auto,${email},${company}`;
}

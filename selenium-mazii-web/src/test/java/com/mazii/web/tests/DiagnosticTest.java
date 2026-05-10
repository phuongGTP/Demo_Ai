package com.mazii.web.tests;

import com.mazii.web.config.WebConfig;
import com.mazii.web.drivers.WebDriverFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;
import java.time.Duration;

import java.util.List;

public class DiagnosticTest {

    @Test
    public void inspectConversationPage() throws Exception {
        WebDriver driver = WebDriverFactory.createDriver();
        driver.manage().window().setSize(new Dimension(1440, 900));

        try {
            // Step 1: Login
            System.out.println("\n========= LOGIN =========");
            driver.get(WebConfig.getBaseUrl() + "/vi-VN");
            Thread.sleep(2000);

            // Click "Đăng nhập"
            WebElement loginBtn = driver.findElement(By.cssSelector("button.btn.btn-primary"));
            loginBtn.click();
            Thread.sleep(2000);
            System.out.println("URL after click Đăng nhập: " + driver.getCurrentUrl());

            // Dump input fields in login form
            System.out.println("\n--- LOGIN FORM INPUTS ---");
            List<WebElement> inputs = driver.findElements(By.tagName("input"));
            for (WebElement inp : inputs) {
                System.out.println("  type='" + inp.getAttribute("type")
                        + "' placeholder='" + inp.getAttribute("placeholder")
                        + "' id='" + inp.getAttribute("id")
                        + "' name='" + inp.getAttribute("name") + "'");
            }

            // Buttons in modal/page
            System.out.println("\n--- LOGIN BUTTONS ---");
            List<WebElement> buttons = driver.findElements(By.tagName("button"));
            for (int i = 0; i < Math.min(buttons.size(), 15); i++) {
                WebElement b = buttons.get(i);
                System.out.println("  [" + i + "] text='" + b.getText().trim().replaceAll("\\s+", " ")
                        + "' class='" + b.getAttribute("class") + "'");
            }

            // Step 2: Fill email/password and submit
            System.out.println("\n--- Filling credentials ---");
            List<WebElement> emailFields = driver.findElements(
                By.cssSelector("input[type='email'], input[placeholder*='mail'], input[placeholder*='tài khoản'], input[name*='email'], input[id*='email']"));
            List<WebElement> passwordFields = driver.findElements(
                By.cssSelector("input[type='password']"));

            System.out.println("Email fields found: " + emailFields.size());
            System.out.println("Password fields found: " + passwordFields.size());

            if (!emailFields.isEmpty() && !passwordFields.isEmpty()) {
                emailFields.get(0).sendKeys(WebConfig.getPremiumEmail());
                passwordFields.get(0).sendKeys(WebConfig.getPremiumPassword());

                // Find submit button
                List<WebElement> submitBtns = driver.findElements(
                    By.cssSelector("button[type='submit'], button.btn-primary"));
                System.out.println("Submit buttons: " + submitBtns.size());
                if (!submitBtns.isEmpty()) {
                    submitBtns.get(0).click();
                    Thread.sleep(4000);
                }
            }

            System.out.println("URL after login attempt: " + driver.getCurrentUrl());

            // Step 3: Navigate to conversation-translation
            String convUrl = WebConfig.getBaseUrl() + "/vi-VN/conversation-translation";
            System.out.println("\n========= conversation-translation =========");
            driver.get(convUrl);
            Thread.sleep(4000);
            System.out.println("URL: " + driver.getCurrentUrl());

            String bodyText = driver.findElement(By.tagName("body")).getText();
            System.out.println("\n--- BODY TEXT (3000 chars) ---");
            System.out.println(bodyText.substring(0, Math.min(bodyText.length(), 3000)));

            // Buttons on this page
            System.out.println("\n--- ALL BUTTONS ---");
            buttons = driver.findElements(By.tagName("button"));
            for (int i = 0; i < Math.min(buttons.size(), 30); i++) {
                WebElement b = buttons.get(i);
                System.out.println("  [" + i + "] text='" + b.getText().trim().replaceAll("\\s+", " ")
                        + "' class='" + b.getAttribute("class")
                        + "' id='" + b.getAttribute("id") + "'");
            }

            // Dismiss modal if present
            try {
                new WebDriverWait(driver, Duration.ofSeconds(4))
                    .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".modal.show")));
                ((JavascriptExecutor) driver).executeScript(
                    "document.querySelectorAll('.modal.show, .modal-backdrop').forEach(el => el.remove());" +
                    "document.body.classList.remove('modal-open');" +
                    "document.body.style.overflow = '';" +
                    "document.body.style.paddingRight = '';");
                Thread.sleep(500);
                System.out.println("Modal dismissed");
            } catch (Exception e) {
                System.out.println("No modal or dismiss failed: " + e.getMessage());
            }

            // Capture HTML before toggle
            String htmlBefore = (String) ((JavascriptExecutor) driver).executeScript(
                "var el = document.querySelector('app-conversation-translation');" +
                "return el ? el.outerHTML.substring(0, 8000) : document.body.innerHTML.substring(0, 8000)");
            System.out.println("\n--- CONVERSATION HTML BEFORE TOGGLE ---");
            System.out.println(htmlBefore);

            // Click keyboard toggle
            System.out.println("\n========= CLICKING KEYBOARD TOGGLE =========");
            try {
                WebElement toggleBtn = driver.findElement(By.cssSelector("button.btn-action"));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", toggleBtn);
                System.out.println("Toggle clicked! class='" + toggleBtn.getAttribute("class") + "'");
                Thread.sleep(2000);
            } catch (Exception e) {
                System.out.println("Toggle click failed: " + e.getMessage());
            }

            // Capture HTML after toggle
            String htmlAfter = (String) ((JavascriptExecutor) driver).executeScript(
                "var el = document.querySelector('app-conversation-translation');" +
                "return el ? el.outerHTML.substring(0, 10000) : document.body.innerHTML.substring(0, 10000)");
            System.out.println("\n--- CONVERSATION HTML AFTER TOGGLE (keyboard mode) ---");
            System.out.println(htmlAfter);

            // List all inputs after toggle
            System.out.println("\n--- INPUTS AFTER TOGGLE ---");
            List<WebElement> allInputs = driver.findElements(By.cssSelector("textarea, input:not([type='hidden'])"));
            for (WebElement inp : allInputs) {
                System.out.println("  tag=" + inp.getTagName() + " class='" + inp.getAttribute("class")
                    + "' placeholder='" + inp.getAttribute("placeholder")
                    + "' id='" + inp.getAttribute("id") + "' visible=" + inp.isDisplayed());
            }

            // ── TYPE TEXT + SEND ──────────────────────────────────────────────────
            System.out.println("\n========= TYPE TEXT + SEND =========");
            try {
                // First, need to select language (click small VI mic in swap-language)
                List<WebElement> swapBtns = driver.findElements(By.cssSelector("app-conversation-translation .swap-language button"));
                System.out.println("Swap-language buttons found: " + swapBtns.size());
                for (int i = 0; i < swapBtns.size(); i++) {
                    System.out.println("  swap-btn[" + i + "] class='" + swapBtns.get(i).getAttribute("class") + "'");
                }

                // Click VI mic button (last in swap-language = btn-primary)
                if (swapBtns.size() >= 3) {
                    System.out.println("Clicking VI mic button to set VI input mode...");
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", swapBtns.get(2));
                    Thread.sleep(500);
                }

                // Find input and type
                WebElement input = driver.findElement(By.cssSelector("app-conversation-translation .input-container input.form-control"));
                input.click();
                input.clear();
                input.sendKeys("Xin chào");
                ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].dispatchEvent(new Event('input', {bubbles: true}));", input);
                Thread.sleep(1000);

                // Check send button state after typing
                List<WebElement> sendBtns = driver.findElements(By.cssSelector("app-conversation-translation .input-container > button.btn-primary.rounded-circle"));
                System.out.println("Direct-child send buttons: " + sendBtns.size());
                for (WebElement sb : sendBtns) {
                    System.out.println("  send btn class='" + sb.getAttribute("class")
                        + "' disabled='" + sb.getAttribute("disabled")
                        + "' visible=" + sb.isDisplayed());
                }

                // Click send
                if (!sendBtns.isEmpty()) {
                    System.out.println("Clicking send button...");
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", sendBtns.get(0));
                    Thread.sleep(5000);
                } else {
                    System.out.println("No send button found with direct-child selector!");
                    // Try ALL btn-primary buttons
                    List<WebElement> allPrimary = driver.findElements(By.cssSelector("app-conversation-translation button.btn-primary.rounded-circle"));
                    System.out.println("All btn-primary.rounded-circle: " + allPrimary.size());
                    for (WebElement b : allPrimary) {
                        System.out.println("  class='" + b.getAttribute("class") + "' disabled='" + b.getAttribute("disabled") + "'");
                    }
                }

                // Capture chat-scroll HTML after send
                String chatHtml = (String) ((JavascriptExecutor) driver).executeScript(
                    "var el = document.querySelector('div.chat-scroll');" +
                    "return el ? el.outerHTML.substring(0, 8000) : 'chat-scroll not found'");
                System.out.println("\n--- CHAT SCROLL HTML AFTER SEND ---");
                System.out.println(chatHtml);

            } catch (Exception e) {
                System.out.println("Type+Send failed: " + e.getMessage());
            }

        } finally {
            driver.quit();
        }
    }
}

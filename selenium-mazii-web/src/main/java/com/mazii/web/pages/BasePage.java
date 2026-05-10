package com.mazii.web.pages;

import com.mazii.web.config.WebConfig;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public abstract class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;
    protected final Actions actions;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(WebConfig.getExplicitWait()));
        this.actions = new Actions(driver);
    }

    protected WebElement waitVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected boolean isDisplayed(By locator) {
        return isDisplayed(locator, WebConfig.getExplicitWait());
    }

    protected boolean isDisplayed(By locator, int timeoutSeconds) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                    .until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    protected boolean isAbsent(By locator, int timeoutSeconds) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                    .until(ExpectedConditions.invisibilityOfElementLocated(locator));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    protected List<WebElement> findAll(By locator) {
        return driver.findElements(locator);
    }

    protected void hover(By locator) {
        WebElement element = waitVisible(locator);
        actions.moveToElement(element).perform();
    }

    protected void hover(WebElement element) {
        actions.moveToElement(element).perform();
    }

    protected void click(By locator) {
        try {
            waitClickable(locator).click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(locator));
        }
    }

    protected void jsClick(By locator) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(locator));
    }

    protected void type(By locator, String text) {
        WebElement el = waitVisible(locator);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        // Focus + clear via JS to bypass any overlay (modal, backdrop)
        js.executeScript("arguments[0].focus(); arguments[0].value = '';", el);
        el.sendKeys(text);
        // Trigger Angular's input event so reactive forms update button state
        js.executeScript("arguments[0].dispatchEvent(new Event('input', {bubbles: true}));", el);
    }

    protected String getText(By locator) {
        return waitVisible(locator).getText();
    }

    public void waitForUrl(String urlFragment) {
        wait.until(ExpectedConditions.urlContains(urlFragment));
    }

    protected void waitForPageLoad() {
        wait.until(d -> ((JavascriptExecutor) d)
                .executeScript("return document.readyState").equals("complete"));
    }
}

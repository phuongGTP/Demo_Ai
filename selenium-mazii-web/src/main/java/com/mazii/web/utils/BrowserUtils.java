package com.mazii.web.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v120.network.Network;

import java.util.Optional;

public class BrowserUtils {

    /**
     * Bật offline mode qua Chrome DevTools Protocol.
     * Chỉ hoạt động với ChromeDriver.
     */
    public static void setOffline(WebDriver driver) {
        if (driver instanceof ChromeDriver) {
            DevTools devTools = ((ChromeDriver) driver).getDevTools();
            devTools.createSessionIfThereIsNotOne();
            devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
            devTools.send(Network.emulateNetworkConditions(
                    true, 0, -1, -1, Optional.empty()));
        } else {
            // Fallback via JS navigator.onLine override (limited — only affects JS checks, not actual requests)
            ((JavascriptExecutor) driver).executeScript(
                    "Object.defineProperty(navigator, 'onLine', { get: () => false })");
        }
    }

    /**
     * Tắt offline mode, khôi phục kết nối mạng.
     */
    public static void setOnline(WebDriver driver) {
        if (driver instanceof ChromeDriver) {
            DevTools devTools = ((ChromeDriver) driver).getDevTools();
            devTools.createSessionIfThereIsNotOne();
            devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
            devTools.send(Network.emulateNetworkConditions(
                    false, 0, -1, -1, Optional.empty()));
        } else {
            ((JavascriptExecutor) driver).executeScript(
                    "Object.defineProperty(navigator, 'onLine', { get: () => true })");
        }
    }

    /**
     * Cuộn trang xuống cuối để kiểm tra auto-scroll behavior.
     */
    public static void scrollToBottom(WebDriver driver) {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    /**
     * Kiểm tra một element có nằm trong viewport không.
     */
    public static boolean isInViewport(WebDriver driver, org.openqa.selenium.WebElement element) {
        return (Boolean) ((JavascriptExecutor) driver).executeScript(
                "var rect = arguments[0].getBoundingClientRect();" +
                "return (" +
                "  rect.top >= 0 &&" +
                "  rect.left >= 0 &&" +
                "  rect.bottom <= (window.innerHeight || document.documentElement.clientHeight) &&" +
                "  rect.right <= (window.innerWidth || document.documentElement.clientWidth)" +
                ");", element);
    }
}

package com.mazii.web.pages;

import com.mazii.web.config.WebConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DichPage extends BasePage {

    // Navigate directly — app uses /vi-VN/ locale prefix
    private static final String CONV_URL = "/vi-VN/conversation-translation";

    public DichPage(WebDriver driver) {
        super(driver);
    }

    public DichHoiThoaiPage goToDichHoiThoai() {
        driver.get(WebConfig.getBaseUrl() + CONV_URL);
        waitForPageLoad();
        return new DichHoiThoaiPage(driver);
    }

    public DichHoiThoaiPage goToDichHoiThoaiAsGuest() {
        driver.get(WebConfig.getBaseUrl() + CONV_URL);
        waitForPageLoad();
        return new DichHoiThoaiPage(driver);
    }
}

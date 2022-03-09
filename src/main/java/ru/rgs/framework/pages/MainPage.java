package ru.rgs.framework.pages;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MainPage extends BasePage{

    @FindBy(xpath = "//div[contains(@class, 'cookie')]//button[contains(text(), 'Хорошо')]")
    private WebElement cookieConsentButton;

    @Step("Проверка загрузки главной страницы")
    public MainPage checkPageOpening() {
        Assert.assertTrue(
                "Главная страница не открыта",
                waitUntilTitleIs("ПАО СК «Росгосстрах» — флагман отечественного рынка страхования")
        );
        return this;
    }
    @Step("Закрыть окно Cookie")
    public MainPage closeCookieWindow() {
        waitUntilElementToBeClickable(cookieConsentButton).click();

        Assert.assertTrue("Окно Cookie не закрылось",
                waitUntilInvisibilityOf("//div[contains(@class, 'cookie')]"));
        return this;
    }
}

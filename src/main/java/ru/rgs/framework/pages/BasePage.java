package ru.rgs.framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.rgs.framework.managers.DriverManager;

public class BasePage {
    protected DriverManager driverManager = DriverManager.getInstance();
    protected WebDriverWait wait = new WebDriverWait(driverManager.getDriver(), 10, 1000);
    protected JavascriptExecutor js = (JavascriptExecutor) driverManager.getDriver();

    public BasePage() {
        PageFactory.initElements(driverManager.getDriver(), this);
    }

    protected WebElement waitUntilElementToBeClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    protected WebElement waitUntilElementToBeVisible(String elementXpath) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(elementXpath)));
    }

    protected Boolean waitUntilTitleIs(String title) {
        return wait.until(ExpectedConditions.titleIs(title));
    }

    protected Boolean waitUntilInvisibilityOf(String elementXpath) {
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(elementXpath)));
    }

    protected WebElement scrollToElementByJs(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView(true);", element);
        return element;
    }

    public WebElement scrollWithOffset(WebElement element, int x, int y) {
        String code = "window.scroll(" + (element.getLocation().x + x) + ","
                + (element.getLocation().y + y) + ");";
        js.executeScript(code, element, x, y);

        return element;
    }

    protected void fillInputField(WebElement field, String value) {
        waitUntilElementToBeClickable(scrollToElementByJs(field)).click();
        field.sendKeys(value);
    }

    protected Boolean checkInputFilling(WebElement field, String value) {
        waitUntilElementToBeClickable(field);
        return field.getAttribute("value").equals(value);
    }
}

package ru.rgs.framework.pages;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.util.List;

public class ForCompaniesPage extends  BasePage{

    @FindBy(xpath = "//div[@class='category']//li")
    private List<WebElement> categories;

    @FindBy(xpath = "//div[@class='header-list-products']//li")
    private List<WebElement> products;

    @Step("Проверка загрузки страницы 'Для компаний'")
    public ForCompaniesPage checkPageOpening() {
        Assert.assertTrue(
                "Страницы 'Для компаний' не открыта",
                waitUntilTitleIs("Страхование компаний и юридических лиц | Росгосстрах")
        );
        return this;
    }

    @Step("Выбрать категорию '{name}'")
    public ForCompaniesPage selectCategoryByName(String categoryName) {
        for (WebElement category : categories) {
            if(category.getText().trim().equalsIgnoreCase(categoryName)) {
                waitUntilElementToBeClickable(category).click();

                String categoryTabsXpath = "//div[contains(@class, 'tabs-wrap')]//a[contains(text(), '" + categoryName +"')]";
                Assert.assertTrue(
                        "Подменю категории '" + categoryName + "' не открылось",
                        waitUntilElementToBeVisible(categoryTabsXpath).isDisplayed()
                );

                return this;
            }
        }
        Assert.fail("Меню с текстом '" + categoryName + "' не найдено на странице " + driverManager.getDriver().getCurrentUrl());
        return this;
    }

    @Step("Перейти по ссылке '{name}' в открывшейся категории")
    public ForCompaniesPage followTheLinkInTheOpenedCategoryByName(String name) {
        for (WebElement product : products) {
            if(product.getText().trim().equalsIgnoreCase(name)) {
                waitUntilElementToBeClickable(product).click();
                return this;
            }
        }
        Assert.fail("Меню с текстом '" + name + "' не найдено на странице " + driverManager.getDriver().getCurrentUrl());
        return this;
    }
}

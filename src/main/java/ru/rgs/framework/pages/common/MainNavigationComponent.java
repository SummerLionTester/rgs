package ru.rgs.framework.pages.common;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.rgs.framework.pages.BasePage;

import java.util.List;

public class MainNavigationComponent extends BasePage {

    @FindBy(xpath = "//nav[@class='navigation']//li")
    private List<WebElement> navigationItems;

    @Step("Выбрать '{name}' в главном навигационном меню")
    public MainNavigationComponent selectNavigationItemByName(String name) {
        for (WebElement item : navigationItems) {
            if(item.getText().trim().equalsIgnoreCase(name)) {
                waitUntilElementToBeClickable(item).click();
                return this;
            }
        }

        Assert.fail("Меню с текстом '" + name + "' не найдено на странице " + driverManager.getDriver().getCurrentUrl());
        return this;
    }
}

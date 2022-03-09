package ru.rgs.framework.pages;

import io.qameta.allure.Step;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class DMSPage extends BasePage {
    private final String NAME_FORM_FIELD_NAME = "ваши фио";
    private final String PHONE_FORM_FIELD_NAME = "номер телефона";
    private final String MAIL_FORM_FIELD_NAME = "ваша почта";
    private final String ADDRESS_FORM_FIELD_NAME = "ваш адрес";


    @FindBy(xpath = "//button//span[contains(text(), 'Отправить заявку')]")
    private WebElement sendAnApplicationButton;

    @FindBy(xpath = "//div[contains(@class, 'sectionForm')]")
    private WebElement formSection;

    @FindBy(xpath = "//input[@name='userName']")
    private WebElement formInputName;

    @FindBy(xpath = "//input[@name='userTel']")
    private WebElement formInputPhone;

    @FindBy(xpath = "//input[@name='userEmail']")
    private WebElement formInputMail;

    @FindBy(xpath = "//input[@placeholder='Введите']")
    private WebElement formInputAddress;

    @FindBy(xpath = "//input[@type='checkbox']")
    private WebElement formPersonalDataCheckbox;

    @FindBy(xpath = "//div[contains(@class, 'form__checkbox')]//label")
    private WebElement formPersonalDataCheckboxLabel;

    @FindBy(xpath = "//button[contains(text(), 'Свяжитесь со мной')]")
    private WebElement formSubmitButton;

    @Step("Проверка загрузки страницы 'ДМС'")
    public DMSPage checkPageOpening() {
        Assert.assertTrue(
                "Страницы 'ДMC' не открыта",
                waitUntilTitleIs("Добровольное медицинское страхование для компаний и юридических лиц в Росгосстрахе")
        );
        return this;
    }

    @Step("Нажать на кнопку 'Отправить заявку'")
    public DMSPage clickOnSendAnApplicationButton() {
        waitUntilElementToBeClickable(sendAnApplicationButton).click();

        Assert.assertTrue("Секцию формы заявки на ДМС не видно на экране",
                wait.until(new ExpectedCondition<Boolean>() {
                    @NullableDecl
                    @Override
                    public Boolean apply(@NullableDecl WebDriver input) {
                        return (Boolean) wait.until(ExpectedConditions.jsReturnsValue("return (Math.round(document.querySelector(\".sectionForm\").getBoundingClientRect().top + window.pageYOffset) === window.scrollY)"));
                    }
                }));

        return this;
    }

    @Step("Заполнить поле формы '{fieldName}' значением '{value}'")
    public DMSPage fillFormField(String fieldName, String value) {
        WebElement field = getFormInputField(fieldName);
        fillInputField(field, value);

        if (fieldName.equals(PHONE_FORM_FIELD_NAME)) {
            Assert.assertTrue(
                    "Поле формы '" + fieldName + "' не заполнилось значением '" + value + "'",
                    checkInputFilling(field, value.replaceFirst("(\\d{3})(\\d{3})(\\d+)", "+7 ($1) $2-$3"))
            );
        } else {
            Assert.assertTrue(
                    "Поле формы '" + fieldName + "' не заполнилось значением '" + value + "'",
                    checkInputFilling(field, value)
            );
        }

        return this;
    }

    private WebElement getFormInputField(String fieldName) {
        WebElement field = null;
        switch (fieldName.toLowerCase()) {
            case NAME_FORM_FIELD_NAME:
                field = formInputName;
                break;
            case PHONE_FORM_FIELD_NAME:
                field = formInputPhone;
                break;
            case MAIL_FORM_FIELD_NAME:
                field = formInputMail;
                break;
            case ADDRESS_FORM_FIELD_NAME:
                field = formInputAddress;
                break;
            default:
                Assert.fail("Поле " + fieldName + "' отсутствует в форме на странице ДМС");
        }

        return field;
    }

    @Step("Активировать чекбокс согласия на обработку персональных данных")
    public DMSPage activatePersonalDataFormCheckbox() {
        if (!formPersonalDataCheckbox.isSelected()) {
            waitUntilElementToBeClickable(formPersonalDataCheckboxLabel).click();
        }
        Assert.assertTrue(
                "Чекбокс согласия на обработку персональных данных не активирован",
                wait.until(ExpectedConditions.elementToBeSelected(formPersonalDataCheckbox))
        );

        return this;
    }

    @Step("Нажать кнопку 'Свяжитесь со мной'")
    public DMSPage clickSubmitFormButton() {
        waitUntilElementToBeClickable(scrollToElementByJs(formSubmitButton)).click();

        Assert.assertTrue(
                "Кнопка не отключена",
                wait.until(ExpectedConditions.attributeToBe(formSubmitButton, "disabled", "true"))
        );

        return this;
    }

    @Step("Проверить, что сообщение c текстом ошибки для поля '{fieldName}' о ошибке содержит '{errorText}'")
    public DMSPage checkErrorMessageOnField(String fieldName, String errorText) {
        WebElement errorMessageField = getFormInputField(fieldName).findElement(By.xpath("./../..//span[contains(@class, 'input__error')]"));
        wait.until(ExpectedConditions.visibilityOf(errorMessageField));
        scrollWithOffset(scrollToElementByJs(getFormInputField(fieldName)), 0, -300);

        Assert.assertEquals(
                "Сообщение с ошибкой для поля '" + fieldName + "' неверное. Ожидаемое: " + errorText + ". Фактическое: " + errorMessageField.getText().trim(),
                errorMessageField.getText().trim(),
                errorText
        );

        return this;
    }
}

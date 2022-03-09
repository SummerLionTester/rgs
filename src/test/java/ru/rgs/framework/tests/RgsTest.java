package ru.rgs.framework.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import ru.rgs.framework.base.BaseTests;
import ru.rgs.framework.pages.DMSPage;
import ru.rgs.framework.pages.ForCompaniesPage;
import ru.rgs.framework.pages.MainPage;
import ru.rgs.framework.pages.common.MainNavigationComponent;

public class RgsTest extends BaseTests {


    @Test
    @DisplayName("Страхование ДМС")
    @Description("Проверяет появление сообщения 'Введите корректный email' при вводе некорректного email на форме отпраки заявки ")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Dmitriy Petrov")
    public void insuranceTest(){
        pageManager.getPage(MainPage.class)
                .checkPageOpening()
                .closeCookieWindow();
        pageManager.getPage(MainNavigationComponent.class)
                .selectNavigationItemByName("Компаниям");
        pageManager.getPage(ForCompaniesPage.class)
                .checkPageOpening()
                .selectCategoryByName("Здоровье")
                .followTheLinkInTheOpenedCategoryByName("Добровольное медицинское страхование");
        pageManager.getPage(DMSPage.class)
                .checkPageOpening()
                .clickOnSendAnApplicationButton()
                .fillFormField("ваши фио", "Иванов Иван Иваныч")
                .fillFormField("номер телефона", "9999999999")
                .fillFormField("ваша почта", "sdasdd")
                .fillFormField("ваш адрес", "тест адрес")
                .activatePersonalDataFormCheckbox()
                .clickSubmitFormButton()
                .checkErrorMessageOnField("ваша почта", "Введите корректный адрес электронной почты");
    }
}

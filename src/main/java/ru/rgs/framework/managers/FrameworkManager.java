package ru.rgs.framework.managers;

import org.openqa.selenium.WebDriver;
import ru.rgs.framework.constants.APP_PROPS;

import java.util.concurrent.TimeUnit;

public class FrameworkManager {

    private static final PropManager props = PropManager.getInstance();
    private static final DriverManager driverManager = DriverManager.getInstance();
    private static final PageManager pageManager = PageManager.getInstance();
    private static final long implicitlyWaitTime = Integer.parseInt(props.getProperty(APP_PROPS.IMPLICITLY_WAIT.toString()));
    private static final long pageLoadTimeoutTime = Integer.parseInt(props.getProperty(APP_PROPS.PAGE_LOAD_TIMEOUT.toString()));

    public static void init() {
        WebDriver driver = driverManager.getDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(implicitlyWaitTime, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(pageLoadTimeoutTime, TimeUnit.SECONDS);
    }

    public static void quit() {
        driverManager.quitDriver();
        pageManager.clearPageStorage();
    }
}

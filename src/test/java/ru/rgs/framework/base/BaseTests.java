package ru.rgs.framework.base;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import ru.rgs.framework.constants.APP_PROPS;
import ru.rgs.framework.managers.DriverManager;
import ru.rgs.framework.managers.FrameworkManager;
import ru.rgs.framework.managers.PageManager;
import ru.rgs.framework.managers.PropManager;

public class BaseTests {
    protected PageManager pageManager = PageManager.getInstance();
    private final PropManager props = PropManager.getInstance();
    private final DriverManager driverManager = DriverManager.getInstance();
    private final String baseUrl = props.getProperty(APP_PROPS.BASE_URL.toString());

    @BeforeClass
    public static void beforeAll() {
        FrameworkManager.init();
    }

    @Before
    public void before() {
        driverManager.getDriver().get(baseUrl);
    }

    @AfterClass
    public static void afterAll() {
        FrameworkManager.quit();
    }

}

import helpers.ResultOutput;
import helpers.drivers.WebDriverManager;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import pages.LoginPage;
import pages.WelcomePage;

public class TestAutomation {
    private WebDriverManager webDriverManager;
    private String nameMethod;
    private final String authPage = "https://idemo.bspb.ru/";

    private LoginPage loginPage;
    private WelcomePage welcomePage;

    @BeforeMethod
    public void setUp() {
        loginPage = new LoginPage();
        welcomePage = new WelcomePage();
        webDriverManager = new WebDriverManager();
    }

    @Test
    public void testLoginAndVerifyFinancialFreedom() {
        nameMethod = "testLoginAndVerifyFinancialFreedom";
        ResultOutput.printTestStart(nameMethod);

        verifyUrl(authPage + "auth/otp", loginPage.login("demo", "demo"));
        verifyUrl(authPage + "welcome", loginPage.enterConfirmationCode("0000"));

        assertTrue(welcomePage.isFinancialFreedomVisible(), "Финансовая свобода не отображается");
        assertEquals("2 851 176.01 ₽", welcomePage.getFinancialFreedomAmount(), "Сумма отображается неверно");

        verifyCardPopoverContent("Travel *6192");
        verifyCardPopoverContent("Золотая *2224");
    }

    private void verifyUrl(String expectedUrl, String actualUrl) {
        assertEquals(expectedUrl, actualUrl, "URL страницы не соответствует ожидаемому");
    }

    private void verifyCardPopoverContent(String cardName) {
        welcomePage.hoverOverCardSymbol(cardName);
        assertEquals(cardName, welcomePage.getCardPopoverContent(cardName), "Надпись для " + cardName + " не отображается корректно");
    }

    @AfterClass
    public void tearDown() {
        ResultOutput.printTestEnd(nameMethod);
        webDriverManager.closeDriver();
    }
}

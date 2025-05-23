import helpers.ResultOutput;
import helpers.drivers.WebDriverManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.LoginPage;
import pages.WelcomePage;

public class TestAutomation {
    private String nameMethod;
    private final String authPage = "https://idemo.bspb.ru/";

    private LoginPage loginPage;
    private WelcomePage welcomePage;

    @BeforeEach
    public void setUp() {
        ResultOutput.log("выполнение тестов класса TestAutomation");
        loginPage = new LoginPage();
        welcomePage = new WelcomePage();
    }

    @Test
    public void testLoginAndVerifyFinancialFreedom() {
        nameMethod = "testLoginAndVerifyFinancialFreedom";
        ResultOutput.printTestStart(nameMethod);

        verifyUrl(authPage + "auth/otp", loginPage.login("demo", "demo"));
        verifyUrl(authPage + "welcome", loginPage.enterConfirmationCode("0000"));

        assertTrue(welcomePage.isFinancialFreedomVisible(), "Финансовая свобода не отображается");
        assertTrue(welcomePage.retrieveAndFormatFinancialFreedomAmount().matches("^\\d{1,3}( \\d{3})*(\\.\\d{2})? ₽$"),
                "Сумма на странице не соответствует ожидаемому формату.");

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

    @AfterEach
    public void tearDown() {
        ResultOutput.printTestEnd(nameMethod);
        WebDriverManager.closeDriver();
    }
}

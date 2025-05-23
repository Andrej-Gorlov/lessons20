package pages;

import helpers.ElementAction;
import helpers.ResultOutput;
import helpers.drivers.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class LoginPage extends WebDriverManager implements ElementAction {

    @Override
    public void perform(WebElement element) {
        element.click();
    }

    private final By usernameField = By.id("username");
    private final By passwordField = By.name("password");
    private final By loginButton = By.id("login-button");
    private final By confirmationCodeField = By.id("otp-code");
    private final By confirmButton = By.id("login-otp-button");

    public LoginPage(){
        checkDriverInitialization();
    }

    /**
     * Выполняет вход в систему, заполняя поля имени пользователя и пароля.
     *
     * @param username Имя пользователя.
     * @param password Пароль.
     * @return URL текущей страницы после попытки входа.
     */
    public String login(String username, String password) {
        ResultOutput.log("Выполняется вход в систему");
        fillField(usernameField, username);
        fillField(passwordField, password);
        clickElement(loginButton);
        ResultOutput.log("Возращение страницы: " + driver.getCurrentUrl());
        return driver.getCurrentUrl();
    }

    /**
     * Вводит код подтверждения и нажимает кнопку подтверждения.
     *
     * @param code Код подтверждения.
     * @return URL текущей страницы после ввода кода.
     */
    public String enterConfirmationCode(String code) {
        ResultOutput.log("Ввод кода подтверждения: " + code);
        fillField(confirmationCodeField, code);
        clickElement(confirmButton);
        ResultOutput.log("Возращение страницы: " + driver.getCurrentUrl());
        return driver.getCurrentUrl();
    }

    /**
     * Заполняет поле, очищая его перед вводом нового значения.
     *
     * @param locator Локатор элемента, который нужно заполнить.
     * @param value   Значение для ввода в поле.
     */
    private void fillField(By locator, String value) {
        waitForElementAndPerform(locator, element -> {
            element.clear();
            element.sendKeys(value);
        });
    }

    private void clickElement(By locator) {
        ResultOutput.log("Клик на элемент, используя его локатор ");
        waitForElementAndPerform(locator, WebElement::click);
    }

    /**
     * Ожидает видимости элемента и выполняет указанное действие над ним.
     *
     * @param locator Локатор элемента, с которым нужно взаимодействовать.
     * @param action  Действие, которое нужно выполнить над элементом.
     */
    private void waitForElementAndPerform(By locator, ElementAction action) {
        WebElement element = waitForElementAndFind(locator);
        action.perform(element);
    }

    private WebElement waitForElementAndFind(By locator) {
        waitForElementVisible(locator, 10);
        return driver.findElement(locator);
    }
}



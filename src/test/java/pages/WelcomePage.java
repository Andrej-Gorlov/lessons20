package pages;

import helpers.ResultOutput;
import helpers.drivers.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class WelcomePage extends WebDriverManager {
    private final By financialFreedomBlock = By.xpath("//span[@id='accounts-can-spend']//span[contains(text(), 'Финансовая свобода')]");
    private final By financialFreedomAmount = By.xpath("//span[@id='accounts-can-spend']//span[contains(@class, 'can-spend-amount')]//span[@class='value']");

    public WelcomePage(){
        checkDriverInitialization();
    }

    /**
     * Проверяет, отображается ли блок на странице.
     *
     * @return true, если блок виден; false в противном случае.
     */
    public boolean isFinancialFreedomVisible() {
        ResultOutput.log("Находим элемент блока \"Финансовая свобода\" и проверяем, отображается ли он");
        return waitForElementAndFind(financialFreedomBlock).isDisplayed();
    }

    /**
     * Получает сумму, доступную в блоке "Финансовая свобода", в формате строки.
     *
     * @return строка, представляющая сумму в формате "число ₽".
     */
    public String getFinancialFreedomAmount() {
        ResultOutput.log("Получаем текст суммы из элемента и форматируем его");
        return formatAmount(waitForElementAndFind(financialFreedomAmount).getText());
    }

    /**
     * Наводит курсор на символ карты, используя указанный текст символа.
     *
     * @param cardSymbol текст, который соответствует значению атрибута data-content элемента карты.
     */
    public void hoverOverCardSymbol(String cardSymbol) {
        ResultOutput.log("Находим элемент карты по тексту символа и выполняем наведение курсора");
        WebElement card = waitForElementAndFind(By.xpath(String.format("//a[@data-content='%s']", cardSymbol)));
        new Actions(driver).moveToElement(card).perform();
    }

    /**
     * Получает содержимое поповера для символа карты, используя указанный текст символа.
     *
     * @param cardSymbol текст, который соответствует значению атрибута data-content элемента карты.
     * @return строка, содержащая текст поповера для указанного символа карты.
     */
    public String getCardPopoverContent(String cardSymbol) {
        ResultOutput.log("Находим элемент карты и возвращаем содержимое атрибута data-content");
        WebElement card = waitForElementAndFind(By.xpath(String.format("//a[@data-content='%s']", cardSymbol)));
        return card.getAttribute("data-content");
    }

    private WebElement waitForElementAndFind(By locator) {
        waitForElementVisible(locator, 10);
        return driver.findElement(locator);
    }

    /**
     * Форматирует строку суммы, заменяя неразрывные пробелы и добавляя символ валюты.
     *
     * @param amount строка, представляющая сумму.
     * @return отформатированная строка суммы в формате "число ₽".
     */
    private String formatAmount(String amount) {
        ResultOutput.log("Заменяем неразрывные пробелы на обычные и добавляем символ валюты");
        return amount.replace("&nbsp;", " ").trim() + " ₽";
    }
}

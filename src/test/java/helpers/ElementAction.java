package helpers;

import org.openqa.selenium.WebElement;

@FunctionalInterface
public interface ElementAction {
    void perform(WebElement element);
}

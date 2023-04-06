package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;


public class CardDeliveryTest {
    public String generateDate(long numberOfDays, String pattern) {
        return LocalDate.now().plusDays(numberOfDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void shouldSuccessfullyIssueCardDelivery() {
        Configuration.holdBrowserOpen = true;
        String currentDate = generateDate(3, "dd.MM.yyyy");
        open("http://localhost:9999/");
        $("[data-test-id=city] input").setValue("Тверь");
        $("[data-test-id=date]").click();
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] input").sendKeys(currentDate);
        $("[data-test-id=name] input").setValue("Каменская Анна-Мария");
        $("[data-test-id=phone] input").setValue("+79652145634");
        $("[data-test-id='agreement']").click();
        $x("//span[@class='button__content']//parent::button").click();
        $("[data-test-id='notification']").shouldBe(Condition.visible, Duration.ofSeconds(15)).shouldHave(Condition.text("Встреча успешно забронирована на " + currentDate));
    }
}

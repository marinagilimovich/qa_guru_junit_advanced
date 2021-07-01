package tests;

import extensions.Screenshots;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byAttribute;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

@ExtendWith({Screenshots.class})
public class CheckSoftAssertionsPageOnGitHub {

    @Test
    void findSelenideOnGitHub() {
        open("https://github.com/");
        $("[name='q']").setValue("selenide").pressEnter();
        $(byAttribute("href", "/selenide/selenide")).click();
        $(byAttribute("data-tab-item", "i4wiki-tab")).click();
        $(byText("Soft assertions")).click();
        $("#wiki-body").shouldHave(text("Using JUnit5 extend test class:"));
    }

    @Test
    void findTextOnSelenideGitHubPage() {
        open("https://github.com/");
        $("[name='q']").setValue("selenide").pressEnter();
        $(byAttribute("href", "/selenide/selenide")).click();
        $(byAttribute("data-tab-item", "i4wiki-tab")).click();
        $(byText("Soft assertions")).click();
        $("#wiki-body").shouldHave(text("Hello, world!"));
    }

}

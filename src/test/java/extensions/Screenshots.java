package extensions;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static helpers.AttachmentHelper.attachScreenshot;

    public class Screenshots implements AfterEachCallback {

        @Override
        public void afterEach(ExtensionContext context){
            if(context.getExecutionException().isPresent())
                attachScreenshot("Last screenshot");
            closeWebDriver();
        }
    }

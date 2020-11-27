package tests.features;

import com.github.stormbit.playwright.Deferred;
import com.github.stormbit.playwright.Frame;
import com.github.stormbit.playwright.Page;
import com.github.stormbit.playwright.Response;
import io.cucumber.java.After;
import io.cucumber.java.en.*;
import tests.BrowserClass;

import java.util.List;
import java.util.Map;

import static com.github.stormbit.playwright.Page.LoadState.DOMCONTENTLOADED;
import static com.github.stormbit.playwright.Page.LoadState.LOAD;
import static org.junit.jupiter.api.Assertions.*;

public class BrowserSteps extends BrowserClass {
    @After
    public void afterAll() {
        shutdownPlaywright();
    }

    @Given("using fresh browser instance")
    public void usingFreshBrowserInstance() {
    }

    @And("should be logged in as {string} user")
    public void shouldBeLoggedInAsUser(String firstName) {
        String TEST_BANNER_MESSAGE = "Welcome, " + firstName;
        String TEST_BANNER_SELECTOR = "li[data-test-name=\"ADAM SMITH\"]";
        String welcome = page.textContent(TEST_BANNER_SELECTOR);
        assertTrue(welcome.contains(TEST_BANNER_MESSAGE), "Welcome banner should contain " +
                TEST_BANNER_MESSAGE + " for " + page.url());
    }

    @And("should made a screenshot")
    public void shouldMadeAScreenshot() {
        madeScreenShot(page);
    }

    @Given("trying to open {string}")
    public void tryingToOpen(String address) {
        Deferred<Response> response;
        response = page.waitForNavigation();
        page.navigate(address);
        response.get();
    }
}

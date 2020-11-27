package tests;

import com.github.stormbit.playwright.*;
import io.cucumber.junit.platform.engine.Cucumber;

import java.nio.file.Paths;

import static java.lang.System.currentTimeMillis;

@Cucumber
public class BrowserClass extends RootClass {
    public static Page page = initPage();
    private static Browser browser = null;
    private static BrowserContext context = null;
    private static Playwright playwright = null;

    private static boolean PLAYWRIGHT_ENABLED = true;
    private static boolean HEADLESS_BROWSER = true;

    private static final long WEB_POLLING_INTERVAL = 1000;
    private static final long WEB_WAIT_INTERVAL = 30000;
    private static final String SCREENSHOTS_FOLDER = "target/screenshots/";

    static Browser initBrowser() {
        System.out.println(" ******* initBrowser");
        if (playwright != null) {
            BrowserType.LaunchOptions lo = new BrowserType.LaunchOptions();
            if (HEADLESS_BROWSER) {
                lo.headless = false;
            }
            if(proxy != null) {
                lo.setProxy().withServer(proxyAddress);
            }
            return playwright.chromium().launch(lo);
        }
        return null;
    }

    static BrowserContext initContext() {
        System.out.println(" ******* initContext");
        if (browser != null) {
            return browser.newContext(
                    new Browser
                            .NewContextOptions()
                            .withViewport(1600, 950)
            );
        }
        return null;
    }

    static Page initPage() {
        System.out.println(" ******* initPage");
//        if (PLAYWRIGHT_ENABLED) {
            playwright = initPlaywright();
            browser = initBrowser();
            context = initContext();
            if (context != null) {
                return context.newPage();
            }
//        }
        return null;
    }

    static Playwright initPlaywright() {
        System.out.println(" ******* initPlaywright");
        if (playwright == null) {
            return Playwright.create();
        }
        return null;
    }

    public static void shutdownPlaywright() {
        System.out.println(" ******* shutdownPlaywright");
    }

    public String stripHTML(String src) {
        return src.replaceAll("\\<.*?\\>", "");
    }

    public void madeScreenShot(Page page) {
        String screenshotFileName = SCREENSHOTS_FOLDER + currentTimeMillis() + "-" + new Exception().getStackTrace()[1].getMethodName() + ".png";
        System.out.println(" ******* madeScreenShot " + screenshotFileName);
        page.screenshot(new Page.ScreenshotOptions().withPath(Paths.get(screenshotFileName)));
    }

}

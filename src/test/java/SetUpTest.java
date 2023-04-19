import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

public class SetUpTest {
    private  final String username = "rakiraja7751841655";
    private  final String accesskey = "oLQR2fqwguqaaZutVJr9OZXDpYgIcKKQV2MrOftxguThhcfzXK";
    private  final String gridURL = "@hub.lambdatest.com/wd/hub";

    public  WebDriver setupDriver(String browserName) {
        WebDriver driver=null;
        switch (browserName) {
            case "Chrome": {
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
            }
            break;
            case "Edge": {
                WebDriverManager.edgedriver().setup();
               driver=new EdgeDriver();
            }
            break;
            case "FireFox": {
               WebDriverManager.firefoxdriver().setup();
               driver=new FirefoxDriver();
            }
            break;
            case "InternetExplorer": {
               WebDriverManager.iedriver().setup();
               driver=new InternetExplorerDriver();
            }
            break;
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        return driver;
    }

    public  WebDriver setupDriver(String browserName, String browserVersion, String os) {
        WebDriver driver=null;
        HashMap<String, Object> ltOptions = new HashMap<String, Object>();
        ltOptions.put("username", username);
        ltOptions.put("accessKey", accesskey);
        ltOptions.put("visual", true);
        ltOptions.put("video", true);
        ltOptions.put("network", true);
        ltOptions.put("project", "Selenium java Certification");
        ltOptions.put("name", "Test using BrowserName: "+browserName+", Version: "+browserVersion+", "+"OS: "+os);
        ltOptions.put("console", "true");
        ltOptions.put("selenium_version", "4.8.0");
        ltOptions.put("w3c", true);

        Capabilities capabilities = null;
        switch (browserName) {
            case "Chrome": {
                ChromeOptions browserOptions = new ChromeOptions();
                browserOptions.setPlatformName(os);
                browserOptions.setBrowserVersion(browserVersion);
                browserOptions.setCapability("LT:Options", ltOptions);
                capabilities = browserOptions;
            }
            break;
            case "Edge": {
                EdgeOptions browserOptions = new EdgeOptions();
                browserOptions.setPlatformName(os);
                browserOptions.setBrowserVersion(browserVersion);
                browserOptions.setCapability("lambdaStrict",false);
                browserOptions.setCapability("LT:Options", ltOptions);
                capabilities = browserOptions;
            }
            break;
            case "FireFox": {
                FirefoxOptions browserOptions = new FirefoxOptions();
                browserOptions.setPlatformName(os);
                browserOptions.setBrowserVersion(browserVersion);
                browserOptions.setCapability("LT:Options", ltOptions);
                capabilities = browserOptions;
            }
            break;
            case "InternetExplorer": {
                InternetExplorerOptions browserOptions = new InternetExplorerOptions();
                browserOptions.setPlatformName(os);
                browserOptions.setBrowserVersion(browserVersion);
                browserOptions.setCapability("LT:Options", ltOptions);
                capabilities = browserOptions;
            }
            break;
        }

        try {
            driver = new RemoteWebDriver(new URL("https://" + username + ":" + accesskey + gridURL), capabilities);
            driver.manage().window().maximize();
//            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

        } catch (MalformedURLException e) {
            System.out.println("Invalid grid URL");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return driver;
    }

}


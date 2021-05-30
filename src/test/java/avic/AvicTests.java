package avic;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.openqa.selenium.interactions.Actions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.By.xpath;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class AvicTests {

    private WebDriver driver;

    @BeforeTest
    public void profileSetUp() {
        System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\chromedriver.exe");
    }

    @BeforeMethod
    public void testsSetUp() {

        Map<String, Object> prefs = new HashMap<String, Object>();

        prefs.put("profile.default_content_setting_values.notifications", 2);

        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", prefs);

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get("https://avic.ua/");
    }

    @Test(priority = 1)
    public void checkAdress() {
        driver.findElement(xpath("//ul[contains(@class,'header-top__list')]//a[contains(text(),'Контакты')]")).click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.findElement(xpath("//div[contains(@class,'general-col')]/p[contains(text(),'м. \"Университет\"')]"));
    }

    @Test(priority = 2)
    public void powerBanks() {
        driver.findElement(xpath("//div[@class='header-bottom__logo']")).click();
        Actions action = new Actions(driver);
        action.moveToElement(driver.findElement(xpath("//span[contains(text(),'Смартфоны и телефоны')]"))).perform();
        action.moveToElement(driver.findElement(xpath("//li[@class='parent js_sidebar-item']/a[contains(text(),'Аксессуары для смартфонов')]"))).perform();
        action.moveToElement(driver.findElement(xpath("//img[@alt='Внешние аккумуляторы (Power Bank)']"))).perform();
        driver.findElement(xpath("//a[@class='single-hover__link']/div[contains(text(),'Внешние аккумуляторы (Power Bank)')]")).click();
        List<WebElement> elementList = driver.findElements(xpath("//div[contains(@class,'bg-orange')]"));
        int actualElementsSize = elementList.size();
        assertEquals(actualElementsSize, 3);
    }

    @Test(priority = 3)
    public void checkCurvedScreenFilter() {
        driver.findElement(xpath("//img[@alt='Телевизоры и аксессуары']")).click();
        driver.findElement(xpath("//img[@alt='Телевизоры']")).click();
        driver.findElement(xpath("//div[@class='filter-area js_filter_parent']//a[contains(text(),'Samsung')]")).click();
        driver.manage().timeouts().implicitlyWait(35, TimeUnit.SECONDS);
        driver.findElement(xpath("//p[contains(text(),'Изогнутый экран')]/span[@class='symbol']")).click();
        driver.manage().timeouts().implicitlyWait(35, TimeUnit.SECONDS);
        driver.findElement(xpath("//label[@for='fltr-izognutyj-ekran-est']")).click();
        driver.findElement(xpath("//div[@class='prod-cart__img']")).click();
        driver.findElement(xpath("//td[contains(text(),'есть')]"));
        WebElement element = driver.findElement(xpath("//div[contains(@class,'hidden-mob')]//td[contains(text(),'Изогнутый экран')]/following-sibling::td"));
        String strng = element.getText();
        assertEquals("есть", strng);
    }

    @Test(priority = 4)
    public void checkWarranty() {
        driver.findElement(xpath("//div[contains(@class,'partners-section')]//img[@alt='Xiaomi']")).click();
        driver.findElement(xpath("//ul[@class='category-box__list']//a[contains(text(),'Электротранспорт')]")).click();
        driver.manage().timeouts().implicitlyWait(35, TimeUnit.SECONDS);
        driver.findElement(xpath("//div[@data-product='243976']//a[@class='js_go_product']")).click();
        assertTrue(driver.getCurrentUrl().contains("xiaomi"));
        WebElement element =  driver.findElement(xpath("//span[contains(@id,'warranty')]"));
        String strng = element.getText();
        assertTrue(strng.contains("3") || strng.contains("6") || strng.contains("12"));
    }

    @AfterMethod
    public void tearDown() {
        driver.close();//закрытие драйвера
    }
}
